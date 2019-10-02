package com.example.a12182019login.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.a12182019login.auxiliar.DadosBancoInterno;
import com.example.a12182019login.modelo.SisUser;

public class SisUserDAO {
    private static SQLiteDatabase db;

    @SuppressLint("WrongConstant")
    //obramo334.db é um banco de dados do celular!
    public SisUserDAO(Context context) {

        db = context.openOrCreateDatabase("12182019login.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
    }
    //db roxo é local; db branco é global
    public SisUserDAO(SQLiteDatabase db) {

        this.db = db;
    }


    public static void criarTabela(SQLiteDatabase database) {

        try {

            database.beginTransaction();

            String sql ="CREATE TABLE IF NOT EXISTS sisUser (" +
                    "     sisUser INTEGER PRIMARY KEY, " +
                    "     nome TEXT, " +
                    "     login TEXT, " +
                    "     senha TEXT, " +
                    "     email TEXT, " +
                    "     tel INTEGER, " +
                    "     cel INTEGER, " +
                    "     sisPerfil INTEGER, " +
                    "       situacao TEXT" +
                    ");";
            database.execSQL(sql);

            database.setTransactionSuccessful();
        }
        finally {

            database.endTransaction();
        }
    }

    public void inserir(SisUser sisUser) {

        try {

            db.beginTransaction();

            String insert = "INSERT INTO sisUser ( " +
                    "       sisUser, " +
                    "     nome, " +
                    "     login, " +
                    "     senha, " +
                    "     email, " +
                    "     tel, " +
                    "     cel, " +
                    "     sisPerfil, " +
                    "     situacao " +
                    ") VALUES ( " +
                    "       ?,?,?,?,?,?,?,?,? " +
                    ") ";
            db.execSQL(insert, new Object[] {
                    sisUser.getSisUser(),
                    sisUser.getNome(),
                    sisUser.getLogin(),
                    sisUser.getSenha(),
                    sisUser.getEmail(),
                    sisUser.getTel(),
                    sisUser.getCel(),
                    sisUser.getSisPerfil(),
                    sisUser.getSituacao()
                    //cdtEquipe.getDtInsert()
            });

            db.setTransactionSuccessful();
            System.out.println("ok");
        }
        catch (Exception e) {

            System.out.println(e.toString());
        }
        finally {
            System.out.println("erro");
            db.endTransaction();
        }
    }
    public void lista() {

        Cursor cursor = null;

        try {

            cursor = db.rawQuery(
                    "SELECT * " +
                            "FROM   sisUser " +
                            "WHERE  situacao = ? ",
                    new String[] {
                            "A",
                    });

            cursor.moveToFirst();
            do {

                cdtEquipe = new CdtEquipe();
                cdtEquipe.setCdtEquipe(cursor.getLong(cdtEquipeIndex));
                cdtEquipe.setEquipe(cursor.getString(equipeIndex));
                cdtEquipe.setSituacao(cursor.getString(situacaoIndex));
                cdtEquipe.setLogin(cursor.getString(loginIndex));
                cdtEquipe.setSenha(cursor.getString(senhaIndex));
                cdtEquipe.setCsiEquipe(cursor.getLong(csiEquipeIndex));
                cdtEquipe.setCdtEmpresa(cursor.getLong(cdtEmpresaIndex));
                cdtEquipe.setDtInsert(cursor.getString(dtInsertIndex));

                cursor.moveToNext();
            }

            while (!cursor.isAfterLast());

            return false;
        }
        finally {

            if (cursor != null) {

                cursor.close();
            }
        }
    }

}