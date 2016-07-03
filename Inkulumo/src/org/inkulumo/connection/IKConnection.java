package org.inkulumo.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.jms.ConnectionConsumer;
import javax.jms.ConnectionMetaData;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.ServerSessionPool;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;

import org.inkulumo.exceptions.IKCouldNotConnectToServerException;
import org.inkulumo.exceptions.IKRegistrationNotAckedException;
import org.inkulumo.exceptions.IKShouldNotHaveBeenThrownException;
import org.inkulumo.exceptions.IKUnimplementedException;
import org.inkulumo.message.IKNewQueryListener;
import org.inkulumo.message.IKQuery;
import org.inkulumo.net.IKRequestHandler;
import org.inkulumo.session.IKSession;

public class IKConnection implements TopicConnection, IKNewQueryListener {

	private String clientID;
	private List<Session> sessions;
	private IKRequestHandler recvRequestHandler;
	private IKRequestHandler sendRequestHandler;

	public IKConnection(InetAddress address, int port) throws IKRegistrationNotAckedException,
			IKShouldNotHaveBeenThrownException, IKCouldNotConnectToServerException {
		clientID = UUID.randomUUID().toString();
		sessions = new ArrayList<Session>();

		try {
			recvRequestHandler = new IKRequestHandler(address, port, clientID, IKRequestHandler.Type.CONSUMER, this);
			sendRequestHandler = new IKRequestHandler(address, port, clientID, IKRequestHandler.Type.PRODUCER, this);
		} catch (IOException e) {
			throw new IKCouldNotConnectToServerException();
		}
	}

	@Override
	public String getClientID() throws JMSException {
		return clientID;
	}

	@Override
	public void setClientID(String clientID) throws JMSException {
		this.clientID = clientID;
	}

	@Override
	public void onNewQuery(IKQuery query) {
		// TODO Auto-generated method stub
	}

	@Override
	public void start() throws IKCouldNotConnectToServerException {
		try {
			sendRequestHandler.init();
			recvRequestHandler.init();
			recvRequestHandler.receiveForeverAsync();
		} catch (IOException | IKRegistrationNotAckedException | IKShouldNotHaveBeenThrownException e) {
			throw new IKCouldNotConnectToServerException();
		}
	}

	@Override
	public Session createSession(boolean transacted, int acknowledgeMode) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public TopicSession createTopicSession(boolean transacted, int acknowledgeMode) throws JMSException {
		TopicSession session = new IKSession(this, acknowledgeMode);
		sessions.add(session);
		return session;
	}

	@Override
	public ConnectionMetaData getMetaData() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public ExceptionListener getExceptionListener() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void setExceptionListener(ExceptionListener listener) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void stop() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void close() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public ConnectionConsumer createConnectionConsumer(Destination destination, String messageSelector,
			ServerSessionPool sessionPool, int maxMessages) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public ConnectionConsumer createDurableConnectionConsumer(Topic topic, String subscriptionName,
			String messageSelector, ServerSessionPool sessionPool, int maxMessages) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public ConnectionConsumer createConnectionConsumer(Topic topic, String messageSelector,
			ServerSessionPool sessionPool, int maxMessages) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}
}
