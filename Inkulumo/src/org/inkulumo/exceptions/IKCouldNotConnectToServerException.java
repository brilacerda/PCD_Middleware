package org.inkulumo.exceptions;

import javax.jms.JMSException;

@SuppressWarnings("serial")
public class IKCouldNotConnectToServerException extends JMSException {

	public IKCouldNotConnectToServerException() {
		super("Could not connect to server");
	}
}
