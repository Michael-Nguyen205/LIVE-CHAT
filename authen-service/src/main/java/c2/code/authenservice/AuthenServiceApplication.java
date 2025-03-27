package c2.code.authenservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = {"c2.code"})
@EnableMongoRepositories
public class AuthenServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthenServiceApplication.class, args);
	}
}
