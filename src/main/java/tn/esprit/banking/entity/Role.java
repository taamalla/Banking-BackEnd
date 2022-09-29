package tn.esprit.banking.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.banking.enums.ERole;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public ERole getName() {
        return name;
    }
    public void setName(ERole name) {
        this.name = name;
    }
    public Role(ERole name) {
        super();
        this.name = name;
    }


}

