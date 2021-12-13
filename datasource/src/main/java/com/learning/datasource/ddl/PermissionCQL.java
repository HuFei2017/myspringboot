package com.learning.datasource.ddl;

import com.learning.schema.KeyMap;

import java.util.List;

import static org.apache.parquet.Strings.isNullOrEmpty;

public class PermissionCQL {

    private StringBuilder stringbuilder = new StringBuilder();
    public StringBuilder getStringbuilder() {
        return stringbuilder;
    }

    public PermissionCQL addCreateRole(String roleName){
        String cql = "CREATE ROLE IF NOT EXISTS "+roleName;
        stringbuilder.append(cql);
        return this;
    }

    /**
     * role_options          ::=  role_option ( AND role_option )*
     * role_option           ::=  PASSWORD '=' string
     *                          | LOGIN '=' boolean
     *                          | SUPERUSER '=' boolean
     *                          | OPTIONS '=' map_literal
     */
    public PermissionCQL addRoleOptions(List<KeyMap> list){
        StringBuilder cql = new StringBuilder();
        if(list.size()>0){
            cql.append("\n WITH");
            for (KeyMap km : list) {
                cql.append(" ").append(km.getName()).append("=").append(km.getValue()).append("\n AND");
            }
            cql = new StringBuilder(new StringBuilder(cql.substring(0, cql.length() - 4)));
        }
        stringbuilder.append(cql);
        return this;
    }

    public PermissionCQL addAlterRole(String roleName){
        String cql = "ALTER ROLE "+roleName;
        stringbuilder.append(cql);
        return this;
    }

    public PermissionCQL addDropRole(String roleName){
        String cql = "DROP ROLE IF EXISTS "+roleName;
        stringbuilder.append(cql);
        return this;
    }

    public PermissionCQL addGrantRole(String src_roleName, String dst_roleName){
        String cql = "GRANT "+src_roleName+" TO "+dst_roleName;
        stringbuilder.append(cql);
        return this;
    }

    public PermissionCQL addRevokeRole(String src_roleName, String dst_roleName){
        String cql = "REVOKE "+src_roleName+" FROM "+dst_roleName;
        stringbuilder.append(cql);
        return this;
    }

    public PermissionCQL addListRole(String roleName, boolean isRecursive){
        String cql = "LIST ROLES";//which equivalent to 'LIST USERS'
        if(!isNullOrEmpty(roleName))
            cql += " OF "+roleName;
        if(!isRecursive)
            cql += " NORECURSIVE";
        stringbuilder.append(cql);
        return this;
    }

    public PermissionCQL addCreateUser(String roleName){
        String cql = "CREATE USER IF NOT EXISTS "+roleName;
        stringbuilder.append(cql);
        return this;
    }

    public PermissionCQL addPassword(String password){
        String cql = "";
        if(!isNullOrEmpty(password))
            cql += " WITH PASSWORD '"+password+"'";
        stringbuilder.append(cql);
        return this;
    }

    public PermissionCQL addUserOption(boolean isSuperUser){
        String cql = "";
        if(isSuperUser)
            cql += " SUPERUSER";
        else
            cql += " NOSUPERUSER";
        stringbuilder.append(cql);
        return this;
    }

    public PermissionCQL addAlterUser(String roleName){
        String cql = "ALTER USER "+roleName;
        stringbuilder.append(cql);
        return this;
    }

    public PermissionCQL addDropUser(String roleName){
        String cql = "DROP USER IF EXISTS "+roleName;
        stringbuilder.append(cql);
        return this;
    }

    /**
     * permissions                ::=  ALL [ PERMISSIONS ] | permission [ PERMISSION ]
     * permission                 ::=  CREATE | ALTER | DROP | SELECT | MODIFY | AUTHORIZE | DESCRIBE | EXECUTE
     * resource                   ::=  ALL KEYSPACES
     *                                  | KEYSPACE keyspace_name
     *                                  | [ TABLE ] table_name
     *                                  | ALL ROLES
     *                                  | ROLE role_name
     *                                  | ALL FUNCTIONS [ IN KEYSPACE keyspace_name ]
     *                                  | FUNCTION function_name '(' [ cql_type ( ',' cql_type )* ] ')'
     *                                  | ALL MBEANS
     *                                  | ( MBEAN | MBEANS ) string
     */
    public PermissionCQL addGrantPermission(boolean isAll, String permission, String res, String roleName){
        String cql = "GRANT "+(isAll?"ALL PERMISSIONS":(permission+" PERMISSION"))+" ON "+res+" TO "+roleName;
        stringbuilder.append(cql);
        return this;
    }

    /**
     * permissions                ::=  ALL [ PERMISSIONS ] | permission [ PERMISSION ]
     * permission                 ::=  CREATE | ALTER | DROP | SELECT | MODIFY | AUTHORIZE | DESCRIBE | EXECUTE
     * resource                   ::=  ALL KEYSPACES
     *                                  | KEYSPACE keyspace_name
     *                                  | [ TABLE ] table_name
     *                                  | ALL ROLES
     *                                  | ROLE role_name
     *                                  | ALL FUNCTIONS [ IN KEYSPACE keyspace_name ]
     *                                  | FUNCTION function_name '(' [ cql_type ( ',' cql_type )* ] ')'
     *                                  | ALL MBEANS
     *                                  | ( MBEAN | MBEANS ) string
     */
    public PermissionCQL addRevokePermission(boolean isAll, String permission, String res, String roleName){
        String cql = "REVOKE "+(isAll?"ALL PERMISSIONS":(permission+" PERMISSION"))+" ON "+res+" FROM "+roleName;
        stringbuilder.append(cql);
        return this;
    }

    public PermissionCQL addListPermissions(boolean isAll, String permission, String res, String roleName, boolean isRecursive){
        String cql = "LIST "+(isAll?"ALL PERMISSIONS":(permission+" PERMISSIONS"))+" ON "+res+" OF "+roleName+(isRecursive?"":" NORECURSIVE");
        stringbuilder.append(cql);
        return this;
    }

}
