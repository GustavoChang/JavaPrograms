package com.example.andperfms336.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.andperfms336.modelo.VwVarredura;
import com.example.andperfms336.modelo.VwVarreduraCDP;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class VwVarreduraCDPDAO {

    private static SQLiteDatabase db;

    public VwVarreduraCDPDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public void criarTabela() {
        try {
            db.beginTransaction();

            String sql =
                    "CREATE TABLE IF NOT EXISTS vwVarreduraCDP (" +
                    "		idVarreduraCDP INTEGER PRIMARY KEY, " +
                    "		idVarreduraClas INTEGER, " +
                    "		varreduraClas TEXT, " +
                    "		idVarreduraDiag INTEGER, " +
                    "		varreduraDiag TEXT, " +
                    "		idVarreduraProg INTEGER, " +
                    "		varreduraProg TEXT " +
                    ");";
            db.execSQL(sql);

            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    public void inserir(VwVarreduraCDP vwVarreduraCDP) {

        try {
            db.beginTransaction();

            String insert =
                    "INSERT INTO vwVarreduraCDP ( " +
                            "		idVarreduraCDP, idVarreduraClas, varreduraClas, idVarreduraDiag, varreduraDiag, " +
                            "		idVarreduraProg, varreduraProg " +
                    ") VALUES ( " +
                            "       ?,?,?,?,?, " +
                            "       ?,? " +
                    ") ";
            db.execSQL(insert, new Object[] {
                    vwVarreduraCDP.getIdVarreduraCDP(),
                    vwVarreduraCDP.getIdVarreduraClas(),
                    vwVarreduraCDP.getVarreduraClas(),
                    vwVarreduraCDP.getIdVarreduraDiag(),
                    vwVarreduraCDP.getVarreduraDiag(),

                    vwVarreduraCDP.getIdVarreduraProg(),
                    vwVarreduraCDP.getVarreduraProg()
            });

            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    public VwVarreduraCDP getModeloByFK(VwVarreduraCDP vwVarreduraCDP) throws Exception {

        Cursor cursor = null;
        VwVarreduraCDP modelo = null;
        try {
            String sql =
                    "SELECT * " +
                            "FROM     vwVarreduraCDP " +
                            "WHERE  idVarreduraClas = ? AND idVarreduraDiag = ? AND idVarreduraProg = ? " ;
            cursor = db.rawQuery(sql,new String[] {
                    vwVarreduraCDP.getIdVarreduraClas().toString(),
                    vwVarreduraCDP.getIdVarreduraDiag().toString(),
                    vwVarreduraCDP.getIdVarreduraProg().toString()

            });
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    modelo = new VwVarreduraCDP();
                    modelo.setIdVarreduraCDP(cursor.getLong(cursor.getColumnIndex("idVarreduraCDP")));
                    modelo.setIdVarreduraClas(cursor.getLong(cursor.getColumnIndex("idVarreduraClas")));
                    modelo.setVarreduraClas(cursor.getString(cursor.getColumnIndex("varreduraClas")));
                    modelo.setIdVarreduraDiag(cursor.getLong(cursor.getColumnIndex("idVarreduraDiag")));
                    modelo.setVarreduraDiag(cursor.getString(cursor.getColumnIndex("varreduraDiag")));
                    modelo.setIdVarreduraProg(cursor.getLong(cursor.getColumnIndex("idVarreduraProg")));
                    modelo.setVarreduraProg(cursor.getString(cursor.getColumnIndex("varreduraProg")));
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

    public VwVarreduraCDP getModeloByPK(Long id) {

        Cursor cursor = null;
        VwVarreduraCDP modelo = null;
        try {
            String sql =
                    "SELECT * " +
                            "FROM     vwVarreduraCDP " +
                            "WHERE  idVarreduraCDP = ? " ;
            cursor = db.rawQuery(sql,new String[] {
                    Long.toString(id)

            });
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    modelo = new VwVarreduraCDP();
                    modelo.setIdVarreduraCDP(cursor.getLong(cursor.getColumnIndex("idVarreduraCDP")));
                    modelo.setIdVarreduraClas(cursor.getLong(cursor.getColumnIndex("idVarreduraClas")));
                    modelo.setVarreduraClas(cursor.getString(cursor.getColumnIndex("varreduraClas")));
                    modelo.setIdVarreduraDiag(cursor.getLong(cursor.getColumnIndex("idVarreduraDiag")));
                    modelo.setVarreduraDiag(cursor.getString(cursor.getColumnIndex("varreduraDiag")));
                    modelo.setIdVarreduraProg(cursor.getLong(cursor.getColumnIndex("idVarreduraProg")));
                    modelo.setVarreduraProg(cursor.getString(cursor.getColumnIndex("varreduraProg")));
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


        if(resp != null && !resp.trim().equals("") && resp.trim() != "[]"){

            VwVarreduraCDP[] array = new Gson().fromJson(resp, VwVarreduraCDP[].class);

            limparTabela();
            for(int i = 0; i < array.length; i++){
                inserir(array[i]);
            }

            processoOk = true;
        }
        else{
            processoOk = false;
        }
        return processoOk;
    }


    public void limparTabela(){

        try {

            db.beginTransaction();

            String sql = "DELETE FROM vwVarreduraCDP";
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
