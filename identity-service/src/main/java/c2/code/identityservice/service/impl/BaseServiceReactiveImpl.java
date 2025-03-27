package c2.code.identityservice.service.impl;

import c2.code.identityservice.enums.ErrorCodeEnum;
import c2.code.identityservice.exceptions.AppException;
import c2.code.identityservice.service.IBaseServiceReactive;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;



@Log4j2
@RequiredArgsConstructor
//@AllArgsConstructor
public class BaseServiceReactiveImpl<T, ID extends Serializable,R extends  ReactiveCrudRepository<T, ID>> implements IBaseServiceReactive<T, ID>
//        ,R extends ReactiveCrudRepository<T, ID>>
{

   protected  R repository;  // Sử dụng repository hỗ trợ Reactive

    public BaseServiceReactiveImpl(  R Pepo) {
        this.repository = Pepo;
    }

    @Override
    public Flux<T> findAll() {
        return repository.findAll()
                .onErrorResume( e -> {
                    throw new AppException(ErrorCodeEnum.DATA_NOT_FOUND,"khong tim thay model nao");
                });  // Trả về tất cả dưới dạng Flux<T>
    }

    @Override
    public Mono<T> findById(ID id) {
        return repository.findById(id);  // Tìm theo ID dưới dạng Mono<T>
    }

    @Override
    public Mono<T> save(T entity) {
        return repository.save(entity)
                .onErrorResume(DuplicateKeyException.class, e -> {
                    // Trả về lỗi Mono.error thay vì ném lại lỗi ngay lập tức
                    log.error("CHECK NAME LOI: {}", e.getClass().getName());
                    return Mono.error(new AppException(ErrorCodeEnum.DUPLICATE_DATA));
                })
           .onErrorResume(e -> {
            // Xử lý tất cả các lỗi khác
            e.printStackTrace();
               return Mono.error(e);

           });
    }


//    @Override
//    public Mono<T> update( T updatedEntity) {
//        // Tìm entity hiện tại, nếu tìm thấy thì cập nhật, nếu không thì trả về lỗi
//        return repository.findById(id)
//                .flatMap(existingEntity -> repository.save(updatedEntity))  // Cập nhật entity
//                .switchIfEmpty(Mono.error(new IllegalArgumentException("Entity not found with id: " + id)));  // Xử lý lỗi nếu không tìm thấy
//    }


    @Override
    public Mono<T> update( T updatedEntity) {
        // Tìm entity hiện tại, nếu tìm thấy thì cập nhật, nếu không thì trả về lỗi
        return repository.save(updatedEntity)
                .onErrorResume(DuplicateKeyException.class, e -> {
                    // Trả về lỗi Mono.error thay vì ném lại lỗi ngay lập tức
                    log.error("CHECK NAME LOI: {}", e.getClass().getName());
                    return Mono.error(new AppException(ErrorCodeEnum.DUPLICATE_DATA));
                })
                .onErrorResume(e -> {
                    // Xử lý tất cả các lỗi khác
                    e.printStackTrace();
                    return Mono.error(e);

                });
    }

    @Override
    public Mono<Void> deleteById(ID id) {
        // Tìm entity theo ID và xóa, nếu không tìm thấy thì trả về lỗi
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new AppException(ErrorCodeEnum.DATA_NOT_FOUND, "Không tìm thấy entity với id là: " + id)))
                .then(repository.deleteById(id))
                .doOnError(Throwable::printStackTrace)  .then();  // In ra stack trace nếu có lỗi
    }

}
