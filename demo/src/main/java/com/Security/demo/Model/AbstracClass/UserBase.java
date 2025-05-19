package com.Security.demo.Model.AbstracClass;

import com.Security.demo.Model.Enum.UserRol;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@SuperBuilder
@AllArgsConstructor
public abstract class UserBase extends EntidadAuditable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRol rol;


    public String getUsername() { return username; }

    public String getPassword() {
        return password;
    }


    public Long getId() { return id; }

    public void setUsername(String username) { this.username = username; }


    public UserRol getRol() {
        return rol;
    }

    /* Mas de un rol ---> activar en entidad User
     @Enumerated(EnumType.STRING)
     private Set<Rol> roles;

    }*/
}
