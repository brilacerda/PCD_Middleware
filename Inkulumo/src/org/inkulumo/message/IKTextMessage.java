package org.inkulumo.message;

import javax.jms.Destination;
import javax.jms.TextMessage;

public class IKTextMessage extends IKMessage implements TextMessage {

	private String text;

	public IKTextMessage(String text, Destination destination) {
		super(destination);
		this.text = text;
	}

	public IKTextMessage() {
		this("", null);
	}

	@Override
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String getText() {
		return text;
	}
}
