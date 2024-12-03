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
        this.executorService = Executors.newFixedThreadPool(10);
    }

    public void start() throws IOException {
        try(ServerSocket serverSocket = new ServerSocket(port);) {
            System.out.println("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientSocket.setSoTimeout(30000);
                System.out.println("New client connected");
                executorService.submit(() -> handleRequest(clientSocket));
            }
        } catch (IOException e) {
            System.out.println("Error starting server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleRequest(Socket clientSocket) {
        try (InputStream inStream = clientSocket.getInputStream();
             OutputStream outStream = clientSocket.getOutputStream()) {

            HttpRequest request = ReqHandler.parse(inStream);
            HttpResponse response = new HttpResponse(outStream);

            router.handle(request, response);
            outStream.flush();
        } catch (IOException e) {
            System.out.println("Error handling client request: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
