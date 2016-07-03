package org.inkulumo.connection;

import javax.jms.MessageListener;
import javax.jms.Topic;

import org.inkulumo.exceptions.IKCouldNotConnectToServerException;
import org.inkulumo.message.IKMessage;
import org.inkulumo.message.IKQuery;

public interface IKConnectionActionListener {
	public void onNewQuery(IKQuery query);
	public void onSubscribeRequest(Topic topic, MessageListener listener) throws IKCouldNotConnectToServerException;
	public void onPublishRequest(IKMessage message) throws IKCouldNotConnectToServerException;
	public void onNewTopicRequest(String topicName) throws IKCouldNotConnectToServerException;
}
