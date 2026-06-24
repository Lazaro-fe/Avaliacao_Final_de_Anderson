/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_avaliativo.dao;

import com.mycompany.projeto_avaliativo.conexao_de_banco.conexao_de_banco;
import com.mycompany.projeto_avaliativo.model.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author aluno.den
 */
public class Categoria_dao {
    
    private Connection conncon;
    
    // Construtor: Inicializa a conexão sempre que o DAO é instanciado
    public Categoria_dao(){
        this.conncon = new conexao_de_banco().getConnection();
    }
    
    // Insere uma nova categoria no banco
    public void cadastrar (Categoria c){
        // Comando SQL parametrizado (o '?' evita ataques de SQL Injection)
        String sql = "INSERT INTO categoria (nome) VALUES (?)";
        try (PreparedStatement state = conncon.prepareStatement(sql)){
            // Substitui o '?' pelo nome da categoria
            state.setString(1, c.getNome());
            // Executa a inserção no banco de dados
            state.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
