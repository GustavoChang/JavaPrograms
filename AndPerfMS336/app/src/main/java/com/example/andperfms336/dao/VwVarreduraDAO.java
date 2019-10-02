package com.example.andperfms336.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.andperfms336.auxiliar.Auxiliar;
import com.example.andperfms336.modelo.VwVarredura;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.util.ArrayList;
import java.util.List;


public class VwVarreduraDAO {

    private static SQLiteDatabase db;

    public VwVarreduraDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public void criarTabela() {
        try {
            db.beginTransaction();

            String sql =
                    "CREATE TABLE IF NOT EXISTS vwVarredura (" +
                    "		idVarredura INTEGER PRIMARY KEY, " +
                    "		data TEXT, " +
                    "		idEquipe TEXT, " +
                    "		obs TEXT, " +
                    "		idVarreduraRet INTEGER, " +
                    "		idVarreduraPav INTEGER, " +
                    "		idVarreduraCroq INTEGER, " +
                    "		idVarreduraTubo1 INTEGER, " +
                    "		idVarreduraTubo2 INTEGER, " +
                    "		idVarreduraTubo3 INTEGER, " +
                    "		diam1 INTEGER, " +
                    "		diam2 INTEGER, " +
                    "		diam3 INTEGER, " +
                    "		prof1 INTEGER, " +
                    "		prof2 INTEGER, " +
                    "		prof3 INTEGER, " +
                    "		idPv INTEGER, " +
                    "		nome TEXT, " +
                    "		idBacia INTEGER, " +
                    "		bacia TEXT, " +
                    "		idArea INTEGER, " +
                    "		area TEXT, " +
                    "		coordX TEXT, " +
                    "		coordY TEXT, " +
                    "		idPvTp INTEGER, " +
                    "		pvTp TEXT, " +
                    "		idVarreduraCDP INTEGER, " +
                    "		recebidoServidor TEXT, " +
                    "		tp1 TEXT, " +
                    "		tp2 TEXT, " +
                    "		tp3 TEXT, " +
                    "		tp4 TEXT, " +
                    "		idVarreduraTubo4 INTEGER, " +
                    "		diam4 INTEGER, " +
                    "		prof4 INTEGER, " +
                    "		referencia TEXT, " +
                    "		varCoordX TEXT, " +
                    "		varCoordY TEXT " +
                    ");";
            db.execSQL(sql);

            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    public void inserir(VwVarredura vwVarredura) {

        try {
            db.beginTransaction();
            System.out.println("aki ta o erro");
            String insert =
                    "INSERT INTO vwVarredura ( " +
                            "		idVarredura, data, idEquipe, obs, idVarreduraRet, " +
                            "		idVarreduraPav, idVarreduraCroq, idVarreduraTubo1, idVarreduraTubo2, idVarreduraTubo3, " +
                            "		diam1, diam2, diam3, prof1, prof2, " +
                            "		prof3, idPv, nome, idBacia, bacia, " +
                            "		idArea, area, coordX, coordY, idPvTp, " +
                            "		pvTp, idVarreduraCDP, recebidoServidor, tp1, tp2, " +
                            "		tp3, tp4, idVarreduraTubo4, diam4, prof4, " +
                            "		referencia " +
                    ") VALUES ( " +
                            "       ?,?,?,?,?, " +
                            "       ?,?,?,?,?, " +
                            "       ?,?,?,?,?, " +
                            "       ?,?,?,?,?, " +
                            "       ?,?,?,?,?, " +
                            "       ?,?,?,?,?, " +
                            "       ?,?,?,?,?, " +
                            "       ? " +
                    ") ";
            db.execSQL(insert, new Object[] {
                    vwVarredura.getIdVarredura(),
                    vwVarredura.getData(),
                    vwVarredura.getIdEquipe(),
                    vwVarredura.getObs(),
                    vwVarredura.getIdVarreduraRet(),

                    vwVarredura.getIdVarreduraPav(),
                    vwVarredura.getIdVarreduraCroq(),
                    vwVarredura.getIdVarreduraTubo1(),
                    vwVarredura.getIdVarreduraTubo2(),
                    vwVarredura.getIdVarreduraTubo3(),

                    vwVarredura.getDiam1(),
                    vwVarredura.getDiam2(),
                    vwVarredura.getDiam3(),
                    vwVarredura.getProf1(),
                    vwVarredura.getProf2(),

                    vwVarredura.getProf3(),
                    vwVarredura.getIdPv(),
                    vwVarredura.getNome(),
                    vwVarredura.getIdBacia(),
                    vwVarredura.getBacia(),

                    vwVarredura.getIdArea(),
                    vwVarredura.getArea(),
                    vwVarredura.getCoordX(),
                    vwVarredura.getCoordY(),
                    vwVarredura.getIdPvTp(),

                    vwVarredura.getPvTp(),
                    vwVarredura.getIdVarreduraCDP(),
                    vwVarredura.getRecebidoServidor(),
                    vwVarredura.getTp1(),
                    vwVarredura.getTp2(),

                    vwVarredura.getTp3(),
                    vwVarredura.getTp4(),
                    vwVarredura.getIdVarreduraTubo4(),
                    vwVarredura.getDiam4(),
                    vwVarredura.getProf4(),

                    vwVarredura.getReferencia()
            });

            db.setTransactionSuccessful();
        }
        finally {
            System.out.println("aki ta o erro");
            db.endTransaction();
        }
    }

    public void alterar(VwVarredura vwVarredura) {

        try {
            db.beginTransaction();

            String insert =
                    "UPDATE vwVarredura SET " +
                            "		idVarredura=?, data=?, idEquipe=?, obs=?, idVarreduraRet=?, " +
                            "		idVarreduraPav=?, idVarreduraCroq=?, idVarreduraTubo1=?, idVarreduraTubo2=?, idVarreduraTubo3=?, " +
                            "		diam1=?, diam2=?, diam3=?, prof1=?, prof2=?, " +
                            "		prof3=?, idPv=?, nome=?, idBacia=?, bacia=?, " +
                            "		idArea=?, area=?, varCoordX=?, varCoordY=?, idPvTp=?, " +
                            "		pvTp=?, idVarreduraCDP=?, tp1=?, tp2=?, tp3=?, " +
                            "       tp4=?, idVarreduraTubo4=?, diam4=?, prof4=?, referencia=?, " +
                            "       recebidoServidor=? WHERE idVarredura=? ";
            db.execSQL(insert, new Object[] {
                    vwVarredura.getIdVarredura(),
                    vwVarredura.getData(),
                    vwVarredura.getIdEquipe(),
                    vwVarredura.getObs(),
                    vwVarredura.getIdVarreduraRet(),

                    vwVarredura.getIdVarreduraPav(),
                    vwVarredura.getIdVarreduraCroq(),
                    vwVarredura.getIdVarreduraTubo1(),
                    vwVarredura.getIdVarreduraTubo2(),
                    vwVarredura.getIdVarreduraTubo3(),

                    vwVarredura.getDiam1(),
                    vwVarredura.getDiam2(),
                    vwVarredura.getDiam3(),
                    vwVarredura.getProf1(),
                    vwVarredura.getProf2(),

                    vwVarredura.getProf3(),
                    vwVarredura.getIdPv(),
                    vwVarredura.getNome(),
                    vwVarredura.getIdBacia(),
                    vwVarredura.getBacia(),

                    vwVarredura.getIdArea(),
                    vwVarredura.getArea(),
                    vwVarredura.getVarCoordX(),
                    vwVarredura.getVarCoordY(),
                    vwVarredura.getIdPvTp(),

                    vwVarredura.getPvTp(),
                    vwVarredura.getIdVarreduraCDP(),
                    vwVarredura.getTp1(),
                    vwVarredura.getTp2(),
                    vwVarredura.getTp3(),

                    vwVarredura.getTp4(),
                    vwVarredura.getIdVarreduraTubo4(),
                    vwVarredura.getDiam4(),
                    vwVarredura.getProf4(),
                    vwVarredura.getReferencia(),

                    vwVarredura.getRecebidoServidor(),
                    vwVarredura.getIdVarredura()
            });

            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    public boolean processar(String resp) throws Exception{

        boolean processoOk = false;


        if(resp != null && !resp.trim().equals("") && resp.trim() != "[]"){

            VwVarredura[] array = new Gson().fromJson(resp, VwVarredura[].class);

            for(int i = 0; i < array.length; i++){
                if(existe(array[i])){
                    System.out.println("alterar");
                    alterar(array[i]);
                }
                else{
                    System.out.println("inserir");
                    inserir(array[i]);
                }
            }

            processoOk = true;
        }
        else{
            processoOk = false;
        }
        return processoOk;
    }

    public boolean existe(VwVarredura vwVarredura) throws Exception {

        Cursor cursor = null;
        try {

            String sql = "SELECT count(*) AS qtde FROM vwVarredura WHERE idVarredura = ? " ;

            cursor = db.rawQuery(sql, new String[] {vwVarredura.getIdVarredura().toString()});
            cursor.moveToFirst();
            int rowCount = cursor.getInt(cursor.getColumnIndex("qtde"));
            if (rowCount > 0) {
                return true;
            }
            return false;
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

    public boolean existeServicoByRecebido(String recebidoServidor) throws Exception {

        Cursor cursor = null;
        try {

            String sql = "SELECT count(*) AS qtde FROM vwVarredura WHERE recebidoServidor = ? " ;

            cursor = db.rawQuery(sql, new String[] {recebidoServidor});
            cursor.moveToFirst();
            int rowCount = cursor.getInt(cursor.getColumnIndex("qtde"));
            if (rowCount > 0) {
                return true;
            }
            return false;
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

    public List<VwVarredura> listar() throws Exception {

        Cursor cursor = null;

        try {

            List<VwVarredura> list = new ArrayList<>();

            cursor = db.rawQuery(
                    "SELECT * " +
                            "FROM     vwVarredura  WHERE idEquipe = "+ Auxiliar.getLogin().getIdEquipe()+" " +
                            "ORDER BY idVarredura "
                    , null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    VwVarredura modelo = new VwVarredura();

                    modelo.setIdVarredura(cursor.getLong(cursor.getColumnIndex("idVarredura")));
                    modelo.setData(cursor.getString(cursor.getColumnIndex("data")));
                    modelo.setIdEquipe(cursor.getLong(cursor.getColumnIndex("idEquipe")));
                    modelo.setObs(cursor.getString(cursor.getColumnIndex("obs")));
                    modelo.setIdVarreduraRet(cursor.getLong(cursor.getColumnIndex("idVarreduraRet")));

                    modelo.setIdVarreduraPav(cursor.getLong(cursor.getColumnIndex("idVarreduraPav")));
                    modelo.setIdVarreduraCroq(cursor.getLong(cursor.getColumnIndex("idVarreduraCroq")));
                    modelo.setIdVarreduraTubo1(cursor.getLong(cursor.getColumnIndex("idVarreduraTubo1")));
                    modelo.setIdVarreduraTubo2(cursor.getLong(cursor.getColumnIndex("idVarreduraTubo2")));
                    modelo.setIdVarreduraTubo3(cursor.getLong(cursor.getColumnIndex("idVarreduraTubo3")));

                    modelo.setDiam1(cursor.getLong(cursor.getColumnIndex("diam1")));
                    modelo.setDiam2(cursor.getLong(cursor.getColumnIndex("diam2")));
                    modelo.setDiam3(cursor.getLong(cursor.getColumnIndex("diam3")));
                    modelo.setProf1(cursor.getLong(cursor.getColumnIndex("prof1")));
                    modelo.setProf2(cursor.getLong(cursor.getColumnIndex("prof2")));

                    modelo.setProf3(cursor.getLong(cursor.getColumnIndex("prof3")));
                    modelo.setIdPv(cursor.getLong(cursor.getColumnIndex("idPv")));
                    modelo.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                    modelo.setIdBacia(cursor.getLong(cursor.getColumnIndex("idBacia")));
                    modelo.setBacia(cursor.getString(cursor.getColumnIndex("bacia")));

                    modelo.setIdArea(cursor.getLong(cursor.getColumnIndex("idArea")));
                    modelo.setArea(cursor.getString(cursor.getColumnIndex("area")));
                    modelo.setCoordX(cursor.getString(cursor.getColumnIndex("coordX")));
                    modelo.setCoordY(cursor.getString(cursor.getColumnIndex("coordY")));
                    modelo.setIdPvTp(cursor.getLong(cursor.getColumnIndex("idPvTp")));

                    modelo.setPvTp(cursor.getString(cursor.getColumnIndex("pvTp")));
                    modelo.setIdVarreduraCDP(cursor.getLong(cursor.getColumnIndex("idVarreduraCDP")));
                    modelo.setRecebidoServidor(cursor.getString(cursor.getColumnIndex("recebidoServidor")));
                    modelo.setTp1(cursor.getString(cursor.getColumnIndex("tp1")));
                    modelo.setTp2(cursor.getString(cursor.getColumnIndex("tp2")));

                    modelo.setTp3(cursor.getString(cursor.getColumnIndex("tp3")));
                    modelo.setTp4(cursor.getString(cursor.getColumnIndex("tp4")));
                    modelo.setIdVarreduraTubo4(cursor.getLong(cursor.getColumnIndex("idVarreduraTubo4")));
                    modelo.setDiam4(cursor.getLong(cursor.getColumnIndex("diam4")));
                    modelo.setProf4(cursor.getLong(cursor.getColumnIndex("prof4")));

                    modelo.setReferencia(cursor.getString(cursor.getColumnIndex("referencia")));

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

    public List<VwVarredura> getListByRecebido(String recebidoServidor) throws Exception {

        Cursor cursor = null;

        try {

            List<VwVarredura> list = new ArrayList<>();

            cursor = db.rawQuery(
                    "SELECT * " +
                            "FROM     vwVarredura  WHERE recebidoServidor = ? " +
                            "ORDER BY idVarredura "
                    ,  new String[] {recebidoServidor});

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    VwVarredura modelo = new VwVarredura();

                    modelo.setIdVarredura(cursor.getLong(cursor.getColumnIndex("idVarredura")));
                    modelo.setData(cursor.getString(cursor.getColumnIndex("data")));
                    modelo.setIdEquipe(cursor.getLong(cursor.getColumnIndex("idEquipe")));
                    modelo.setObs(cursor.getString(cursor.getColumnIndex("obs")));
                    modelo.setIdVarreduraRet(cursor.getLong(cursor.getColumnIndex("idVarreduraRet")));

                    modelo.setIdVarreduraPav(cursor.getLong(cursor.getColumnIndex("idVarreduraPav")));
                    modelo.setIdVarreduraCroq(cursor.getLong(cursor.getColumnIndex("idVarreduraCroq")));
                    modelo.setIdVarreduraTubo1(cursor.getLong(cursor.getColumnIndex("idVarreduraTubo1")));
                    modelo.setIdVarreduraTubo2(cursor.getLong(cursor.getColumnIndex("idVarreduraTubo2")));
                    modelo.setIdVarreduraTubo3(cursor.getLong(cursor.getColumnIndex("idVarreduraTubo3")));

                    modelo.setDiam1(cursor.getLong(cursor.getColumnIndex("diam1")));
                    modelo.setDiam2(cursor.getLong(cursor.getColumnIndex("diam2")));
                    modelo.setDiam3(cursor.getLong(cursor.getColumnIndex("diam3")));
                    modelo.setProf1(cursor.getLong(cursor.getColumnIndex("prof1")));
                    modelo.setProf2(cursor.getLong(cursor.getColumnIndex("prof2")));

                    modelo.setProf3(cursor.getLong(cursor.getColumnIndex("prof3")));
                    modelo.setIdPv(cursor.getLong(cursor.getColumnIndex("idPv")));
                    modelo.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                    modelo.setIdBacia(cursor.getLong(cursor.getColumnIndex("idBacia")));
                    modelo.setBacia(cursor.getString(cursor.getColumnIndex("bacia")));

                    modelo.setIdArea(cursor.getLong(cursor.getColumnIndex("idArea")));
                    modelo.setArea(cursor.getString(cursor.getColumnIndex("area")));
                    modelo.setCoordX(cursor.getString(cursor.getColumnIndex("coordX")));
                    modelo.setCoordY(cursor.getString(cursor.getColumnIndex("coordY")));
                    modelo.setIdPvTp(cursor.getLong(cursor.getColumnIndex("idPvTp")));

                    modelo.setPvTp(cursor.getString(cursor.getColumnIndex("pvTp")));
                    modelo.setIdVarreduraCDP(cursor.getLong(cursor.getColumnIndex("idVarreduraCDP")));
                    modelo.setRecebidoServidor(cursor.getString(cursor.getColumnIndex("recebidoServidor")));
                    modelo.setTp1(cursor.getString(cursor.getColumnIndex("tp1")));
                    modelo.setTp2(cursor.getString(cursor.getColumnIndex("tp2")));

                    modelo.setTp3(cursor.getString(cursor.getColumnIndex("tp3")));
                    modelo.setTp4(cursor.getString(cursor.getColumnIndex("tp4")));
                    modelo.setIdVarreduraTubo4(cursor.getLong(cursor.getColumnIndex("idVarreduraTubo4")));
                    modelo.setDiam4(cursor.getLong(cursor.getColumnIndex("diam4")));
                    modelo.setProf4(cursor.getLong(cursor.getColumnIndex("prof4")));

                    modelo.setReferencia(cursor.getString(cursor.getColumnIndex("referencia")));
                    modelo.setVarCoordX(cursor.getString(cursor.getColumnIndex("varCoordX")));
                    modelo.setVarCoordY(cursor.getString(cursor.getColumnIndex("varCoordY")));

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

    public void limparTabela(){

        try {

            db.beginTransaction();

            String sql = "DELETE FROM vwVarredura";
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


    public void marcaEnviado(List<VwVarredura> list){

        try {
            for(VwVarredura lista: list){
                db.beginTransaction();

                String sql = "UPDATE vwVarredura SET recebidoServidor = 'S' WHERE idVarredura = "+lista.getIdVarredura();
                db.execSQL(sql);

                db.setTransactionSuccessful();
            }
        }
        catch (Exception e) {

            throw e;
        }
        finally {

            db.endTransaction();
        }
    }
}
