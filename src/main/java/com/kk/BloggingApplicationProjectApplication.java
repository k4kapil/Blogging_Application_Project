package com.kk;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kk.config.AppConstants;
import com.kk.entity.Role;
import com.kk.repository.RoleRepository;

@SpringBootApplication
public class BloggingApplicationProjectApplication implements CommandLineRunner{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(BloggingApplicationProjectApplication.class, args);
	}
	
	@Bean
	ModelMapper modelMapper() {
		ModelMapper model= new ModelMapper();
		return model;
	}
	
	@Override
	public void run(String... args) throws Exception {
	System.out.println(passwordEncoder.encode("anjali123"));	
		
	try {
		Role role= new Role();
		role.setRoleId(AppConstants.ADMIN_USER);
		role.setName("ROLE_ADMIN");
		
		Role role1= new Role();
		role1.setRoleId(AppConstants.NORMAL_USER);
		role1.setName("ROLE_NORMAL");
		
		List<Role> list = List.of(role, role1);
		List<Role> result = roleRepository.saveAll(list);
		
		result.forEach(r->{
			System.out.println(r.getName());
			});
		
	} catch (Exception e) {
		e.printStackTrace();
	}
}
}