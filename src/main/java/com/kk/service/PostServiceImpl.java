package com.kk.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kk.entity.Category;
import com.kk.entity.Post;
import com.kk.entity.User;
import com.kk.exceptions.ResourceNotFoundException;
import com.kk.payloads.PostDto;
import com.kk.payloads.PostResponse;
import com.kk.repository.CategoryRepository;
import com.kk.repository.PostRepository;
import com.kk.repository.UserRepository;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository; 
	
	@Autowired
	private CategoryRepository categoryRepository; 
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId, Integer categoryId) {
		
		User user= userRepository.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","User id", userId));
		
		Category category= categoryRepository.findById(categoryId)
		.orElseThrow(()-> new ResourceNotFoundException("Category","Category id", categoryId));
		
		Post post= modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post post2= postRepository.save(post);
		return modelMapper.map(post2, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post= postRepository.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "post Id",postId));
	post.setTitle(postDto.getTitle());
	post.setContent(postDto.getContent());
	post.setImageName(postDto.getImageName());
	
	Post updatedpost= postRepository.save(post);
	return modelMapper.map(updatedpost, PostDto.class);
	}

	@Override
	public void deletPost(Integer postId) {
		Post post= postRepository.findById(postId)
					.orElseThrow(()-> new ResourceNotFoundException("Post", "post Id",postId));
		postRepository.delete(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize, String sortBy, String sortDirection) {
		Sort sort= null;
		if (sortDirection.equalsIgnoreCase("asc")) 
		{
			sort= Sort.by(sortBy).ascending();
		}
		else 
		{
			sort= Sort.by(sortBy).descending();
		}
		
		Pageable page= PageRequest.of(pageNumber, pageSize,sort);
		Page<Post> pagePost=postRepository.findAll(page);
		List<Post>	posts=pagePost.getContent();
		
		List<PostDto> dtos=  posts.stream().map((post)-> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse= new PostResponse();
		postResponse.setContent(dtos);
		postResponse.setLastPage(pagePost.isLast());
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		
		return postResponse;
	}

	@Override
	public PostDto getPostByPostId(Integer postId) {
		Post post= postRepository.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "post Id",postId));
			return modelMapper.map(post, PostDto.class);	
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		Category category= categoryRepository.findById(categoryId)
						  .orElseThrow(()-> new ResourceNotFoundException("Category", "category Id",categoryId));
		List<Post> posts= postRepository.findByCategory(category);
		List<PostDto> dtos=  posts.stream().map((post)-> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return dtos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user= userRepository.findById(userId)
				  .orElseThrow(()-> new ResourceNotFoundException("User", "user Id",userId));	
		List<Post> posts= postRepository.findByUser(user);
		List<PostDto> dtos=  posts.stream().map((post)-> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return dtos;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Post> listTitle = postRepository.findByTitleContaining(keyword);
		List<PostDto> dtos= listTitle.stream().map((post)-> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return dtos;
	}

	
}
