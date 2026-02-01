package mini_project_01;

public interface IMessageSerializer <T>{

	String serialize (T message) throws Exception;
	
	T deserialize (String data, Class<T> clazz) throws Exception;
	
}