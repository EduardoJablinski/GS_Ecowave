package com.ecowave.ecowave.service;

import com.ecowave.ecowave.model.Amigos;
import com.ecowave.ecowave.repository.AmigosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmigosService {

    @Autowired
    private AmigosRepository amigosRepository;

    public void adicionarAmigo(Long idUsuario, Long idAmigo) {
        Amigos amizade = new Amigos();
        amizade.setIdUsuario(idUsuario);
        amizade.setIdAmigo(idAmigo);
        amigosRepository.save(amizade);
    }

    public List<Amigos> obterAmigos(Long idUsuario) {
        return amigosRepository.findByIdUsuario(idUsuario);
    }

    public void removerAmigo(Long idUsuario, Long idAmigo) {
        amigosRepository.deleteByIds(idUsuario, idAmigo);
    }
}
