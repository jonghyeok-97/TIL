import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketImpl;
import java.net.URI;
import java.net.UnknownHostException;
import java.net.http.WebSocket;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        InetAddress naverIPAddress = InetAddress.getByName("disney.co.kr");
        System.out.println("naverIPAddress = " + naverIPAddress);

        try (Socket socket = new Socket(naverIPAddress, 80);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            HttpRequestLine requestLine = HttpRequestLine.createRequestLineOfGet(URI.create("/"), HttpVersion.HTTP_1_1);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Host", "disney.co.kr");
            headers.add("Connection", "close");

            HttpRequest httpRequest = new HttpRequest(requestLine, headers);

            bw.write(httpRequest.getRequestMessage());
            bw.flush();

            String s;
            System.out.println("=========응답===========");
            while ((s = br.readLine()) != null) {
                System.out.println(s);
            }
        }
    }
}
