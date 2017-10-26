package com.lemelo.controlepasse;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by leoci on 24/10/2017.
 */

class SaldoDao {
    private SQLiteDatabase db;

    public SaldoDao(SQLiteDatabase db) {
        this.db = db;
    }

    public List<Saldo> listAll() throws ParseException {
        List<Saldo> saldos = new ArrayList<>();
        Cursor cursor = db.query(
                "Saldo",
                new String[] {
                        "id","data_hora","valor_recarga","saldo_anterior","saldo_atual"
                },
                null,null,null,null,"id"
        );

        while(cursor != null && cursor.moveToNext()) {
            Saldo saldo = new Saldo();
            saldo.setId(cursor.getInt(0));

            String dataHoraStr = cursor.getString(1);
//            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//            Date dataUtil = sdf1.parse(dataStr);
//            SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//            saldo.setData(sdf2.format(dataUtil));
            saldo.setDataHora(dataHoraStr);

            NumberFormat valorRecargaNf = NumberFormat.getCurrencyInstance();
            saldo.setValorRecarga(valorRecargaNf.format(cursor.getDouble(3)));

            NumberFormat saldoAnteriorNf = NumberFormat.getCurrencyInstance();
            saldo.setSaldoAnterior(saldoAnteriorNf.format(cursor.getDouble(4)));

            NumberFormat saldoAtualNf = NumberFormat.getCurrencyInstance();
            saldo.setSaldoAtual(saldoAtualNf.format(cursor.getDouble(5)));

            saldos.add(saldo);
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return saldos;
    }

    public void insert(Saldo saldo) throws ParseException {

        String dataHoraStr = saldo.getDataHora();
//        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//        java.util.Date dataUtil = sdf1.parse(dataStr);
//        java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime());

        String valorRecargaStr = saldo.getValorRecarga();
        String valorRecargaNf = NumberFormat.getCurrencyInstance().parse(valorRecargaStr).toString();
        BigDecimal valorRecarga = new BigDecimal(valorRecargaNf);

        String saldoAnteriorStr = saldo.getSaldoAnterior();
        String saldoAnteriorNf = NumberFormat.getCurrencyInstance().parse(saldoAnteriorStr).toString();
        BigDecimal saldoAnterior = new BigDecimal(saldoAnteriorNf);

        String saldoAtualStr = saldo.getSaldoAtual();
        String saldoAtualNf = NumberFormat.getCurrencyInstance().parse(saldoAtualStr).toString();
        BigDecimal saldoAtual = new BigDecimal(saldoAtualNf);

        Object bindArgsSaldo[] = new Object[] {
                dataHoraStr, valorRecarga, saldoAnterior, saldoAtual
        };

        String sqlInsertSaldo = "insert into saldo (data_hora, valor_recarga, saldo_anterior, saldo_atual) values (?,?,?,?)";
        db.execSQL(sqlInsertSaldo, bindArgsSaldo);
    }

    public void update(Saldo saldo) throws ParseException {

        String dataHoraStr = saldo.getDataHora();
//        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//        java.util.Date dataUtil = sdf1.parse(dataStr);
//        java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime());

        String valorRecargaStr = saldo.getValorRecarga();
        String valorRecargaNf = NumberFormat.getCurrencyInstance().parse(valorRecargaStr).toString();
        BigDecimal valorRecarga = new BigDecimal(valorRecargaNf);

        String saldoAnteriorStr = saldo.getSaldoAnterior();
        String saldoAnteriorNf = NumberFormat.getCurrencyInstance().parse(saldoAnteriorStr).toString();
        BigDecimal saldoAnterior = new BigDecimal(saldoAnteriorNf);

        String saldoAtualStr = saldo.getSaldoAtual();
        String saldoAtualNf = NumberFormat.getCurrencyInstance().parse(saldoAtualStr).toString();
        BigDecimal saldoAtual = new BigDecimal(saldoAtualNf);

        Integer id = saldo.getId();

        Object bindArgsSaldo[] = new Object[] {
                dataHoraStr, valorRecarga, saldoAnterior, saldoAtual, id
        };

        String sqlInsertSaldo = "update saldo set data_hora = ?, valor_recarga = ?, saldo_anterior = ?, saldo_atual = ? where id = ?";
        db.execSQL(sqlInsertSaldo, bindArgsSaldo);
    }

    public void delete(Integer id) {
        Object bindArgsSaldo[] = new Object[] {
                id
        };

        String sqlDeleteSaldoById = "delete from saldo where id = ?";
        db.execSQL(sqlDeleteSaldoById, bindArgsSaldo);
    }


    public String getSaldoAtual() {
        Cursor cursor = db.rawQuery(
                "select * from saldo order by id desc limit 1",
                null
        );

        String saldoAtualStr = null;

        while (cursor != null && cursor.moveToNext()) {
            saldoAtualStr = cursor.getString(4);
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return saldoAtualStr;
    }
}
