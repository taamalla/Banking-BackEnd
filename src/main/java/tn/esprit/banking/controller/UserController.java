package tn.esprit.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;
import tn.esprit.banking.entity.BanUserDescription;
import tn.esprit.banking.entity.User;
import tn.esprit.banking.service.IUserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping("/findAll")
    @ResponseBody
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> findAll(){

        List<User>  users = (List<User>) userService.getAllUsers();
        return users;
    }
    @GetMapping("/findClients")
    @ResponseBody
  //  @PreAuthorize("hasRole('ADMIN')")
    public List<User> findClients(){
        List<User> users = (List<User>) userService.getClients();
        return users;
    }
    @GetMapping("/findByEmail/{email}")
    @ResponseBody
   // @PreAuthorize("hasRole('ADMIN')")
    public User findByMail(@PathVariable("email") String email){
        User user= userService.getUserByEmail(email);
        return user;
    }

    @GetMapping("/findByCin/{cin}")
    @ResponseBody
  //  @PreAuthorize("hasRole('ADMIN')")
    public User findByCin(@PathVariable("cin") String cin){
        User user = userService.getUserByCin(cin);
        return user;
    }

        @GetMapping("/findById/{id}")
    @ResponseBody
   // @PreAuthorize("hasRole('ADMIN')")
    public User findByCin(@PathVariable("id") long id){
        User user = userService.getUserById(id);
        return user;
    }
    @GetMapping("/findByUsername/{username}")
    @ResponseBody
    //@PreAuthorize("hasRole('ADMIN')")
    public User findByUsername(@PathVariable("username") String username){
        User user = userService.getUserByUsername(username);
        return user;
    }

    @PostMapping ("/add")
    @ResponseBody
    //@PreAuthorize("hasRole('ADMIN')")
    public User add(@RequestBody User userBody){
        User user = userService.addUser(userBody);
        return user;
    }

    @PutMapping("/signUser/{id}")
    @ResponseBody
    //@PreAuthorize("hasRole('ADMIN')")
    public User signUser(@PathVariable("id") Long id){
        return userService.signUser(id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    //  @PreAuthorize("hasRole('ADMIN')")
    void  deleteUser(@PathVariable("id") long id){
        userService.deleteUser(id);
    }
    @PutMapping("/update")
    @ResponseBody
    public User update(@RequestBody User newUser){
        return userService.updateUser(newUser);
    }


    @PutMapping("/banUser")
    @ResponseBody
  //  @PreAuthorize("hasRole('ADMIN')")
    public User banUser(@RequestBody BanUserDescription banUserDesc){
        return userService.banUser(banUserDesc.getUsername(),banUserDesc.getBanRaison());
    }










}
