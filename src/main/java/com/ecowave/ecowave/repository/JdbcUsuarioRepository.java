package com.ecowave.ecowave.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.dao.EmptyResultDataAccessException;

import com.ecowave.ecowave.model.Usuario;

@Repository
public abstract class JdbcUsuarioRepository implements UsuarioRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Usuario findByIdUsuario(Long idUsuario) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM USUARIO WHERE ID_USUARIO=?",
                    new Object[]{idUsuario},
                    (rs, rowNum) -> {
                        Usuario usuario = new Usuario();
                        usuario.setIdUsuario(rs.getLong("ID_USUARIO"));
                        usuario.setNomeUsuario(rs.getString("NOME_USUARIO"));
                        usuario.setSenhaUsuario(rs.getString("SENHA_USUARIO"));
                        usuario.setEmailUsuario(rs.getString("EMAIL_USUARIO"));
                        usuario.setDataRegistroUsuario(rs.getDate("DATA_REGISTRO_USUARIO"));
                        usuario.setLocalizacaoUsuario(rs.getString("LOCALIZACAO_USUARIO"));
                        return usuario;
                    }
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}


