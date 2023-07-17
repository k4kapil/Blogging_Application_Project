package com.kk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kk.entity.User;
import com.kk.exceptions.ResourceNotFoundException;
import com.kk.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// loading user from database by user name
		User user= userRepository.findByEmail(username)
		.orElseThrow(()-> new ResourceNotFoundException("User ","username "));
		
		return user;			
	}

}
