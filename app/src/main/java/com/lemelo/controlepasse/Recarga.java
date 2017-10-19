package com.lemelo.controlepasse;

/**
 * Created by leoci on 19/10/2017.
 */

class Recarga {
    private Integer id;
    private String data;
    private String quantidade;
    private String valorPasse;

    public Recarga() {
        setId(id);
        setData(data);
        setQuantidade(quantidade);
        setValorPasse(valorPasse);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getValorPasse() {
        return valorPasse;
    }

    public void setValorPasse(String valorPasse) {
        this.valorPasse = valorPasse;
    }

    @Override
    public String toString() {
        return "Data: " + data +
                "Quantidade: " + quantidade +
                "Valor do Passe: " + valorPasse;
    }
}
