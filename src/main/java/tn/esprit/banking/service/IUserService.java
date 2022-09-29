package tn.esprit.banking.service;

import tn.esprit.banking.entity.User;

import java.util.List;

public interface IUserService {

    public List<User> getAllUsers();
    public List<User> getClients();
    public User getUserById(long id);
    public User getUserByEmail(String email);
    public User getUserByCin(String cin);
    public User getUserByUsername(String username);
    public User addUser(User user);
    public void deleteUser(long id);
    public User signUser(long id);
    public User updateUser(User user);
    public User banUser(String username, String banRaison);



}
