package mini_project_01;

/*
 * Interface to be used for inter-process message serialization and deserialization. 
 */

public interface IMessageSerializer <T>{

	String serialize (T message) throws Exception; // Used by servers to send some message.
	
	T deserialize (String data, Class<T> clazz) throws Exception; // Used by servers to read some message.
	
}