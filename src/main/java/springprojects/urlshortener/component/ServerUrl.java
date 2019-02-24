package springprojects.urlshortener.component;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static java.lang.String.format;

@Getter
@Component
public class ServerUrl {

    private String url;

    public ServerUrl(@Value("${server.port}") int serverPort) throws UnknownHostException {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        url = format("http://%s:%d/", hostAddress, serverPort);
    }
}
