package mini_project_01;

public class Message {
	private String type;
	private int senderId;
	private String payload;
	
	public Message() {}
	
	public Message(String type, int senderId, String payload) {
		this.type = type;
		this.senderId = senderId;
		this.payload = payload;
	}
	
	public String getType() {
        return type;
    }

    public int getSenderId() {
        return senderId;
    }

    public String getPayload() {
        return payload;
    }
}