package com.ecowave.ecowave.repository;

import com.ecowave.ecowave.model.ItensReciclados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItensRecicladosRepository extends JpaRepository<ItensReciclados, Long> {
    List<ItensReciclados> findByUsuario_IdUsuario(long idUsuario);

    List<ItensReciclados> findByTipoItemContaining(String tipoItem);

    long countByUsuario_IdUsuario(long idUsuario);

}
