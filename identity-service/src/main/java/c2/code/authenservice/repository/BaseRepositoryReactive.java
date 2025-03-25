package c2.code.authenservice.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepositoryReactive<T, ID extends Serializable> extends ReactiveCrudRepository<T, ID>
//        , ReactiveSortingRepository<T, ID>
{
    // Các phương thức tùy chỉnh có thể được định nghĩa ở đây
}