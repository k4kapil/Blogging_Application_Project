package com.kk.controller;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kk.config.AppConstants;
import com.kk.payloads.ApiResponse;
import com.kk.payloads.PostDto;
import com.kk.payloads.PostResponse;
import com.kk.service.FileService;
import com.kk.service.PostService;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	String path;
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
			@PathVariable Integer categoryId, @PathVariable Integer userId) {
		
	PostDto dto= postService.createPost(postDto, userId, categoryId);
	return new ResponseEntity<PostDto>(dto, HttpStatus.CREATED);
	}
	
	
	// get post by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable("userId") Integer userId){
		List<PostDto> dto=postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(dto, HttpStatus.OK);
	}
	
	// get post by category
		@GetMapping("/category/{categoryId}/posts")
		public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("categoryId") Integer categoryId){
			List<PostDto> dto=postService.getPostByCategory(categoryId);
			return new ResponseEntity<List<PostDto>>(dto, HttpStatus.OK);
		}
	
		// get all posts			by using pagination
		@GetMapping("/posts")
		public ResponseEntity<PostResponse> getAllPosts(
				@RequestParam(value="pageNumber", defaultValue =AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
				@RequestParam(value="pageSize",defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
				@RequestParam(value="sortBy",defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
				@RequestParam(value="sortDirection",defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDirection){
			
			PostResponse postResponse = postService.getAllPost(pageNumber, pageSize, sortBy, sortDirection);
			return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
		}
		
		// get post details by id
		@GetMapping("/posts/{postId}")
		public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Integer postId){
			PostDto post= postService.getPostByPostId(postId);
			return new ResponseEntity<PostDto>(post, HttpStatus.OK);
		}
		
		
		// form admin only
		
		// delete post
		@PreAuthorize("hasRole('ADMIN')")
		@DeleteMapping("/posts/{postId}")
		public ApiResponse deletePost(@PathVariable("postId") Integer postId) {
			postService.deletPost(postId);
			return new ApiResponse("Post deleted successfully !!", true);
		}
		
		// update post
		@PutMapping("/posts/{postId}")
		public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto ,@PathVariable("postId") Integer postId) {
			PostDto updatedPost= postService.updatePost(postDto, postId);
			return  new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
		}
		
		// search by title
		@GetMapping("/posts/search/{keyword}")
		public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keyword") String keyword){
			List<PostDto> searchPost = postService.searchPost(keyword);
			return new ResponseEntity<List<PostDto>>(searchPost,HttpStatus.OK);
		}
		
		// post image upload
		@PostMapping("/post/image/upload/{postId}")
		public ResponseEntity<PostDto> uploadImage(
				@RequestParam("image") MultipartFile image,
				@PathVariable Integer postId) throws IOException{
			
			PostDto postDto = postService.getPostByPostId(postId);
			String imageName = fileService.uploadImage(path, image);
			
			postDto.setImageName(imageName);
			 PostDto updatePost = postService.updatePost(postDto, postId);
			 return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
		}
		
		// method to serve image
		@GetMapping(value= "/images/{imageName}", produces=MediaType.IMAGE_JPEG_VALUE)
		public void downloadImage(@PathVariable("imageName") String imageName,
				HttpServletResponse response) throws IOException  {
			
			InputStream resource = fileService.getResource(path, imageName);
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			StreamUtils.copy(resource, response.getOutputStream());
		}
}