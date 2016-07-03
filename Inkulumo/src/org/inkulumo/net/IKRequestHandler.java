package org.inkulumo.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.inkulumo.exceptions.IKRegistrationNotAckedException;
import org.inkulumo.exceptions.IKShouldNotHaveBeenThrownException;
import org.inkulumo.message.IKNewQueryListener;
import org.inkulumo.message.IKQuery;

public class IKRequestHandler {

	public enum Type {
		PRODUCER, CONSUMER
	}

	private InetAddress address;
	private int port;
	private Type type;
	private String clientID;
	private IKNewQueryListener listener;
	private SmartSocket socket = null;

	public IKRequestHandler(InetAddress address, int port, String clientID, Type type, IKNewQueryListener listener)
			throws UnknownHostException, IOException, IKRegistrationNotAckedException,
			IKShouldNotHaveBeenThrownException {
		this.address = address;
		this.port = port;
		this.type = type;
		this.clientID = clientID;
		this.listener = listener;
	}

	public void init() throws IKRegistrationNotAckedException, IKShouldNotHaveBeenThrownException, IOException {
		socket = new SmartSocket(address.getHostAddress(), port);
		registerAndWaitAck(clientID);
	}

	private void registerAndWaitAck(String clientID)
			throws IKRegistrationNotAckedException, IKShouldNotHaveBeenThrownException, IOException {
		IKQuery.Type queryType = type == Type.PRODUCER ? IKQuery.Type.REGISTER_PRODUCER
				: IKQuery.Type.REGISTER_CONSUMER;
		IKQuery query = new IKQuery(clientID, queryType);
		send(query);

		IKQuery ack = (IKQuery) receive();
		if ((type == Type.PRODUCER && ack.type != IKQuery.Type.REGISTER_PRODUCER_ACK)
				|| type == Type.CONSUMER && ack.type != IKQuery.Type.REGISTER_CONSUMER_ACK)
			throw new IKRegistrationNotAckedException();
	}

	public void send(Object message) throws IOException {
		new ObjectOutputStream(socket.getDataOutputStream()).writeObject(message);
	}

	public Object receive() throws IOException, IKShouldNotHaveBeenThrownException {
		try {
			return new ObjectInputStream(socket.getDataInputStream()).readObject();
		} catch (ClassNotFoundException e) {
			throw new IKShouldNotHaveBeenThrownException();
		}
	}

	public Object sendAndReceive(Object message) throws IOException, IKShouldNotHaveBeenThrownException {
		send(message);
		return receive();
	}

	public void receiveForeverAsync() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						listener.onNewQuery((IKQuery) receive());
					} catch (IKShouldNotHaveBeenThrownException | IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
}
