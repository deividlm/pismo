package com.deividlm.pismo.operations;

import com.deividlm.pismo.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class OperationFactory {

    private Map<String, Operation> operations;

    @Autowired
    public OperationFactory(Set<Operation> operationSet){
        createOperation(operationSet);
    }

    public Operation findOperation(String transactionType) {
        return operations.get(transactionType);
    }
    private void createOperation(Set<Operation> operationSet) {
        operations = new HashMap<String, Operation>();
        operationSet.forEach(
                operation ->operations.put(operation.getOperationName(), operation));
    }

}
