package com.example.andperfms336.modelo;

import java.io.Serializable;

public class FtVarredura implements Serializable {
    private Long id;
    private Long idVarredura;
    private String arquivo;
    private String coordX;
    private String coordY;
    private String recebidoServidor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdVarredura() {
        return idVarredura;
    }

    public void setIdVarredura(Long idVarredura) {
        this.idVarredura = idVarredura;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    public String getCoordX() {
        return coordX;
    }

    public void setCoordX(String coordX) {
        this.coordX = coordX;
    }

    public String getCoordY() {
        return coordY;
    }

    public void setCoordY(String coordY) {
        this.coordY = coordY;
    }

    public String getRecebidoServidor() {
        return recebidoServidor;
    }

    public void setRecebidoServidor(String recebidoServidor) {
        this.recebidoServidor = recebidoServidor;
    }
}
