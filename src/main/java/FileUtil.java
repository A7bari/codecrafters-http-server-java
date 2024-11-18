import java.io.BufferedReader;
import java.io.FileReader;
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
        try (java.io.FileWriter writer = new java.io.FileWriter(path)) {
            writer.write(content);
        }
    }
} 
