package com.charles.demoparkapi.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.charles.demoparkapi.entity.Usuario;
import com.charles.demoparkapi.entity.Usuario.Role;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>  {

	Optional<Usuario> findByUsername(String username);

	@Query(value = "SELECT role FROM usuarios WHERE username = :username", nativeQuery = true)
	Role findRoleByUsername(@Param("username") String username);

}
