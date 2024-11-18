import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private final String method;
    private final String path;
    private final String version;
    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, String> pathParams = new HashMap<>();
    
    public HttpRequest(String method, String path, String version) {
        this.method = method;
        this.path = path;
        this.version = version;
    }
    
    public void addPathParam(String key, String value) {
        System.out.println("adding params -  key: " + key + " value: " + value);
        pathParams.put(key, value);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
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
