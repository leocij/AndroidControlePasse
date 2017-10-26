package com.lemelo.controlepasse;

/**
 * Created by leoci on 19/10/2017.
 */

class Recarga {
    private Integer id;
    private String dataHora;
    private String valorPasse;
    private String quantidade;
    private String saldoAtual;

    public Recarga() {
        setId(id);
        setDataHora(dataHora);
        setValorPasse(valorPasse);
        setQuantidade(quantidade);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getValorPasse() {
        return valorPasse;
    }

    public void setValorPasse(String valorPasse) {
        this.valorPasse = valorPasse;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Dt/Hr: " + dataHora +
                "\nValor do Passe: " + valorPasse +
                "\nQuantidade: " + quantidade;
    }

    public void setSaldoAtual(String saldoAtual) {
        this.saldoAtual = saldoAtual;
    }

    public String getSaldoAtual() {
        return saldoAtual;
    }
}
