package mini_project_01;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class HeartbeatSender implements Runnable {
	private final String monitorHost; // Server monitor ip address.
	private final int monitorPort; // Server monitor port number.
	private final int serverId; // Unique server id of the server that initializes a HeartbeatSender object.
	private boolean running = true;
	private int intervalMs = 5000; // How often heart beats are sent.
	private Socket socket;
	private DataOutputStream out;
	private JsonMessageSerializer serializer;
	
	/*
	 * Initialize with Monitor's information as well as Server's information. 
	 */
	public HeartbeatSender (String host, int port, int serverId) {
		this.monitorHost = host;
		this.monitorPort = port;
		this.serverId = serverId;
		this.serializer = new JsonMessageSerializer();
	}
	
	/*
	 * Instead of creating a new TCP connection every time a heart beat is sent, just connect once and listen
	 * while the server is running
	 */
	
	public void connect() throws IOException {
		socket = new Socket(monitorHost, monitorPort);
		out = new DataOutputStream(socket.getOutputStream());
		System.out.println("Connected to monitor at " + socket.getInetAddress() + ":" + socket.getLocalPort()); // Display actual socket connection.
	}
	
	public void closeConnection() {
		try{
			if (socket != null)
				socket.close();
		} catch (IOException ignored) {}
		
		socket = null;
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
	 * HeartbeatSender object connects to the Monitor server on some IP and port. Message object, heart beat, is created containing
	 * message type = heart beat, the sender, and pay load (can be changed to something useful later). Message is serialized using 
	 * JsonMessageSerializer then sent to the monitor.
	 */
	
	public void sendHeartbeat() {
		try {
			// if not already connected to the monitor than make a connection.
			if (socket==null || socket.isClosed()) {
				connect(); 
			}
			
			Message heartbeat = new Message(
				"Heartbeat",
				serverId,
				"alive" // pay load is just a useless string right now, but a save state can be implemented and used later. 
			);
			
			byte[] data;
			
			try {
				data = serializer.serialize(heartbeat);
		    } catch(Exception e) {
		    	System.err.println("Heartbeat serialization error for server " + serverId + ": " + e.getMessage());
	            return; // Skip sending this heart beat.
			}
		    
			if (data==null) {
				System.err.println("Heartbeat serialization returned null for server " + serverId);
	            return; // Since null, don't send heart beat.
			}
			
	        out.write(data);
	        out.flush();  
	
		} catch(IOException e) {
			System.err.println("Heartbeat failed from server " + serverId + ": " + e.getMessage());
			closeConnection(); // Force reconnection if heart beat fails.
		} 
	}
	
	public void stop() {
		running = false;
		closeConnection();
	}
	
}
