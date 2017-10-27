package com.lemelo.controlepasse;

import android.database.sqlite.SQLiteDatabase;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Created by leoci on 27/10/2017.:)
 */

class PrincipalDao {

    private final SQLiteDatabase db;

    PrincipalDao(SQLiteDatabase db) {
        this.db = db;
    }

    void insert(Principal principal) throws ParseException {
        String saldoAtualStr = principal.getSaldoAtual();
        String saldoAtualNft = NumberFormat.getCurrencyInstance().parse(saldoAtualStr).toString();
        BigDecimal saldoAtualBdc = new BigDecimal(saldoAtualNft);

        String dataHoraStr = principal.getDataHora();

        String linhaOnibusStr = principal.getLinhaOnibus();

        String valorPasseStr = principal.getValorPasse();
        String valorPasseNft = NumberFormat.getCurrencyInstance().parse(valorPasseStr).toString();
        BigDecimal valorPasseBdc = new BigDecimal(valorPasseNft);

        String saldoFuturoStr = principal.getSaldoFuturo();
        String saldoFuturoNft = NumberFormat.getCurrencyInstance().parse(saldoFuturoStr).toString();
        BigDecimal saldoFuturoBdc = new BigDecimal(saldoFuturoNft);

        String valorRecargaStr = "0";
        BigDecimal valorRecargaBdc = new BigDecimal(valorRecargaStr);

        Object bindArgsPrincipal[] = new Object[] {
                saldoAtualBdc, dataHoraStr, linhaOnibusStr, valorPasseBdc, saldoFuturoBdc
        };

        Object bindArgsSaldo[] = new Object[] {
                dataHoraStr, valorRecargaBdc, saldoAtualBdc, saldoFuturoBdc
        };

        String sqlInsertPrincipal = "insert into principal (saldo_atual, data_hora, linha_onibus, valor_passe, saldo_futuro) values (?,?,?,?,?)";
        db.execSQL(sqlInsertPrincipal, bindArgsPrincipal);

        String sqlInsertSaldo = "insert into saldo (data_hora, valor_recarga, saldo_anterior, saldo_atual) values (?,?,?,?)";
        db.execSQL(sqlInsertSaldo, bindArgsSaldo);
    }
}
