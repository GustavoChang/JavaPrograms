package com.example.andperfms336.modelo;


public class TbVarreduraRet {
    private Long idVarreduraRet;
    private String descricao;

    public Long getIdVarreduraRet() {
        return idVarreduraRet;
    }

    public void setIdVarreduraRet(Long idVarreduraRet) {
        this.idVarreduraRet = idVarreduraRet;
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
