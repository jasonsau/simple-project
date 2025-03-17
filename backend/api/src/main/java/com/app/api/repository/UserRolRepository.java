package com.app.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entities.UserRol;

@Repository
public interface UserRolRepository extends JpaRepository<UserRol, Long>{

}
