package com.projeto.Avaliacao_Final.Data_base.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.projeto.Avaliacao_Final.Data_base.Model.Usuario;

public interface Usuario_repository  extends JpaRepository<Usuario, Long>{
    
    Optional<Usuario> findByLogin(String login);
}
