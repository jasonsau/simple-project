package com.app.api.service.contract;

import java.util.List;

import com.app.entities.Rol;

public interface IRoleService extends CommonOperation<Rol> {

    List<Rol> findRolesByUserId(Long id);

    Rol findByName(String name);

}
