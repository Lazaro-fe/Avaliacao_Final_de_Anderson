package com.projeto.Avaliacao_Final_definitiva.Data_base.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categoria")
public class Categoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_categoria;

    @Column(nullable = false, name = "categoria_do_produto")
    private String categoria_do_produto;

    public Categoria(){
    }

    public Categoria(String categoria_do_produto){
        this.categoria_do_produto = categoria_do_produto;
    }

    public Categoria(Long id_categoria, String categoria_do_produto){
        this.id_categoria = id_categoria;
        this.categoria_do_produto = categoria_do_produto;
    }
    
    public Long getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Long id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getCategoria_do_produto() {
        return categoria_do_produto;
    }

    public void setCategoria_do_produto(String categoria_do_produto) {
        this.categoria_do_produto = categoria_do_produto;
    }
}
