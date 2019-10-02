package com.example.andperfms336.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.andperfms336.modelo.TbVarreduraTubo;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class TbVarreduraTuboDAO {

    private static SQLiteDatabase db;

    public TbVarreduraTuboDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public void criarTabela() {
        try {
            db.beginTransaction();

            String sql ="CREATE TABLE IF NOT EXISTS tbvarreduratubo (" +
                    "		idVarreduraTubo INTEGER PRIMARY KEY, " +
                    "		descricao TEXT " +
                    ");";
            db.execSQL(sql);

            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    public void inserir(TbVarreduraTubo modelo) {
        try {

            db.beginTransaction();

            String sql =
            "INSERT INTO tbvarreduratubo ( " +
                    "       idVarreduraTubo, " +
                    "		descricao " +
                    ") VALUES ( " +
                    "       ?,?" +
                    ") ";
            db.execSQL(sql, new Object[] {
                    modelo.getIdVarreduraTubo(),
                    modelo.getDescricao()
            });
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    public List<TbVarreduraTubo> listar() throws Exception {

        Cursor cursor = null;
        List<TbVarreduraTubo> list = new ArrayList<>();
        try {
            String sql =
                    "SELECT * " +
                            "FROM     tbvarreduratubo " +
                            "ORDER BY idVarreduraTubo " ;
            cursor = db.rawQuery(sql,null);
            int cont = 0;
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    cont++;
                    if(cont == 1){
                        TbVarreduraTubo modelo = new TbVarreduraTubo();
                        modelo.setIdVarreduraTubo((long)0);
                        modelo.setDescricao("Selecione");
                        list.add(modelo);
                    }

                    TbVarreduraTubo modelo = new TbVarreduraTubo();
                    modelo.setIdVarreduraTubo(cursor.getLong(cursor.getColumnIndex("idVarreduraTubo")));
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

            TbVarreduraTubo [] array = new Gson().fromJson(resp, TbVarreduraTubo[].class);

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

            String sql = "DELETE FROM tbvarreduratubo";
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
