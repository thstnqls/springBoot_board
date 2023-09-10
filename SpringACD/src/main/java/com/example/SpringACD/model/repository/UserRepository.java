package com.example.SpringACD.model.repository;

import com.example.SpringACD.model.models.User_info;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User_info,String> {
    Optional<User_info> findByUsername(String username);
}
