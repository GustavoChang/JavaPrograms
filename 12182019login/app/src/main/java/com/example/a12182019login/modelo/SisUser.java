package com.example.a12182019login.modelo;

public class SisUser {
    private Long sisUser;
    private String nome;
    private String login;
    private String senha;
    private String email;
    private String tel;
    private String cel;
    private Long sisPerfil;
    private String situacao;

    public Long getSisUser() {
        return sisUser;
    }

    public void setSisUser(Long sisUser) {
        this.sisUser = sisUser;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCel() {
        return cel;
    }

    public void setCel(String cel) {
        this.cel = cel;
    }

    public Long getSisPerfil() {
        return sisPerfil;
    }

    public void setSisPerfil(Long sisPerfil) {
        this.sisPerfil = sisPerfil;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
