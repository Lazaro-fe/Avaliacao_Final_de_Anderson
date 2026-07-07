package com.projeto.Avaliacao_Final_definitiva.Controller;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.Avaliacao_Final_definitiva.Data_base.Model.Categoria;
import com.projeto.Avaliacao_Final_definitiva.Data_base.Repository.Categoria_repository;

@RestController
@RequestMapping("/api/categoria")
@CrossOrigin("*")
public class Categoria_controller {
    
    private Categoria_repository categoria_repository;

    public Categoria_controller(Categoria_repository categoria_repository) {
        this.categoria_repository = categoria_repository;
    }

    @GetMapping("/listar")
    public List<Categoria> listar_categorias() {
        return categoria_repository.findAll();
    }

    @PostMapping("/cadastrar")
    public Categoria cadastrar_categoria(@RequestBody Categoria nova_categoria) {
        return categoria_repository.save(nova_categoria);
    }
}
