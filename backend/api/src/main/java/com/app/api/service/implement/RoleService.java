package com.app.api.service.implement;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.app.api.repository.RoleRepository;
import com.app.api.service.contract.IRoleService;
import com.app.entities.Rol;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService{

    private final RoleRepository roleRepository;


    @Override
    public Page<Rol> findAll(Pageable pageable) {
        return this.roleRepository.findAll(pageable);
    }

    @Override
    public Page<Rol> findAll(Pageable pageable, Specification<Rol> specification) {
        return this.roleRepository.findAll(specification, pageable);
    }

    @Override
    public void save(Rol rol) {
        this.roleRepository.save(rol);
    }

    @Override
    public void delete(Rol t) {
        this.roleRepository.delete(t);
    }

    @Override
    public Rol findById(Long id) {
        return this.findById(id);
    }

    @Override
    public Long count() {
        return this.roleRepository.count();
    }

    @Override
    public List<Rol> findRolesByUserId(Long id) {
        return this.roleRepository.findByUserId(id);
    }

    public Rol findByName(String name) {
        return roleRepository.findByName(name.toUpperCase()).orElse(null);
    }

}
