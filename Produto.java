package com.projeto.Avaliacao_Final.Data_base.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_produto;

    @Column(nullable = false, name = "nome_do_produto")
    private String nome_do_produto;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false)
    private double preco;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario id_usuario;

    @ManyToOne
    @JoinColumn(name = "categoria", nullable = false)
    private Categoria categoria;
    
    public Produto(){
    }

    public Produto(String nome_do_produto, int quantidade, double preco, Usuario id_usuario, Categoria categoria){
        this.nome_do_produto = nome_do_produto;
        this.quantidade = quantidade;
        this.preco = preco;
        this.id_usuario = id_usuario;
        this.categoria = categoria;
    }

    public Produto(Long id_produto, String nome_do_produto, int quantidade, double preco, Usuario id_usuario, Categoria categoria){
        this.id_produto = id_produto;
        this.nome_do_produto = nome_do_produto;
        this.quantidade = quantidade;
        this.preco = preco;
        this.id_usuario = id_usuario;
        this.categoria = categoria;
    }

    public Long getId_produto() {
        return id_produto;
    }

    public void setId_produto(Long id_produto) {
        this.id_produto = id_produto;
    }

    public String getNome_do_produto() {
        return nome_do_produto;
    }

    public void setNome_do_produto(String nome_do_produto) {
        this.nome_do_produto = nome_do_produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Usuario getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Usuario id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Categoria getcategoria() {
        return categoria;
    }

    public void setcategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
