package com.example.Blog_Application;

import com.example.Blog_Application.Repo.RoleRepo;
import com.example.Blog_Application.config.AppConstants;
import com.example.Blog_Application.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Blog_Application implements CommandLineRunner {
	@Autowired
	RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(Blog_Application.class, args);
	}
	public void run(String... args) throws Exception{
		try{
			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setRole("ADMIN_USER");
			Role role1 = new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setRole("NORMAL_USER");
			List<Role> roles = roleRepo.saveAll(List.of(role,role1));
//			for(Role role2 : roles) System.out.println(role2.getRole());

		}catch (Exception e){
			e.printStackTrace();
		}
	}

	private static final ModelMapper modelMapper = new ModelMapper();

	@Bean
	public static ModelMapper getModelMapper() {
		return modelMapper;
	}
}
