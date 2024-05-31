package com.ecowave.ecowave.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ecowave.ecowave.model.Usuario;
import com.ecowave.ecowave.model.ItensReciclados;

@Repository
public class JdbcItensRecicladosRepository implements ItensRecicladosRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public int save(ItensReciclados itensReciclados) {
        System.out.println("Item a ser salvo: ");
        System.out.println("Tipo: " + itensReciclados.getTipoItem());
        System.out.println("Data de Coleta: " + itensReciclados.getDataColetaItem());
        System.out.println("Localização: " + itensReciclados.getLocalizacaoItem());
        System.out.println("Quantidade: " + itensReciclados.getQuantidadeItem());
        System.out.println("ID do Usuário: " + itensReciclados.getUsuario().getIdUsuario());

        return jdbcTemplate.update("INSERT INTO ITENSRECICLADOS (ID_USUARIO, TIPO_ITEM, DATA_COLETA_ITEM, LOCALIZACAO_ITEM, QUANTIDADE_ITEM) VALUES(?,?,?,?,?)",
                new Object[] { itensReciclados.getUsuario().getIdUsuario(), itensReciclados.getTipoItem(), itensReciclados.getDataColetaItem(), itensReciclados.getLocalizacaoItem(), itensReciclados.getQuantidadeItem() });
    }


    @Override
    public ItensReciclados findById(long idItem) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM ITENSRECICLADOS WHERE ID_ITEM=?",
                    new Object[] { idItem }, (rs, rowNum) -> {
                        ItensReciclados itensReciclados = new ItensReciclados();
                        itensReciclados.setIdItem(rs.getLong("ID_ITEM"));
                        itensReciclados.setTipoItem(rs.getString("TIPO_ITEM"));
                        itensReciclados.setDataColetaItem(rs.getDate("DATA_COLETA_ITEM"));
                        itensReciclados.setLocalizacaoItem(rs.getString("LOCALIZACAO_ITEM"));
                        itensReciclados.setQuantidadeItem(rs.getInt("QUANTIDADE_ITEM"));
                        // Aqui, você precisa carregar a empresa associada ao equipamento
                        // Você pode implementar esse método no JdbcEquipamentoRepository
                        // ou usar a injeção do EmpresaRepository para acessar o método findById
                        itensReciclados.setUsuario(usuarioRepository.findByIdUsuario(rs.getLong("ID_USUARIO")));
                        return itensReciclados;
                    });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    @Override
    public int deleteById(long id) {
        return jdbcTemplate.update("DELETE FROM ITENSRECICLADOS WHERE ID_ITEM=?", id);
    }

    @Override
    public List<ItensReciclados> findByUsuarioId(long idUsuario) {
        // Convertendo o idEmpresa de long para String
        String usuarioId = String.valueOf(idUsuario);

        return jdbcTemplate.query("SELECT * FROM ITENSRECICLADOS WHERE ID_USUARIO=?", new Object[]{usuarioId}, (rs, rowNum) -> {
            ItensReciclados itensReciclados = new ItensReciclados();
            itensReciclados.setIdItem(rs.getLong("ID_ITEM"));
            itensReciclados.setTipoItem(rs.getString("TIPO_ITEM"));
            itensReciclados.setDataColetaItem(rs.getDate("DATA_COLETA_ITEM"));
            itensReciclados.setLocalizacaoItem(rs.getString("LOCALIZACAO_ITEM"));
            itensReciclados.setQuantidadeItem(rs.getInt("QUANTIDADE_ITEM"));
            // Carregar a empresa associada ao equipamento
            // Como o ID da empresa é um long, você precisa usar o método findById que aceita um long como parâmetro
            Usuario usuario = usuarioRepository.findByIdUsuario(idUsuario);
            itensReciclados.setUsuario(usuario);
            return itensReciclados;
        });
    }

    @Override
    public List<ItensReciclados> findAll() {
        return jdbcTemplate.query("SELECT * FROM ITENSRECICLADOS", (rs, rowNum) -> {
            ItensReciclados itensReciclados = new ItensReciclados();
            itensReciclados.setIdItem(rs.getLong("ID_ITEM"));
            itensReciclados.setTipoItem(rs.getString("TIPO_ITEM"));
            itensReciclados.setDataColetaItem(rs.getDate("DATA_COLETA_ITEM"));
            itensReciclados.setLocalizacaoItem(rs.getString("LOCALIZACAO_ITEM"));
            itensReciclados.setQuantidadeItem(rs.getInt("QUANTIDADE_ITEM"));
            // Carregar a empresa associada ao equipamento
            long usuarioId = rs.getLong("ID_USUARIO");
            Usuario usuario = usuarioRepository.findByIdUsuario(usuarioId);
            itensReciclados.setUsuario(usuario);
            return itensReciclados;
        });
    }


    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE FROM ITENSRECICLADOS");
    }

    @Override
    public List<ItensReciclados> findByTipoContaining(String tipoItem) {
        String q = "SELECT * from ITENSRECICLADOS WHERE TIPO_ITEM LIKE '%" + tipoItem + "%' collate binary_ci";

        return jdbcTemplate.query(q, BeanPropertyRowMapper.newInstance(ItensReciclados.class));
    }

    @Override
    public long findTotalItensByUsuarioId(long idUsuario) {
        String sql = "SELECT COALESCE(SUM(QUANTIDADE_ITEM), 0) FROM ITENSRECICLADOS WHERE ID_USUARIO = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{idUsuario}, Long.class);
    }

    @Override
    public long findTotalItens() {
        String sql = "SELECT COALESCE(SUM(QUANTIDADE_ITEM), 0) FROM ITENSRECICLADOS";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }



}
