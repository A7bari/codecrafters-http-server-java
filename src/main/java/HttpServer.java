import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class HttpServer {
    private final Router router;
    private final int port;

    public HttpServer(Router router, int port) {
        this.router = router;
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            ExecutorService executorService = Executors.newFixedThreadPool(10);
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
            e.printStackTrace();
        }
    }
}
