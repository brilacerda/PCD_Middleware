package org.inkulumo.session;

import java.io.Serializable;
import java.rmi.Remote;

import javax.jms.Topic;

public class IKTopic implements Topic, Serializable, Remote {

	private static final long serialVersionUID = 9143556922450949248L;

	private final String name;

	public IKTopic(String name) {
		this.name = name;
	}

	@Override
	public String getTopicName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}
