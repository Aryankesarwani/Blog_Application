package com.example.Blog_Application.serviceImpl;

import com.example.Blog_Application.Exception.ResourceAlreadyExistException;
import com.example.Blog_Application.Exception.UserNotFoundException;
import com.example.Blog_Application.Repo.RoleRepo;
import com.example.Blog_Application.Repo.UserRepo;
import com.example.Blog_Application.Transformer.UserTransformer;
import com.example.Blog_Application.config.AppConstants;
import com.example.Blog_Application.entity.Role;
import com.example.Blog_Application.entity.User;
import com.example.Blog_Application.service.JwtService;
import com.example.Blog_Application.service.UserService;
import com.example.Blog_Application.DTO.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    UserTransformer userTransformer;
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JwtService jwtService;
    @Autowired
    RoleRepo roleRepo;


    private BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder(12);


    @Override
    public UserDto registerUser(UserDto userdto) {
        if(userRepo.findByEmail(userdto.getEmail())!=null) throw new ResourceAlreadyExistException("Email Already Exist...");
        User user = userTransformer.DtoToEntity(userdto);
        user.setPassword(bcpe.encode(user.getPassword()));
        Role role = roleRepo.findById(AppConstants.NORMAL_USER).get();
        user.getRoles().add(role);
        User saveduser = userRepo.save(user);
        return userTransformer.EntityToDto(saveduser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer user_Id) throws UserNotFoundException {

        Optional<User> optionaluser = userRepo.findById(user_Id);
        if(optionaluser.isEmpty()){
            throw new UserNotFoundException("User Not Found");
        }
        userRepo.deleteById(user_Id);
        User user = userTransformer.DtoToEntity(userDto);
//        user.setId(user_Id);
        System.out.println(user_Id);
        User savedUser = userRepo.save(user);
        System.out.println(savedUser.getId());
        return userTransformer.EntityToDto(savedUser);
    }

    @Override
    public UserDto getuserById(Integer user_Id) throws UserNotFoundException {
        Optional<User> user = userRepo.findById(user_Id);
        if(user.isEmpty()){
            throw new UserNotFoundException("User Not Found");
        }

        return userTransformer.EntityToDto(user.get());
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> usersList= userRepo.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for(User user : usersList){
            userDtos.add(userTransformer.EntityToDto(user));
        }
        return  userDtos;
    }

    @Override
    public void deleteUser(Integer user_Id) throws UserNotFoundException {
        Optional<User> user = userRepo.findById(user_Id);
        if(user.isEmpty()){
            throw new UserNotFoundException("User Not Found");
        }
        userRepo.deleteById(user_Id);
    }

    @Override
    public User getuserByName(String userName) {
        User user = userRepo.findByName(userName);
        return user;
    }

    @Override
    public String varify(UserDto userDto) {

        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getEmail(),userDto.getPassword()));
        if (authentication.isAuthenticated()) return jwtService.generateToken(userDto.getEmail());
        else throw new RuntimeException("unauthorized user");
    }
}
