package com.projeto.Avaliacao_Final.Data_base.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String senha;

    public Usuario(){
    }

    public Usuario (String login, String senha){
        this.login = login;
        this.senha = senha;
    }

    public Usuario(Long id_usuario, String login, String senha){
        this.id_usuario = id_usuario;
        this.login = login;
        this.senha  = senha;
    }

    public Long getId() {
        return id_usuario;
    }

    public void setId(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}