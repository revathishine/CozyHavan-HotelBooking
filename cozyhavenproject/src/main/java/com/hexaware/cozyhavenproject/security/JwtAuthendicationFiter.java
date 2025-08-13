package com.hexaware.cozyhavenproject.security;

import java.io.IOException;

import com.hexaware.cozyhavenproject.entities.RevokedToken;
import com.hexaware.cozyhavenproject.repository.RevokedTokenRepository;
import com.hexaware.cozyhavenproject.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthendicationFiter extends OncePerRequestFilter {

	 @Autowired
	    private JwtUtil jwtUtil;

	    @Autowired
	    private CustomerUserDetailsService userDetailsService;

	    @Autowired
	    private RevokedTokenRepository revokedTokenRepository;

	    private String parseJwt(HttpServletRequest request) {
	        String header = request.getHeader("Authorization");
	        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
	            return header.substring(7);
	        }
	        return null;
	    }

	    @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	            throws ServletException, IOException {
	        try {
	            String token = parseJwt(request);
	            if (token != null) {
	                // check revoked
	                RevokedToken revoked = revokedTokenRepository.findByToken(token);
	                if (revoked != null) {
	                    // token revoked -> reject
	                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token revoked");
	                    return;
	                }

	                String username = jwtUtil.extractUsername(token);
	                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
	                if (jwtUtil.validateToken(token, userDetails.getUsername())) {
	                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
	                            userDetails, null, userDetails.getAuthorities());
	                    SecurityContextHolder.getContext().setAuthentication(auth);
	                }
	            }
	        } catch (Exception ex) {
	            // invalid token or user not found
	            // let security handle it by not setting authentication
	        }
	        filterChain.doFilter(request, response);
	    }
}
