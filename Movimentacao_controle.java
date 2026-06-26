package com.mycompany.projeto_avaliativo.controle;

import com.mycompany.projeto_avaliativo.dao.Movimentacao_dao;
import com.mycompany.projeto_avaliativo.model.Movimentacao;
import java.util.List;

public class Movimentacao_controle {
    
    private Movimentacao_dao movimentacao_dao;

    public Movimentacao_controle() {
        this.movimentacao_dao = new Movimentacao_dao();
    }
    
    public void registrar_movimentacao(String tipoMovi, int quantidade, String data, int idProduto, int idUsuario) {
        
        // Não faz sentido registrar movimentação de zero itens ou valores negativos
        if (quantidade <= 0) {
            throw new IllegalArgumentException(
                    "A quantidade deve ser maior que zero.");
        }

        // Instancia a movimentação com os dados recebidos
        Movimentacao novaMovi =
                new Movimentacao(
                        tipoMovi,
                        quantidade,
                        data,
                        idProduto,
                        idUsuario);

        // Salva a movimentação no banco
        movimentacao_dao.registrar(novaMovi);
    }

    // Método para resgatar todo o histórico de movimentações do banco de dados
    public List<Movimentacao> listar_de_Movimentacoes() {
        return movimentacao_dao.listar();
    }
    
    // Atualiza o registro de uma movimentação existente
    public void atualizar_movimentacao(int id, String tipoMovi, int quantidade, int idProduto, int idUsuario) {
        // Valida se o ID fornecido é válido para atualização
    if (id <= 0) {
        throw new IllegalArgumentException("ID de movimentação inválido para atualização.");
    }
    // Valida se a nova quantidade é maior que zero
    if (quantidade <= 0) {
        throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
    }
    // Valida se o tipo de movimentação foi digitado corretamente (ignora maiúsculas/minúsculas)
    if (!tipoMovi.equalsIgnoreCase("entrada") && !tipoMovi.equalsIgnoreCase("saída") && !tipoMovi.equalsIgnoreCase("saida")) {
        throw new IllegalArgumentException("O tipo de movimentação deve ser 'entrada' ou 'saída'.");
    }

    // Cria o objeto com o ID já existente para atualizar
    Movimentacao moviEditada = new Movimentacao(id, tipoMovi, quantidade, null, idProduto, idUsuario);
    // Envia as alterações para o banco
    movimentacao_dao.atualizar(moviEditada);
    }
}