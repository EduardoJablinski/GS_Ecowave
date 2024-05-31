package com.ecowave.ecowave.repository;

import com.ecowave.ecowave.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByNomeUsuario(String nomeUsuario);

    Usuario findByIdUsuario(long idUsuario);
}
