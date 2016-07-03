package org.inkulumo.session;

import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.Topic;
import javax.jms.TopicPublisher;

import org.inkulumo.exceptions.IKCouldNotConnectToServerException;
import org.inkulumo.exceptions.IKUnimplementedException;
import org.inkulumo.message.IKMessage;

public class IKTopicPublisher implements TopicPublisher {

	private final Topic topic;
	private IKMessageSender sender;

	public IKTopicPublisher(Topic topic, IKMessageSender sender) {
		this.topic = topic;
		this.sender = sender;
	}

	@Override
	public Destination getDestination() {
		return topic;
	}

	@Override
	public void send(Message message) throws IKCouldNotConnectToServerException {
		sender.send((IKMessage) message);
	}

	@Override
	public Topic getTopic() {
		return topic;
	}

	@Override
	public void publish(Message message) throws IKCouldNotConnectToServerException {
		send(message);
	}

	@Override
	public boolean getDisableMessageID() {
		return true;
	}

	@Override
	public boolean getDisableMessageTimestamp() {
		return false;
	}

	@Override
	public void setDisableMessageID(boolean value) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void setDisableMessageTimestamp(boolean value) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void setDeliveryMode(int deliveryMode) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public int getDeliveryMode() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void setPriority(int defaultPriority) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public int getPriority() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void setTimeToLive(long timeToLive) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public long getTimeToLive() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void close() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void send(Message message, int deliveryMode, int priority, long timeToLive) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void send(Destination destination, Message message) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void send(Destination destination, Message message, int deliveryMode, int priority, long timeToLive)
			throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void publish(Message message, int deliveryMode, int priority, long timeToLive)
			throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void publish(Topic topic, Message message) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void publish(Topic topic, Message message, int deliveryMode, int priority, long timeToLive)
			throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

}
