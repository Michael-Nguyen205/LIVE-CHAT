package c2.code.userservice.service;

import c2.code.dto.UserCache;
import c2.code.dto.request.CheckSendMessageRequest;
import c2.code.dto.response.CheckSendMessageResponse;
import c2.code.userservice.dao.UserRedisDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRedisDao userRedisDao;

    public CheckSendMessageResponse checkCanSendMessage(CheckSendMessageRequest request) {
        log.info("Check can send message. Request: {}", request);
        CheckSendMessageResponse response = new CheckSendMessageResponse();
        if (request.getUserId().equals(request.getDestId())) {
            log.info("Sender and receiver are the same");
            response.setValid(false);
            return response;
        }

        // kiểm tra bạn bè có tồn tại trong hệ thống không
        UserCache userCache = userRedisDao.getUser(request.getDestId());
        if(userCache == null) {
            log.info("Friend not found. Can not send message");
            response.setValid(false);
            return response;
        }

        // TODO: kiểm tra bạn bè có chặn không

        response.setValid(true);
        return response;
    }
}
