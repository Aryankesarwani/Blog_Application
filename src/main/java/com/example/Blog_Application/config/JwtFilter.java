package com.example.Blog_Application.config;

import com.example.Blog_Application.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;
    @Autowired
    UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJOaWtoaWwiLCJpYXQiOjE3NDc3NDY0OTksImV4cCI6MTc0Nzc0NjYwN30.tmJGkof4PsXSJiLxxgSvF_ZRlY2pVKSAqHWQI4Y3fWk

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
//        System.out.println("AuthHeader "+authHeader);

        if(authHeader!=null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            username = jwtService.extractUserName(token);
        }

        if((username != null) && (SecurityContextHolder.getContext().getAuthentication() == null)){

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if(jwtService.validateToken(token,userDetails)){

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
//                System.out.println(authToken.toString());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
