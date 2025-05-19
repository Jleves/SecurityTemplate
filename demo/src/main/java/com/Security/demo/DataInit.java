package com.Security.demo;


import com.Security.demo.Model.AbstracClass.UserBase;
import com.Security.demo.Model.Enum.UserRol;
import com.Security.demo.Model.User;
import com.Security.demo.Repository.UserRepository;
import com.Security.demo.Security.PasswordEncoder;
import com.Security.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataInit implements CommandLineRunner {


    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public DataInit(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;

        this.passwordEncoder = passwordEncoder;
    }




    @Override
    public void run(String... args) throws Exception {


        User admin = User.builder().createdAt(LocalDateTime.now())
                .username("admin")
                .password(passwordEncoder.bCryptPasswordEncoder().encode("admin"))
                .rol(UserRol.ADMINISTRADOR)
                .build();
        userRepository.save(admin);

        User user = User.builder().createdAt(LocalDateTime.now())
                .username("user")
                .password(passwordEncoder.bCryptPasswordEncoder().encode("user"))
                .rol(UserRol.USER)
                .build();
        userRepository.save(user);


        User invitado = User.builder().createdAt(LocalDateTime.now())
                .username("invitado")
                .password(passwordEncoder.bCryptPasswordEncoder().encode("invitado"))
                .rol(UserRol.INVITADO)
                .build();
        userRepository.save(invitado);


        User superadmin = User.builder().createdAt(LocalDateTime.now())
                .username("superadmin")
                .password(passwordEncoder.bCryptPasswordEncoder().encode("superadmin"))
                .rol(UserRol.SUPERADMINISTRADOR)
                .build();
        userRepository.save(superadmin);



    }
}
