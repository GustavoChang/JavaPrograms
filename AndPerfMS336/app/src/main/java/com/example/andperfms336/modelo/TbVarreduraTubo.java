package com.example.andperfms336.modelo;


public class TbVarreduraTubo {
    private Long idVarreduraTubo;
    private String descricao;

    public Long getIdVarreduraTubo() {
        return idVarreduraTubo;
    }

    public void setIdVarreduraTubo(Long idVarreduraTubo) {
        this.idVarreduraTubo = idVarreduraTubo;
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
