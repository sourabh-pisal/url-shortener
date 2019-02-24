package springprojects.urlshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.UnknownHostException;

@SpringBootApplication
public class Application {
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication.run(Application.class, args);
    }
}
