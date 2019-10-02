package com.example.andperfms336.modelo;


public class TbVarreduraPav{
    private Long idVarreduraPav;
    private String descricao;

    public Long getIdVarreduraPav() {
        return idVarreduraPav;
    }

    public void setIdVarreduraPav(Long idVarreduraPav) {
        this.idVarreduraPav = idVarreduraPav;
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
