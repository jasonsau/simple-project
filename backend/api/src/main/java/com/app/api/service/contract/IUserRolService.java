package com.app.api.service.contract;

import com.app.entities.Rol;
import com.app.entities.User;
import com.app.entities.UserRol;

public interface IUserRolService {

    UserRol save(User user, Rol rol);

}
