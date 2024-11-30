package com.sparta.msa_exam.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.msa_exam.auth.entity.User;
import com.sparta.msa_exam.auth.valueobject.Username;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(Username username);
	boolean existsByUsername(Username username);


}
