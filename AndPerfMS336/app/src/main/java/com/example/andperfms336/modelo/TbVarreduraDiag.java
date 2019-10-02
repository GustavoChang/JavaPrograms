package com.example.andperfms336.modelo;


public class TbVarreduraDiag {
    private Long idVarreduraDiag;
    private String descricao;

    public Long getIdVarreduraDiag() {
        return idVarreduraDiag;
    }

    public void setIdVarreduraDiag(Long idVarreduraDiag) {
        this.idVarreduraDiag = idVarreduraDiag;
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
