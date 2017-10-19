package com.lemelo.controlepasse;

/**
 * Created by leoci on 16/10/2017.
 */

class Passe {
    private Integer id;
    private String data;
    private String valor;

    public Passe() {
        setId(id);
        setData(data);
        setValor(valor);
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

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "" + "id: " + id +
                "\ndata: " + data +
                "\nvalor: " + valor;
    }
}
