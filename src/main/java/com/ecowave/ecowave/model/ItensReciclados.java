package com.ecowave.ecowave.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "ItensReciclados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItensReciclados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ITEM")
    private long idItem;

    @Column(name = "TIPO_ITEM")
    private String tipoItem;

    @Column(name = "DATA_COLETA_ITEM")
    private Date dataColetaItem;

    @Column(name = "LOCALIZACAO_ITEM")
    private String localizacaoItem;

    @Column(name = "QUANTIDADE_ITEM")
    private Number quantidadeItem;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;

    public ItensReciclados(String tipoItem, Date dataColetaItem, String localizacaoItem, Number quantidadeItem, Usuario usuario) {
        this.tipoItem = tipoItem;
        this.dataColetaItem = dataColetaItem;
        this.localizacaoItem = localizacaoItem;
        this.quantidadeItem = quantidadeItem;
        this.usuario = usuario;
    }

}
