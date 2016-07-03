package org.inkulumo.message;

import java.io.Serializable;

public class IKQuery implements Serializable {

	private static final long serialVersionUID = -993336225634517521L;

	public enum Type {
		REGISTER_PUBLISHER,
		REGISTER_SUBSCRIBER,
		REGISTER_PUBLISHER_ACK,
		REGISTER_SUBSCRIBER_ACK,
		SUBSCRIBE,
		UNSUBSCRIBE,
		CREATE_TOPIC,
		MESSAGE,
		ACK
	}

	public final String clientID;
	public final Type type;
	public final String message;

	public IKQuery(String clientID, Type type) {
		this(clientID, type, "");
	}

	public IKQuery(String clientID, Type type, String message) {
		this.clientID = clientID;
		this.type = type;
		this.message = message;
	}
}
