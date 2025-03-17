package com.app.api.service.contract;

import org.springframework.security.core.Authentication;

import com.app.api.domain.dto.UserRegisterDto;
import com.app.entities.User;

public interface IUserService extends CommonOperation<User> {
    User findByEmail(String email);

    User registerUser(UserRegisterDto userRegisterDto);
    Authentication login(String username, String password);
    String generateToken(Authentication authentication);
    String generateRefreshToken(Authentication authentication);
}
