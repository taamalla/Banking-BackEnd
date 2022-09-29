package tn.esprit.banking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.banking.enums.Gender;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String cin;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private Long phone;
    private String address;
    private String email;
    private Date dateCreate;
    private Date dateUpdate;
    private String job;
    private Boolean isSigned;
    private Boolean isBanned;
    private String banRaison;
    private float salary;
    private Gender gender;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch=FetchType.LAZY)
    private List<Compte> comptes;


    public User(String username, String email, String password) {
        this.username = username;
        String[] codes = username.split("-");
        this.cin = codes[0];
        this.firstname = codes[2];
        this.lastname = codes[1];
        this.email = email;
        this.password = password;
    }


}
