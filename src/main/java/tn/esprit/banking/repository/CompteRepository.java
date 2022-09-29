package tn.esprit.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.banking.entity.Compte;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {
}
