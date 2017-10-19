package com.lemelo.controlepasse;

/**
 * Created by leoci on 17/10/2017.
 */

class LinhaOnibus {
    private Integer id;
    private String letra;
    private Integer numero;
    private String descricao;

    public LinhaOnibus() {
        setId(id);
        setLetra(letra);
        setNumero(numero);
        setDescricao(descricao);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "" + letra + numero + " - " + descricao;
    }
}
