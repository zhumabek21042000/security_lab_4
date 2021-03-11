import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.TreeMap;

public class Files {

    public static void saveMessages(String name, TreeMap<String, String[]> messages) {
        try {
            File file = new File(System.getProperty("user.home") + File.separator + "EncryptedChatMessages_" + name + ".ser");
            file.createNewFile();
            FileOutputStream stream = new FileOutputStream(file, false);
            ObjectOutputStream out = new ObjectOutputStream(stream);
            out.writeObject(messages);
            out.close();
            stream.close();
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    public static TreeMap<String, String[]> loadMessages(String name) {
        
        TreeMap<String, String[]> messages = new TreeMap<String, String[]>();
        
        try {
            File file = new File(System.getProperty("user.home") + File.separator + "EncryptedChatMessages_" + name + ".ser");
            file.createNewFile();
            if (file.length() == 0) return messages;
            FileInputStream stream = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(stream);
            messages = (TreeMap<String, String[]>) in.readObject();
            in.close();
            stream.close();
        } catch (Exception e) { e.printStackTrace(); }
        
        return messages;
        
    }
    
}
