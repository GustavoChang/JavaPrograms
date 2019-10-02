package com.example.andperfms336.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.andperfms336.modelo.FtVarredura;
import com.example.andperfms336.modelo.VwVarredura;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class FtVarreduraDAO {

    private static SQLiteDatabase db;

    public FtVarreduraDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public void criarTabela() {
        try {
            db.beginTransaction();

            String sql =
                    "CREATE TABLE IF NOT EXISTS ftVarredura (" +
                    "		id INTEGER PRIMARY KEY, " +
                    "		idVarredura INTEGER, " +
                    "		arquivo TEXT, " +
                    "		coordX TEXT, " +
                    "		coordY TEXT, " +
                    "		recebidoServidor TEXT " +
                    ");";
            db.execSQL(sql);

            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    public void inserir(FtVarredura ftVarredura) {

        try {
            db.beginTransaction();

            String insert =
                    "INSERT INTO ftVarredura ( " +
                            "		id, idVarredura, arquivo, coordX, coordY, " +
                            "		recebidoServidor " +
                    ") VALUES ( " +
                            "       ?,?,?,?,?, " +
                            "       ? " +
                    ") ";
            db.execSQL(insert, new Object[] {
                    ftVarredura.getId(),
                    ftVarredura.getIdVarredura(),
                    ftVarredura.getArquivo(),
                    ftVarredura.getCoordX(),
                    ftVarredura.getCoordY(),
                    ftVarredura.getRecebidoServidor()
            });

            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    public void enviado(FtVarredura ftVarredura) {

        try {
            db.beginTransaction();

            String insert =
                    "UPDATE ftVarredura SET recebidoServidor = 'S' WHERE id = ? ";
            db.execSQL(insert, new Object[] {
                    ftVarredura.getId()
            });

            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    public List<FtVarredura> listarByIdVarredura(long idVarredura) throws Exception {

        Cursor cursor = null;

        try {

            List<FtVarredura> list = new ArrayList<>();

            cursor = db.rawQuery(
                    "SELECT * " +
                            "FROM     ftVarredura WHERE idVarredura = "+idVarredura+" " +
                            "ORDER BY arquivo "
                    , null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    FtVarredura modelo = new FtVarredura();

                    modelo.setIdVarredura(cursor.getLong(cursor.getColumnIndex("idVarredura")));
                    modelo.setArquivo(cursor.getString(cursor.getColumnIndex("arquivo")));
                    modelo.setCoordX(cursor.getString(cursor.getColumnIndex("coordX")));
                    modelo.setCoordY(cursor.getString(cursor.getColumnIndex("coordY")));
                    modelo.setRecebidoServidor(cursor.getString(cursor.getColumnIndex("recebidoServidor")));

                    list.add(modelo);
                    cursor.moveToNext();
                }
                while (!cursor.isAfterLast());
            }

            return list;
        }
        catch (Exception e) {

            throw e;
        }
        finally {

            if (cursor != null) {

                cursor.close();
            }
        }
    }

    public List<FtVarredura> listarBySituacao(String situacao) throws Exception {

        Cursor cursor = null;

        try {

            List<FtVarredura> list = new ArrayList<>();

            cursor = db.rawQuery(
                    "SELECT * " +
                            "FROM     ftVarredura WHERE recebidoServidor = '"+situacao+"' " +
                            "ORDER BY arquivo "
                    , null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    FtVarredura modelo = new FtVarredura();
                    modelo.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    modelo.setIdVarredura(cursor.getLong(cursor.getColumnIndex("idVarredura")));
                    modelo.setArquivo(cursor.getString(cursor.getColumnIndex("arquivo")));
                    modelo.setCoordX(cursor.getString(cursor.getColumnIndex("coordX")));
                    modelo.setCoordY(cursor.getString(cursor.getColumnIndex("coordY")));
                    modelo.setRecebidoServidor(cursor.getString(cursor.getColumnIndex("recebidoServidor")));

                    list.add(modelo);
                    cursor.moveToNext();
                }
                while (!cursor.isAfterLast());
            }

            return list;
        }
        catch (Exception e) {

            throw e;
        }
        finally {

            if (cursor != null) {

                cursor.close();
            }
        }
    }

    public long getId() {
        int resposta;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT MAX(id)+1 AS id FROM ftVarredura ", null);
            int id = cursor.getColumnIndex("id");
            cursor.moveToFirst();
            resposta = cursor.getInt(id);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return resposta;
    }

    public long getQtdeTotal() {
        int resposta;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT COUNT(id) AS qtde FROM ftVarredura ", null);
            int id = cursor.getColumnIndex("qtde");
            cursor.moveToFirst();
            resposta = cursor.getInt(id);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return resposta;
    }

    public long getQtdePendente() {
        int resposta;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT COUNT(id) AS qtde FROM ftVarredura WHERE recebidoServidor = 'N' ", null);
            int id = cursor.getColumnIndex("qtde");
            cursor.moveToFirst();
            resposta = cursor.getInt(id);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return resposta;
    }

    public long getQtdeEnviada() {
        int resposta;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT COUNT(id) AS qtde FROM ftVarredura WHERE recebidoServidor = 'S' ", null);
            int id = cursor.getColumnIndex("qtde");
            cursor.moveToFirst();
            resposta = cursor.getInt(id);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return resposta;
    }

    public void limparTabela(){

        try {

            db.beginTransaction();

            String sql = "DELETE FROM fwVarredura";
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
