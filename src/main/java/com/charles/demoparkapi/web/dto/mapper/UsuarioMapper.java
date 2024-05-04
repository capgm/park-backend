package com.charles.demoparkapi.web.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import com.charles.demoparkapi.entity.Usuario;
import com.charles.demoparkapi.web.dto.UsuarioCreateDto;
import com.charles.demoparkapi.web.dto.UsuarioResponseDto;

public class UsuarioMapper {

	public static Usuario toUsuario(UsuarioCreateDto usuarioCreateDto) {
		return new ModelMapper().map(usuarioCreateDto, Usuario.class);
	}
	
	public static UsuarioResponseDto toDto(Usuario usuario) {
		
		
		String role = usuario.getRole().name().replace("ROLE_","");
		
		PropertyMap<Usuario, UsuarioResponseDto> props = new PropertyMap<Usuario, UsuarioResponseDto>() {
			
			@Override
			protected void configure() {
				map().setRole(role);
				
			}
		};
		
		ModelMapper mapper = new ModelMapper();
		mapper.addMappings(props);
		
		return mapper.map(usuario, UsuarioResponseDto.class);
	}
	
	public static List<UsuarioResponseDto> toListDto(List<Usuario> users){
		
	 return	users.stream().map(user-> toDto(user)).collect(Collectors.toList());
	}
}
