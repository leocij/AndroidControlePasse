package com.lemelo.controlepasse;

/**
 * Created by Leoci Melo on 27/10/2017:)
 */

class Principal {
    private Integer id;
    private String saldoAtual;
    private String dataHora;
    private String linhaOnibus;
    private String valorPasse;
    private String saldoFuturo;

    public Principal() {
    }

    public Principal(Integer id, String saldoAtual, String dataHora, String linhaOnibus, String valorPasse, String saldoFuturo) {
        this.id = id;
        this.saldoAtual = saldoAtual;
        this.dataHora = dataHora;
        this.linhaOnibus = linhaOnibus;
        this.valorPasse = valorPasse;
        this.saldoFuturo = saldoFuturo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSaldoAtual() {
        return saldoAtual;
    }

    public void setSaldoAtual(String saldoAtual) {
        this.saldoAtual = saldoAtual;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getLinhaOnibus() {
        return linhaOnibus;
    }

    public void setLinhaOnibus(String linhaOnibus) {
        this.linhaOnibus = linhaOnibus;
    }

    public String getValorPasse() {
        return valorPasse;
    }

    public void setValorPasse(String valorPasse) {
        this.valorPasse = valorPasse;
    }

    public String getSaldoFuturo() {
        return saldoFuturo;
    }

    public void setSaldoFuturo(String saldoFuturo) {
        this.saldoFuturo = saldoFuturo;
    }

    @Override
    public String toString() {
        return "Saldo Atual: " + saldoAtual +
                "\nDt/Hr: " + dataHora +
                "\nLinha de Onibus: " + linhaOnibus +
                "\nValor do Passe: " + valorPasse +
                "\nSaldo Futuro: " + saldoFuturo;
    }
}
