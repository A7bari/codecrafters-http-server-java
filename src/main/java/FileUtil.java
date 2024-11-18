import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
    public static String readFile(String path) throws IOException {
        StringBuilder fileContents = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContents.append(line).append("\n");
            }
        }
        return fileContents.toString();
    }
    
    public static void writeFile(String path, String content) throws IOException {
        System.out.println("Writing to file: " + path + " content: " + content);
        
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(content);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            throw e;
        }

        System.out.println("File written successfully");    
    }
} 
