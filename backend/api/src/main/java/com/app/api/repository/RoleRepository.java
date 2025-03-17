package com.app.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.entities.Rol;

@Repository
public interface RoleRepository extends JpaRepository<Rol, Long>, JpaSpecificationExecutor<Rol> {


    @Query("SELECT r FROM Rol r  LEFT JOIN UserRol ur on r = ur.rol WHERE ur.user.id = ?1")
    List<Rol> findByUserId(Long id);

    Optional<Rol> findByName(String name);

}
