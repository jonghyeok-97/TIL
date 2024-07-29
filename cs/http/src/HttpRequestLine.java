import java.net.URI;

public class HttpRequestLine {
    private final HttpMethod httpMethod;
    private final URI uri;
    private final HttpVersion httpVersion;

    private HttpRequestLine(HttpMethod httpMethod, URI uri, HttpVersion httpVersion) {
        this.httpMethod = httpMethod;
        this.uri = uri;
        this.httpVersion = httpVersion;
    }

    public static HttpRequestLine createRequestLineOfGet(URI uri, HttpVersion httpVersion) {
        return new HttpRequestLine(HttpMethod.GET, uri, httpVersion);
    }

    public HttpRequestLine changeHttpMethod(HttpMethod httpMethod) {
        return new HttpRequestLine(httpMethod, this.uri, this.httpVersion);
    }

    public String getValue() {
        return httpMethod
                + " "
                + uri.toString()
                + " "
                + httpVersion.getValue();
    }
}
