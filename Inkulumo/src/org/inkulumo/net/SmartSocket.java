package org.inkulumo.net;

import java.util.Timer;
import java.util.TimerTask;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * SmarSocket é responsável por encapsular o socket TCP juntamente com a lógica
 * de reconexão. O socket é fechado se ultrapassar uma determinada quantidade de
 * tempo sem ser usado. Se for usado depois de ser fechado, a conexão é
 * reestabelecida.
 */

public class SmartSocket {
	private String host = null;
	private int port;
	private Socket socket;
	private final boolean[] isClosing = { false };
	private final int TIMER_MILISECONDS = 1000 * 20;
	private DataOutputStream dos = null;
	private DataInputStream dis = null;

	public SmartSocket(String host, int port) throws UnknownHostException, IOException {
		this.host = host;
		this.port = port;
	}

	public SmartSocket(Socket socket) throws UnknownHostException, IOException {
		this.host = socket.getInetAddress().getHostAddress();
		this.port = socket.getPort();
		this.socket = socket;
		setStreams();
	}

	/**
	 * Retorna um socket TCP, pronto para ação
	 */
	private Socket getSocket() throws UnknownHostException, IOException {
		if (socket == null || socket.isClosed()) {
			socket = new Socket(host, port);
			setStreams();
		}

		isClosing[0] = false;
		return socket;
	}

	/**
	 * Não fecha o socket intantaneamente, mas cria um timer. Se o socket for
	 * usado novamente antes que o timer termine, o socket não é fechado
	 */
	public void close() {
		isClosing[0] = true;
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					if (isClosing[0]) {
						dis.close();
						dos.close();
						socket.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, TIMER_MILISECONDS);
	}

	public DataInputStream getDataInputStream() throws IOException {
		setStreams();
		return dis;
	}

	public DataOutputStream getDataOutputStream() throws IOException {
		setStreams();
		return dos;
	}

	/**
	 * Obtém os stream de saída e entrada do socket, se esses ainda não tiverem
	 * sido criados ou se o socket foi recriado
	 */
	private void setStreams() throws IOException {
		Socket oldSocket = socket;
		if (getSocket() != null) {
			if (dis == null || socket != oldSocket)
				dis = new DataInputStream(socket.getInputStream());
			if (dos == null || socket != oldSocket)
				dos = new DataOutputStream(socket.getOutputStream());
		}
	}
}