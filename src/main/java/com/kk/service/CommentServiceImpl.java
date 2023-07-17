package com.kk.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kk.entity.Comment;
import com.kk.entity.Post;
import com.kk.exceptions.ResourceNotFoundException;
import com.kk.payloads.CommentDto;
import com.kk.repository.CommentRepository;
import com.kk.repository.PostRepository;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto dto, Integer postId) {
	Post post= postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post id", postId));
	Comment comment = modelMapper.map(dto, Comment.class);
	comment.setPost(post);
	Comment savedComment = commentRepository.save(comment);
	return modelMapper.map(savedComment, CommentDto.class);
	}
	
	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "comment id", commentId));
		commentRepository.delete(comment);
	}

}
