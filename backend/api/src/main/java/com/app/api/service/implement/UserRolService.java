package com.app.api.service.implement;

import org.springframework.stereotype.Service;
import com.app.api.repository.UserRolRepository;
import com.app.api.service.contract.IUserRolService;
import com.app.entities.Rol;
import com.app.entities.User;
import com.app.entities.UserRol;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserRolService implements IUserRolService{


    private final UserRolRepository userRolRepository;

    @Override
    public UserRol save(User user, Rol rol) {
        UserRol userRol = new UserRol();
        userRol.setRol(rol);
        userRol.setUser(user);
        return userRolRepository.save(userRol);
    }

}
