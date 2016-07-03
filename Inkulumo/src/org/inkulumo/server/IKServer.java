package org.inkulumo.server;

import java.io.IOException;
import java.net.ServerSocket;

import org.inkulumo.net.SmartSocket;

public class IKServer extends Thread {

	private ServerSocket serverSocket;

	public IKServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}

	@Override
	public void run() {
		while (true) {
			try {
				SmartSocket socket = new SmartSocket(serverSocket.accept());
				new IKConnectionHandler(socket).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
