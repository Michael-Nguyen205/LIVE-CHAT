package c2.code.response.departmentAgentAssignmentConditionResponse;

import java.util.List;

public class DepartmentAgentAssignmentConditionResponse {
    private Long id;
    private Integer departmentId;
    private String accountId;
    private List<IssueConditionResponse> issueCondition;
    private List<KeywordConditionResponse> keywordCondition;
    private List<TagConditionResponse> tagsCondition;
    private List<AgentResponse> agent;

    // Getters and Setters
}
