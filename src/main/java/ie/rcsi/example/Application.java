package ie.rcsi.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    private Application() { }
    static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
