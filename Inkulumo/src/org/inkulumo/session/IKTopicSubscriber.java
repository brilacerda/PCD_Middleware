package org.inkulumo.session;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import org.inkulumo.exceptions.IKShouldNotHaveBeenThrownException;
import org.inkulumo.exceptions.IKUnimplementedException;

public class IKTopicSubscriber implements TopicSubscriber, MessageListener {
	
	private final Topic topic;
	private BlockingQueue<Message> messageQueue;
	
	public IKTopicSubscriber(Topic topic) {
		this.topic = topic;
		messageQueue = new LinkedBlockingQueue<>();
	}

	@Override
	public MessageListener getMessageListener() {
		return this;
	}

	@Override
	public Message receive() throws IKShouldNotHaveBeenThrownException {
		try {
			return messageQueue.take();
		} catch (InterruptedException e) {
			throw new IKShouldNotHaveBeenThrownException();
		}
	}

	@Override
	public Message receiveNoWait() throws JMSException {
		return messageQueue.poll();
	}

	@Override
	public void onMessage(Message message) {
		messageQueue.add(message);
	}

	@Override
	public Topic getTopic() throws JMSException {
		return topic;
	}

	@Override
	public boolean getNoLocal() throws JMSException {
		return false;
	}

	@Override
	public void close() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public Message receive(long timeout) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public String getMessageSelector() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void setMessageListener(MessageListener listener) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}
}
