/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_avaliativo.conexao_de_banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author aluno.den
 */
public class conexao_de_banco {
    
    
    private static final String URL= "jdbc:mysql://localhost:3306/projeto_estoque";
    private static final String USER= "root";
    public static final String PASSWORD= "";
    
    // Método responsável por abrir e devolver a conexão ativa com o banco de dados
    public Connection getConnection(){
        try{
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e){
            // Lança uma exceção em tempo de execução caso a conexão falhe (ex: MySQL desligado)
            throw new RuntimeException("ERRO DE CONEXÃO COM O BANCO DE DADOS" +e.getMessage());
        }
    }
}
