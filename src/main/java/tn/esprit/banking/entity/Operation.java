package tn.esprit.banking.entity;

import lombok.*;
import tn.esprit.banking.enums.OperationStatus;
import tn.esprit.banking.enums.OperationSubType;
import tn.esprit.banking.enums.OperationType;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Operation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private LocalDate date;
    private BigDecimal amount;
    private Boolean isInternal;
    private OperationType operationtype;
    private OperationSubType operationSubType;
    private OperationStatus operationStatus;
    private Boolean isArchived;

    @ManyToOne
    @JoinColumn(name = "Compte_Id")
    private Compte compte;


    @OneToMany(mappedBy="operation")
    private Collection<Transaction> transactions;


    public Operation(LocalDate date, BigDecimal v, boolean b, OperationType retrieve, OperationSubType regluement_credit, OperationStatus toBeExecuted) {
        this.date = date;
        this.amount = v;
        this.isInternal = b;
        this.operationtype = retrieve;
        this.operationSubType = regluement_credit;
        this.operationStatus = toBeExecuted;
    }


}
