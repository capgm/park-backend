package com.charles.demoparkapi.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioLoginDto {

	@Email(message = "Formato do e-mail invalido!", regexp="^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
	@NotBlank
	private String username;
	
	@NotBlank
	@Size(min = 6, max = 6)
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UsuarioLoginDto(
			@Email(message = "Formato do e-mail invalido!", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$") @NotBlank String username,
			@NotBlank @Size(min = 6, max = 6) String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public UsuarioLoginDto() {
		super();
	}
	
}
