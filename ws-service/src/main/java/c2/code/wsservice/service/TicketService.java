package c2.code.wsservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // Lấy dữ liệu Tag Assignment Condition từ Redis
    public TagAssignmentCondition getTagAssignmentCondition(String tagId) {
        return (TagAssignmentCondition) redisTemplate.opsForHash().get("TagAssignmentCondition", tagId);
    }

    // Lấy dữ liệu Agent Assignment Condition từ Redis
    public AgentAssignmentCondition getAgentAssignmentCondition(int agentId) {
        return (AgentAssignmentCondition) redisTemplate.opsForHash().get("AgentAssignmentCondition", agentId);
    }

    // Lọc ticket và phân công cho agent và department
    public Ticket assignTicketToAgent(Ticket ticket) {
        // Lọc tag của ticket từ TagAssignmentCondition
        for (String keyword : ticket.getKeywords()) {
            TagAssignmentCondition tagAssignmentCondition = getTagAssignmentCondition(keyword);

            if (tagAssignmentCondition != null) {
                // Kiểm tra loại issue và gán tag
                for (Issue issue : tagAssignmentCondition.getIssues()) {
                    if (ticket.getIssue().equals(issue.getType())) {
                        ticket.setTag(tagAssignmentCondition.getTagId());
                        break;
                    }
                }

                // Phân công phòng ban và agent từ AgentAssignmentCondition
                for (AgentAssignmentCondition agentAssignmentCondition : tagAssignmentCondition.getAgentAssignmentConditions()) {
                    for (TicketTypeAssignment ticketTypeAssignment : agentAssignmentCondition.getTicketTypeAssignment()) {
                        if (ticket.getTicketType().equals(ticketTypeAssignment.getType())) {
                            ticket.setDepartmentId(agentAssignmentCondition.getDepartmentId());
                            ticket.setAgentId(agentAssignmentCondition.getAgentId());
                            break;
                        }
                    }
                }
            }
        }

        return ticket;
    }
}
