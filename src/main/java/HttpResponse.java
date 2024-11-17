import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private final OutputStream writer;
    private final Map<String, String> headers = new HashMap<>();
    private String body;
    private String statusString = "OK";
    private int statusCode = 200;

    public HttpResponse(OutputStream writer) {
        this.writer = writer;
    }

    public void send() {
        String statusLine = "HTTP/1.1 " + statusCode + " " + statusString + "\r\n";
        String headerString = "";

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            headerString += entry.getKey() + ": " + entry.getValue() + "\r\n";
        }

        headerString += "Content-Length: " + body.length() + "\r\n";
        headerString += "\r\n";

        try {
            writer.write((statusLine + headerString + body).getBytes());
            writer.flush();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    public HttpResponse setHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public HttpResponse setBody(String body) {
        this.body = body;

        return this;
    }

    public HttpResponse setStatus(int statusCode, String statusString) {
        this.statusCode = statusCode;
        this.statusString = statusString;

        return this;
    }
}