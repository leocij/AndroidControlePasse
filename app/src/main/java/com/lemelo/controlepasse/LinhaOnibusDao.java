package com.lemelo.controlepasse;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leoci on 17/10/2017.
 */

public class LinhaOnibusDao {
    private SQLiteDatabase db;

    public LinhaOnibusDao(SQLiteDatabase db) {
        this.db = db;
    }

    public void insert(LinhaOnibus linhaOnibus) throws ParseException {
        String sql = "insert into linha_onibus (letra,numero,descricao) values (?,?,?)";
        Object bindArgs[] = new Object[] {
                linhaOnibus.getLetra(), linhaOnibus.getNumero(), linhaOnibus.getDescricao()
        };
        db.execSQL(sql, bindArgs);
    }

    public void delete(Integer id) {
        String sql = "delete from linha_onibus where id = ?";
        Object bindArgs[] = new Object[] {
                id
        };
        db.execSQL(sql, bindArgs);
    }

    private void update(LinhaOnibus linhaOnibus) throws ParseException {
        String sql = "update linha_onibus set letra = ?, numero = ?, descricao = ? where id = ?";
        Object bindArgs[] = new Object[] {
                linhaOnibus.getLetra(), linhaOnibus.getNumero(), linhaOnibus.getDescricao(), linhaOnibus.getId()
        };
        db.execSQL(sql, bindArgs);
    }

    public List<LinhaOnibus> listAll() throws ParseException {
        List<LinhaOnibus> linhas = new ArrayList<>();
        Cursor cursor = db.query("linha_onibus", new String[]{"id","letra","numero","descricao"}, null, null,null,null,"id");
        while (cursor != null && cursor.moveToNext()) {
            LinhaOnibus linhaOnibus = new LinhaOnibus();
            linhaOnibus.setId(cursor.getInt(0));
            linhaOnibus.setLetra(cursor.getString(1));
            linhaOnibus.setNumero(cursor.getInt(2));
            linhaOnibus.setDescricao(cursor.getString(3));
            linhas.add(linhaOnibus);
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return linhas;
    }
}
