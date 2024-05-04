package com.charles.demoparkapi.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.charles.demoparkapi.entity.Usuario;
import com.charles.demoparkapi.service.UsuarioService;
import com.charles.demoparkapi.web.dto.UsuarioCreateDto;
import com.charles.demoparkapi.web.dto.UsuarioResponseDto;
import com.charles.demoparkapi.web.dto.UsuarioSenhaDto;
import com.charles.demoparkapi.web.dto.mapper.UsuarioMapper;
import com.charles.demoparkapi.web.exception.ErrorMenssageAPI;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Usuarios", description = "Contem as operações referentes ao usuário")
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@Operation(summary = "Criar um novo usuário", description = "Recurso para um novo usuário", responses = {
			@ApiResponse(responseCode = "201", description = "Recurso criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
			@ApiResponse(responseCode = "409", description = "Usuário e-mail já cadastrado no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMenssageAPI.class))),
			@ApiResponse(responseCode = "422", description = "Recurso não processado por dados inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMenssageAPI.class))) })
	@PostMapping
	public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioCreateDto usuarioCreateDto) {

		Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(usuarioCreateDto));

		return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
	}

	@Operation(summary = "Recuperar um usuário pelo id", description = "Recuperar um usuário pelo id", responses = {
			@ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMenssageAPI.class))) })
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long id) {

		Usuario user = usuarioService.findById(id);

		return ResponseEntity.ok().body(UsuarioMapper.toDto(user));
	}

	@Operation(summary = "Atualizar senha", description = "Atualizar senha", responses = {
			@ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMenssageAPI.class))),
			@ApiResponse(responseCode = "400", description = "Senha não confere", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMenssageAPI.class))),
			@ApiResponse(responseCode = "422", description = "Campos inválidos ou mal formatados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMenssageAPI.class))) })
	@PatchMapping("/{id}")
	public ResponseEntity<Void> updatePassword(@Valid @PathVariable Long id, @RequestBody UsuarioSenhaDto usuarioDto) {

		usuarioService.editarSenha(id, usuarioDto.getSenhaAtual(), usuarioDto.getNovaSenha(),
				usuarioDto.getConfirmaSenha());

		return ResponseEntity.noContent().build();
	}

	@GetMapping()
	public ResponseEntity<List<UsuarioResponseDto>> getAll() {

		List<Usuario> users = usuarioService.findAll();
		return ResponseEntity.ok(UsuarioMapper.toListDto(users));
	}
}
