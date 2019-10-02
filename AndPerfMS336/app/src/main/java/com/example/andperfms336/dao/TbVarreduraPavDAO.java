package com.example.andperfms336.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.andperfms336.http.Http;
import com.example.andperfms336.modelo.TbEquipe;
import com.example.andperfms336.modelo.TbVarreduraPav;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class TbVarreduraPavDAO {

    private static SQLiteDatabase db;

    public TbVarreduraPavDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public void criarTabela() {
        try {
            db.beginTransaction();

            String sql ="CREATE TABLE IF NOT EXISTS tbvarredurapav (" +
                    "		idVarreduraPav INTEGER PRIMARY KEY, " +
                    "		descricao TEXT " +
                    ");";
            db.execSQL(sql);

            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    public void inserir(TbVarreduraPav modelo) {
        try {

            db.beginTransaction();

            String sql =
            "INSERT INTO tbvarredurapav ( " +
                    "       idVarreduraPav, " +
                    "		descricao " +
                    ") VALUES ( " +
                    "       ?,?" +
                    ") ";
            db.execSQL(sql, new Object[] {
                    modelo.getIdVarreduraPav(),
                    modelo.getDescricao()
            });
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    public List<TbVarreduraPav> listar() throws Exception {

        Cursor cursor = null;
        List<TbVarreduraPav> list = new ArrayList<>();
        try {
            String sql =
                    "SELECT * " +
                            "FROM     tbvarredurapav " +
                            "ORDER BY idVarreduraPav " ;
            cursor = db.rawQuery(sql,null);
            int cont = 0;
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    cont++;
                    if(cont == 1){
                        TbVarreduraPav modelo = new TbVarreduraPav();
                        modelo.setIdVarreduraPav((long)0);
                        modelo.setDescricao("Selecione");
                        list.add(modelo);
                    }
                    TbVarreduraPav modelo = new TbVarreduraPav();
                    modelo.setIdVarreduraPav(cursor.getLong(cursor.getColumnIndex("idVarreduraPav")));
                    modelo.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                    list.add(modelo);
                    cursor.moveToNext();
                }
                while (!cursor.isAfterLast());
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    public boolean processar(String resp) throws Exception{

        boolean processoOk = false;

        if(resp != null
                && !resp.trim().equals("")){

            TbVarreduraPav [] array = new Gson().fromJson(resp, TbVarreduraPav[].class);

            limparTabela();
            for(int i = 0; i < array.length; i++){
                inserir(array[i]);
            }

            processoOk = true;
        }

        return processoOk;
    }
    public void limparTabela() throws Exception {

        try {

            db.beginTransaction();

            String sql = "DELETE FROM tbvarredurapav";
            db.execSQL(sql);

            db.setTransactionSuccessful();
        }
        catch (Exception e) {

            throw e;
        }
        finally {

            db.endTransaction();
        }
    }

}
