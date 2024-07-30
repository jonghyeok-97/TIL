public class HttpRequest {

    private final HttpRequestLine requestLine;
    private final HttpHeaders headers;
    //필요하면 구현
    //private final HttpBody body;

    public HttpRequest(HttpRequestLine requestLine, HttpHeaders headers) {
        this.requestLine = requestLine;
        this.headers = headers;
    }

    public String getRequestMessage() {
        return requestLine.getValue() + "\r\n" +
                headers.getValue() + "\r\n" +
                "\r\n";
    }
}
