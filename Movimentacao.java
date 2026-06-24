package com.mycompany.projeto_avaliativo.model;

public class Movimentacao {
    
    private int id;
    private String tipo_de_movi; // 'ENTRADA' ou 'SAÍDA'
    private int quantidade;
    private String data_de_movimentacao_do_produto;
    private int id_produto;
    private int id_usuario;
    
    // Construtor para registrar novas movimentações (O banco gera o ID automaticamente)
    public Movimentacao(String tipo_de_movi, int quantidade, String data_de_movimentacao_do_produto, int id_produto, int id_usuario){
        this.tipo_de_movi = tipo_de_movi;
        this.quantidade = quantidade;
        this.data_de_movimentacao_do_produto = data_de_movimentacao_do_produto;
        this.id_produto = id_produto;
        this.id_usuario = id_usuario;
    }
    
    // Construtor completo (Usado para listar os dados vindos do Banco de Dados)
    public Movimentacao(int id, String tipo_de_movi, int quantidade, String data_de_movimentacao_do_produto, int id_produto, int id_usuario){
        this.id = id;
        this.tipo_de_movi = tipo_de_movi;
        this.quantidade = quantidade;
        this.data_de_movimentacao_do_produto = data_de_movimentacao_do_produto;
        this.id_produto = id_produto;
        this.id_usuario = id_usuario;
    }
    
    // Métodos Getters e Setters obrigatórios
    public int getId() { 
        return id; 
    }
    public void setId(int id) { 
        this.id = id; 
    }

    public String getTipo_de_movi() { 
        return tipo_de_movi; 
    }
    public void setTipo_de_movi(String tipo_de_movi) { 
        this.tipo_de_movi = tipo_de_movi; 
    }

    public int getQuantidade() { 
        return quantidade; 
    }
    
    public void setQuantidade(int quantidade) { 
        this.quantidade = quantidade; 
    }

    public String getData_de_movimentacao_do_produto() { 
        return data_de_movimentacao_do_produto; 
    }
    public void setData_de_movimentacao_do_produto(String data) { 
        this.data_de_movimentacao_do_produto = data; 
    }

    public int getId_produto() { 
        return id_produto; 
    }
    
    public void setId_produto(int id_produto) { 
        this.id_produto = id_produto; 
    }

    public int getId_usuario() { 
        return id_usuario; 
    }
    
    public void setId_usuario(int id_usuario) { 
        this.id_usuario = id_usuario; 
    }
}