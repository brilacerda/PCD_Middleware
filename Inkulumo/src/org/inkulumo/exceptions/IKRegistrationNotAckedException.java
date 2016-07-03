package org.inkulumo.exceptions;

import javax.jms.JMSException;

@SuppressWarnings("serial")
public class IKRegistrationNotAckedException extends JMSException {
	
	public IKRegistrationNotAckedException() {
		super("Registration not acked");
	}
}
