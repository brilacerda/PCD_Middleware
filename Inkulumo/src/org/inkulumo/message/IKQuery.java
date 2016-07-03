package org.inkulumo.message;

import java.io.Serializable;

public class IKQuery implements Serializable {

	private static final long serialVersionUID = -993336225634517521L;

	public enum Type {
		REGISTER_PRODUCER,
		REGISTER_CONSUMER,
		REGISTER_PRODUCER_ACK,
		REGISTER_CONSUMER_ACK,
		SUBSCRIBE,
		UNSUBSCRIBE,
		CREATE_TOPIC,
		MESSAGE,
		ACK
	}

	public String clientID;
	public Type type;

	public IKQuery(String clientID, Type type) {
		this.clientID = clientID;
		this.type = type;
	}
}
