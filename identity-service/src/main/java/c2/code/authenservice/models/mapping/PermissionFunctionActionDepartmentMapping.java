package c2.code.authenservice.models.mapping;

import io.r2dbc.spi.Readable;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
@Data // Lombok annotation to generate getters, setters, toString, etc.
public class PermissionFunctionActionDepartmentMapping {

    static AtomicInteger loopCreatePackagepProjectionMapping = new AtomicInteger(0);

    // Permission fields
    private Integer permissionId;
    private String permissionName;

    // Model fields
    private Integer functionId;  // Changed from modelId to functionId
    private String functionName; // Changed from modelName to functionName

    // Action fields
    private Integer actionId;
    private String actionName;
    private String departmentId;



    public static PermissionFunctionActionDepartmentMapping mapRowToMapping(Readable row) {
        PermissionFunctionActionDepartmentMapping mapping = new PermissionFunctionActionDepartmentMapping();
        mapping.setPermissionId(row.get("permission_id", Integer.class));
        mapping.setPermissionName(row.get("permission_name", String.class));
        mapping.setFunctionId(row.get("function_id", Integer.class));
        mapping.setFunctionName(row.get("function_name", String.class));
        mapping.setActionId(row.get("action_id", Integer.class));
        mapping.setActionName(row.get("action_name", String.class));
        mapping.setDepartmentId(row.get("department_id", String.class));
        return mapping;
    }

//    public static Set<PermissionFunctionActionDepartmentMapping> toPermissionModelActionMapping(ResultSet rs) throws SQLException {
//        Set<PermissionFunctionActionDepartmentMapping> permissionModelActionMappings = new HashSet<>();
//        while (rs.next()) {
//
//            PermissionFunctionActionDepartmentMapping permissionFunctionActionDepartmentMapping = new PermissionFunctionActionDepartmentMapping();
//
//            // Mapping Permission fields
//            permissionFunctionActionDepartmentMapping.setPermissionId(rs.getInt("permission_id"));
//            permissionFunctionActionDepartmentMapping.setPermissionName(rs.getString("permission_name"));
//
//            // Mapping Model fields
//            permissionFunctionActionDepartmentMapping.setFunctionId(rs.getInt("function_id"));
//            permissionFunctionActionDepartmentMapping.setFunctionName(rs.getString("function_name"));
//
//            // Mapping Action fields
//            permissionFunctionActionDepartmentMapping.setActionId(rs.getInt("action_id"));
//            permissionFunctionActionDepartmentMapping.setActionName(rs.getString("action_name"));
//
//
//            // Mapping Raw fields
//
//            // Add the mapped object to the set
//            permissionModelActionMappings.add(permissionFunctionActionDepartmentMapping);
//        }


        return permissionModelActionMappings;
    }
}
