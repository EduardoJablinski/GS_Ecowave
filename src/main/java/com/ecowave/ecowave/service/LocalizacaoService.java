package com.ecowave.ecowave.service;

import com.ecowave.ecowave.model.Localizacao;
import com.ecowave.ecowave.repository.LocalizacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocalizacaoService {

    @Autowired
    private LocalizacaoRepository localizacaoRepository;

    public Localizacao salvarLocalizacao(Localizacao localizacao) {
        return localizacaoRepository.save(localizacao);
    }

    public List<Localizacao> obterTodasLocalizacoes() {
        return localizacaoRepository.findAll();
    }

    public Optional<Localizacao> obterLocalizacaoPorId(Long id) {
        return localizacaoRepository.findById(id);
    }
}
