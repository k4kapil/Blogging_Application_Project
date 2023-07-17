package com.kk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kk.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
