package com.example.CustomerWebsite.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CustomerWebsite.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}