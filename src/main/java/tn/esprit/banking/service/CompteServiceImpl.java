package tn.esprit.banking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.esprit.banking.entity.Compte;
import tn.esprit.banking.repository.CompteRepository;
import tn.esprit.banking.response.ResponseMessage;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CompteServiceImpl implements ICompteService {

    @Autowired
    CompteRepository compteRepository;

    @Override
    public List<Compte> getAllComptes() {
        return compteRepository.findAll();
    }

    @Override
    public Compte getCompteById(long id) {
        return compteRepository.findById(id).orElse(null);
    }

    @Override
    public Compte addCompte(Compte newCompte) {
        return compteRepository.save(newCompte);
    }

    @Override
    public ResponseEntity<ResponseMessage> deleteCompte(long id) {

        try {
            compteRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Element deleted sucessfully !"));
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Error deleting element ! "));

        }
    }

    @Override
    public ResponseEntity<ResponseMessage> updateCompte(Compte compte) {
        Compte oldCompte = compteRepository.findById(compte.getId()).orElse(null);
        if (null != compte.getNomComplet()
                && !("".equals(compte.getNomComplet()))
                && !(oldCompte.getNomComplet().equals(compte.getNomComplet()))) {
            oldCompte.setNomComplet(compte.getNomComplet());
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Please Fill the value of nomComplet"));
        }
        if (null != compte.getNatureCompte()
                && oldCompte.getNatureCompte() != compte.getNatureCompte()) {
            oldCompte.setNatureCompte(compte.getNatureCompte());
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Please Fill the value of NatureCompte"));
        }
        if (null != compte.getRib()
                && !("".equals(compte.getRib().trim()))
                && !(oldCompte.getRib().equals(compte.getRib()))) {
            oldCompte.setRib(compte.getRib());
        }
        if (null != compte.getIban()
                && !("".equals(compte.getIban().trim()))
                && !(oldCompte.getIban().equals(compte.getIban()))) {
            oldCompte.setIban(compte.getIban());
        }
        if (null != compte.getCodeBic()
                && !("".equals(compte.getIban().trim()))
                && !(oldCompte.getCodeBic().equals(compte.getCodeBic()))) {
            oldCompte.setCodeBic(compte.getCodeBic());
        }
        if (new BigDecimal(0) != compte.getSolde()
                && oldCompte.getSolde() != compte.getSolde()) {
            oldCompte.setSolde(compte.getSolde());
        }
        compte.setUser(oldCompte.getUser());
        try {
            compteRepository.save(compte);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("You have a constraint violation please check !"));

        }
        return  ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Element updated sucessfully !"));
    }
}
