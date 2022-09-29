package tn.esprit.banking.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.banking.enums.TypeTransaction;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private LocalDate date;
    private TypeTransaction transactionType;
    private Boolean isNegativeTx;
    private Boolean isRevertedTransaction;
    private BigDecimal movement;


    @ManyToOne
    @JoinColumn(name = "Operation_Id")
    private Operation operation;

    public Transaction(LocalDate date, TypeTransaction txtype, boolean b1, Operation operation, BigDecimal m) {
        this.date = date;
        this.transactionType =txtype;
        this.isNegativeTx = b1;
        this.operation = operation;
        this.movement = m;
    }
}
