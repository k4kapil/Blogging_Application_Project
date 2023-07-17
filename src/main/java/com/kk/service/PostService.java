package com.kk.service;

import java.util.List;

import com.kk.entity.Post;
import com.kk.payloads.PostDto;
import com.kk.payloads.PostResponse;

public interface PostService {

	// create post
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	// update post
	public PostDto updatePost(PostDto postDto, Integer postId);
	
	// delete post
	public void deletPost(Integer postId);
	
	// get all posts
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);	// by using pagination
	
	// get single post
	public PostDto getPostByPostId(Integer postId);
	
	// get all post by category
	public List<PostDto> getPostByCategory(Integer categoryId);
	
	// get all post by user
	public List<PostDto> getPostByUser(Integer userId);
	
	// search post	
	public List<PostDto> searchPost(String keyword);
}
