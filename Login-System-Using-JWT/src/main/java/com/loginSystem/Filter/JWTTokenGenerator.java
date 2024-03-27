package com.loginSystem.Filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTTokenGenerator extends OncePerRequestFilter {

	public static final String JWT_KEY = "jxgEQeXHuPq8VdbyYFNkANdudQ53YUn4";
	public static final String JWT_HEADER = "Authorization";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if (null != authentication) {
			SecretKey key = Keys.hmacShaKeyFor(JWT_KEY.getBytes(StandardCharsets.UTF_8)); // generating the Secret_key
			String jwt = Jwts.builder().issuer("Mukesh").subject("JWT Token")
					.claim("username", authentication.getName())
					.claim("authorities", authentication.getAuthorities())
					.issuedAt(new Date())
					.expiration(new Date((new Date()).getTime() + 3000)).signWith(key)
					.compact();
			response.setHeader(JWT_HEADER, jwt); // Create a JSON response body containing the token
			System.out.println("Authentication Set in the claims is :" + authentication.getAuthorities());
			System.out.println("JWT token");
			System.out.println(jwt);

		}

		filterChain.doFilter(request, response);
	}

	// On false this filter will run
	// on true this filter will not run
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return !request.getServletPath().equals("/adminlogin");
	}

}
