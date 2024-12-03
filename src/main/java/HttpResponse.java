import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;

public class HttpResponse {
    private static final String[] supportedEncodings = {"gzip"};
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

    try {
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

        if (!this.body.isEmpty()) {
            responseString.append("Content-Length: ")
                .append(this.body.getBytes().length)
                .append("\r\n");
        }

        responseString.append("\r\n");

        responseString.append(this.body);

        // Log response for debugging
        System.out.println("Sending response:");
        System.out.println(responseString);

        writer.write(responseString.toString().getBytes());
        writer.flush();
        } catch (Exception e) {
            System.out.println("Exception while sending response: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void send(String body) {
        this.body = encodeBody(body);
        send();
    }

    public HttpResponse setHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public HttpResponse setBody(String body) {
        this.body = encodeBody(body);

        return this;
    }

    public HttpResponse setStatus(int statusCode, String statusString) {
        this.statusCode = statusCode;
        this.statusString = statusString;

        return this;
    }

    public HttpResponse setEncoding(String acceptEncoding) {
        if (acceptEncoding == null) {
            return this;
        }

        String[] encodings = acceptEncoding.split(", ");

        for (String encoding : encodings) {
            for (String supportedEncoding : supportedEncodings) {
                if (encoding.contains(supportedEncoding)) {
                    headers.put("Content-Encoding", supportedEncoding);
                    return this;
                }
            }
        }

        return this;
    }

    private String encodeBody(String body) {
        String encoding = headers.get("Content-Encoding");

        if (encoding.equals("gzip")) {
            // Implement gzip encoding
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
                gzipOutputStream.write(body.getBytes());
                gzipOutputStream.finish();
                return byteArrayOutputStream.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return body;
            }

        }

        return body;
    }
}