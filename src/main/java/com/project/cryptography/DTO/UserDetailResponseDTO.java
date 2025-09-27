package com.project.cryptography.DTO;

import com.project.cryptography.Model.User;

public class UserDetailResponseDTO {

    private Long id;
    private String nome;
    private String username;
    private String senha;

    public UserDetailResponseDTO(User user) {
        this.id = user.getId();
        this.nome = user.getNome();
        this.username = user.getUsername();
        this.senha = user.getSenha();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
