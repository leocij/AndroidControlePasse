package com.lemelo.controlepasse;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ParseException;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public void insert(Recarga recarga) throws ParseException, java.text.ParseException {
        String sql = "insert into recarga (data,quantidade,valor_passe) values (?,?,?)";

        String dataStr = recarga.getData();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        java.util.Date d = sdf1.parse(dataStr);
        java.sql.Date dataSql = new java.sql.Date(d.getTime());

        Integer quantidade = Integer.parseInt(recarga.getQuantidade());

        String valorPasseStr = recarga.getValorPasse();
        String valorPasseNF = NumberFormat.getCurrencyInstance().parse(valorPasseStr).toString();
        BigDecimal valorPasse = new BigDecimal(Long.parseLong(valorPasseNF));

        Object bindArgs[] = new Object[] {
                dataSql, quantidade, valorPasse
        };
        db.execSQL(sql, bindArgs);
    }

    public void delete(Integer id){
        String sql = "delete from recarga where id = ?";
        Object bindArgs[] = new Object[]{
                id
        };
        db.execSQL(sql, bindArgs);
    }

    public void update(Recarga recarga) throws ParseException, java.text.ParseException {
        String sql = "update recarga set data = ?, quantidade = ?, valor_passe = ? where id = ?";

        String dataStr = recarga.getData();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        java.util.Date d = sdf1.parse(dataStr);
        java.sql.Date dataSql = new java.sql.Date(d.getTime());

        Integer quantidade = Integer.parseInt(recarga.getQuantidade());

        String valorPasseStr = recarga.getValorPasse();
        String valorPasseNF = NumberFormat.getCurrencyInstance().parse(valorPasseStr).toString();
        BigDecimal valorPasse = new BigDecimal(Long.parseLong(valorPasseNF));

        Object bindArgs[] = new Object[] {
                dataSql, quantidade, valorPasse
        };
        db.execSQL(sql, bindArgs);
    }

    public List<Recarga> listAll() throws ParseException, java.text.ParseException {
        List<Recarga> recargas = new ArrayList<>();
        Cursor cursor = db.query("recarga", new String[]{"id","data","quantidade","valor_passe"},null,null,null,null,"id");
        while (cursor != null && cursor.moveToNext()) {
            Recarga recarga = new Recarga();

            recarga.setId(cursor.getInt(0));

            String dataStr = cursor.getString(1);
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            Date dataUtil = sdf1.parse(dataStr);
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            recarga.setData(sdf2.format(dataUtil));

            recarga.setQuantidade(String.valueOf(cursor.getInt(2)));

            NumberFormat nf = NumberFormat.getCurrencyInstance();
            recarga.setValorPasse(nf.format(cursor.getDouble(3)));

            recargas.add(recarga);
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return recargas;
    }
}
