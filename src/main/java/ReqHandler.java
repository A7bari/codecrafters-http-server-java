import java.io.InputStream;

public class ReqHandler {
    private InputStream inStream;

    public ReqHandler(InputStream inStream) {
        this.inStream = inStream;
    }

    public Request parse() {
        String request = readRequest();
        String[] lines = request.split("\r\n");
        String[] requestLine = lines[0].split(" ");
        return new Request(requestLine[0], requestLine[1], requestLine[2]);
    }

    private String readRequest() {
        StringBuilder request = new StringBuilder();
        try {
            int c;
            while ((c = inStream.read()) != -1) {
                request.append((char) c);
                if (request.length() >= 4 && request.substring(request.length() - 4).equals("\r\n\r\n")) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return request.toString();
    }
}
