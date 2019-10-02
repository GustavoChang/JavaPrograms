package com.example.andperfms336.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.andperfms336.modelo.TbVarreduraRet;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class TbVarreduraRetDAO {

    private static SQLiteDatabase db;

    public TbVarreduraRetDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public void criarTabela() {
        try {
            db.beginTransaction();

            String sql ="CREATE TABLE IF NOT EXISTS tbvarreduraret (" +
                    "		idVarreduraRet INTEGER PRIMARY KEY, " +
                    "		descricao TEXT " +
                    ");";
            db.execSQL(sql);

            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    public void inserir(TbVarreduraRet modelo) {
        try {

            db.beginTransaction();

            String sql =
            "INSERT INTO tbvarreduraret ( " +
                    "       idVarreduraRet, " +
                    "		descricao " +
                    ") VALUES ( " +
                    "       ?,?" +
                    ") ";
            db.execSQL(sql, new Object[] {
                    modelo.getIdVarreduraRet(),
                    modelo.getDescricao()
            });
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    public List<TbVarreduraRet> listar() throws Exception {

        Cursor cursor = null;
        List<TbVarreduraRet> list = new ArrayList<>();
        try {
            String sql =
                    "SELECT * " +
                            "FROM     tbvarreduraret " +
                            "ORDER BY idVarreduraRet " ;
            cursor = db.rawQuery(sql,null);
            int cont = 0;
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    cont++;
                    if(cont == 1){
                        TbVarreduraRet modelo = new TbVarreduraRet();
                        modelo.setIdVarreduraRet((long)0);
                        modelo.setDescricao("Selecione");
                        list.add(modelo);
                    }
                    TbVarreduraRet modelo = new TbVarreduraRet();
                    modelo.setIdVarreduraRet(cursor.getLong(cursor.getColumnIndex("idVarreduraRet")));
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

    public TbVarreduraRet getModeloByPk(Long id) throws Exception {

        Cursor cursor = null;
        TbVarreduraRet modelo = null;
        try {
            String sql =
                    "SELECT * " +
                            "FROM     tbvarreduraret WHERE idVarreduraRet = "+id+
                            " ORDER BY idVarreduraRet " ;
            cursor = db.rawQuery(sql,null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    modelo = new TbVarreduraRet();
                    modelo.setIdVarreduraRet(cursor.getLong(cursor.getColumnIndex("idVarreduraRet")));
                    modelo.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
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
        return modelo;
    }

    public boolean processar(String resp) throws Exception{

        boolean processoOk = false;

        if(resp != null
                && !resp.trim().equals("")){

            TbVarreduraRet [] array = new Gson().fromJson(resp, TbVarreduraRet[].class);

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

            String sql = "DELETE FROM tbvarreduraret";
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
