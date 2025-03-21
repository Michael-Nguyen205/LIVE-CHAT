package c2.code.wsservice.channelHandler;

import c2.code.response.departmentAgentAssignmentConditionResponse.DepartmentAgentAssignmentConditionResponse;
import c2.code.wsservice.cache.ChannelContextCache;
import c2.code.wsservice.dto.ContactDTO;
import c2.code.wsservice.dto.TicketDTO;
import c2.code.wsservice.dto.TicketStatusDTO;
import c2.code.wsservice.dto.TicketTypeDTO;
import c2.code.wsservice.request.ClientRequest;
import c2.code.wsservice.message.M0300CreateTicketMessage;
import c2.code.wsservice.service.api.AuthService;
import c2.code.wsservice.util.RequestUtils;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

// /api/login
// mid0100 -> login
@Service(value = "mid0300")
public class M0300CreateTicketHandlerByContact implements ChatHandler {

    private static final Logger logger = LoggerFactory.getLogger(M0100LoginHandler.class);
    private final Gson gson = new Gson();

    @Autowired
    private ChannelContextCache contextCache;
    @Autowired
    private RequestUtils requestUtils;
    @Autowired
    private AuthService authService;

    @Value("${chat.nodeId}")
    private int serverId;

    @Override
    public void processMessage(String requestId, ClientRequest clientRequest, ChannelHandlerContext context) {
        logger.info("[{}] Start processing ticket creation request. requestId: {}. Message: {}", requestId, requestId, clientRequest.getContent());

        // Tạo ticket và contact DTO
        M0300CreateTicketMessage request = requestUtils.buildRequest(requestId, clientRequest, M0300CreateTicketMessage.class);
        TicketDTO ticketDTO = createTicketDTO(request);
        ContactDTO contactDTO = createContactDTO(request);
        ticketDTO.setContact(contactDTO);


        //kiểm tra khách hàng có đến từ trình duyệt mình thật không
        //kiểm tra xem có áp dụng tự động gán tag dựa trên keyword với issue không , có thì gán (logic...)

        ObjectId accountId =  request.getAccountId();
        DepartmentAgentAssignmentConditionResponse  departmentAgentAssignmentConditionResponse = new DepartmentAgentAssignmentConditionResponse();


        // phân chia phòng ban phù hợp
        // dùng webflux webclient truy vấn


        // phân chia ticket cho agent
        // check quyền user được phép xem th , hay được chỉnh sửa hay không có quyền tất
        // kiểm tra agent online hay offline
        // nếu online thì flush và cache ticket contact
        // nếu offline thì bắn kafka để lưu luôn



        // Xử lý ticket và phân loại phòng ban
        handleTicketAndAssignAgent(ticketDTO, contactDTO);

        // Tiếp tục các thao tác sau (cập nhật trạng thái, gửi kafka, etc.)
        processAgentAssignment(ticketDTO, contactDTO);
    }

    // Tạo TicketDTO từ request
    private TicketDTO createTicketDTO(M0300CreateTicketMessage request) {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setId(new ObjectId());
        ticketDTO.setCreatedBy(request.getUserId());
        ticketDTO.setCreatedAt(request.getClientTime());
        ticketDTO.setCode(NanoIdUtils.randomNanoId());
        ticketDTO.setAccountId(request.getAccountId());
        ticketDTO.setIsActive(false);
        TicketStatusDTO ticketStatus = new TicketStatusDTO();
        TicketTypeDTO ticketTypeDTO = new TicketTypeDTO();

        if (request.getTicketType().getTicketTypeId() != null) {
            ticketTypeDTO.setTicketTypeId(request.getTicketType().getTicketTypeId());
            ticketTypeDTO.setName(request.getTicketType().getName());
        }
        if (request.getStatus().getStatusId() != null) {
            ticketStatus.setStatusId(request.getStatus().getStatusId());
            ticketStatus.setStatus(request.getStatus().getStatus());
        }

        ticketDTO.setTicketStatus(ticketStatus);
        ticketDTO.setTicketType(ticketTypeDTO);
        return ticketDTO;
    }

    // Tạo ContactDTO từ request
    private ContactDTO createContactDTO(M0300CreateTicketMessage request) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setContactId(request.getUserId());
        contactDTO.setName(request.getName());
        contactDTO.setIpContact(request.getIp());
        contactDTO.setPriorityLanguage(request.getLanguage());

        // Thiết lập các thuộc tính liên quan đến contact
        setContactDetailIfNotNull(contactDTO, request.getEmail(), "Email is null", contactDTO::setEmail);
        setContactDetailIfNotNull(contactDTO, request.getPhone(), "Phone number is null", contactDTO::setPhone);
        setContactDetailIfNotNull(contactDTO, request.getUrlStart(), "URL Start is null", contactDTO::setUrlStart);
        setContactDetailIfNotNull(contactDTO, request.getLanguage(), "Priority Language is null", contactDTO::setPriorityLanguage);

        return contactDTO;
    }

    // Phương thức hỗ trợ để kiểm tra và thiết lập các thuộc tính trong ContactDTO
    private void setContactDetailIfNotNull(ContactDTO contactDTO, String value, String errorMessage, Consumer<String> setter) {
        if (value != null) {
            setter.accept(value);
        } else {
            logger.warn(errorMessage);  // Thay vì System.out, chúng ta sử dụng logger
        }
    }

    // Xử lý phân công agent và gán ticket cho agent
    private void handleTicketAndAssignAgent(TicketDTO ticketDTO, ContactDTO contactDTO) {
        // Phân chia phòng ban, lấy tag và ticket type
        // Tạo logic phân chia agent và kiểm tra quyền
        logger.info("Assigning ticket to agent based on department...");
        // Bạn có thể thêm logic phân loại tại đây
    }

    // Kiểm tra trạng thái agent và xử lý theo trạng thái (online hoặc offline)
    private void processAgentAssignment(TicketDTO ticketDTO, ContactDTO contactDTO) {
        // Kiểm tra agent online hay offline
        boolean isAgentOnline = checkAgentStatus(ticketDTO);
        if (isAgentOnline) {
            logger.info("Agent is online. Flushing and caching ticket...");
            // Tiến hành xử lý khi agent online
        } else {
            logger.info("Agent is offline. Sending data to Kafka...");
            // Gửi dữ liệu đến Kafka khi agent offline
        }
    }

    // Kiểm tra trạng thái agent (ví dụ: từ cache hoặc database)
    private boolean checkAgentStatus(TicketDTO ticketDTO) {
        // Logic kiểm tra trạng thái agent (ví dụ: kiểm tra cache hoặc truy vấn DB)
        return true;  // Giả sử agent luôn online
    }




}




