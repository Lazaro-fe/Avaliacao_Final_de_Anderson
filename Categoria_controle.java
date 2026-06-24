/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_avaliativo.controle;

import com.mycompany.projeto_avaliativo.dao.Categoria_dao;
import com.mycompany.projeto_avaliativo.model.Categoria;

/**
 *
 * @author aluno.den
 */
public class Categoria_controle {
    
    private Categoria_dao categoria_dao;
    
    public Categoria_controle() {
        this.categoria_dao = new Categoria_dao();
    }
    
    
    public void cadastrar_de_categoria(String nome_da_categoria) {
        // Validação: verifica se o nome é nulo ou contém apenas espaços em branco
        if (nome_da_categoria == null || nome_da_categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da categoria não pode estar vazio.");
        }
        
        // Cria uma nova instância de Categoria (ID 0 indica que será gerado pelo banco)
        Categoria novaCategoria = new Categoria(0, nome_da_categoria);
      
        // Envia o objeto validado para ser salvo no banco via DAO
        categoria_dao.cadastrar(novaCategoria);
    }
}
