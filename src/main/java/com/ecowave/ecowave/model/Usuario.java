package com.ecowave.ecowave.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "USUARIOS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private long idUsuario;

    @Column(name = "NOME_USUARIO", nullable = false, unique = true)
    private String nomeUsuario;

    @Column(name = "SENHA_USUARIO", nullable = false)
    private String senhaUsuario;

    @Column(name = "EMAIL_USUARIO", nullable = false, unique = true)
    private String emailUsuario;

    @Column(name = "DATA_REGISTRO_USUARIO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRegistroUsuario;

    @Column(name = "LOCALIZACAO_USUARIO")
    private String localizacaoUsuario;
}
