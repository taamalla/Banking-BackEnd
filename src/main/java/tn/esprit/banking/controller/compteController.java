package tn.esprit.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.esprit.banking.entity.Compte;
import tn.esprit.banking.response.ResponseMessage;
import tn.esprit.banking.service.ICompteService;
import tn.esprit.banking.service.IUserService;

import java.util.List;

@RestController
@RequestMapping("/compte")
public class compteController {

    @Autowired
    ICompteService compteService;

    @Autowired
    IUserService userService;


    @GetMapping("/findAll")
    @ResponseBody
    public List<Compte> findAll(){
        List<Compte> comptes = (List<Compte>) compteService.getAllComptes();
        return comptes;
    }

    //create compte after create user (to affect a compte to a user)
    @PostMapping("/add/{id}")
    @ResponseBody
    public Compte addCompte(@RequestBody Compte compte, @PathVariable("id") Long id){
        compte.setUser(userService.getUserById(id));
        Compte compteResult = compteService.addCompte(compte);
        return compteResult;
    }

    @GetMapping("/findById/{id}")
    @ResponseBody
    public Compte findById(@PathVariable("id") Long id){
        Compte compte = compteService.getCompteById(id);
        return compte;
    }


    @DeleteMapping("/delete/{id}")
    @ResponseBody
  //  @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<ResponseMessage> delete(@PathVariable("id") Long id){
        return compteService.deleteCompte(id);
    }

    @PutMapping("/update")
    @ResponseBody
   // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> update(@RequestBody Compte compte){
        return compteService.updateCompte(compte);
    }



}
