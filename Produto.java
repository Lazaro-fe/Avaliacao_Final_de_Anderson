/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_avaliativo.model;

/**
 *
 * @author aluno.den
 */
public class Produto {

    private int id;
    private String nome_do_produto;
    private int quantidade;
    private double preco;
    private int id_do_usuario;
    private int id_categoria;
    private String nome_usuario;
    
    public Produto(){
    }
    
    public Produto (String nome_do_produto, int quantidade, double preco, int id_do_usuario, int id_categoria){
        this.nome_do_produto = nome_do_produto;
        this.quantidade = quantidade;
        this.preco = preco;
        this.id_do_usuario = id_do_usuario;
        this.id_categoria = id_categoria;
    }
    
    public Produto (int id, String nome_do_produto, int quantidade, double preco, int id_do_usuario, int id_categoria){
        this.id = id;
        this.nome_do_produto = nome_do_produto;
        this.quantidade = quantidade;
        this.preco = preco;
        this.id_do_usuario = id_do_usuario;
        this.id_categoria = id_categoria;
    }
    
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public String getNome_do_produto(){
        return nome_do_produto;
    }
    
    public void setNome_do_produto(String nome_do_produto){
        this.nome_do_produto = nome_do_produto;
    }
    
    public int getQuantidade(){
        return quantidade;
    }
    
    public void setQuantidade(int quantidade){
        this.quantidade = quantidade;
    }
    
    public double getPreco(){
        return preco;
    }
    
    public void setPreco(double preco){
        this.preco = preco;
    }
    
    public int getId_do_usuario(){
        return id_do_usuario;
    }
    
    public void setId_do_usuario(int id_do_usuario){
        this.id_do_usuario = id_do_usuario;
    }
    
    public int getId_categoria(){
        return id_categoria;
    }
    
    public void setId_categoria(int id_categoria){
        this.id_categoria = id_categoria;
    }
    
    public String getNome_usuario(){
        return nome_usuario;
    }
    
    public void setNome_usuario(String nome_usuario){
        this.nome_usuario = nome_usuario;
    }
}