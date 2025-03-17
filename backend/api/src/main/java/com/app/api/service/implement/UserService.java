package com.app.api.service.implement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.api.domain.dto.UserRegisterDto;
import com.app.api.repository.UserRepository;
import com.app.api.service.contract.IRoleService;
import com.app.api.service.contract.IUserRolService;
import com.app.api.service.contract.IUserService;
import com.app.entities.Rol;
import com.app.entities.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    @Value("${jwt.duration}")
    Long duration;

    private final UserRepository userRepository;
    private final IRoleService roleService;
    private final IUserRolService iUserRolService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    @Override
    public Page<User> findAll(Pageable pageable, Specification<User> specification) {
        return this.userRepository.findAll(specification, pageable);
    }

    @Override
    public void save(User t) {
        this.userRepository.save(t);
    }

    @Override
    public void delete(User t) {
        this.userRepository.delete(t);
    }

    @Override
    public User findById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public Long count() {
        return this.userRepository.count();
    }

    @Transactional
    public User registerUser(UserRegisterDto userRegisterDto){
        Rol rolDefault = roleService.findByName("USER");
        if(rolDefault == null) return null;

        String passwordEncode = passwordEncoder.encode(userRegisterDto.getPassword());
        User user = new User();
        user.setEmail(userRegisterDto.getEmail());
        user.setPassword(passwordEncode);
        user.setName(userRegisterDto.getName());
        this.save(user);
        iUserRolService.save(user, rolDefault);
        return user;

    }

    public Authentication login(String username, String password) {
        Authentication auth = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
        return this.authenticationManager.authenticate(auth);
    }

    public String generateToken(Authentication authentication) {
        return jwtService.createToken(authentication, duration, null);
    }

    public String generateRefreshToken(Authentication authentication) {
        return jwtService.createToken(authentication, duration * 24, "REFRESH_TOKEN");

    }


}
