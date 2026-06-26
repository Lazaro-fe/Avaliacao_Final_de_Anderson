/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_avaliativo.controle;

import com.mycompany.projeto_avaliativo.dao.Usuario_dao;
import com.mycompany.projeto_avaliativo.model.Usuario;
import java.sql.Connection;

/**
 *
 * @author aluno.den
 */
public class Usuario_controle {
    
    private Usuario_dao usuario_dao;

    public Usuario_controle(Connection conexao_de_banco) {
        this.usuario_dao = new Usuario_dao();
    }

    // Valida se os campos de login e senha foram preenchidos
    public Usuario autenticar(String login, String senha) {
        if (login == null || login.isEmpty() || senha == null || senha.isEmpty()) {
            return null; 
        }
        
        // Aciona o banco para verificar as credenciais
        return usuario_dao.autenticar(login, senha);
    }

    // Realiza o registro de um novo usuário no sistema
    public String registrar(String login, String senha) {
        
        // Verifica se os dados inseridos são nulos ou apenas espaços vazios
        if (login == null || login.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            return "Erro: Login e senha não podem estar vazios!"; 
        }
        
        // Cria um novo usuário com as credenciais válidas
        Usuario novoUsuario = new Usuario(login, senha);
        // Tenta cadastrar no banco. Retorna 'true' se der certo, 'false' se falhar (ex: login duplicado)
        boolean sucesso = usuario_dao.cadastrarUsuario(novoUsuario);
        
        // Retorna o feedback apropriado para a interface que chamou o método
        if (sucesso) {
            return "Usuário cadastrado com sucesso!";
        } else {
            return "Erro: O login já está em uso."; // Feedback da unicidade [cite: 20]
        }
    }
}
