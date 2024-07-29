import java.net.URI;
import java.util.HashMap;

public class HttpRequest {

    private final HttpRequestLine requestLine;
    private final HttpHeaders headers;
    //필요하면 구현
    //private final HttpBody body;

    public HttpRequest(HttpRequestLine requestLine, HttpHeaders headers) {
        this.requestLine = requestLine;
        this.headers = headers;
    }

//    public static HttpRequest createRequestOfGet(HttpRequestLine requestLine, HttpHeaders headers) {
//        return new HttpRequest(requestLine, headers);
//    }

//    public void addHeader(String key, String value) {
//        headers.add(key, value);
//    }

    public String getRequestMessage() {
        return requestLine.getValue() + "\r\n" +
                headers.getValue() + "\r\n" +
                "\r\n";
    }
}
