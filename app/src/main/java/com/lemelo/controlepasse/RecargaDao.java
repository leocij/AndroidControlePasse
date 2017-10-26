package com.lemelo.controlepasse;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ParseException;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by leoci on 19/10/2017.
 */

class RecargaDao {
    private SQLiteDatabase db;

    public RecargaDao(SQLiteDatabase db) {
        this.db = db;
    }

    public List<Recarga> listAll() throws ParseException, java.text.ParseException {
        List<Recarga> recargas = new ArrayList<>();
        Cursor cursor = db.query("recarga", new String[]{"id","data_hora","valor_passe","quantidade"},null,null,null,null,"id");
        while (cursor != null && cursor.moveToNext()) {
            Recarga recarga = new Recarga();

            recarga.setId(cursor.getInt(0));

            String dataHoraStr = cursor.getString(1);
            recarga.setDataHora(dataHoraStr);

            NumberFormat nf = NumberFormat.getCurrencyInstance();
            recarga.setValorPasse(nf.format(cursor.getDouble(2)));

            recarga.setQuantidade(String.valueOf(cursor.getInt(3)));

            recargas.add(recarga);
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return recargas;
    }

    public void insert(Recarga recarga) throws ParseException, java.text.ParseException {

        String saldoAnteriorStr = recarga.getSaldoAtual();
        String saldoAnteriorNF = NumberFormat.getCurrencyInstance().parse(saldoAnteriorStr).toString();
        BigDecimal saldoAnterior = new BigDecimal(saldoAnteriorNF);

        String dataHoraStr = recarga.getDataHora();

        String valorPasseStr = recarga.getValorPasse();
        String valorPasseNF = NumberFormat.getCurrencyInstance().parse(valorPasseStr).toString();
        BigDecimal valorPasse = new BigDecimal(valorPasseNF);

        Integer quantidade = Integer.parseInt(recarga.getQuantidade());

        BigDecimal valorRecarga = valorPasse.multiply(BigDecimal.valueOf(quantidade));

        BigDecimal saldoAtual = valorRecarga.add(saldoAnterior);

        Object bindArgsRecarga[] = new Object[] {
                dataHoraStr, valorPasse, quantidade
        };

        Object bindArgsSaldo[] = new Object[] {
                dataHoraStr, valorRecarga, saldoAnterior, saldoAtual
        };

        String sqlInsertRecarga = "insert into recarga (data_hora, valor_passe, quantidade) values (?,?,?)";
        db.execSQL(sqlInsertRecarga, bindArgsRecarga);

        String sqlInsertSaldo = "insert into saldo (data_hora, valor_recarga, saldo_anterior, saldo_atual) values (?,?,?,?)";
        db.execSQL(sqlInsertSaldo, bindArgsSaldo);
    }

    public void delete(Integer id){
        String sql = "delete from recarga where id = ?";
        Object bindArgs[] = new Object[]{
                id
        };
        db.execSQL(sql, bindArgs);
    }

    public void update(Recarga recarga) throws ParseException, java.text.ParseException {
        String sql = "update recarga set data_hora = ?, valor_passe = ?, quantidade = ? where id = ?";

        String dataHoraStr = recarga.getDataHora();

        Integer quantidade = Integer.parseInt(recarga.getQuantidade());

        String valorPasseStr = recarga.getValorPasse();
        String valorPasseNF = NumberFormat.getCurrencyInstance().parse(valorPasseStr).toString();
        BigDecimal valorPasse = new BigDecimal(Long.parseLong(valorPasseNF));

        Object bindArgs[] = new Object[] {
                dataHoraStr, quantidade, valorPasse
        };
        db.execSQL(sql, bindArgs);
    }

    public String getValorPasse() {
        Cursor cursor = db.rawQuery("select * from recarga order by id desc limit 1", null);
        String valorPasseStr = null;

        while (cursor != null && cursor.moveToNext()) {
            valorPasseStr = cursor.getString(2);
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return valorPasseStr;
    }
}
