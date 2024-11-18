import java.util.HashMap;
import java.util.Map;

public class ArgsParser {
    private final Map<String, String> argsMap = new HashMap<>();

    public ArgsParser(String[] args) {
        for (int i = 0; i < args.length; i += 2) {
            argsMap.put(args[i], args[i + 1]);
        }
    }

    public String get(String key) {
        return argsMap.get(key);
    }

    public boolean has(String key) {
        return argsMap.containsKey(key);
    }
}
