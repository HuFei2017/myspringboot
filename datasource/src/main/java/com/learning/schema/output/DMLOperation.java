package com.learning.schema.output;

import com.learning.schema.TableInstance;

public class DMLOperation {

    private TableInstance resultSet;
    private DDLOperation operationState;

    public DMLOperation getResult(String state, TableInstance result, String message, boolean has){
        if(state.equals("OK")) {
            if(has)
                return new DMLOperation(result, new DDLOperation(200, state, message));
            else
                return new DMLOperation(result, new DDLOperation(201, state, message));
        }
        else
            return new DMLOperation(result,new DDLOperation(500,state,message));
    }

    public DMLOperation() {
    }

    private DMLOperation(TableInstance resultSet, DDLOperation operationState) {
        this.resultSet = resultSet;
        this.operationState = operationState;
    }

    public TableInstance getResultSet() {
        return resultSet;
    }

    public void setResultSet(TableInstance resultSet) {
        this.resultSet = resultSet;
    }

    public DDLOperation getOperationState() {
        return operationState;
    }

    public void setOperationState(DDLOperation operationState) {
        this.operationState = operationState;
    }

}
