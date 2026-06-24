package com.mycompany.projeto_avaliativo.dao;

import com.mycompany.projeto_avaliativo.conexao_de_banco.conexao_de_banco;
import com.mycompany.projeto_avaliativo.model.Movimentacao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Movimentacao_dao {
    
    private Connection conncon;
    
    public Movimentacao_dao(){
        this.conncon = new conexao_de_banco().getConnection();
    }
    
    // Registra a movimentação e atualiza o estoque do produto na mesma transação
    public void registrar (Movimentacao movi){
        String sql = "INSERT INTO movimentacao (tipo, quantidade, data_movimentacao_do_produto, id_produto, id_usuario) VALUES (?, ?, NOW(), ?, ?)";
        try {
            // Desativa o auto-commit para iniciar uma transação manual.
            // Isso garante que se uma das operações falhar, nada é salvo no banco.
            conncon.setAutoCommit(false);
            
            // Salva o registro da movimentação no histórico
            try (PreparedStatement state = conncon.prepareStatement(sql)){
                state.setString(1, movi.getTipo_de_movi());
                state.setInt(2, movi.getQuantidade());
                state.setInt(3, movi.getId_produto());
                state.setInt(4, movi.getId_usuario());
                state.executeUpdate();
            }
            
            
            // Prepara o SQL para atualizar a quantidade do produto na tabela 'produtos'
            // Se for "entrada", soma; caso contrário, subtrai.
            String sqlUpdate = movi.getTipo_de_movi().equalsIgnoreCase("entrada") ? 
                    "UPDATE produtos SET quantidade = quantidade + ? WHERE id = ?" : 
                    "UPDATE produtos SET quantidade = quantidade - ? WHERE id = ?";
            
            try (PreparedStatement stateUpdate = conncon.prepareStatement(sqlUpdate)) {
                stateUpdate.setInt(1, movi.getQuantidade());
                stateUpdate.setInt(2, movi.getId_produto());
                stateUpdate.executeUpdate();
            }
            
            // Se as duas operações acima deram certo, confirma (salva) as alterações no banco
            conncon.commit();
        } catch (SQLException e) {
            try {
                // Se deu erro em qualquer parte, desfaz tudo (rollback) para manter a integridade do banco
                conncon.rollback();
            } catch (SQLException ex){
                ex.printStackTrace();
            }
            throw new RuntimeException("Erro ao registrar movimentação: " + e.getMessage());
        } finally {
            try {
                // Restaura o comportamento padrão da conexão para as próximas operações
                conncon.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    
    public List<Movimentacao> listar() {
        List<Movimentacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM movimentacao ORDER BY data_movimentacao_do_produto DESC";
        try (PreparedStatement state = conncon.prepareStatement(sql);

        ResultSet rs = state.executeQuery()) {

        // Percorre linha por linha do resultado do banco
        while (rs.next()) {
            Movimentacao m =new Movimentacao(
                        rs.getInt("id"),
                        rs.getString("tipo"),
                        rs.getInt("quantidade"),
                        rs.getString("data_movimentacao_do_produto"),
                        rs.getInt("id_produto"),
                        rs.getInt("id_usuario")
                    );
            lista.add(m); // Adiciona à lista final
        }

        System.out.println("Movimentações encontradas: " + lista.size());

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar movimentações: "+ e.getMessage());
        }
        return lista; 
    }
    
    // Atualiza os dados de um registro de movimentação específico
    public void atualizar(Movimentacao movi) {
        String sql = "UPDATE movimentacao SET tipo = ?, quantidade = ?, id_produto = ?, id_usuario = ? WHERE id = ?";
        try (PreparedStatement state = conncon.prepareStatement(sql)) {
            state.setString(1, movi.getTipo_de_movi());
            state.setInt(2, movi.getQuantidade());
            state.setInt(3, movi.getId_produto());
            state.setInt(4, movi.getId_usuario());
            state.setInt(5, movi.getId());
        
            state.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar registro de movimentação: " + e.getMessage());
        }
    }
}