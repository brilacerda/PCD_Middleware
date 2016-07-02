package org.inkulumo.exceptions;

import javax.jms.JMSException;

@SuppressWarnings("serial")
public class IKUnimplementedException extends JMSException {

	public IKUnimplementedException() {
		super("Not implemented");
	}
}