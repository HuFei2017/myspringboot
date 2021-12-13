package com.learning.schema;

public class CassColumnInfo {

    private String column_name;

    /**
     * ASCII
     | BIGINT
     | BOOLEAN
     | COUNTER
     | DATE
     | DECIMAL
     | DOUBLE
     | DURATION
     | FLOAT
     | INET
     | INT
     | SMALLINT
     | TEXT
     | TIME
     | TIMESTAMP
     | TIMEUUID
     | TINYINT
     | UUID
     | VARCHAR
     | VARINT
     */
    private String column_type;

    private boolean isStatic = false;

    private boolean isPrimary = false;

    public String getColumn_name() {
        return column_name;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    public String getColumn_type() {
        return column_type;
    }

    public void setColumn_type(String column_type) {
        this.column_type = column_type;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }
}
