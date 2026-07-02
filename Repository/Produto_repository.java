package com.projeto.Avaliacao_Final.Data_base.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.projeto.Avaliacao_Final.Data_base.Model.Produto;

public interface Produto_repository extends JpaRepository<Produto, Long>{
    
    List<Produto> findByNameProdutoContainingIgnoreCase(String nome);
}
