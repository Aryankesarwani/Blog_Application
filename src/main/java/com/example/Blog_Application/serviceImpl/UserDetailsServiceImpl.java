package com.example.Blog_Application.serviceImpl;

import com.example.Blog_Application.Repo.UserRepo;
import com.example.Blog_Application.entity.User;
import com.example.Blog_Application.entity.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if(user==null){
            System.out.println("User Not Found Exception");
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);
    }
}
