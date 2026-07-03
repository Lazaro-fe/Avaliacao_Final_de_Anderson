package com.projeto.Avaliacao_Final.Controller;


import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.projeto.Avaliacao_Final.Data_base.Model.Usuario;
import com.projeto.Avaliacao_Final.Data_base.Repository.Usuario_repository;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*")
public class Usuario_controller {
    
    private Usuario_repository usuario_repository;

    public Usuario_controller(Usuario_repository usuario_repository) {
        this.usuario_repository = usuario_repository;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar_usuario(@RequestBody Usuario novo_usuario) {
        if (usuario_repository.findByLogin(novo_usuario.getLogin()).isPresent()) {
            return ResponseEntity.badRequest().body("Login ja cadastrado!");
        }
        usuario_repository.save(novo_usuario);
        return ResponseEntity.ok("Usuario cadastrado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity<Object> realizar_login(@RequestBody Usuario dados_login) {
        Optional<Usuario> usuario_banco = usuario_repository.findByLogin(dados_login.getLogin());
        
        if (usuario_banco.isPresent() && usuario_banco.get().getSenha().equals(dados_login.getSenha())) {
            return ResponseEntity.ok(usuario_banco.get());
        }
        return ResponseEntity.status(401).body("Usuario ou senha incorretos!");
    }
}
