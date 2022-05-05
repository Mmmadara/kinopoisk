package com.example.demo.service;

import com.example.demo.exceptions.UserExistException;
import com.example.demo.entities.ERole;
import com.example.demo.entities.User;
import com.example.demo.payload.request.SignUpRequest;
import com.example.demo.repository.UserRepo;
import com.example.demo.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserService {
    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User createUser(SignUpRequest userIn){
        User user = new User();
        user.setEmail(userIn.getEmail());
        user.setName(userIn.getFirstName());
        user.setLastname(userIn.getLastName());
        user.setUsername(userIn.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userIn.getPassword()));
        user.getRole().add(ERole.ROLE_USER);

        try {
            LOG.info("Saving User {}", userIn.getEmail());
            return userRepo.save(user);
        }catch (Exception exception){
            LOG.error("Error during registration. {}", exception.getMessage());
            throw new UserExistException("The user " + user.getName() + " already exist. Please check credentials");
        }
    }

    public User updateUser(UserDTO userDTO, Principal principal){
        User user = getUserByPrinciple(principal);
        user.setName(userDTO.getName());
        user.setLastname(userDTO.getLastname());

        return userRepo.save(user);
    }

    public User getCurrentUser(Principal principal){
        return getUserByPrinciple(principal);
    }
    
    public User getUserByPrinciple(Principal principal){
        String username = principal.getName();
        return userRepo.findUserByUsername((username)).orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
    }

    public User getUserById(Long userId) {
        return userRepo.findUserById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
