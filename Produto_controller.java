package com.projeto.Avaliacao_Final.Controller;

import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.Avaliacao_Final.Data_base.Model.Produto;
import com.projeto.Avaliacao_Final.Data_base.Repository.Produto_repository;

@RestController
@RequestMapping("/api/produto")
@CrossOrigin(origins = "*")
public class Produto_controller {
    
    private Produto_repository produto_repository;

    public Produto_controller(Produto_repository produto_repository) {
        this.produto_repository = produto_repository;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar_produto(@RequestBody Produto novo_produto) {
        produto_repository.save(novo_produto);
        return ResponseEntity.ok("Produto salvo com sucesso!");
    }

    @GetMapping("/listar")
    public List<Produto> listar_produtos() {
        return produto_repository.findAll();
    }

    @GetMapping("/buscar")
    public List<Produto> buscar_produtos(@RequestParam String nome) {
        return produto_repository.findByNameProdutoContainingIgnoreCase(nome);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizar_produto(@PathVariable Long id, @RequestBody Produto dados_novos) {
        Optional<Produto> produto_antigo = produto_repository.findById(id);
        if (produto_antigo.isPresent()) {
            Produto p = produto_antigo.get();
            p.setNome_do_produto(dados_novos.getNome_do_produto());
            p.setQuantidade(dados_novos.getQuantidade());
            p.setPreco(dados_novos.getPreco());
            p.setcategoria(dados_novos.getcategoria());
            produto_repository.save(p);
            return ResponseEntity.ok("Produto alterado com sucesso!");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletar_produto(@PathVariable Long id) {
        if (produto_repository.existsById(id)) {
            produto_repository.deleteById(id);
            return ResponseEntity.ok("Produto removido com sucesso!");
        }
        return ResponseEntity.notFound().build();
    }
}