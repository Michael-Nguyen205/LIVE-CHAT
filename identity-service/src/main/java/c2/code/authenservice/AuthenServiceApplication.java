package c2.code.authenservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"c2.code"})
public class AuthenServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenServiceApplication.class, args);
	}

}
