package org.inkulumo.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.inkulumo.message.IKQuery;
import org.inkulumo.net.SmartSocket;

public class IKConnectionHandler extends Thread {

	private SmartSocket socket;
	private IKMessagingManager messagingManager;

	public IKConnectionHandler(SmartSocket socket, IKMessagingManager messagingManager) {
		this.socket = socket;
		this.messagingManager = messagingManager;
	}

	@Override
	public void run() {
		try {
			while (true) {
				IKQuery query = (IKQuery) new ObjectInputStream(socket.getDataInputStream()).readObject();

				switch (query.type) {
				case CREATE_TOPIC: {
					messagingManager.registerTopic(query.message);
					IKQuery newQuery = new IKQuery(IKServer.SERVER_ID, IKQuery.Type.BROADCAST_TOPICS, "");
					for (String clientID : messagingManager.getSubscriberIDs())
						messagingManager.getSubscriberHandler(clientID).sendQuery(newQuery);
					break;
				}

				case MESSAGE: {
					IKQuery newQuery = new IKQuery(IKServer.SERVER_ID, IKQuery.Type.MESSAGE, query.topic,
							query.message);
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
					sendQuery(new IKQuery(query.clientID, IKQuery.Type.BROADCAST_TOPICS, ""));
					break;

				case SUBSCRIBE:
					messagingManager.subscribe(query.clientID, query.topic);
					break;

				case UNSUBSCRIBE:
					messagingManager.unsubscribe(query.clientID, query.topic);
					break;

				default:
					break;
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void sendQuery(IKQuery query) {
		try {
			new ObjectOutputStream(socket.getDataOutputStream()).writeObject(query);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
