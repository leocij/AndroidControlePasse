package com.lemelo.controlepasse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by leoci on 16/10/2017.
 */

public class FabricaConexao extends SQLiteOpenHelper {
    public FabricaConexao(Context context) {
        super(context, "passe.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table passe (id integer primary key autoincrement, data date, valor decimal(18,2))");
        db.execSQL("create table linha_onibus (id integer primary key autoincrement, letra text, numero integer, descricao text)");
        db.execSQL("create table recarga (id integer primary key autoincrement, data date, quantidade integer, valor_passe decimal(18,2))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table passe");
        db.execSQL("drop table linha_onibus");
        db.execSQL("drop table recarga");
        onCreate(db);
    }
}
