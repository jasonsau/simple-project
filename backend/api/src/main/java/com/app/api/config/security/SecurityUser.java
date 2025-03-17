package com.app.api.config.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.app.api.service.implement.RoleService;
import com.app.entities.Rol;
import com.app.entities.User;

public class SecurityUser implements UserDetails{

    private User user;
    private final RoleService roleService;

    public SecurityUser(User user, RoleService roleService) {
        this.user = user;
        this.roleService = roleService;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        try{
            List<Rol> roles = roleService.findRolesByUserId(this.user.getId());
            if(roles.isEmpty()) {
                return null;
            }
            List<GrantedAuthority> authorities = new ArrayList<>();
            for(Rol rol : roles) {
                authorities.add(new SimpleGrantedAuthority(rol.getName().toUpperCase()));
            }
            return authorities;
        } catch(Exception e){
            return Collections.emptyList();
        }
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

}
