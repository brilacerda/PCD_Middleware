package org.inkulumo.session;

import java.io.Serializable;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.StreamMessage;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import org.inkulumo.exceptions.IKUnimplementedException;
import org.inkulumo.message.IKBytesMessage;
import org.inkulumo.message.IKMessage;

public class IKSession implements TopicSession {
	
	private Connection connection;
	private MessageListener messageListener;
	private int acknowledgeMode;
	
	public IKSession(Connection connection, int acknowledgeMode) {
		this.connection = connection;
		this.acknowledgeMode = acknowledgeMode;
	}

	@Override
	public BytesMessage createBytesMessage() throws JMSException {
		return (BytesMessage) new IKBytesMessage();
	}

	@Override
	public MapMessage createMapMessage() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public Message createMessage() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public ObjectMessage createObjectMessage() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public ObjectMessage createObjectMessage(Serializable object) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public StreamMessage createStreamMessage() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public TextMessage createTextMessage() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public TextMessage createTextMessage(String text) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public boolean getTransacted() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public int getAcknowledgeMode() throws JMSException {
		return acknowledgeMode;
	}

	@Override
	public void commit() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void rollback() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void close() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void recover() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public MessageListener getMessageListener() throws JMSException {
		return messageListener;
	}

	@Override
	public void setMessageListener(MessageListener messageListener) throws JMSException {
		this.messageListener = messageListener; 
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	}

	@Override
	public MessageProducer createProducer(Destination destination) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public MessageConsumer createConsumer(Destination destination) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public MessageConsumer createConsumer(Destination destination, String messageSelector)
			throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public MessageConsumer createConsumer(Destination destination, String messageSelector, boolean NoLocal)
			throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public Queue createQueue(String queueName) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public Topic createTopic(String topicName) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TopicSubscriber createDurableSubscriber(Topic topic, String name) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public TopicSubscriber createDurableSubscriber(Topic topic, String name, String messageSelector, boolean noLocal)
			throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public QueueBrowser createBrowser(Queue queue) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public QueueBrowser createBrowser(Queue queue, String messageSelector) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public TemporaryQueue createTemporaryQueue() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public TemporaryTopic createTemporaryTopic() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void unsubscribe(String name) throws JMSException {
		// TODO Auto-generated method stub
	}

	@Override
	public TopicSubscriber createSubscriber(Topic topic) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TopicSubscriber createSubscriber(Topic topic, String messageSelector, boolean noLocal) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TopicPublisher createPublisher(Topic topic) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}
}
