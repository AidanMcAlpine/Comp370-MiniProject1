package mini_project_01;

import java.io.*;
import java.net.*;

public class HeartbeatSender implements Runnable {
	private final String monitorHost; // Server monitor ip address.
	private final int monitorPort; // Server monitor port number.
	private final int serverId; // Unique server id of the server that initializes a HeartbeatSender object.
	private boolean running = true;
	private int intervalMs = 1000; // How often heart beats are sent.
	
	/*
	 * Initialize with Monitor's information as well as Server's information. 
	 */
	public HeartbeatSender (String host, int port, int serverId) {
		this.monitorHost = host;
		this.monitorPort = port;
		this.serverId = serverId;
	}
	
	/*
	 * While running, sendHeartbeat(), than try to sleep for some intervalMs,
	 * if interrupted or stopped while sleeping, then set a flag and gracefully stop.
	 */
	@Override
	public void run() {
		while (running) {
			sendHeartbeat();
			try {
				Thread.sleep(intervalMs);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}
	}
				
	/*
	 * HeartbeatSender object connects to the Monitor server on some ip and port,
	 * then the serverId of the server that initialized the HeartbeatSender is sent
	 * to the monitor as a heart beat. 
	 * 
	 * out.println(serverId); can be changed later if more data needs to be sent to
	 * the monitor using the message serialization class.
	 * 
	 */
	public void sendHeartbeat() {
		try (Socket socket = new Socket (monitorHost, monitorPort); // Connection to Monitor.
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true) // PrintWriter object made to send the heartbeat.
			) 
		{
			out.println(serverId); // Send serverId as heart beat signature.
		} catch(IOException e) {
			System.err.println("Heartbeat failed from server " + serverId);
		}
	}
	
	public void stop() {
		running = false;
	}
	
}
