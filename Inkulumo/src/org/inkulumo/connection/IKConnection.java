package org.inkulumo.connection;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

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

import org.inkulumo.exceptions.IKUnimplementedException;
import org.inkulumo.session.IKSession;

public class IKConnection implements TopicConnection {

	private InetAddress address;
	private int port;
	private List<Session> sessions;
	private String clientID;

	public IKConnection(InetAddress address, int port) {
		this.address = address;
		this.port = port;
		sessions = new ArrayList<Session>();
	}

	@Override
	public Session createSession(boolean transacted, int acknowledgeMode) throws IKUnimplementedException {
		throw new IKUnimplementedException();
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
	public void start() throws JMSException {
		// TODO Auto-generated method stub
	}

	@Override
	public void stop() throws JMSException {
		// TODO Auto-generated method stub
	}

	@Override
	public void close() throws JMSException {
		// TODO Auto-generated method stub
	}

	@Override
	public TopicSession createTopicSession(boolean transacted, int acknowledgeMode) throws JMSException {
		TopicSession session = new IKSession(this, acknowledgeMode);
		sessions.add(session);
		return session;
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
