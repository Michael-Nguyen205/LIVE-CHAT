package c2.code.authenservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepositoryJpa<T, ID extends Serializable> extends JpaRepository<T, ID> {
    // Các phương thức tùy chỉnh có thể được định nghĩa ở đây
}
