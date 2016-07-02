package org.inkulumo.exceptions;

import java.io.IOException;

import javax.jms.JMSException;

@SuppressWarnings("serial")
public class IKIOException extends JMSException {

	public IKIOException(IOException e) {
		super("IOException: " + e.getMessage());
	}
}
