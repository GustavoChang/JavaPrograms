package com.example.andperfms336.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.andperfms336.modelo.TbVarreduraClas;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class TbVarreduraClasDAO {

    private static SQLiteDatabase db;

    public TbVarreduraClasDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public void criarTabela() {
        try {
            db.beginTransaction();

            String sql ="CREATE TABLE IF NOT EXISTS tbvarreduraclas (" +
                    "		idVarreduraClas INTEGER PRIMARY KEY, " +
                    "		descricao TEXT " +
                    ");";
            db.execSQL(sql);

            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    public void inserir(TbVarreduraClas modelo) {
        try {

            db.beginTransaction();

            String sql =
            "INSERT INTO tbvarreduraclas ( " +
                    "       idVarreduraClas, " +
                    "		descricao " +
                    ") VALUES ( " +
                    "       ?,?" +
                    ") ";
            db.execSQL(sql, new Object[] {
                    modelo.getIdVarreduraClas(),
                    modelo.getDescricao()
            });
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    public List<TbVarreduraClas> listar() throws Exception {

        Cursor cursor = null;
        List<TbVarreduraClas> list = new ArrayList<>();
        try {
            String sql =
                    "SELECT * " +
                            "FROM     tbvarreduraclas " +
                            "ORDER BY idVarreduraClas " ;
            cursor = db.rawQuery(sql,null);
            int cont = 0;
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    cont++;
                    if(cont == 1){
                        TbVarreduraClas modelo = new TbVarreduraClas();
                        modelo.setIdVarreduraClas((long)0);
                        modelo.setDescricao("Selecione");
                        list.add(modelo);
                    }
                    TbVarreduraClas modelo = new TbVarreduraClas();
                    modelo.setIdVarreduraClas(cursor.getLong(cursor.getColumnIndex("idVarreduraClas")));
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

            TbVarreduraClas [] array = new Gson().fromJson(resp, TbVarreduraClas[].class);

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

            String sql = "DELETE FROM tbvarreduraclas";
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
