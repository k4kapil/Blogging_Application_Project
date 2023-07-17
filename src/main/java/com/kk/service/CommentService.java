package com.kk.service;

import com.kk.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto dto, Integer postId);
	void deleteComment(Integer commentId);
}
