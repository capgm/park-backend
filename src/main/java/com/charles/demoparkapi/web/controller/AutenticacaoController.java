package com.charles.demoparkapi.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.charles.demoparkapi.jwt.JwtToken;
import com.charles.demoparkapi.jwt.JwtUserDetailsService;
import com.charles.demoparkapi.web.dto.UsuarioLoginDto;
import com.charles.demoparkapi.web.exception.ErrorMenssageAPI;

import io.jsonwebtoken.Jwts.HeaderBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1")
public class AutenticacaoController {
	@Autowired
	private JwtUserDetailsService detailService;
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/auth")
	public ResponseEntity<?> autenticar(@RequestBody @Valid UsuarioLoginDto usuarioLoginDto,
			HttpServletRequest request) {
		try {
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(usuarioLoginDto.getUsername(), usuarioLoginDto.getPassword());
			
			authenticationManager.authenticate(authenticationToken);
			
			JwtToken token = detailService.getTokenAuthenticatesd(usuarioLoginDto.getUsername());
			
			return ResponseEntity.ok(token);
			
		}catch (AuthenticationException e) {
			return ResponseEntity
					.badRequest()
					.body(new ErrorMenssageAPI(request, HttpStatus.BAD_REQUEST, "Credenciais Inválidas"));
		}
		
//		return ResponseEntity
//				.badRequest()
//				.body(new ErrorMenssageAPI(request, HttpStatus.BAD_REQUEST, "Credenciais Inválidas"));
	}
}
