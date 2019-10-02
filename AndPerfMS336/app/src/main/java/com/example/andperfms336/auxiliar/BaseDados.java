package com.example.andperfms336.auxiliar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.example.andperfms336.dao.BaseDadosDAO;

public class BaseDados {
    public static String nomeBancoPadrao = "varredura336.db";
    //"/data/data/!!!!!package do manifest!!!!/databases"
    public static String pathDoBanco = "/data/data/com.example.andperfms336/databases";
    public static SQLiteDatabase bd;
    public static String pathFotos = "/varredura336_fotos/";

    @SuppressLint("WrongConstant")
    public static SQLiteDatabase getBD(Context context) {
        return isOpen(context);
    }

    @SuppressLint("WrongConstant")
    public static SQLiteDatabase isOpen(Context context) {
        if(bd == null){
            return bd = context.openOrCreateDatabase(nomeBancoPadrao, SQLiteDatabase.CREATE_IF_NECESSARY, null);
        }
        else if(!bd.isOpen()){
            return bd = context.openOrCreateDatabase(nomeBancoPadrao, SQLiteDatabase.CREATE_IF_NECESSARY, null);
        }
        else{
            return bd;
        }
    }

    public static void closeBD(SQLiteDatabase bd) {
        if(bd != null){
            bd.close();
        }
    }

    public static void fazerBackUp(Context context){
        BaseDadosDAO baseDadosDAO = new BaseDadosDAO(getBD(context));
        try{
            baseDadosDAO.backUpDB();
        }
        catch (Exception e){
            Log.d("backUp", "erro ao realizar backup: " + e.toString());
        }
    }
    public static String getPathFotos() {
        return Environment.getExternalStorageDirectory()+pathFotos;
    }

    public static String getPathDb() {
        return BaseDados.pathDoBanco+"/"+BaseDados.nomeBancoPadrao;
    }
}
