package com.projeto.Avaliacao_Final_definitiva.Controller;

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

import com.projeto.Avaliacao_Final_definitiva.Data_base.Model.Produto;
import com.projeto.Avaliacao_Final_definitiva.Data_base.Repository.Produto_repository;

@RestController
@RequestMapping("/api/produto")
@CrossOrigin("*")
public class Produto_controller {
    
    private Produto_repository produto_repository;
    
    public Produto_controller(Produto_repository produto_repository) {
        this.produto_repository = produto_repository;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Produto> cadastrar_produto(@RequestBody Produto novo_produto) {
        // Agora devolvemos o objeto gravado com o ID gerado!
        Produto produtoSalvo = produto_repository.save(novo_produto);
        return ResponseEntity.ok(produtoSalvo);
    }

    @GetMapping("/listar")
    public List<Produto> listar_produtos() {
        return produto_repository.findAll();
    }

    @GetMapping("/buscar")
    public List<Produto> buscar_produtos(@RequestParam String nome) {
        return produto_repository.findByNomeDoProduto(nome);
    }

    @GetMapping("/buscar/{nome}")
    public ResponseEntity<List<Produto>> buscar_por_nome(@PathVariable String nome) {
        List<Produto> produtos = produto_repository.findByNomeDoProduto(nome);
        return ResponseEntity.ok(produtos);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizar_produto(@PathVariable Long id, @RequestBody Produto dados_novos) {
        Optional<Produto> produto_antigo = produto_repository.findById(id);
        if (produto_antigo.isPresent()) {
            Produto p = produto_antigo.get();
            p.setNomeDoProduto(dados_novos.getNomeDoProduto());
            p.setQuantidade(dados_novos.getQuantidade());
            p.setPreco(dados_novos.getPreco());
            p.setCategoria(dados_novos.getCategoria());
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