package com.kk.payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
	
	private int id;
	
	@NotEmpty
	@Size(min=4,max=800, message="Content must be of min 4 characters !!")
	private String content;
	
}
