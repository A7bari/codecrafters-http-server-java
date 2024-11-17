import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Router {
    private final Map<String, BiConsumer<HttpRequest, HttpResponse>> routes = new HashMap<>();
    private final Map<Pattern, BiConsumer<HttpRequest, HttpResponse>> dynamicRoutes = new HashMap<>();

    public void get(String path, BiConsumer<HttpRequest, HttpResponse> handler) {
        if (isDynamicRoute(path)) {
            Pattern pattern = pathToPattern(path);
            dynamicRoutes.put(pattern, handler);
        } else {
            routes.put("GET " + path, handler);
        }
    }

    public void post(String path, BiConsumer<HttpRequest, HttpResponse> handler) {
        if (isDynamicRoute(path)) {
            Pattern pattern = pathToPattern(path);
            dynamicRoutes.put(pattern, handler);
        } else {
            routes.put("POST " + path, handler);
        }
    }

    public void handle(HttpRequest request, HttpResponse response) {
        String key = request.getMethod() + " " + request.getPath();
        BiConsumer<HttpRequest, HttpResponse> handler = routes.get(key);

        if (handler != null) {
            handler.accept(request, response);
            return;
        } 

        // check dynamic routes
        for (Map.Entry<Pattern, BiConsumer<HttpRequest, HttpResponse>> entry : dynamicRoutes.entrySet()) {
            Matcher matcher = entry.getKey().matcher(request.getPath());

            // if the path matches the pattern
            if (matcher.matches()) {
                // add path params to the request object
                for (int i = 1; i <= matcher.groupCount(); i++) {
                    String name = matcher.group(i);
                    String value = matcher.group(i);
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
