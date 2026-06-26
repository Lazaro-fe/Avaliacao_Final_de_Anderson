/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_avaliativo.dao;

import com.mycompany.projeto_avaliativo.conexao_de_banco.conexao_de_banco;
import com.mycompany.projeto_avaliativo.model.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.spi.DirStateFactory.Result;

/**
 *
 * @author aluno.den
 */
public class Produto_dao {
    
    private Connection conncon;
    
    public Produto_dao(){
        this.conncon = new conexao_de_banco().getConnection();
    }
    
    
    public void Cadastrar (Produto p){
        String sql = "INSERT INTO produtos (nome_do_produto, quantidade, preco, id_usuario, id_categoria) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement state = conncon.prepareStatement(sql)){
            state.setString(1, p.getNome_do_produto());
            state.setInt(2, p.getQuantidade());
            state.setDouble(3, p.getPreco());
            state.setInt(4, p.getId_do_usuario());
            state.setInt(5, p.getId_categoria());
            state.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException("Erro ao salvar no banco: " +e.getMessage());
        }
    }
    
    public void atualizar (Produto p){
        String sql = "UPDATE produtos SET nome_do_produto= ?, quantidade=?, preco=?, id_categoria=? WHERE id=?";
        try (PreparedStatement state = conncon.prepareStatement(sql)){
            state.setString(1, p.getNome_do_produto());
            state.setInt(2, p.getQuantidade());
            state.setDouble(3, p.getPreco());
            state.setInt(4, p.getId_categoria());
            state.setInt(5, p.getId());
            state.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    public void remover (int id){
        String sql = "DELETE FROM produtos WHERE id=?";
        try (PreparedStatement state = conncon.prepareStatement(sql)){
            state.setInt(1, id);
            state.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    public List<Produto> listar(){
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT p.*, u.login as nome_usuario FROM produtos p JOIN usuarios u on p.id_usuario = u.id";
        try (PreparedStatement state = conncon.prepareStatement(sql);
             ResultSet rs = state.executeQuery()){
            
            while (rs.next()){
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome_do_produto(rs.getString("nome_do_produto"));
                p.setQuantidade(rs.getInt("quantidade"));
                p.setPreco(rs.getDouble("preco"));
                p.setId_do_usuario(rs.getInt("id_usuario"));
                p.setId_categoria(rs.getInt("id_categoria"));
                p.setNome_usuario(rs.getString("nome_usuario"));
                
                produtos.add(p);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return produtos;
    }
    
    public List<Produto> buscarPorNome(String nome){
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT p.*, u.login as nome_usuario FROM produtos p " + "JOIN usuarios u on p.id_usuario = u.id WHERE p.nome_do_produto LIKE ?";
        try (PreparedStatement state = conncon.prepareStatement(sql)){
            state.setString(1, "%" + nome + "%");
            try (ResultSet rs = state.executeQuery()) {
                while (rs.next()){
                    Produto p = new Produto();
                    p.setId(rs.getInt("id"));
                    p.setNome_do_produto(rs.getString("nome_do_produto"));
                    p.setQuantidade(rs.getInt("quantidade"));
                    p.setPreco(rs.getDouble("preco"));
                    p.setId_do_usuario(rs.getInt("id_usuario"));
                    p.setId_categoria(rs.getInt("id_categoria"));
                    p.setNome_usuario(rs.getString("nome_usuario"));
                    
                    produtos.add(p);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return produtos;
    }
}
