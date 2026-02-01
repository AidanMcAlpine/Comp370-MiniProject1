package mini_project_01;
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

abstract class ServerProcess {
	protected int serverId;
	protected int port;
	protected ServerSocket serverSocket;
	protected boolean running;
	protected ExecutorService threadPool = Executors.newCachedThreadPool();
	
	
	public void start() throws IOException {
		serverSocket = new ServerSocket(port);
		running = true;
		
		while (running) {
			try {
				Socket clientSocket = serverSocket.accept();
				threadPool.submit(() -> handleConnection(clientSocket));
			} catch (IOException e){
				if (running) e.printStackTrace();
			}
		}
	}
	
	public void stop() throws IOException {
		running = false;
		serverSocket.close();
		threadPool.shutdownNow();
	}
	
	public int getServerId() {
		return serverId;
	}

	public int getPort() {
		return port;
	}

	protected abstract void handleConnection(Socket clientSocket);
	protected abstract void processMessage(String message);
	
}
