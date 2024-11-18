import java.io.InputStream;

public class ReqHandler {
    static public HttpRequest parse(InputStream inStream) {
        String reqStr = readRequest(inStream);
        String[] lines = reqStr.split("\r\n");

        // track the index of the current line
        int currIndex = 0;

        // request line
        String[] requestLine = lines[currIndex].split(" ");
        currIndex++;

        HttpRequest request = new HttpRequest(requestLine[0], requestLine[1], requestLine[2]);

        // headers
        while (currIndex < lines.length) {
            if (lines[currIndex].equals("")) {
                currIndex++;
                break;
            }

            String[] header = lines[currIndex].split(": ");
            if (header.length == 2) {
                request.addHeader(header[0], header[1]);
            }

            currIndex++;
        }

        // set body if exist 
        if (currIndex < lines.length) {
            request.setBody(lines[currIndex]);
        }

        return request;
    }

    static private String readRequest(InputStream inStream) {
        StringBuilder request = new StringBuilder();
        try {
            int c;
            while ((c = inStream.read()) != -1) {
                request.append((char) c);
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return request.toString();
    }
}
