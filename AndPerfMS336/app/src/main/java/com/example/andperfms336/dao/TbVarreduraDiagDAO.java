package com.example.andperfms336.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.andperfms336.modelo.TbVarreduraDiag;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class TbVarreduraDiagDAO {

    private static SQLiteDatabase db;

    public TbVarreduraDiagDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public void criarTabela() {
        try {
            db.beginTransaction();

            String sql ="CREATE TABLE IF NOT EXISTS tbvarreduradiag (" +
                    "		idVarreduraDiag INTEGER PRIMARY KEY, " +
                    "		descricao TEXT " +
                    ");";
            db.execSQL(sql);

            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    public List<TbVarreduraDiag> listar() throws Exception {

        Cursor cursor = null;
        List<TbVarreduraDiag> list = new ArrayList<>();
        try {
            String sql =
                    "SELECT * " +
                            "FROM     tbvarreduradiag " +
                            "ORDER BY idVarreduraDiag " ;
            cursor = db.rawQuery(sql,null);
            int cont = 0;
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    cont++;
                    if(cont == 1){
                        TbVarreduraDiag modelo = new TbVarreduraDiag();
                        modelo.setIdVarreduraDiag((long)0);
                        modelo.setDescricao("Selecione");
                        list.add(modelo);
                    }
                    TbVarreduraDiag modelo = new TbVarreduraDiag();
                    modelo.setIdVarreduraDiag(cursor.getLong(cursor.getColumnIndex("idVarreduraDiag")));
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

    public void inserir(TbVarreduraDiag modelo) {
        try {

            db.beginTransaction();

            String sql =
            "INSERT INTO tbvarreduradiag ( " +
                    "       idVarreduraDiag, " +
                    "		descricao " +
                    ") VALUES ( " +
                    "       ?,?" +
                    ") ";
            db.execSQL(sql, new Object[] {
                    modelo.getIdVarreduraDiag(),
                    modelo.getDescricao()
            });
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    public List<TbVarreduraDiag> listarByClas(long idVarreduraClas){

        Cursor cursor = null;
        List<TbVarreduraDiag> list = new ArrayList<>();
        try {
            String sql =
                    "SELECT idVarreduraDiag, varreduraDiag AS descricao " +
                            "FROM     vwVarreduraCDP WHERE idVarreduraClas = "+idVarreduraClas+" GROUP BY idVarreduraDiag, varreduraDiag " +
                            "ORDER BY idVarreduraDiag " ;
            cursor = db.rawQuery(sql,null);
            int cont = 0;
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    cont++;
                    if(cont == 1){
                        TbVarreduraDiag modelo = new TbVarreduraDiag();
                        modelo.setIdVarreduraDiag((long)0);
                        modelo.setDescricao("Selecione");
                        list.add(modelo);
                    }
                    TbVarreduraDiag modelo = new TbVarreduraDiag();
                    modelo.setIdVarreduraDiag(cursor.getLong(cursor.getColumnIndex("idVarreduraDiag")));
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

            TbVarreduraDiag [] array = new Gson().fromJson(resp, TbVarreduraDiag[].class);

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

            String sql = "DELETE FROM tbvarreduradiag";
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
