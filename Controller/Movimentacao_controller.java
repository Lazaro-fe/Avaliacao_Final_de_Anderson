package com.projeto.Avaliacao_Final.Controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.Avaliacao_Final.Data_base.Model.Movimentacao;
import com.projeto.Avaliacao_Final.Data_base.Repository.Movimentacao_repository;

@RestController
@RequestMapping("/api/movimentacao")
@CrossOrigin(origins = "*")
public class Movimentacao_controller {
    
    @Autowired
    private Movimentacao_repository movimentacao_repository;

    @GetMapping("/listar")
    public List<Movimentacao> listar_movimentacoes() {
        return movimentacao_repository.findAll();
    }

    @PostMapping("/cadastrar")
    public Movimentacao cadastrar_movimentacao(@RequestBody Movimentacao nova_movimentacao) {
        nova_movimentacao.setData_movimentacao(LocalDateTime.now());
        return movimentacao_repository.save(nova_movimentacao);
    }
}