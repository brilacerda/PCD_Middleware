package org.inkulumo.connection;

import org.inkulumo.exceptions.IKCouldNotConnectToServerException;
import org.inkulumo.message.IKQuery;

public interface IKConnectionActionListener {
	public void onNewQuery(IKQuery query);
	public void onSubscribeRequest(String topicName) throws IKCouldNotConnectToServerException;
	public void onNewTopicRequest(String topicName) throws IKCouldNotConnectToServerException;
}
