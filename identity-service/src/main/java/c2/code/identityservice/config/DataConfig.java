package c2.code.identityservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "c2.code.identityservice.repository")
public class DataConfig {
    // Cấu hình cho cả MongoDB và JPA
}
