/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_avaliativo.dao;

import com.mycompany.projeto_avaliativo.conexao_de_banco.conexao_de_banco;
import com.mycompany.projeto_avaliativo.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author aluno.den
 */
public class Usuario_dao {
    
    private Connection conncon;
    
    
    public Usuario_dao(){
        this.conncon = new conexao_de_banco().getConnection();
    }
    
    public Usuario autenticar(String login, String senha){
        String sql = "SELECT * FROM usuarios WHERE login= ? AND senha= ?";
        try (PreparedStatement state = conncon.prepareStatement(sql)){
            state.setString(1, login);
            state.setString(2, senha);
            // Se houver um registro (rs.next() for true), as credenciais estão corretas    
            try (ResultSet rs = state.executeQuery()){
                    if(rs.next()){
                        Usuario u = new Usuario();
                        u.setId(rs.getInt("id"));
                        u.setLogin(rs.getString("login"));
                        u.setSenha(rs.getString("senha"));
                        return u;
                    }
                }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean cadastrarUsuario(Usuario u){
        String checkSql = "SELECT id FROM usuarios WHERE login= ?";
        String insertSql = "INSERT INTO usuarios (login, senha) VALUES (?, ?)";
        
        try (PreparedStatement checkState = conncon.prepareStatement(checkSql)){
            checkState.setString(1, u.getLogin());
            try (ResultSet rs = checkState.executeQuery()){
                if (rs.next())
                    // Se houver um registro (rs.next() for true), as credenciais estão corretas 
                    return false;
            }
            
            try (PreparedStatement insertState = conncon.prepareStatement(insertSql)){
                insertState.setString(1, u.getLogin());
                insertState.setString(2, u.getSenha());
                insertState.executeUpdate();
                return true; // Sucesso no cadastro
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
