package com.ecowave.ecowave.repository;

import com.ecowave.ecowave.model.Localizacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalizacaoRepository extends JpaRepository<Localizacao, Long> {
    Page<Localizacao> findAll(Pageable pageable);
}
