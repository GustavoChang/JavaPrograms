package com.example.andperfms336.dao;

import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.andperfms336.R;
import com.example.andperfms336.modelo.TbVarreduraCroq;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class TbVarreduraCroqDAO {

    private static SQLiteDatabase db;

    public TbVarreduraCroqDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public void criarTabela() {
        try {
            db.beginTransaction();

            String sql ="CREATE TABLE IF NOT EXISTS tbvarreduracroq (" +
                    "		idVarreduraCroq INTEGER PRIMARY KEY, " +
                    "		arquivo TEXT, " +
                    "		qtde INTEGER, " +
                    "		tp1 TEXT, " +
                    "		tp2 TEXT, " +
                    "		tp3 TEXT, " +
                    "		tp4 TEXT " +
                    ");";
            db.execSQL(sql);

            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    public void inserir(TbVarreduraCroq modelo) {
        try {

            db.beginTransaction();

            String sql =
            "INSERT INTO tbvarreduracroq ( " +
                    "       idVarreduraCroq, " +
                    "		arquivo, " +
                    "		qtde, " +
                    "		tp1, " +
                    "		tp2, " +
                    "		tp3, " +
                    "		tp4 " +
                    ") VALUES ( " +
                    "       ?,?,?,?,?,?,?" +
                    ") ";
            db.execSQL(sql, new Object[] {
                    modelo.getIdVarreduraCroq(),
                    modelo.getArquivo(),
                    modelo.getQtde(),
                    modelo.getTp1(),
                    modelo.getTp2(),
                    modelo.getTp3(),
                    modelo.getTp4()
            });
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    public String[] getSpinnerTitles(List<TbVarreduraCroq> list) {
        int qtde = list.size();
        String[] spinnerTitles = new String[qtde];
        int cont = 0;
        for(TbVarreduraCroq lista: list){
            spinnerTitles[cont] = lista.getArquivo();
            cont++;
        }
        return spinnerTitles;
    }

    public String[] getSpinnerPopulation(List<TbVarreduraCroq> list) {
        int qtde = list.size();
        String[] spinnerPopulation = new String[qtde];
        int cont = 0;
        for(TbVarreduraCroq lista: list){
            spinnerPopulation[cont] = lista.getIdVarreduraCroq().toString();
            cont++;
        }
        return spinnerPopulation;
    }

    public int[] getSpinnerImages(List<TbVarreduraCroq> list) {
        int qtde = list.size();
        int[] spinnerImages = new int[qtde];
        int cont = 0;

        spinnerImages = new int[]{0, R.drawable.ic_croqui1
                , R.drawable.ic_croqui2
                , R.drawable.ic_croqui3
                , R.drawable.ic_croqui4
                , R.drawable.ic_croqui5
                , R.drawable.ic_croqui6
                , R.drawable.ic_croqui7
                , 0};
        return spinnerImages;
    }

    public List<TbVarreduraCroq> listar() throws Exception {

        Cursor cursor = null;
        List<TbVarreduraCroq> list = new ArrayList<>();
        try {
            String sql =
                    "SELECT * " +
                            "FROM     tbvarreduracroq " +
                            "ORDER BY idVarreduraCroq " ;
            cursor = db.rawQuery(sql,null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    TbVarreduraCroq modelo = new TbVarreduraCroq();
                    modelo.setIdVarreduraCroq(cursor.getLong(cursor.getColumnIndex("idVarreduraCroq")));
                    modelo.setArquivo(cursor.getString(cursor.getColumnIndex("arquivo")));
                    modelo.setQtde(cursor.getLong(cursor.getColumnIndex("qtde")));
                    modelo.setTp1(cursor.getString(cursor.getColumnIndex("tp1")));
                    modelo.setTp2(cursor.getString(cursor.getColumnIndex("tp2")));
                    modelo.setTp3(cursor.getString(cursor.getColumnIndex("tp3")));
                    modelo.setTp4(cursor.getString(cursor.getColumnIndex("tp4")));
                    if(modelo.getIdVarreduraCroq() >= 1 && modelo.getIdVarreduraCroq() <= 6 ){
                        modelo.setArquivo("");
                    }
                    else if(modelo.getIdVarreduraCroq() == 7){
                        modelo.setArquivo("OUTROS");
                    }
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

    public TbVarreduraCroq getModeloById(long id){

        Cursor cursor = null;
        TbVarreduraCroq modelo = new TbVarreduraCroq();
        try {
            String sql =
                    "SELECT * " +
                            "FROM     tbvarreduracroq WHERE idVarreduraCroq = "+id+" " +
                            "ORDER BY idVarreduraCroq " ;
            cursor = db.rawQuery(sql,null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    modelo.setIdVarreduraCroq(cursor.getLong(cursor.getColumnIndex("idVarreduraCroq")));
                    modelo.setArquivo(cursor.getString(cursor.getColumnIndex("arquivo")));
                    modelo.setQtde(cursor.getLong(cursor.getColumnIndex("qtde")));
                    modelo.setTp1(cursor.getString(cursor.getColumnIndex("tp1")));
                    modelo.setTp2(cursor.getString(cursor.getColumnIndex("tp2")));
                    modelo.setTp3(cursor.getString(cursor.getColumnIndex("tp3")));
                    modelo.setTp4(cursor.getString(cursor.getColumnIndex("tp4")));
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

            TbVarreduraCroq [] array = new Gson().fromJson(resp, TbVarreduraCroq[].class);

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

            String sql = "DELETE FROM tbvarreduracroq";
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
