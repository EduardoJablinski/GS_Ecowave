package com.ecowave.ecowave.service;

import com.ecowave.ecowave.repository.UsuarioRepository;
import com.ecowave.ecowave.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registrarUsuario(Usuario usuario) {
        if (usuario.getDataRegistroUsuario() == null) {
            usuario.setDataRegistroUsuario(new Date());
        }
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenhaUsuario());
        usuario.setSenhaUsuario(senhaCriptografada);
        usuarioRepository.save(usuario);
    }

    public boolean verificarCredenciais(String nomeUsuario, String senha) {
        Usuario usuario = usuarioRepository.findByNomeUsuario(nomeUsuario);
        if (usuario != null) {
            return passwordEncoder.matches(senha, usuario.getSenhaUsuario());
        }
        return false;
    }
}
