package c2.code.authenservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepositoryMongo<T, ID extends Serializable> extends MongoRepository<T, ID> {
    // Các phương thức tùy chỉnh có thể được định nghĩa ở đây
}
