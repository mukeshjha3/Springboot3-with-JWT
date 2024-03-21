package com.loginSystem.Filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTTokenValidator extends OncePerRequestFilter {

	public static final String JWT_KEY = "jxgEQeXHuPq8VdbyYFNkANdudQ53YUn4";
	public static final String JWT_HEADER = "Authorization";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String jwt = request.getHeader(JWT_HEADER);

		if (null != jwt) {

			//Bearer ihaskidkn
			
			try {
				 SecretKey key = Keys.hmacShaKeyFor(
	                        JWT_KEY.getBytes(StandardCharsets.UTF_8));
				 System.out.println("Secret Key is : "+key);
				var afterremovingbearer = jwt.substring(7);
				System.out.println(jwt);
				System.out.println(afterremovingbearer);
				Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(afterremovingbearer)
						.getPayload();
				String username = String.valueOf(claims.get("username"));
				List<Map<String, String>> authoritiesClaim = (List<Map<String, String>>) claims.get("authorities");

				// Create a list of GrantedAuthority objects from the authorities claim
				List<GrantedAuthority> authorities = new ArrayList<>();
				for (Map<String, String> authorityMap : authoritiesClaim) {
				    authorities.add(new SimpleGrantedAuthority(authorityMap.get("authority")));
				}
				//List<String> authoritiesList = (List<String>) claims.get("authorities");
				//String authoritiesString = String.join(",", authoritiesList);
				Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
				SecurityContextHolder.getContext().setAuthentication(auth);
				System.out.println("Validation Completed");
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new BadCredentialsException("Invalid Token received!");

			}

		}
		 filterChain.doFilter(request, response);

	}

	protected boolean shouldNotFilter(HttpServletRequest request) {
		if (request.getServletPath().equals("/adminlogin") || request.getServletPath().equals("/register/admin")) {

			return true;
		}

		return false;
	}

}
