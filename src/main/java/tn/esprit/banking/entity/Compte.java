package tn.esprit.banking.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import tn.esprit.banking.enums.Nature;
import tn.esprit.banking.enums.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Compte implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull(message = "Name may not be null")
    private String nomComplet;
    private Nature natureCompte;
    private String rib;
    private String iban;
    private String codeBic;
    private BigDecimal solde;
    private String email;
    private boolean emailsent;
    private boolean status;
    private Type type;




    @ManyToOne
    @JsonIgnore
    User user;
    @OneToMany(mappedBy="compte")
    private Collection<Operation> operations;



}
