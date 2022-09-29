package tn.esprit.banking.service;

import tn.esprit.banking.entity.Operation;
import tn.esprit.banking.entity.OperationByStatus;
import tn.esprit.banking.enums.OperationStatus;

import java.util.List;

public interface IOperationService {

    public Operation addOperation(Operation operation);
    public List<Operation> getAllOperations();
    public List<Operation> getAllOperationByClient(long acountNumber);
    public List<Operation> getArchivedOperation(boolean inArchived);
    public Operation getOperationById(Integer id);
    public Operation updateOperationStatus(Integer idOperation, OperationStatus operationStatus);

    List<Operation> getAllOperationByClientAndStatus(long accountNumber, OperationStatus operationStatus);
     List<OperationByStatus> getOperationFormsStats();
}
