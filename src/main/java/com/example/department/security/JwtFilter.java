package com.example.department.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.department.clients.UserServiceClient;
import com.example.department.dtos.UserDto;

import java.io.IOException;
import java.util.List;
import java.util.Collections;


@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserServiceClient userServiceClient;
    private static final List<String> EXCLUDE_URLS = List.of(
            //"/department",
            //"/department/**",
            //"/api/department"
            //"/api/department/**"
    );

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        if (isExcluded(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Department API need token header from ." +
                    " authenticate_user. Authorization header is missing or invalid");
            return;
        }
        final String jwt;
        final String userEmail;
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        System.out.println("before call user");
        if (request.getServletPath().contains("/register") || request.getServletPath().contains("/delete")) {
            final List<String> authority = jwtService.extractAuthorities(jwt);
            if (authority.contains("ADMIN")){
                filterChain.doFilter(request, response);
                return;
            }
            throw new IllegalStateException("No authority");
        }

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("tag1");
            UserDto userDto = userServiceClient.getUserByEmail(userEmail);

            //UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    userDto.getEmail(),
                    "",
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
            );
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
    private boolean isExcluded(String requestURI) {
        return EXCLUDE_URLS.stream().anyMatch(excludeUrl -> requestURI.matches(excludeUrl.replace("**", ".*")));
    }
}
