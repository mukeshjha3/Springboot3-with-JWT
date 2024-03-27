package com.loginSystem.Filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import io.jsonwebtoken.ExpiredJwtException;
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
			try {
				SecretKey key = Keys.hmacShaKeyFor(JWT_KEY.getBytes(StandardCharsets.UTF_8));
				var afterremovingbearer = jwt.substring(7);
				Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(afterremovingbearer)
						.getPayload();
				String username = String.valueOf(claims.get("username"));
				List<Map<String, String>> authoritiesClaim = (List<Map<String, String>>) claims.get("authorities");

				// Create a list of GrantedAuthority objects from the authorities claim
				List<GrantedAuthority> authorities = new ArrayList<>();
				for (Map<String, String> authorityMap : authoritiesClaim) {
					authorities.add(new SimpleGrantedAuthority(authorityMap.get("authority")));
				}
				System.out.println(authorities);
				Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
				SecurityContextHolder.getContext().setAuthentication(auth);
				System.out.println("Validation Completed");

			}
			catch (ExpiredJwtException e) {
			    try {
			        System.out.println("Renewing the token....");
			        SecretKey key = Keys.hmacShaKeyFor(JWT_KEY.getBytes(StandardCharsets.UTF_8));
			        var afterremovingbearer = jwt.substring(7);
			        String username = e.getClaims().get("username", String.class);
			    	List<Map<String, String>> authoritiesClaim = (List<Map<String, String>>) e.getClaims().get("authorities", Collection.class);

			    	List<GrantedAuthority> authorities = new ArrayList<>();
					for (Map<String, String> authorityMap : authoritiesClaim) {
						authorities.add(new SimpleGrantedAuthority(authorityMap.get("authority")));
					}
			        String renewaljwt = Jwts.builder().issuer("Mukesh").subject("JWT Token")
							.claim("username", username)
							.claim("authorities", authorities)
							.issuedAt(new Date())
							.expiration(new Date((new Date()).getTime() + 3000)).signWith(key)
							.compact();
			        response.setHeader(JWT_HEADER, renewaljwt);
			        System.out.println("JWT after renewal : " + renewaljwt);
			        
			        Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
					SecurityContextHolder.getContext().setAuthentication(auth);
					System.out.println("Validation Completed");

			        // Proceed with the filter chain after token renewal
			        filterChain.doFilter(request, response);
			        
			    }catch (Exception ex) {
				ex.printStackTrace();
				throw new BadCredentialsException("Invalid Token received!");

			}

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
