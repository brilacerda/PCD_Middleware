package org.inkulumo.message;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import org.inkulumo.exceptions.IKMustCallSubClassException;
import org.inkulumo.exceptions.IKUnimplementedException;

public class IKMessage implements Message {
	
	private String ID;
	private Long timestamp;
	private HashMap<String, Object> properties;

	@Override
	public String getJMSMessageID() throws JMSException {
		return ID;
	}

	@Override
	public void setJMSMessageID(String ID) throws JMSException {
		this.ID = ID;
	}

	@Override
	public long getJMSTimestamp() throws JMSException {
		return timestamp;
	}

	@Override
	public void setJMSTimestamp(long timestamp) throws JMSException {
		this.timestamp = timestamp;
	}

	@Override
	public byte[] getJMSCorrelationIDAsBytes() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void setJMSCorrelationIDAsBytes(byte[] correlationID) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void setJMSCorrelationID(String correlationID) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public String getJMSCorrelationID() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public Destination getJMSReplyTo() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void setJMSReplyTo(Destination replyTo) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public Destination getJMSDestination() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void setJMSDestination(Destination destination) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public int getJMSDeliveryMode() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void setJMSDeliveryMode(int deliveryMode) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public boolean getJMSRedelivered() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void setJMSRedelivered(boolean redelivered) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public String getJMSType() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void setJMSType(String type) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public long getJMSExpiration() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void setJMSExpiration(long expiration) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public int getJMSPriority() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void setJMSPriority(int priority) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void clearProperties() throws JMSException {
		properties.clear();
	}

	@Override
	public boolean propertyExists(String name) throws JMSException {
		return properties.containsKey(name);
	}
	
	private <T> T getTypedProperty(String name, Class<T> clazz) throws JMSException {
		Object prop = properties.get(name);
		if (prop != null && clazz.getClass().isAssignableFrom(prop.getClass()))
			return clazz.cast(prop);
		
		throw new JMSException("No such " + clazz.getName() + " property '" + name + "' in message " + ID);
	}

	@Override
	public boolean getBooleanProperty(String name) throws JMSException {
		return getTypedProperty(name, Boolean.class);
	}

	@Override
	public byte getByteProperty(String name) throws JMSException {
		return getTypedProperty(name, Byte.class);
	}

	@Override
	public short getShortProperty(String name) throws JMSException {
		return getTypedProperty(name, Short.class);
	}

	@Override
	public int getIntProperty(String name) throws JMSException {
		return getTypedProperty(name, Integer.class);
	}

	@Override
	public long getLongProperty(String name) throws JMSException {
		return getTypedProperty(name, Long.class);
	}

	@Override
	public float getFloatProperty(String name) throws JMSException {
		return getTypedProperty(name, Float.class);
	}

	@Override
	public double getDoubleProperty(String name) throws JMSException {
		return getTypedProperty(name, Double.class);
	}

	@Override
	public String getStringProperty(String name) throws JMSException {
		return getTypedProperty(name, String.class);
	}

	@Override
	public Object getObjectProperty(String name) throws JMSException {
		return getTypedProperty(name, Object.class);
	}

	@Override
	public Enumeration<String> getPropertyNames() throws JMSException {
		return Collections.enumeration(properties.keySet());
	}

	@Override
	public void setBooleanProperty(String name, boolean value) throws JMSException {
		properties.put(name, value);
	}

	@Override
	public void setByteProperty(String name, byte value) throws JMSException {
		properties.put(name, value);
	}

	@Override
	public void setShortProperty(String name, short value) throws JMSException {
		properties.put(name, value);
	}

	@Override
	public void setIntProperty(String name, int value) throws JMSException {
		properties.put(name, value);
	}

	@Override
	public void setLongProperty(String name, long value) throws JMSException {
		properties.put(name, value);
	}

	@Override
	public void setFloatProperty(String name, float value) throws JMSException {
		properties.put(name, value);
	}

	@Override
	public void setDoubleProperty(String name, double value) throws JMSException {
		properties.put(name, value);
	}

	@Override
	public void setStringProperty(String name, String value) throws JMSException {
		properties.put(name, value);
	}

	@Override
	public void setObjectProperty(String name, Object value) throws JMSException {
		properties.put(name, value);
	}

	@Override
	public void acknowledge() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void clearBody() throws IKMustCallSubClassException {
		throw new IKMustCallSubClassException();
	}
}
