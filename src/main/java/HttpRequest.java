import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private final String method;
    private final String path;
    private final String version;
    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, String> pathParams = new HashMap<>();
    
    private String body = "";

    public HttpRequest(String method, String path, String version) {
        System.out.println("New request - method: " + method + " path: " + path + " version: " + version);
        this.method = method.toUpperCase();
        this.path = path;
        this.version = version;
    }
    
    public void addPathParam(String key, String value) {
        pathParams.put(key, value);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void setBody(String body) {
        this.body = body;
    }
    
    public String getBody() {
        if (this.method.equals("GET"))
            return "";
        return body;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public String getPathParam(String key) {
        return pathParams.get(key);
    }

    public String getMethod() {
        return method;
    }
    
    public String getPath() {
        return path;
    }
    
    public String getVersion() {
        return version;
    }
}
