package Control;

import DAO.Movimentacoes_dao;
import DAO.Produto_dao;
import Model.Movimentacao;
import Model.Produto;
import Model.Usuario;

@Service
public class Movimentacoes_control {
    
    @Autowired
    private Movimentacoes_dao movimentacoes_dao;

    @Autowired
    private Produto_dao produto_dao;

    @Transactional
    public Movimentacao registro_de_movimentacao(String tipo_de_movimentacao, int quantidade, Produto produto, Usuario usuario){
        
        if (quantidade < 0) {
            
        }
    }
}
