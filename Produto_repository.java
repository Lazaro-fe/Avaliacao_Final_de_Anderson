package com.projeto.Avaliacao_Final_definitiva.Data_base.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.projeto.Avaliacao_Final_definitiva.Data_base.Model.Produto;

public interface Produto_repository extends JpaRepository<Produto, Long> {

    // CORREÇÃO 1: O JPQL deve referenciar o atributo Java 'nomeDoProduto'
    // CORREÇÃO 2: Removeu-se o "_" do nome do método para seguir o padrão do Spring (CamelCase)
    @CrossOrigin(origins = "*")
    @Query("SELECT p FROM Produto p WHERE p.nomeDoProduto = :nome")
    List<Produto> findByNomeDoProduto(@Param("nome") String nome);
}