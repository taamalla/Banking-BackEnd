package tn.esprit.banking.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tn.esprit.banking.entity.Operation;
import tn.esprit.banking.entity.OperationByStatus;
import tn.esprit.banking.entity.Transaction;
import tn.esprit.banking.enums.OperationStatus;
import tn.esprit.banking.enums.OperationType;
import tn.esprit.banking.enums.TypeTransaction;
import tn.esprit.banking.repository.OperationRepository;
import tn.esprit.banking.validator.OperationValidator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OperationServiceImpl implements IOperationService{
    @Autowired
    OperationRepository operationRepository;


    @Override
    public Operation addOperation(Operation operation) {

        List<String> errors = OperationValidator.validate(operation);
        if (!errors.isEmpty()) {
            log.error("Operation is not valid {}", operation);
        }
        operation.setOperationStatus(OperationStatus.TO_BE_EXECUTED);
        //  operation.setOperationtype(OperationType.PAYMENT);
        //operation.getCompte().setSolde(new BigDecimal(40000));
        Collection<Transaction> transactions = new ArrayList<>();
        if(operation.getOperationtype().equals(OperationType.PAYMENT) || operation.getOperationtype().equals(OperationType.RETRIEVE)){
            if(operation.getCompte().getSolde().compareTo(operation.getAmount())>0){

                Transaction T1 = new Transaction(operation.getDate(), TypeTransaction.CREDIT,false,operation, operation.getCompte().getSolde());
                Transaction T2 = new Transaction(operation.getDate(), TypeTransaction.CREDIT,false,operation,
                        operation.getAmount().subtract(operation.getCompte().getSolde())
                );
                transactions.add(T1);
                transactions.add(T2);
                operation.getCompte().setSolde(operation.getAmount().subtract(operation.getCompte().getSolde()));
//                if(operation.getCompte() instanceof CurrentAccount){
//                    ((CurrentAccount) operation.getAccount()).setRecoveredAmount(((CurrentAccount) operation.getAccount()).getRecoveredAmount().subtract(T2.getMovement()));
//                }

                //     operation.addTransactions(transactions);
            }else{
                transactions.add(new Transaction(operation.getDate(), TypeTransaction.CREDIT,false,operation,operation.getAmount()));
                operation.setTransactions(transactions);
            }
        }
        //v  operation.setTransactions(transactions);
        operation.setOperationStatus(OperationStatus.EXECUTED);


        return operationRepository.save(operation);
    }

    @Override
    public List<Operation> getAllOperations() {
        return operationRepository.findAll();
    }

    @Override
    public List<Operation> getAllOperationByClient(long acountNumber) {
        return operationRepository.findAll().stream()
                .filter(x-> x.getCompte().getId() == acountNumber)
                .collect(Collectors.toList());
    }

    @Override
    public List<Operation> getArchivedOperation(boolean inArchived) {
        return operationRepository.findAll().stream()
                .filter(x-> x.getIsArchived())
                .collect(Collectors.toList());
    }

    @Override
    public Operation getOperationById(Integer id) {
        return operationRepository.findById(id).orElse(null);
    }

    @Override
    public Operation updateOperationStatus(Integer IdOperation, OperationStatus operationStatus) {

        checkIdOperation(IdOperation);
        checkStatus(operationStatus);
        if (operationStatus.equals(OperationStatus.EXECUTED) || operationStatus.equals(OperationStatus.CANCELLED)) {
            Operation o = operationRepository.getById(IdOperation);
            o.setOperationStatus(operationStatus);
            return o;
        }
        return null;
    }

    @Override
    public List<Operation> getAllOperationByClientAndStatus(long accountNumber, OperationStatus operationStatus) {
     Collection<Operation> operations = getAllOperationByClient(accountNumber);
        return operations.stream().filter(x->x.getOperationStatus().equals(operationStatus)).collect(Collectors.toList());
    }

    @Override
    public List<OperationByStatus> getOperationFormsStats() {
        List<OperationByStatus> stats = new ArrayList<OperationByStatus>();
        List<Operation> operations = new ArrayList<>();
        for (Operation op : getAllOperations()) {

            operations.add(op);
        }

        for (Operation op : operations) {
            OperationByStatus stat = new OperationByStatus();
            stat.setId((int) op.getId());
            int executed = operationRepository.findoperationByStatus((int) op.getId(), OperationStatus.EXECUTED);
            stat.setExecuted(executed);
            int to_be_executed = operationRepository.findoperationByStatus((int) op.getId(), OperationStatus.TO_BE_EXECUTED);
            stat.setTo_be_executed(to_be_executed);
            int cancelled = operationRepository.findoperationByStatus((int) op.getId(), OperationStatus.CANCELLED);
            stat.setCancelled(cancelled);
            stats.add(stat);

        }
        return stats;
    }

    private void checkIdOperation(Integer idopt) {
        if (idopt == null) {
            log.error("Operation ID is NULL");

        }
    }

    private void checkStatus(OperationStatus operationStatus) {
        if (!StringUtils.hasLength(String.valueOf(operationStatus))) {
            log.error("Operation status is NULL");
            //  throw new InvalidOperationException("IS not allowed to chagne status to null",
            //          ErrorCodes.CREDIT_NON_MODIFIABLE);
        }

    }

}
