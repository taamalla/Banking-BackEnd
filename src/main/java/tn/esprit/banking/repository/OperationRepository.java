package tn.esprit.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.banking.entity.Operation;
import tn.esprit.banking.enums.OperationStatus;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Integer> {

    @Query(value = "SELECT COUNT (*) FROM Operation o WHERE o.id=:id AND o.operationStatus=:status", nativeQuery = true)
    public  int findoperationByStatus(@Param("id")int  id,
                                      @Param("status") OperationStatus status);
}
