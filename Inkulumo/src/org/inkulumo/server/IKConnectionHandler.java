package org.inkulumo.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.inkulumo.message.IKQuery;
import org.inkulumo.net.SmartSocket;

public class IKConnectionHandler extends Thread {

	private SmartSocket socket;

	public IKConnectionHandler(SmartSocket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			while (true) {
				IKQuery query = (IKQuery) new ObjectInputStream(socket.getDataInputStream()).readObject();
				System.out.println("LOOOL " + query.type.toString());

				switch (query.type) {
				case CREATE_TOPIC:
					break;

				case MESSAGE:
					break;

				case REGISTER_PUBLISHER: {
					sendQuery(new IKQuery(query.clientID, IKQuery.Type.REGISTER_PUBLISHER_ACK));
					break;
				}

				case REGISTER_SUBSCRIBER: {
					sendQuery(new IKQuery(query.clientID, IKQuery.Type.REGISTER_SUBSCRIBER_ACK));
					break;
				}
					
				case SUBSCRIBE:
					break;
					
				case UNSUBSCRIBE:
					break;
					
				default:
					break;
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void sendQuery(IKQuery query) {
		try {
			new ObjectOutputStream(socket.getDataOutputStream()).writeObject(query);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
