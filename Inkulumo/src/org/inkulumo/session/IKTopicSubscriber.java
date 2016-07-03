package org.inkulumo.session;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import org.inkulumo.exceptions.IKUnimplementedException;

public class IKTopicSubscriber implements TopicSubscriber, MessageListener {
	
	private Topic topic;
	
	public IKTopicSubscriber(Topic topic) {
		this.topic = topic;
	}

	@Override
	public Message receive() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message receive(long timeout) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message receiveNoWait() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() throws JMSException {
		// TODO Auto-generated method stub
	}

	@Override
	public Topic getTopic() throws JMSException {
		return topic;
	}

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean getNoLocal() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public String getMessageSelector() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public MessageListener getMessageListener() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void setMessageListener(MessageListener listener) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}
}
