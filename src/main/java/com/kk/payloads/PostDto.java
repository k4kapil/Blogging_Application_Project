package com.kk.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.kk.entity.Comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	
	private int id;
	@NotEmpty
	@Size(min=4, message="Title must be of min 4 characters !!")
	private String title;
	@NotEmpty
	@Size(min=4,max=800, message="Content must be of min 4 characters !!")
	private String content;
	private String imageName;
	private Date addedDate;
	private CategoryDto category;
	private UserDto user;
	@NotEmpty
	@Size(min=4,max=800, message="Content must be of min 4 characters !!")
	private Set<CommentDto> comments= new HashSet<>();
}
