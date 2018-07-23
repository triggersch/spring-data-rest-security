package com.apress.hellorest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apress.hellorest.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
