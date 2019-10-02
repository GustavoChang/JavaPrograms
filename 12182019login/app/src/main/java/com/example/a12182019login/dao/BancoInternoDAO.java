package com.example.a12182019login.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.a12182019login.auxiliar.DadosBancoInterno;

public class BancoInternoDAO {

    private static SQLiteDatabase db;

    //Caso aconteça algum erro, o @SuppressLint vai ignorar e dar continuidade
    @SuppressLint("WrongConstant")

    public static void criarBanco(Context context){
        //CRIA BD NA MEMORIA DO APARELHO
        db = context.openOrCreateDatabase("12182019login.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        //db = context.openOrCreateDatabase(context.getExternalCacheDir() + "/obramo334.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);//CRIA BD NO CARTAO SD
        criarTabela();
    }

    private static void criarTabela() {
        SisUserDAO.criarTabela(db);

    }
}
