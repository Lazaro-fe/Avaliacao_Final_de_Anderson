package com.projeto.Avaliacao_Final_definitiva.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.Avaliacao_Final_definitiva.Data_base.Model.Movimentacao;
import com.projeto.Avaliacao_Final_definitiva.Data_base.Repository.Movimentacao_repository;

@RestController
@RequestMapping("/api/movimentacao")
@CrossOrigin("*")
public class Movimentacao_controller {
    
    private Movimentacao_repository movimentacao_repository;
    
    public Movimentacao_controller(Movimentacao_repository movimentacao_repository) {
        this.movimentacao_repository = movimentacao_repository;
    }

    @GetMapping("/listar")
    public List<Movimentacao> listar_movimentacoes() {
        return movimentacao_repository.findAll();
    }

    @PostMapping("/cadastrar")
    public Movimentacao cadastrar_movimentacao(@RequestBody Movimentacao nova_movimentacao) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataAtualEmString = LocalDateTime.now().format(formatador);
        
        nova_movimentacao.setData_da_movimentacao(dataAtualEmString);
        return movimentacao_repository.save(nova_movimentacao);
    }
}
