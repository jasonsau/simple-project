package com.app.api.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.api.repository.UserRepository;
import com.app.api.service.implement.RoleService;

@Service
public class JpaUserDetails implements UserDetailsService{

    private final UserRepository userRepository;
    private final RoleService roleService;

    public JpaUserDetails(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new SecurityUser(
            userRepository.findByEmail(username), roleService
        );
    }

}
