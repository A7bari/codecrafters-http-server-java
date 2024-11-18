import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private final OutputStream writer;
    private final Map<String, String> headers = new HashMap<>();
    private String body = "";
    private String statusString = "OK";
    private int statusCode = 200;

    public HttpResponse(OutputStream writer) {
        this.writer = writer;
    }

    public void send() {
        StringBuilder responseString = new StringBuilder();

        responseString.append("HTTP/1.1 ")
            .append(statusCode)
            .append(" ")
            .append(statusString)
            .append("\r\n");

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            responseString.append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue())
                    .append("\r\n");
        }

        if (this.body.length() > 0)
            responseString.append("Content-Length: ")
                .append(this.body.length())
                .append("\r\n");
    
        responseString.append("\r\n");

        responseString.append(this.body);

        try {
            writer.write(responseString.toString().getBytes());
            writer.flush();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    public void send(String body) {
        this.body = body;
        send();
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