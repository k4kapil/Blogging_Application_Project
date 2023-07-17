package com.kk.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.kk.exceptions.ResourceNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kk.config.AppConstants;
import com.kk.entity.Role;
import com.kk.entity.User;
import com.kk.payloads.UserDto;
import com.kk.repository.RoleRepository;
import com.kk.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public UserDto createUser(UserDto dto) {
		User user= dtoToUser(dto);
		User savedUser= userRepository.save(user);
		return usertoDto(savedUser);
		
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) 
	{
		User user= userRepository.findById(userId)
				   .orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());	
		user.setAbout(userDto.getAbout());
		
		User updatedUser= userRepository.save(user);
		UserDto userDto1= usertoDto(updatedUser);
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer id) 
	{
		User user= userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "Id", id));
		return usertoDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> user= userRepository.findAll();
		List<UserDto> userDto= user.stream().map(e-> usertoDto(e)).collect(Collectors.toList());
		return userDto;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user= userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		userRepository.delete(user);
	}

	// converting one object to other object (manually)
	
	public User dtoToUser(UserDto userDto) {
/*		User user= new User();
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
*/		
		// or by using ModelMapper class
		
		User user= modelMapper.map(userDto, User.class);
		return user ;
		
		
		
	}
	
	private UserDto usertoDto(User user) 
	{
/*		UserDto userDto= new UserDto();
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		userDto.setAbout(user.getAbout());		
*/
		UserDto userDto= modelMapper.map(user, UserDto.class);
		return userDto ;
	
	}

	@Override
	public UserDto registerNewUser(UserDto dto) {
		User user = modelMapper.map(dto, User.class);
		// encode password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
	
		// roles
		Role role = roleRepository.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		
		// save user
		User newUser = userRepository.save(user);
		return modelMapper.map(newUser, UserDto.class);
	
	}
}