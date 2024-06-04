package com.ecowave.ecowave.service;

import com.ecowave.ecowave.model.ItensReciclados;
import com.ecowave.ecowave.repository.ItensRecicladosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItensRecicladosService {

    private final ItensRecicladosRepository itensRecicladosRepository;

    @Autowired
    public ItensRecicladosService(ItensRecicladosRepository itensRecicladosRepository) {
        this.itensRecicladosRepository = itensRecicladosRepository;
    }

    public ItensReciclados save(ItensReciclados itensReciclados) {
        return itensRecicladosRepository.save(itensReciclados);
    }

    public ItensReciclados findById(long id) {
        return itensRecicladosRepository.findById(id).orElse(null);
    }

    public int deleteById(long id) {
        try {
            itensRecicladosRepository.deleteById(id);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    public List<ItensReciclados> findByUsuarioId(long idUsuario) {
        return itensRecicladosRepository.findByUsuario_IdUsuario(idUsuario);
    }

    public List<ItensReciclados> findAll() {
        return itensRecicladosRepository.findAll();
    }

    public List<ItensReciclados> findByTipoItemContaining(String tipoItem) {
        return itensRecicladosRepository.findByTipoItemContaining(tipoItem);
    }

    public long findTotalItensByUsuarioId(long idUsuario) {
        return itensRecicladosRepository.countByUsuario_IdUsuario(idUsuario);
    }

    public long findTotalItens() {
        return itensRecicladosRepository.count();
    }

    public long findTotalQuantidadeItens() {
        List<ItensReciclados> itensReciclados = itensRecicladosRepository.findAll();
        long total = 0;
        for (ItensReciclados item : itensReciclados) {
            total += item.getQuantidadeItem();
        }
        return total;
    }

    public long findTotalQuantidadeItensByUsuarioId(long idUsuario) {
        List<ItensReciclados> itensReciclados = itensRecicladosRepository.findByUsuario_IdUsuario(idUsuario);
        long total = 0;
        for (ItensReciclados item : itensReciclados) {
            total += item.getQuantidadeItem();
        }
        return total;
    }


}
