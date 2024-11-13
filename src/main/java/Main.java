import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
  public static void main(String[] args) {

    try {
      ServerSocket serverSocket = new ServerSocket(4221);
    
      // Since the tester restarts your program quite often, setting SO_REUSEADDR
      // ensures that we don't run into 'Address already in use' errors
      serverSocket.setReuseAddress(true);
    
      Socket client = serverSocket.accept(); // Wait for connection from client.
      
      // read the request from the client
      client.getInputStream().read();

      // send the response to the client
      String response = "HTTP/1.1 200 OK\\r\\n\\r\\n";
      client.getOutputStream().write(response.getBytes());

    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    }
  }
}
