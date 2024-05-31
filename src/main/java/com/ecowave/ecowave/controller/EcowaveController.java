package com.ecowave.ecowave.controller;

import java.util.ArrayList;
import java.util.List;

import com.ecowave.ecowave.model.Usuario;
import com.ecowave.ecowave.repository.UsuarioRepository;
import com.ecowave.ecowave.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecowave.ecowave.model.ItensReciclados;
import com.ecowave.ecowave.repository.ItensRecicladosRepository;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class EcowaveController {

    @Autowired
    private UsuarioService usuarioService;
    @Qualifier("usuarioRepository")
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/registrar")
    public ResponseEntity<String> registrarUsuario(@RequestBody Usuario usuario) {
        usuarioService.registrarUsuario(usuario);
        return new ResponseEntity<>("Usuário registrado com sucesso.", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String nomeUsuario, @RequestParam String senha) {
        boolean loginSucesso = usuarioService.verificarCredenciais(nomeUsuario, senha);
        if (loginSucesso) {
            return new ResponseEntity<>("Login bem-sucedido.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Credenciais inválidas.", HttpStatus.UNAUTHORIZED);
        }
    }


    @Autowired
    ItensRecicladosRepository itensRecicladosRepository;


    @GetMapping("/item")
    public ResponseEntity<List<ItensReciclados>> getAllItensReciclados(@RequestParam(required = false) String tipoItem) {
        try {
            List<ItensReciclados> itensReciclados = new ArrayList<ItensReciclados>();

            if (tipoItem == null)
                itensRecicladosRepository.findAll().forEach(itensReciclados::add);
            else
                itensRecicladosRepository.findByTipoContaining(tipoItem).forEach(itensReciclados::add);

            if (itensReciclados.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(itensReciclados, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/item/total")
    public ResponseEntity<Long> getTotalItens() {
        try {
            long totalItens = itensRecicladosRepository.findTotalItens();
            return new ResponseEntity<>(totalItens, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(0L, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/usuario/{id}/item")
    public ResponseEntity<List<ItensReciclados>> getItensByUsuarioId(@PathVariable("id") long id) {
        List<ItensReciclados> itensReciclados = itensRecicladosRepository.findByUsuarioId(id);
        if (itensReciclados.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(itensReciclados, HttpStatus.OK);
    }

    @PostMapping("/usuario/{id}/item")
    public ResponseEntity<String> createItemReciclado(@PathVariable long id, @RequestBody ItensReciclados itensReciclados) {
        try {
            Usuario usuario = usuarioRepository.findByIdUsuario(id);
            if (usuario == null) {
                return new ResponseEntity<>("Usuario com o ID especificado não encontrado.", HttpStatus.NOT_FOUND);
            }
            itensReciclados.setUsuario(usuario);
            itensRecicladosRepository.save(new ItensReciclados(itensReciclados.getTipoItem(), itensReciclados.getDataColetaItem(), itensReciclados.getLocalizacaoItem(), itensReciclados.getQuantidadeItem(), itensReciclados.getUsuario()));
            return new ResponseEntity<>("Item Reciclado foi adicionado com sucesso.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/usuario/{id}/item/total")
    public ResponseEntity<Long> getTotalItensByUsuarioId(@PathVariable("id") long id) {
        long totalItens = itensRecicladosRepository.findTotalItensByUsuarioId(id);
        return new ResponseEntity<>(totalItens, HttpStatus.OK);
    }

    @PutMapping("/usuario/{id}/item/{itemId}")
    public ResponseEntity<String> updateItemReciclado(@PathVariable long id, @PathVariable long itemId, @RequestBody ItensReciclados updatedItem) {
        try {
            Usuario usuario = usuarioRepository.findByIdUsuario(id);
            if (usuario == null) {
                return new ResponseEntity<>("Usuario com o ID especificado não encontrado.", HttpStatus.NOT_FOUND);
            }

            ItensReciclados itemExistente = itensRecicladosRepository.findById(itemId);
            if (itemExistente == null) {
                return new ResponseEntity<>("Item com o ID especificado não encontrado.", HttpStatus.NOT_FOUND);
            }

            itemExistente.setTipoItem(updatedItem.getTipoItem());
            itemExistente.setDataColetaItem(updatedItem.getDataColetaItem());
            itemExistente.setLocalizacaoItem(updatedItem.getLocalizacaoItem());
            itemExistente.setQuantidadeItem(updatedItem.getQuantidadeItem());

            itensRecicladosRepository.save(itemExistente);
            return new ResponseEntity<>("Item Reciclado foi atualizado com sucesso.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao atualizar o item reciclado.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/usuario/{id}/item/{itemId}")
    public ResponseEntity<ItensReciclados> getItemByUsuarioIdAndItemId(@PathVariable("id") long id, @PathVariable("itemId") long itemId) {
        try {
            ItensReciclados item = itensRecicladosRepository.findById(itemId);

            if (item == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            if (item.getUsuario().getIdUsuario() != id) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

