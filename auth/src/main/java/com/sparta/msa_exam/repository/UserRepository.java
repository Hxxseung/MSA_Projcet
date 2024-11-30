package com.sparta.msa_exam.repository;


import com.sparta.msa_exam.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
}