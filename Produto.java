package com.projeto.Avaliacao_Final_definitiva.Data_base.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "nome_do_produto")
    private String nomeDoProduto;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false)
    private double preco;

    // Transformado em String para evitar o erro "not-null property references a null"
    @Column(nullable = false)
    private String categoria; 

    public Produto() {}

    public Produto(String nomeDoProduto, int quantidade, double preco, String categoria) {
        this.nomeDoProduto = nomeDoProduto;
        this.quantidade = quantidade;
        this.preco = preco;
        this.categoria = categoria;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeDoProduto() { return nomeDoProduto; }
    public void setNomeDoProduto(String nomeDoProduto) { this.nomeDoProduto = nomeDoProduto; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}