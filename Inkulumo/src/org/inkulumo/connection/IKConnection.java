package org.inkulumo.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.jms.ConnectionConsumer;
import javax.jms.ConnectionMetaData;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.ServerSessionPool;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;

import org.inkulumo.IKEnvironment;
import org.inkulumo.exceptions.IKCouldNotConnectToServerException;
import org.inkulumo.exceptions.IKRegistrationNotAckedException;
import org.inkulumo.exceptions.IKShouldNotHaveBeenThrownException;
import org.inkulumo.exceptions.IKUnimplementedException;
import org.inkulumo.message.IKMessage;
import org.inkulumo.message.IKQuery;
import org.inkulumo.message.IKTextMessage;
import org.inkulumo.net.IKRequestHandler;
import org.inkulumo.session.IKSession;
import org.inkulumo.session.IKTopic;

public class IKConnection implements TopicConnection, IKConnectionActionListener {

	private String clientID;
	private List<Session> sessions;
	private IKRequestHandler recvRequestHandler;
	private IKRequestHandler sendRequestHandler;
	private HashMap<String, List<MessageListener>> topicListeners;

	public IKConnection(InetAddress address, int port) throws IKRegistrationNotAckedException,
			IKShouldNotHaveBeenThrownException, IKCouldNotConnectToServerException {
		clientID = UUID.randomUUID().toString();
		sessions = new ArrayList<Session>();
		topicListeners = new HashMap<String, List<MessageListener>>();
		topicListeners.put(IKEnvironment.instance().get(IKEnvironment.ROOMS_TOPIC_KEY), new ArrayList<MessageListener>());

		try {
			recvRequestHandler = new IKRequestHandler(address, port, clientID, IKRequestHandler.Type.SUBSCRIBER, this);
			sendRequestHandler = new IKRequestHandler(address, port, clientID, IKRequestHandler.Type.PUBLISHER, this);
		} catch (IOException e) {
			throw new IKCouldNotConnectToServerException();
		}

		start();
	}

	@Override
	public String getClientID() {
		return clientID;
	}

	@Override
	public void setClientID(String clientID) throws JMSException {
		this.clientID = clientID;
	}

	@Override
	public void onNewQuery(IKQuery query) {
		if (query.type == IKQuery.Type.MESSAGE || query.type == IKQuery.Type.BROADCAST_TOPICS)
			for (MessageListener listener : topicListeners.get(query.topic.toString()))
				listener.onMessage(new IKTextMessage(query.message, query.topic));
			
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
	public TopicSession createTopicSession(boolean transacted, int acknowledgeMode) throws JMSException {
		TopicSession session = new IKSession(this, acknowledgeMode);
		sessions.add(session);
		topicListeners.get(IKEnvironment.instance().get(IKEnvironment.ROOMS_TOPIC_KEY)).add((IKSession) session);
		return session;
	}

	@Override
	public void onSubscribeRequest(Topic topic, MessageListener listener) throws IKCouldNotConnectToServerException {
		try {
			sendRequestHandler.send(new IKQuery(getClientID(), IKQuery.Type.SUBSCRIBE, topic));

			List<MessageListener> listeners = topicListeners.get(topic.toString());
			if (listeners == null)
				listeners = new ArrayList<MessageListener>();
			listeners.add(listener);
			topicListeners.put(topic.toString(), listeners);
		} catch (IOException e) {
			throw new IKCouldNotConnectToServerException();
		}
	}

	@Override
	public void onPublishRequest(IKMessage message) throws IKCouldNotConnectToServerException {
		try {
			sendRequestHandler.send(new IKQuery(getClientID(), IKQuery.Type.MESSAGE,
					(Topic) message.getJMSDestination(), ((IKTextMessage) message).getText()));
		} catch (IOException e) {
			throw new IKCouldNotConnectToServerException();
		}
	}

	@Override
	public void onNewTopicRequest(String topicName) throws IKCouldNotConnectToServerException {
		try {
			sendRequestHandler.send(new IKQuery(getClientID(), IKQuery.Type.CREATE_TOPIC, new IKTopic(topicName), topicName));
		} catch (IOException e) {
			throw new IKCouldNotConnectToServerException();
		}
	}

	@Override
	public Session createSession(boolean transacted, int acknowledgeMode) throws IKUnimplementedException {
		throw new IKUnimplementedException();
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
