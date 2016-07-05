package org.inkulumo.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.inkulumo.IKEnvironment;
import org.inkulumo.message.IKQuery;
import org.inkulumo.net.SmartSocket;
import org.inkulumo.session.IKTopic;
import org.inkulumo.util.StringJoiner;

public class IKConnectionHandler extends Thread {

	private SmartSocket socket;
	private IKMessagingManager messagingManager;
	private final String roomsTopicName;

	public IKConnectionHandler(SmartSocket socket, IKMessagingManager messagingManager) {
		this.socket = socket;
		this.messagingManager = messagingManager;
		roomsTopicName = IKEnvironment.instance().get(IKEnvironment.ROOMS_TOPIC_KEY);
	}

	@Override
	public void run() {
		while (true) {
			try {
				IKQuery query = (IKQuery) new ObjectInputStream(socket.getDataInputStream()).readObject();

				switch (query.type) {
				case CREATE_TOPIC: {
					messagingManager.registerTopic(query.message);
					String roomsMessage = StringJoiner.join(messagingManager.getTopics(), "|");
					if (!roomsMessage.isEmpty()) {
						IKQuery newQuery = new IKQuery(IKServer.SERVER_ID, IKQuery.Type.BROADCAST_TOPICS,
								new IKTopic(roomsTopicName), roomsMessage);
						for (String clientID : messagingManager.getSubscriberIDs())
							messagingManager.getSubscriberHandler(clientID).sendQuery(newQuery);
					}
					break;
				}

				case MESSAGE: {
					IKQuery newQuery = new IKQuery(query.clientID, IKQuery.Type.MESSAGE, query.topic, query.message);
					for (String clientID : messagingManager.getTopicSubscribers(query.topic))
						messagingManager.getSubscriberHandler(clientID).sendQuery(newQuery);
					break;
				}

				case REGISTER_PUBLISHER:
					messagingManager.registerPublisher(query.clientID, this);
					sendQuery(new IKQuery(query.clientID, IKQuery.Type.REGISTER_PUBLISHER_ACK));
					break;

				case REGISTER_SUBSCRIBER:
					messagingManager.registerSubscriber(query.clientID, this);
					sendQuery(new IKQuery(query.clientID, IKQuery.Type.REGISTER_SUBSCRIBER_ACK));
					break;

				case SUBSCRIBE:
					messagingManager.subscribe(query.clientID, query.topic);

					if (query.topic.toString().equals(roomsTopicName)) {
						// encode and send rooms
						String roomsMessage = StringJoiner.join(messagingManager.getTopics(), "|");
						if (!roomsMessage.isEmpty())
							sendQuery(new IKQuery(IKServer.SERVER_ID, IKQuery.Type.BROADCAST_TOPICS,
									new IKTopic(roomsTopicName), roomsMessage));
					}
					break;

				case UNSUBSCRIBE:
					messagingManager.unsubscribe(query.clientID, query.topic);
					break;

				default:
					break;
				}
			} catch (IOException | ClassNotFoundException e) {
				socket.close();
				messagingManager.removeHandler(this);
				break;
			}
		}
	}

	public void sendQuery(IKQuery query) throws IOException {
		new ObjectOutputStream(socket.getDataOutputStream()).writeObject(query);
	}
}
