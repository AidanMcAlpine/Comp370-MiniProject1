package mini_project_01;

import java.io.*;
import java.net.*;

public class HeartbeatSender implements Runnable {
	private final String monitorHost;
	private final int monitorPort;
	private final int serverId;
	private boolean running = true;
	private int intervalMs = 1000;
	
	public HeartbeatSender (String host, int port, int serverId) {
		this.monitorHost = host;
		this.monitorPort = port;
		this.serverId = serverId;
	}
	
	@Override
	public void run() {
		while (running) {
			sendHeartbeat();
			try {
				Thread.sleep(intervalMs);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
	
	public void sendHeartbeat() {
		try (Socket socket = new Socket (monitorHost, monitorPort);
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true)){
			out.println(serverId);
		} catch(IOException e) {
			System.err.println("Heartbeat failed from server " + serverId);
		}
	}
	
	public void stop() {
		running = false;
	}
	
}
