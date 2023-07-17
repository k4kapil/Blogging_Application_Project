package com.kk.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.kk.entity.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserDto {

	private int id;
	@NotEmpty
	@Size(min=4, message="Username must be of min 4 characters !!")
	private String name;
	@Email(message="Email address is not valid !!")
	private String email;
	@NotEmpty
	@Size(min=3, max=10, message="Password must be of min 3 and max 10 characters !!")
	private String password;
	@NotEmpty
	@Size(min=2, message="min 2 characters, Please enter something !!")
	private String about;
	
	private Set<RoleDto> roles= new HashSet<>();

}
