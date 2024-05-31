package com.ecowave.ecowave.repository;

import com.ecowave.ecowave.model.ItensReciclados;
import java.util.List;

public interface ItensRecicladosRepository {
    int save(ItensReciclados book);

    ItensReciclados findById(long idItem);

    int deleteById(long idItem);

    List<ItensReciclados> findByUsuarioId(long idUsuario);

    List<ItensReciclados> findAll();

    int deleteAll();

    List<ItensReciclados> findByTipoContaining(String tipoItem);

    long findTotalItensByUsuarioId(long idUsuario);

    long findTotalItens();

}
