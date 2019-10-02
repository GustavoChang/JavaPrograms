package com.example.andperfms336.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.andperfms336.auxiliar.BaseDados;
import com.example.andperfms336.http.Http;
import com.example.andperfms336.modelo.TbEquipe;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


//SITUACAO: A - ATIVO ; I - INATIVO ; D - DESTRUIDO

public class TbEquipeDAO {

    private static SQLiteDatabase db;

    public TbEquipeDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public void criarTabela() {
        try {
            db.beginTransaction();

            String sql ="CREATE TABLE IF NOT EXISTS tbequipe (" +
                    "		id INTEGER PRIMARY KEY, " +
                    "		idEquipe INTEGER, " +
                    "		nome TEXT, " +
                    "		situacao TEXT, " +
                    "		login TEXT, " +
                    "		senha TEXT, " +
                    "		dtInsert DATE " +
                    ");";
            db.execSQL(sql);

            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    public void inserir(TbEquipe tbEquipe) {

        try {
            db.beginTransaction();

            String insert =
                    "INSERT INTO tbequipe ( " +
                    "       id, " +
                    "		idEquipe, " +
                    "		nome, " +
                    "		situacao, " +
                    "		login, " +
                    "		senha, " +
                    "		dtInsert " +
                    ") VALUES ( " +
                    "       ?,?,?,?,?,?,datetime('now', 'localtime') " +
                    ") ";
            db.execSQL(insert, new Object[] {
                    tbEquipe.getId(),
                    tbEquipe.getIdEquipe(),
                    tbEquipe.getNome(),
                    tbEquipe.getSituacao(),
                    tbEquipe.getLogin(),
                    tbEquipe.getSenha()
                    //cdtEquipe.getDtInsert()
            });

            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    public void inativaRegistroTabela(int dias){

        try {
            db.beginTransaction();
            String sql = "UPDATE tbequipe SET situacao = 'I' WHERE date(dtInsert) <= date('now', '" + dias + " day', 'localtime') AND situacao = 'A' ";
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

    public void destroyRegistroTabela(TbEquipe tbEquipe){

        try {
            db.beginTransaction();
            String sql = "UPDATE tbequipe SET situacao = 'D' WHERE idEquipe = "+tbEquipe.getIdEquipe()+" ";
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

    public boolean existeEquipeBySituacao(String situacao) {
        boolean resposta;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT count(*) AS qtde FROM tbequipe WHERE situacao = '"+situacao+"' ", null);
            int countIndex = cursor.getColumnIndex("qtde");
            cursor.moveToFirst();
            int rowCount = cursor.getInt(countIndex);

            if (rowCount > 0) {
                resposta = true;
            }
            else{
                resposta = false;
            }
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return resposta;
    }

    public int getId() {
        int resposta;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT MAX(id)+1 AS id FROM tbequipe ", null);
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

    public TbEquipe getModelo(String situacao) {
        Cursor cursor = null;
        TbEquipe tbEquipe = null;

        try {
            cursor = db.rawQuery(
                    "SELECT " +
                    "		idEquipe, " +
                    "		nome, " +
                    "		situacao, " +
                    "		login, " +
                    "		senha " +
                    "FROM   tbEquipe " +
                    "WHERE  situacao = '"+situacao+"' " +
                    "GROUP BY idEquipe,nome,situacao,login,senha ", null);
            if (cursor.getCount() > 0) {
                int idEquipeIndex = cursor.getColumnIndex("idEquipe");
                int nomeIndex = cursor.getColumnIndex("nome");
                int situacaoIndex = cursor.getColumnIndex("situacao");
                int loginIndex = cursor.getColumnIndex("login");
                int senhaIndex = cursor.getColumnIndex("senha");

                cursor.moveToFirst();
                do {
                    tbEquipe = new TbEquipe();
                    tbEquipe.setIdEquipe(cursor.getLong(idEquipeIndex));
                    tbEquipe.setNome(cursor.getString(nomeIndex));
                    tbEquipe.setSituacao(cursor.getString(situacaoIndex));
                    tbEquipe.setLogin(cursor.getString(loginIndex));
                    tbEquipe.setSenha(cursor.getString(senhaIndex));
                    cursor.moveToNext();
                }
                while (!cursor.isAfterLast());
            }
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return tbEquipe;
    }

    public String autenticar(String url,
                             String login,
                             String senha,
                             String imei,
                             String vCode,
                             String vName,
                             String cel,
                             String app,
                             String dataAparelho,
                             String bateria,
                             String gps) throws IOException {

        Map params = new HashMap();
        params.put("r", "login");
        params.put("login", login);
        params.put("senha", senha);
        params.put("imei", imei);
        params.put("vCode", vCode);
        params.put("vName", vName);
        params.put("dataAparelho", dataAparelho);
        params.put("cel", cel);
        params.put("app", app);
        params.put("bateria", bateria);
        params.put("gps", gps);

        String resposta = Http.getInstance(Http.NORMAL).doPost(url, params);

        return resposta;
    }
}
