import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Router {
    private final Map<String, BiConsumer<HttpRequest, HttpResponse>> routes = new ConcurrentHashMap<>();
    private final Map<String, Map<Pattern, BiConsumer<HttpRequest, HttpResponse>>> dynamicRoutes = new ConcurrentHashMap<>();

    public Router() {
        // initialize the dynamic map with the supported HTTP methods
        dynamicRoutes.put("GET", new ConcurrentHashMap<>());
        dynamicRoutes.put("POST", new ConcurrentHashMap<>());
    }

    public void get(String path, BiConsumer<HttpRequest, HttpResponse> handler) {
        if (isDynamicRoute(path)) {
            Pattern pattern = pathToPattern(path);
            dynamicRoutes.get("GET").put(pattern, handler);
        } else {
            routes.put("GET " + path, handler);
        }
    }

    public void post(String path, BiConsumer<HttpRequest, HttpResponse> handler) {
        if (isDynamicRoute(path)) {
            Pattern pattern = pathToPattern(path);
            dynamicRoutes.get("POST").put(pattern, handler);
        } else {
            routes.put("POST " + path, handler);
        }
    }

    public void handle(HttpRequest request, HttpResponse response) {
        String key = request.getMethod() + " " + request.getPath();
        BiConsumer<HttpRequest, HttpResponse> handler = routes.get(key);

        if (handler != null) {
            System.out.println("Route matched: "+ request.getMethod() + " " + request.getPath());
            handler.accept(request, response);
            return;
        } 

        // check dynamic routes
        var droutes = dynamicRoutes.get(request.getMethod());
        for (Map.Entry<Pattern, BiConsumer<HttpRequest, HttpResponse>> entry : droutes.entrySet()) {
            Matcher matcher = entry.getKey().matcher(request.getPath());

            // if the path matches the pattern    
            if (matcher.matches()) {
                System.out.println("Dynamic route matched: "+ request.getMethod() + " " + request.getPath());
                // add path params to the request object
                Map<String, Integer> groups = matcher.namedGroups();
                for (Map.Entry<String, Integer> group : groups.entrySet()) {
                    String name = group.getKey();
                    String value = matcher.group(group.getValue());
                    request.addPathParam(name, value);
                }
                
                // call the handler
                entry.getValue().accept(request, response);
                return;
            }
        }

        response.setStatus(404, "Not Found").send();
    }

    private boolean isDynamicRoute(String path) {
        return path.contains("{");
    }

    private Pattern pathToPattern(String path) {
        // create a named capturing group in a regular expression 
        // ex: replace {id} with the regex (?<id>[^/]+)
        String regex = path.replaceAll("\\{([^/]+)\\}", "(?<$1>[^/]+)");
        return Pattern.compile("^" + regex + "$");
    }
}
