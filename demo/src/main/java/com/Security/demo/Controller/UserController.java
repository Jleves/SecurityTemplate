package com.Security.demo.Controller;

import com.Security.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
/*
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> buscarUsuarioId(@PathVariable Long id){

        ResponseEntity<UserDTO> resultado= ResponseEntity.ok(usuarioService.usuarioId(id).orElseThrow(()->new ResourceNotFoundException("Usuario no encontrado")));
        System.out.println("Resultado en controller   --->  " + resultado);
        return resultado;
    }

 */
}
