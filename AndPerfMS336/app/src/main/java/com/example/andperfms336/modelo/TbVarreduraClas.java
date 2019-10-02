package com.example.andperfms336.modelo;


public class TbVarreduraClas {
    private Long idVarreduraClas;
    private String descricao;

    public Long getIdVarreduraClas() {
        return idVarreduraClas;
    }

    public void setIdVarreduraClas(Long idVarreduraClas) {
        this.idVarreduraClas = idVarreduraClas;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
