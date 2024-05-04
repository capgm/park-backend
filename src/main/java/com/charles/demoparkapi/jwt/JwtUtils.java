package com.charles.demoparkapi.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class JwtUtils {

	public static final String JWT_BEARER = "Bearer ";
	public static final String JWT_AUTHORIZATION = "Authorization";
	public static final String SECRET_KEY = "0123456789-0123456789-0123456789-ABC";
	public static final long EXPIRE_DAYS = 0;
	public static final long EXPIRE_HOURS = 6;
	public static final long EXPIRE_MINUTES  = 0;
	
	
	private JwtUtils() {
		
	}
	
	private static Key generateKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	}
	
	private static Date toExpireDate(Date start) {
		LocalDateTime dateTime = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDateTime end =  dateTime.plusDays(EXPIRE_DAYS).plusHours(EXPIRE_HOURS).plusMinutes(EXPIRE_MINUTES);
		return Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public static JwtToken createToken(String username, String role) {
		Date issueAt = new Date();
		Date limit = toExpireDate(issueAt);
		
		String token = Jwts.builder()
				.setHeaderParam("typ", "JWT")
				.setSubject(username)
				.setIssuedAt(issueAt)
				.setExpiration(limit)
				.signWith(generateKey(), SignatureAlgorithm.HS256)
				.claim("role", role)
				.compact();
		
		return new JwtToken(token);
	}
	
	private static Claims getClaimsFromToken(String token) {
		try {
			return Jwts.parser().setSigningKey(generateKey()).build()
					.parseClaimsJws(refactorToken(token)).getBody();
		}catch (JwtException e) {
			
		}
		
		return null;
	}
	
	public static String getUsernameFromToken(String token) {
		return getClaimsFromToken(token).getSubject();
	}
	
	
	public static boolean isTokenValid(String token) {
		try {
			Jwts.parser().setSigningKey(generateKey()).build()
					.parseClaimsJws(refactorToken(token));
			
			return true;
		}catch (JwtException e) {
			
		}
		
		return false;
	}
	
	
	private static String refactorToken(String token) {
		if(token.contains(JWT_BEARER)) {
			return token.substring(JWT_BEARER.length());
		}
		
		return token;
	}
}

