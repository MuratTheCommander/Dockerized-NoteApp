package com.example.demo.services;

import com.example.demo.dto.ResponseUserDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,PasswordEncoder passwordEncoder,RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    /*
    1.Check if username already exists
    2.Hash the password
    3.Create and Save user
    4.Assign default role 'ROLE_USER'
    5.Return a simple success response
     */

    public boolean doesUserNameExist(String userName){
       Optional<User> user = userRepository.findByUsername(userName);
        return user.isPresent();
    }


    public ResponseUserDto registerUser(String userName, String password) {
        User userToRegister = new User(userName, passwordEncoder.encode(password));
        userToRegister.setEnabled(true);
        userToRegister.setAccountNonBlocked(true);

        Role role  = roleRepository.findByRoleName("ROLE_USER").orElseThrow();
        userToRegister.getRoles().add(role);

        userRepository.save(userToRegister);

        return new ResponseUserDto(
                userToRegister.getUsername(),
                userToRegister.getCreateDate(),
                userToRegister.getRoles());

    }

    public ResponseUserDto safeLogIn(String userName, String password){
        if(doesUserNameExist(userName)){
            User userToLogIn = userRepository.findByUsername(userName).orElseThrow();
            if(passwordEncoder.matches(password, userToLogIn.getPassword())) {
                return new ResponseUserDto(userToLogIn.getUsername(), userToLogIn.getRoles());
            }
            }return null;
    }

}
