import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
  public static void main(String[] args) {

    try {
      ServerSocket serverSocket = new ServerSocket(4221);
    
      serverSocket.setReuseAddress(true);
    
      Socket client = serverSocket.accept(); 
      
      // read the request from the client
      InputStream inStream = client.getInputStream();
      ReqHandler reqHandler = new ReqHandler(inStream);

      Request request = reqHandler.parse();

      if (request.getPath().equals("/")) {
        String response = "HTTP/1.1 200 OK\r\n\r\n";
        client.getOutputStream().write(response.getBytes());
      } else {
        String response = "HTTP/1.1 404 Not Found\r\n\r\n";
        client.getOutputStream().write(response.getBytes());
      }

    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    }
  }
}
