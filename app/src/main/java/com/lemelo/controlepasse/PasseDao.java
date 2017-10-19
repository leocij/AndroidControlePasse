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
 * Created by leoci on 16/10/2017.
 */

class PasseDao {
    private SQLiteDatabase db;

    public PasseDao(SQLiteDatabase db){
        this.db = db;
    }

    public void insert(Passe passe) throws ParseException, java.text.ParseException {
        String sql = "insert into passe (data, valor) values {?,?)";
        String dataStr = passe.getData();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        java.util.Date d = sdf1.parse(dataStr);
        java.sql.Date dataSql = new java.sql.Date(d.getTime());
        Object bindArgs[] = new Object[]{
                dataSql, new BigDecimal(Long.parseLong(String.valueOf(NumberFormat.getCurrencyInstance().parse(passe.getValor()))))
        };
        db.execSQL(sql, bindArgs);
    }

    public void delete(Integer id){
        String sql = "delete from passe where id = ?";
        Object bindArgs[] = new Object[]{
                id
        };
        db.execSQL(sql, bindArgs);
    }

    public void update(Passe passe) throws java.text.ParseException {

        String sql = "update passe set data = ?, valor = ? where id = ?";
        String dataStr = passe.getData();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        java.util.Date d = sdf1.parse(dataStr);
        java.sql.Date dataSql = new java.sql.Date(d.getTime());
        Object bindArgs[] = new Object[]{
                dataSql, new BigDecimal(Long.parseLong(String.valueOf(NumberFormat.getCurrencyInstance().parse(passe.getValor())))) , passe.getId()
        };
        db.execSQL(sql, bindArgs);
    }

    public List<Passe> listAll() throws java.text.ParseException {
        List<Passe> passes = new ArrayList<Passe>();
        Cursor cursor = db.query("Passe", new String[]{"id","data","valor"},null,null,null,null,"id");
        while (cursor != null && cursor.moveToNext()){
            Passe passe = new Passe();
            passe.setId(cursor.getInt(0));
            String str = cursor.getString(1);
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            Date dateUtil = sdf1.parse(str);
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            passe.setData(sdf2.format(dateUtil));
            NumberFormat nf = NumberFormat.getCurrencyInstance();
            passe.setValor(nf.format(cursor.getDouble(2)));
            passes.add(passe);
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return passes;
    }
}
