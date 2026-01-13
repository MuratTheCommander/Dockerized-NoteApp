package com.example.demo.security;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class JwtAuthenticationFilter {

    @Autowired
    private final SecurityService securityService;

    public JwtAuthenticationFilter(SecurityService securityService){
        this.securityService = securityService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException, java.io.IOException {

    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if(authHeader == null || !authHeader.startsWith("Bearer ")){
        filterChain.doFilter(request,response);
    }

    String token = authHeader.substring(7);

    if(SecurityContextHolder.getContext().getAuthentication() != null){
        filterChain.doFilter(request,response);
        return;
    }

    try{

        if(!securityService.validateJwt(token)){
            filterChain.doFilter(request,response);
            return;
        }

        String userName = securityService.extractUsername(token);
        List<String> roles = securityService.extractRoles(token);

        List<SimpleGrantedAuthority> authorities = roles.stream().map(
                SimpleGrantedAuthority::new
        ).toList();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userName,
                null,
                authorities
        );

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

    } catch (java.io.IOException e) {
        throw new RuntimeException(e);
    } catch (ServletException e) {
        throw new RuntimeException(e);
    }

    filterChain.doFilter(request,response);

    }
}
