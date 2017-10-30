package com.lemelo.controlepasse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by leoci on 16/10/2017.:)
 */

public class FabricaConexao extends SQLiteOpenHelper {
    FabricaConexao(Context context) {
        super(context, "passe.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table linha_onibus (id integer primary key autoincrement, letra text, numero integer, descricao text)");
        db.execSQL("insert into linha_onibus (letra, numero, descricao) values ('A',109,'Marta Helena')");
        db.execSQL("insert into linha_onibus (letra, numero, descricao) values ('A',208,'Cruzeiro')");
        db.execSQL("insert into linha_onibus (letra, numero, descricao) values ('A',234,'Minas Gerais')");
        db.execSQL("insert into linha_onibus (letra, numero, descricao) values ('I',252,'Industrial')");
        db.execSQL("create table recarga (id integer primary key autoincrement, data_hora text, valor_passe decimal(18,2), quantidade integer)");
        db.execSQL("create table saldo (id integer primary key autoincrement, data_hora text, valor_recarga decimal(18,2), saldo_anterior decimal(18,2), saldo_atual decimal(18,2))");
        db.execSQL("create table principal (id integer primary key autoincrement, saldo_atual decimal(18,2), data_hora text, linha_onibus text, valor_passe decimal(18,2), saldo_futuro decimal(18,2))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table linha_onibus");
        db.execSQL("drop table recarga");
        db.execSQL("drop table saldo");
        db.execSQL("drop table principal");
        onCreate(db);
    }
}
