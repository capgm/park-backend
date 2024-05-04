package com.charles.demoparkapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.charles.demoparkapi.entity.Usuario;
import com.charles.demoparkapi.entity.Usuario.Role;
import com.charles.demoparkapi.exception.EntityNotFoundException;
import com.charles.demoparkapi.exception.UsernameUniqueViolationException;
import com.charles.demoparkapi.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder; 

	@Transactional
	public Usuario salvar(Usuario usuario) {
		try {
			usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
			return usuarioRepository.save(usuario);
		} catch (org.springframework.dao.DataIntegrityViolationException ex) {
			throw new UsernameUniqueViolationException(
					String.format("Username {%s} já cadastrado", usuario.getUsername()));
		}

	}

	@Transactional(readOnly = true)
	public Usuario findById(Long id) {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("Usuário id=%S não encontrado.", id)));
	}

	@Transactional
	public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {

		if (!novaSenha.equals(confirmaSenha)) {
			throw new RuntimeException("Nova senha não confere.");
		}

		Usuario user = findById(id);
		if (!passwordEncoder.matches(senhaAtual, user.getPassword())) {
			throw new RuntimeException("Sua senha não confere.");
		}

		user.setPassword(passwordEncoder.encode(novaSenha));
		return user;
	}

	@Transactional(readOnly = true)
	public List<Usuario> findAll() {

		List<Usuario> usuarios = usuarioRepository.findAll();

		return usuarios;
	}

	@Transactional(readOnly = true)
	public Usuario buscarPorUsername(String username) {
		return usuarioRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(
				String.format("Usuário com username = %s não encontrado.", username)));
	}

	@Transactional(readOnly = true)
	public Usuario.Role buscarRolePorUsername(String username) {

	    Role role = usuarioRepository.findRoleByUsername(username);
	    return role;

	}
}
