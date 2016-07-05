package org.inkulumo.server;

import java.io.IOException;
import java.net.ServerSocket;

import org.inkulumo.IKEnvironment;
import org.inkulumo.net.SmartSocket;

public class IKServer extends Thread {

	public static final String SERVER_ID = IKEnvironment.instance().get(IKEnvironment.SERVER_ID_KEY);

	private ServerSocket serverSocket;
	private IKMessagingManager messagingManager;

	public IKServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		messagingManager = new IKMessagingManager();
	}

	@Override
	public void run() {
		while (true) {
			IKConnectionHandler handler = null;
			try {
				SmartSocket socket = new SmartSocket(serverSocket.accept());
				handler = new IKConnectionHandler(socket, messagingManager);
				handler.start();
			} catch (IOException e) {
				messagingManager.removeHandler(handler);
				break;
			}
		}
	}
}
