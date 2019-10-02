package com.example.andperfms336.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.andperfms336.modelo.TbVarreduraProg;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class TbVarreduraProgDAO {

    private static SQLiteDatabase db;

    public TbVarreduraProgDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public void criarTabela() {
        try {
            db.beginTransaction();

            String sql ="CREATE TABLE IF NOT EXISTS tbvarreduraprog (" +
                    "		idVarreduraProg INTEGER PRIMARY KEY, " +
                    "		descricao TEXT " +
                    ");";
            db.execSQL(sql);

            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    public void inserir(TbVarreduraProg modelo) {
        try {

            db.beginTransaction();

            String sql =
            "INSERT INTO tbvarreduraprog ( " +
                    "       idVarreduraProg, " +
                    "		descricao " +
                    ") VALUES ( " +
                    "       ?,?" +
                    ") ";
            db.execSQL(sql, new Object[] {
                    modelo.getIdVarreduraProg(),
                    modelo.getDescricao()
            });
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }


    public List<TbVarreduraProg> listarByDiag(long idVarreduraDiag){
        System.out.println("berrrroooooooooooooooooooooooooooooooooooooo");

        Cursor cursor = null;
        List<TbVarreduraProg> list = new ArrayList<>();
        try {
            String sql =
                    "SELECT idVarreduraProg, varreduraProg AS descricao " +
                            "FROM     vwVarreduraCDP WHERE idVarreduraDiag = "+idVarreduraDiag+" GROUP BY idVarreduraProg, varreduraProg " +
                            "ORDER BY idVarreduraProg " ;
            cursor = db.rawQuery(sql,null);
            int cont = 0;
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    cont++;
                    if(cont == 1){
                        TbVarreduraProg modelo = new TbVarreduraProg();
                        modelo.setIdVarreduraProg((long)0);
                        modelo.setDescricao("Selecione");
                        list.add(modelo);
                    }
                    TbVarreduraProg modelo = new TbVarreduraProg();
                    modelo.setIdVarreduraProg(cursor.getLong(cursor.getColumnIndex("idVarreduraProg")));
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

    public List<TbVarreduraProg> listar() throws Exception {

        Cursor cursor = null;
        List<TbVarreduraProg> list = new ArrayList<>();
        try {
            String sql =
                    "SELECT * " +
                            "FROM     tbvarreduraprog " +
                            "ORDER BY idVarreduraProg " ;
            cursor = db.rawQuery(sql,null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    TbVarreduraProg modelo = new TbVarreduraProg();
                    modelo.setIdVarreduraProg(cursor.getLong(cursor.getColumnIndex("idVarreduraProg")));
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

            TbVarreduraProg [] array = new Gson().fromJson(resp, TbVarreduraProg[].class);

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

            String sql = "DELETE FROM tbvarreduraprog";
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
