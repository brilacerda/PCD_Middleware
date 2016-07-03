package org.inkulumo.exceptions;

import javax.jms.JMSException;

@SuppressWarnings("serial")
public class IKShouldNotHaveBeenThrownException extends JMSException {

	public IKShouldNotHaveBeenThrownException() {
		super("Should not have been thrown!");
	}
}
