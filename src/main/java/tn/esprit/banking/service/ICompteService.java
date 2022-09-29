package tn.esprit.banking.service;

import org.springframework.http.ResponseEntity;
import tn.esprit.banking.entity.Compte;
import tn.esprit.banking.response.ResponseMessage;

import java.util.List;

public interface ICompteService {

    public List<Compte> getAllComptes();
    public Compte getCompteById(long id);
    public Compte addCompte(Compte compte);
    public ResponseEntity<ResponseMessage> deleteCompte(long id);
    public ResponseEntity<ResponseMessage> updateCompte(Compte compte);

}
