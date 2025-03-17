package com.app.api.controller;

import com.app.api.domain.dto.UserRegisterDto;
import com.app.api.domain.dto.UserRequestLogin;
import com.app.api.service.contract.IUserService;
import com.app.entities.User;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController extends Controller{

    @Value("${jwt.duration}")
    Long duration;

    private final IUserService iUserService;


    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterDto userRegisterDto) {

        try{ 
            User user = iUserService.registerUser(userRegisterDto);
            if(user == null) {
                return ResponseEntity.badRequest().body(message.error("400", "Error el usuario no ha podido ser creado"));
            }

            return ResponseEntity.ok().body(message.ok(user));
        } catch(Exception exception) {
            System.out.println(exception.getMessage());
            return ResponseEntity.badRequest().body(message.error("400", "Error el usuario no ha podido ser creado"));

        }
    }

    @PostMapping(path="/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginUser(@RequestBody UserRequestLogin userRequestLogin) {
        try {
            User userLogin = iUserService.findByEmail(userRequestLogin.getUsername());
            if(userLogin == null) return ResponseEntity.status(404).body(message.error("404", "Credenciales incorrectas"));
            Authentication auth = iUserService.login(userRequestLogin.getUsername(), userRequestLogin.getPassword());
            String jwt = iUserService.generateToken(auth);
            String refreshToken = iUserService.generateRefreshToken(auth);
            HashMap<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("refreshToken", refreshToken);
            response.put("duration", duration);
            response.put("username", userRequestLogin.getUsername());
            return ResponseEntity.ok(message.ok(response));
        } catch(AuthenticationException authenticationException) {
            return ResponseEntity.status(404).body(message.error("404", "Credenciales incorrectas"));
        }

    }


}
