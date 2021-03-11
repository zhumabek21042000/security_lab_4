public class EncryptedChat {

	public static void main(String[] args) {
		new ChatClient("Alice", "localhost", 1111);
		new ChatClient("Bob", "localhost", 1111);
		new ChatClient("Eve", "localhost", 1111);
	}
	
}
