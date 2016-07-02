package org.inkulumo.message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.jms.BytesMessage;
import javax.jms.JMSException;

import org.inkulumo.exceptions.IKIOException;

public class IKBytesMessage extends IKMessage implements BytesMessage {

	private byte[] body = null;

	private DataOutputStream dos = null;
	private DataInputStream dis = null;

	private DataInputStream getInputStream() {
		if (dis == null) {
			ByteArrayInputStream bais = new ByteArrayInputStream(body);
			dis = new DataInputStream(bais);
		}

		return dis;
	}

	private DataOutputStream getOutputStream() {
		if (dos == null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			dos = new DataOutputStream(baos);
		}

		return dos;
	}

	@Override
	public long getBodyLength() throws JMSException {
		return body != null ? body.length : 0;
	}

	@Override
	public boolean readBoolean() throws IKIOException {
		try {
			return getInputStream().readBoolean();
		} catch (IOException e) {
			throw new IKIOException(e);
		}
	}

	@Override
	public byte readByte() throws IKIOException {
		try {
			return getInputStream().readByte();
		} catch (IOException e) {
			throw new IKIOException(e);
		}
	}

	@Override
	public int readUnsignedByte() throws IKIOException {
		try {
			return getInputStream().readUnsignedByte();
		} catch (IOException e) {
			throw new IKIOException(e);
		}
	}

	@Override
	public short readShort() throws IKIOException {
		try {
			return getInputStream().readShort();
		} catch (IOException e) {
			throw new IKIOException(e);
		}
	}

	@Override
	public int readUnsignedShort() throws IKIOException {
		try {
			return getInputStream().readUnsignedShort();
		} catch (IOException e) {
			throw new IKIOException(e);
		}
	}

	@Override
	public char readChar() throws IKIOException {
		try {
			return getInputStream().readChar();
		} catch (IOException e) {
			throw new IKIOException(e);
		}
	}

	@Override
	public int readInt() throws IKIOException {
		try {
			return getInputStream().readInt();
		} catch (IOException e) {
			throw new IKIOException(e);
		}
	}

	@Override
	public long readLong() throws IKIOException {
		try {
			return getInputStream().readLong();
		} catch (IOException e) {
			throw new IKIOException(e);
		}
	}

	@Override
	public float readFloat() throws IKIOException {
		try {
			return getInputStream().readFloat();
		} catch (IOException e) {
			throw new IKIOException(e);
		}
	}

	@Override
	public double readDouble() throws IKIOException {
		try {
			return getInputStream().readDouble();
		} catch (IOException e) {
			throw new IKIOException(e);
		}
	}

	@Override
	public String readUTF() throws IKIOException {
		try {
			return getInputStream().readUTF();
		} catch (IOException e) {
			throw new IKIOException(e);
		}
	}

	@Override
	public int readBytes(byte[] value) throws IKIOException {
		return readBytes(value, value.length);
	}

	@Override
	public int readBytes(byte[] value, int length) throws IKIOException {
		try {
			return getInputStream().read(value, 0, length);
		} catch (IOException e) {
			throw new IKIOException(e);
		}
	}

	@Override
	public void writeBoolean(boolean value) throws IKIOException {
		try {
			getOutputStream().writeBoolean(value);
		} catch (IOException e) {
			throw new IKIOException(e);
		}
	}

	@Override
	public void writeByte(byte value) throws IKIOException {
		try {
			getOutputStream().writeByte(value);
		} catch (IOException e) {
			throw new IKIOException(e);
		}
	}

	@Override
	public void writeShort(short value) throws IKIOException {
		try {
			getOutputStream().writeShort(value);
		} catch (IOException e) {
			throw new IKIOException(e);
		}
	}

	@Override
	public void writeChar(char value) throws IKIOException {
		try {
			getOutputStream().writeChar(value);
		} catch (IOException e) {
			throw new IKIOException(e);
		}
	}

	@Override
	public void writeInt(int value) throws IKIOException {
		try {
			getOutputStream().writeInt(value);
		} catch (IOException e) {
			throw new IKIOException(e);
		}
	}

	@Override
	public void writeLong(long value) throws IKIOException {
		try {
			getOutputStream().writeLong(value);
		} catch (IOException e) {
			throw new IKIOException(e);
		}
	}

	@Override
	public void writeFloat(float value) throws IKIOException {
		try {
			getOutputStream().writeFloat(value);
		} catch (IOException e) {
			throw new IKIOException(e);
		}
	}

	@Override
	public void writeDouble(double value) throws IKIOException {
		try {
			getOutputStream().writeDouble(value);
		} catch (IOException e) {
			throw new IKIOException(e);
		}
	}

	@Override
	public void writeUTF(String value) throws IKIOException {
		try {
			getOutputStream().writeUTF(value);
		} catch (IOException e) {
			throw new IKIOException(e);
		}
	}

	@Override
	public void writeBytes(byte[] value) throws IKIOException {
		try {
			getOutputStream().write(value);
		} catch (IOException e) {
			throw new IKIOException(e);
		}
	}

	@Override
	public void writeBytes(byte[] value, int offset, int length) throws IKIOException {
		try {
			getOutputStream().write(value, offset, length);
		} catch (IOException e) {
			throw new IKIOException(e);
		}
	}

	@Override
	public void writeObject(Object value) throws JMSException {
		if (value instanceof Boolean) {
			writeBoolean(((Boolean) value).booleanValue());

		} else if (value instanceof Byte) {
			writeByte(((Byte) value).byteValue());

		} else if (value instanceof Short) {
			writeShort(((Short) value).shortValue());

		} else if (value instanceof Character) {
			writeChar(((Character) value).charValue());

		} else if (value instanceof Integer) {
			writeInt(((Integer) value).intValue());

		} else if (value instanceof Long) {
			writeLong(((Long) value).longValue());

		} else if (value instanceof Float) {
			writeFloat(((Float) value).floatValue());

		} else if (value instanceof Double) {
			writeDouble(((Double) value).doubleValue());

		} else if (value instanceof String) {
			writeUTF((String) value);

		} else if (value instanceof byte[]) {
			writeBytes((byte[]) value);

		} else
			throw new JMSException("Cannot write object of type " + value.getClass().getName());
	}

	@Override
	public void reset() throws IKIOException {
		try {
			dis.reset();
		} catch (IOException e) {
			throw new IKIOException(e);
		}
	}
}
