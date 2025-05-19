package com.Security.demo.Service;

import com.Security.demo.Model.User;
import com.Security.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserService implements UserDetailsService {


    private UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username antes de buscarlo en el repositorio:   --->" + username);
        Optional<User> buscarUsuario = userRepository.findByUsername(username);
        System.out.println("Usuario buscado en el repositorio: ------>  " + buscarUsuario);
        if(buscarUsuario.isPresent()){
            System.out.println("Load user by Username:  " + buscarUsuario.get().getAuthorities());
            return buscarUsuario.get();

        }else throw new UsernameNotFoundException("No se encontro el usuario");

    }

    public User saveUser(User user){
        return userRepository.save(user);
    }
    }

