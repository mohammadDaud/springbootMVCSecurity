package com.daud.mvc.sec.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daud.mvc.sec.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

}
