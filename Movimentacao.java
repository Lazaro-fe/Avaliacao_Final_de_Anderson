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
@Table(name = "movimentacoes")
public class Movimentacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_da_movimentacao;

    @Column(nullable = false, name = "tipo_de_movimentacao")
    private String tipo_de_movimentacao;

    @Column(nullable = false, name = "data_da_movimentacao")
    private String data_da_movimentacao;

    @Column(nullable = false)
    private int quantidade;

    @ManyToOne
    @JoinColumn(name = "produto", nullable = false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "usuario", nullable = false)
    private Usuario usuario;

    public Movimentacao(){
    }

    public Movimentacao(String tipo_de_movimentacao, String data_da_movimentacao, int quantidade, Produto produto, Usuario usuario){
        this.tipo_de_movimentacao = tipo_de_movimentacao;
        this.data_da_movimentacao = data_da_movimentacao;
        this.quantidade = quantidade;
        this.produto = produto;
        this.usuario = usuario;
    }

    public Movimentacao(Long id_da_movimentacao, String tipo_de_movimentacao, String data_da_movimentacao, int quantidade, Produto produto, Usuario usuario){
        this.id_da_movimentacao = id_da_movimentacao;
        this.tipo_de_movimentacao = tipo_de_movimentacao;
        this.data_da_movimentacao = data_da_movimentacao;
        this.quantidade = quantidade;
        this.produto = produto;
        this.usuario = usuario;
    }

    public Long getId_da_movimentacao() {
        return id_da_movimentacao;
    }

    public void setId_da_movimentacao(Long id_da_movimentacao) {
        this.id_da_movimentacao = id_da_movimentacao;
    }

    public String getTipo_de_movimentacao() {
        return tipo_de_movimentacao;
    }

    public void setTipo_de_movimentacao(String tipo_de_movimentacao) {
        this.tipo_de_movimentacao = tipo_de_movimentacao;
    }

    public String getData_da_movimentacao() {
        return data_da_movimentacao;
    }

    public void setData_da_movimentacao(String data_da_movimentacao) {
        this.data_da_movimentacao = data_da_movimentacao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getId_produto() {
        return produto;
    }

    public void setId_produto(Produto produto) {
        this.produto = produto;
    }

    public Usuario getId_usuario() {
        return usuario;
    }

    public void setId_usuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
