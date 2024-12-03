import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReqHandler {
    public static HttpRequest parse(InputStream inStream) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
            // Read the request line
            String requestLine = reader.readLine();
            if (requestLine == null || requestLine.isEmpty()) {
                throw new IOException("Invalid HTTP request: Empty request line");
            }

            String[] requestParts = requestLine.split(" ");
            if (requestParts.length != 3) {
                throw new IOException("Invalid HTTP request: Malformed request line");
            }

            HttpRequest request = new HttpRequest(requestParts[0], requestParts[1], requestParts[2]);

            // Read headers
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                String[] headerParts = line.split(": ", 2);
                if (headerParts.length == 2) {
                    request.addHeader(headerParts[0], headerParts[1]);
                }
            }

            // Read body (if Content-Length is specified)
            String contentLengthHeader = request.getHeader("Content-Length");
            if (contentLengthHeader != null) {
                int contentLength = Integer.parseInt(contentLengthHeader);
                char[] body = new char[contentLength];
                int bytesRead = reader.read(body, 0, contentLength);
                if (bytesRead != contentLength) {
                    throw new IOException("Unexpected end of input while reading body");
                }
                request.setBody(new String(body));
            }

            return request;
        } catch (IOException e) {
            System.out.println("Error parsing request: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
