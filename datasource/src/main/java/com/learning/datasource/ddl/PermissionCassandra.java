package com.learning.datasource.ddl;

import com.datastax.driver.core.Session;
import com.learning.publics.DDLResult;
import com.learning.publics.DMLResult;
import com.learning.schema.KeyMap;
import com.learning.schema.output.DDLOperation;
import com.learning.schema.output.DMLOperation;

import java.util.List;

public class PermissionCassandra {

    public DDLOperation createRole(Session session, String roleName, List<KeyMap> optionList){
        DDLResult result = new DDLResult();
        PermissionCQL operation = new PermissionCQL();
        return result.getResult(session,"system",
                operation.addCreateRole(roleName)
                        .addRoleOptions(optionList).getStringbuilder().toString(),
                "create role " + roleName + " successful!");
    }

    public DDLOperation alterRole(Session session, String roleName, List<KeyMap> optionList){
        DDLResult result = new DDLResult();
        PermissionCQL operation = new PermissionCQL();
        return result.getResult(session,"system",
                operation.addAlterRole(roleName)
                        .addRoleOptions(optionList).getStringbuilder().toString(),
                "alter role " + roleName + " successful!");
    }

    public DDLOperation dropRole(Session session, String roleName){
        DDLResult result = new DDLResult();
        PermissionCQL operation = new PermissionCQL();
        return result.getResult(session,"system",
                operation.addDropRole(roleName).getStringbuilder().toString(),
                "drop role " + roleName + " successful!");
    }

    public DDLOperation grantRole(Session session, String src_roleName, String dst_roleName){
        DDLResult result = new DDLResult();
        PermissionCQL operation = new PermissionCQL();
        return result.getResult(session,"system",
                operation.addGrantRole(src_roleName,dst_roleName).getStringbuilder().toString(),
                "grant role " + src_roleName + " to role "+dst_roleName+" successful!");
    }

    public DDLOperation revokeRole(Session session, String src_roleName, String dst_roleName){
        DDLResult result = new DDLResult();
        PermissionCQL operation = new PermissionCQL();
        return result.getResult(session,"system",
                operation.addRevokeRole(src_roleName,dst_roleName).getStringbuilder().toString(),
                "revoke role " + src_roleName + " from role "+dst_roleName+" successful!");
    }

    public DMLOperation listRoles(Session session, String roleName, boolean isRecursive){
        DMLResult result = new DMLResult();
        PermissionCQL operation = new PermissionCQL();
        return result.getResult(session, "system",
                operation.addListRole(roleName,isRecursive).getStringbuilder().toString(),
                "list roles has done!",
                -1,-1,true);
    }

    public DDLOperation createUser(Session session, String roleName, String password, boolean isSuperUser){
        DDLResult result = new DDLResult();
        PermissionCQL operation = new PermissionCQL();
        return result.getResult(session,"system",
                operation.addCreateUser(roleName)
                        .addPassword(password)
                        .addUserOption(isSuperUser)
                        .getStringbuilder().toString(),
                "create user " + roleName +" successful!");
    }

    public DDLOperation alterUser(Session session, String roleName, String password, boolean isSuperUser){
        DDLResult result = new DDLResult();
        PermissionCQL operation = new PermissionCQL();
        return result.getResult(session,"system",
                operation.addAlterUser(roleName)
                        .addPassword(password)
                        .addUserOption(isSuperUser)
                        .getStringbuilder().toString(),
                "alter user " + roleName +" successful!");
    }

    public DDLOperation dropUser(Session session, String roleName){
        DDLResult result = new DDLResult();
        PermissionCQL operation = new PermissionCQL();
        return result.getResult(session,"system",
                operation.addDropUser(roleName).getStringbuilder().toString(),
                "drop user " + roleName +" successful!");
    }

    public DDLOperation grantPermission(Session session, boolean isAll, String permission, String res, String roleName){
        DDLResult result = new DDLResult();
        PermissionCQL operation = new PermissionCQL();
        return result.getResult(session,"system",
                operation.addGrantPermission(isAll,permission,res,roleName).getStringbuilder().toString(),
                "grant " + (isAll?"ALL PERMISSIONS":("permission "+permission)) +" on "+res+" to role "+roleName+" successful!");
    }

    public DDLOperation revokePermission(Session session, boolean isAll, String permission, String res, String roleName){
        DDLResult result = new DDLResult();
        PermissionCQL operation = new PermissionCQL();
        return result.getResult(session,"system",
                operation.addRevokePermission(isAll,permission,res,roleName).getStringbuilder().toString(),
                "revoke " + (isAll?"ALL PERMISSIONS":("permission "+permission)) +" on "+res+" from role "+roleName+" successful!");
    }

    public DMLOperation listPermissions(Session session, boolean isAll, String permission, String res, String roleName, boolean isRecursive){
        DMLResult result = new DMLResult();
        PermissionCQL operation = new PermissionCQL();
        return result.getResult(session, "system",
                operation.addListPermissions(isAll,permission,res,roleName,isRecursive).getStringbuilder().toString(),
                "list permissions has done!",
                -1,-1,true);
    }

}
