package org.inkulumo.session;

import org.inkulumo.exceptions.IKCouldNotConnectToServerException;
import org.inkulumo.message.IKMessage;

public interface IKMessageSender {
	public void send(IKMessage message) throws IKCouldNotConnectToServerException;
}
