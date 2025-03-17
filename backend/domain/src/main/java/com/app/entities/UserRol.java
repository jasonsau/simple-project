package com.app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users_roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRol {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_sequence")
    @SequenceGenerator(name = "user_role_sequence", sequenceName = "user_role_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name="userid")
    private User user;
    @ManyToOne
    @JoinColumn(name="roleid")
    private Rol rol;
}
