import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class HttpServer {
    private final Router router;
    private final int port;
    private final ExecutorService executorService;



    public HttpServer(Router router, int port) {
        this.router = router;
        this.port = port;
        this.executorService = Executors.newFixedThreadPool(10); // Create the thread pool once
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            executorService.submit(() -> handleRequest(clientSocket));
        }
    }

    private void handleRequest(Socket clientSocket) {
        try (InputStream inStream = clientSocket.getInputStream();
             OutputStream outStream = clientSocket.getOutputStream()) {

            HttpRequest request = ReqHandler.parse(inStream);
            HttpResponse response = new HttpResponse(outStream);

            router.handle(request, response);

        } catch (IOException e) {
            System.out.println("Error handling client request: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
}
