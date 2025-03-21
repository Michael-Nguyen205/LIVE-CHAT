package c2.code.authenservice.entity.departmentAgentAssignmentCondition;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "department_agent_assignment_condition")
public class DepartmentAgentAssignmentCondition {

    @Id
    private String id;
    private Integer departmentId;
    private String accountId;

    private List<IssueCondition> issueCondition;
    private List<KeywordCondition> keywordCondition;
    private List<TagCondition> tagsCondition;
    private List<Agent> agent;

    // Getters and Setters
}
