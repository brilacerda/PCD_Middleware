package org.inkulumo.connection;

import java.io.Serializable;
import java.net.InetAddress;
import java.rmi.Remote;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;

import org.inkulumo.exceptions.IKUnimplementedException;

public class IKConnectionFactory implements TopicConnectionFactory, Remote, Serializable {
		
	private static final long serialVersionUID = 8302377779427538105L;
	
	private InetAddress address;
	private int port;
	
	public IKConnectionFactory(InetAddress address, int port) {
		this.address = address;
		this.port = port;
	}

	@Override
	public TopicConnection createTopicConnection() throws JMSException {
		return new IKConnection(address, port);
	}

	@Override
	public TopicConnection createTopicConnection(String userName, String password) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public Connection createConnection() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public Connection createConnection(String userName, String password) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}
}
