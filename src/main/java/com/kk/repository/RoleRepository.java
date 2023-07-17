package com.kk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kk.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
