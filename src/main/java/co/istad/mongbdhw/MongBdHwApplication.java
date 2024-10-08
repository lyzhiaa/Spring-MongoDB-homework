package co.istad.mongbdhw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class MongBdHwApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongBdHwApplication.class, args);
    }

}
