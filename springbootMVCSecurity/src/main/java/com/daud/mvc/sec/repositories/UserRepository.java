package com.daud.mvc.sec.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.daud.mvc.sec.entities.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

	@Query("select u from Users u where u.email=:email")
    Users getUserByEmail(@Param("email") String email);
	

}
