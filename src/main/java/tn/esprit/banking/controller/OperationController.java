package tn.esprit.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.banking.api.OperationAPI;
import tn.esprit.banking.entity.Operation;
import tn.esprit.banking.entity.OperationByStatus;
import tn.esprit.banking.enums.OperationStatus;
import tn.esprit.banking.service.IOperationService;

import java.util.List;

@RestController
public class OperationController implements OperationAPI {

    @Autowired
    IOperationService operationService;


    @Override
    public ResponseEntity<Operation> updateCreditStatus(Integer idCredit, OperationStatus operationStatus) {
        return ResponseEntity.ok(operationService.updateOperationStatus(idCredit,operationStatus));
    }
    @Override
    public ResponseEntity<Operation> save(@RequestBody Operation operation) {
        return ResponseEntity.ok(operationService.addOperation(operation));
    }
    // @GetMapping(value = "/operations/findbyid/{id}")
    @Override
    public Operation getOperationById(Integer id) {
        return operationService.getOperationById(id);
    }

    //    @GetMapping(value =  "/operations/")
//	@ResponseBody
    @Override
    public List<Operation> findAllByArchived(Boolean archived) {
        return operationService.getArchivedOperation(archived);
    }

    // @GetMapping(value = "/operations/{id}")
    @Override
    public List<Operation> findAllByUser(Integer id) {
        return operationService.getAllOperationByClient(id);
    }

    // @GetMapping(value = "/operations/{id}/{status}")
    @Override
    public List<Operation> getAllOperationByClientAndStatus(Integer id, OperationStatus operationStatus) {
        return operationService.getAllOperationByClientAndStatus(id,operationStatus);
    }

    @GetMapping("/operation/getStatistics")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OperationByStatus>> getStats() {
        List<OperationByStatus> stats = operationService.getOperationFormsStats();
        return ResponseEntity.status(HttpStatus.OK).body(stats);
    }

    @GetMapping("/operation/findAll")
    @ResponseBody
    public List<Operation> findAll() {
        List<Operation> ops = (List<Operation>) operationService.getAllOperations();
        return ops;
    }
}
