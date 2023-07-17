package com.kk.service;

import java.util.List;

import com.kk.payloads.UserDto;

public interface UserService {
	UserDto registerNewUser(UserDto dto);
	UserDto createUser(UserDto dto);
	UserDto updateUser(UserDto dto, Integer id);
	UserDto getUserById(Integer id);	
	List<UserDto> getAllUsers();
	void deleteUser(Integer userId);
}
