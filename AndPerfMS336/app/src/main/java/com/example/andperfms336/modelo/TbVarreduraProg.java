package com.example.andperfms336.modelo;


public class TbVarreduraProg {
    private Long idVarreduraProg;
    private String descricao;

    public Long getIdVarreduraProg() {
        return idVarreduraProg;
    }

    public void setIdVarreduraProg(Long idVarreduraProg) {
        this.idVarreduraProg = idVarreduraProg;
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
