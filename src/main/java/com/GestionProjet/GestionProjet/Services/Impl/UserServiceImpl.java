package com.GestionProjet.GestionProjet.Services.Impl;

import com.GestionProjet.GestionProjet.Entities.User;
import com.GestionProjet.GestionProjet.Repositories.UserRepository;
import com.GestionProjet.GestionProjet.Services.UserService;
import com.GestionProjet.GestionProjet.enumeration.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceImpl implements UserService , UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptEncoder;


    @Override
    public Integer saveUser(User user) {
        //Encode password before saving to DB
        user.setPassword(bCryptEncoder.encode(user.getPassword()));
        return userRepo.save(user).getId();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opt = userRepo.findByUsername(username);

        org.springframework.security.core.userdetails.User springUser=null;

        if(opt.isEmpty()) {
            throw new UsernameNotFoundException("User with username: " +username +" not found");
        }else {
            User user =opt.get();	//retrieving user from DB
            springUser = new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    user.getAuthorities());
        }

        return springUser;
    }
}
