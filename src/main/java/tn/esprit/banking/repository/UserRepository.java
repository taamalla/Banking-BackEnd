package tn.esprit.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.banking.entity.User;
import tn.esprit.banking.enums.ERole;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByCin(String cin);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);


    Boolean existsByCin(String cin);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'CLIENT'")
    List<User> findClients();



}
