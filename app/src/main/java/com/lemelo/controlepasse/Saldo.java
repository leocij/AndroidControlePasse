package com.lemelo.controlepasse;

/**
 * Created by leoci on 24/10/2017.
 */

class Saldo {
    private Integer id;
    private String dataHora;
    private String valorRecarga;
    private String saldoAnterior;
    //saldoAtual = valorRecarga + saldoAnterior
    private String saldoAtual;

    public Saldo() {
        setId(id);
        setDataHora(dataHora);
        setValorRecarga(valorRecarga);
        setSaldoAnterior(saldoAnterior);
        setSaldoAtual(saldoAtual);
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

    public String getValorRecarga() {
        return valorRecarga;
    }

    public void setValorRecarga(String valorRecarga) {
        this.valorRecarga = valorRecarga;
    }

    public String getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(String saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public String getSaldoAtual() {
        return saldoAtual;
    }

    public void setSaldoAtual(String saldoAtual) {
        this.saldoAtual = saldoAtual;
    }

    @Override
    public String toString() {
        return "Dt/Hr: " + dataHora +
                "Valor da Recarga: " + valorRecarga +
                "Saldo Anterior: " + saldoAnterior +
                "Saldo Atual: " + saldoAtual;
    }
}
