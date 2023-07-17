package com.kk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kk.entity.Category;
import com.kk.entity.Post;
import com.kk.entity.User;

public interface PostRepository extends JpaRepository<Post, Integer>{

	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	
	List<Post> findByTitleContaining(String title);
}
