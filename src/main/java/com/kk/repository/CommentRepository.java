package com.kk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kk.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
