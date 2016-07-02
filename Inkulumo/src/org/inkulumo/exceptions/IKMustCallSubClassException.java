package org.inkulumo.exceptions;

import javax.jms.JMSException;

@SuppressWarnings("serial")
public class IKMustCallSubClassException extends JMSException {
	public IKMustCallSubClassException() {
		super("Must call subclass instance");
	}
}
