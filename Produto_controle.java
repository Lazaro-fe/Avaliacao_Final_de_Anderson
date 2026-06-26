/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_avaliativo.controle;

import com.mycompany.projeto_avaliativo.dao.Produto_dao;
import com.mycompany.projeto_avaliativo.model.Produto;
import java.util.List;

/**
 *
 * @author aluno.den
 */
public class Produto_controle {
    
    private Produto_dao produto_dao;

    public Produto_controle() {
        this.produto_dao = new Produto_dao();
    }
    
    public void cadastrar_produto(String nome, int quantidade, double preco, int idUsuario, int idCategoria) {
        // O nome do produto não pode ficar em branco
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório.");
        }
        // Estoque e preço não podem ser negativos
        if (quantidade < 0 || preco < 0) {
            throw new IllegalArgumentException("Quantidade e preço não podem ser negativos.");
        }

        Produto novoProduto = new Produto(nome, quantidade, preco, idUsuario, idCategoria);
        produto_dao.Cadastrar(novoProduto);
    }
    
    // Atualiza os dados de um produto que já existe no sistema
    public void atualizar_produto(int id, String nome, int quantidade, double preco, int idCategoria) {
        // Verifica se o ID fornecido existe/é válido
        if (id <= 0) {
            throw new IllegalArgumentException("ID de produto inválido para atualização.");
        }
        
        // Mantém o ID do usuário como 0 na edição, presumindo que apenas os outros campos mudam
        Produto produtoEditado = new Produto(id, nome, quantidade, preco, 0, idCategoria);
        produto_dao.atualizar(produtoEditado);
    }
    
    // Exclui um produto do banco de dados baseado no seu ID
    public void remover_produto(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido para remoção.");
        }
        produto_dao.remover(id);
    }
    
    // Retorna a lista completa de produtos cadastrados
    public List<Produto> listar_de_Produtos() {
        return produto_dao.listar();
    }
    
    // Realiza a busca de produtos baseada em um termo (nome)
    public List<Produto> buscar_produtos_por_nome(String nome_para_pesquisa) {
        // Se o usuário não digitar nada na pesquisa, retorna a lista completa
        if (nome_para_pesquisa == null || nome_para_pesquisa.trim().isEmpty()) {
            return listar_de_Produtos();
        }

        // Caso contrário, busca no DAO pelos produtos que correspondem ao texto pesquisado
        return produto_dao.buscarPorNome(nome_para_pesquisa);
    }
}
