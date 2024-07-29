public enum HttpVersion {
    HTTP_1_1("HTTP/1.1");

    private final String value;

    HttpVersion(String httpVersion) {
        this.value = httpVersion;
    }

    public String getValue() {
        return value;
    }
}
