import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpHeaders {

    private final Map<String, String> headers;

    public HttpHeaders() {
        this.headers = new HashMap<>();
    }

    public void add(String key, String value) {
        headers.put(key, value);
    }

    public String getValue() {
        return headers.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining("\r\n"));
    }
}
