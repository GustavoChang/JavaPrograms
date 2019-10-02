package com.example.andperfms336.activity;

import com.example.andperfms336.dao.FtVarreduraDAO;
import com.example.andperfms336.dao.TbVarreduraClasDAO;
import com.example.andperfms336.dao.TbVarreduraCroqDAO;
import com.example.andperfms336.dao.TbVarreduraDiagDAO;
import com.example.andperfms336.dao.TbVarreduraPavDAO;
import com.example.andperfms336.dao.TbVarreduraProgDAO;
import com.example.andperfms336.dao.TbVarreduraRetDAO;
import com.example.andperfms336.dao.TbVarreduraTuboDAO;
import com.example.andperfms336.dao.VwVarreduraCDPDAO;
import com.example.andperfms336.dao.VwVarreduraDAO;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.andperfms336.R;
import com.example.andperfms336.auxiliar.Auxiliar;
import com.example.andperfms336.auxiliar.BaseDados;
import com.example.andperfms336.dao.TbEquipeDAO;
import com.example.andperfms336.http.Http;
import com.example.andperfms336.modelo.TbEquipe;



public class LoginActivity extends Activity implements OnClickListener, Runnable {

    private Handler handler = new Handler();
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    TbEquipeDAO tbEquipeDAO;
    TbEquipe tbEquipe;
    boolean direto = false;
    boolean offLine = false;
    String resposta = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tbEquipeDAO =  new TbEquipeDAO(BaseDados.getBD(this));

        setContentView(R.layout.activity_loading);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                criaAlteraBancoPadrao();
                iniciarProcedimento();
                //finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
    public void iniciarProcedimento(){

        //inativando registros com 1 dia de idade
        tbEquipeDAO.inativaRegistroTabela(-1);

        //verificando se existe equipe armazenada o bd local
        if(tbEquipeDAO.existeEquipeBySituacao("A")){
            //EXISTE EQUIPE ATIVA, LOGIN AUTOMATICO
            System.out.println("EXISTEEEEEEEEEEEEEEEEEEE A");
            tbEquipe = tbEquipeDAO.getModelo("A");
            offLine = true;
            direto = true;
            new Thread(this).start();
        }
        else if(tbEquipeDAO.existeEquipeBySituacao("I")){
            tbEquipe = tbEquipeDAO.getModelo("I");
            direto = true;
            new Thread(this).start();

        }
        else{
            //IR PARA TELA DE LOGIN
            setContentView(R.layout.activity_login);
            Button b = (Button) findViewById(R.id.btentrar);
            b.setOnClickListener(this);
        }
    }

    public void criaAlteraBancoPadrao(){
        TbVarreduraPavDAO tbVarreduraPavDAO =  new TbVarreduraPavDAO(BaseDados.getBD(this));
        VwVarreduraDAO vwVarreduraDAO =  new VwVarreduraDAO(BaseDados.getBD(this));
        TbVarreduraClasDAO tbVarreduraClasDAO =  new TbVarreduraClasDAO(BaseDados.getBD(this));
        TbVarreduraCroqDAO tbVarreduraCroqDAO =  new TbVarreduraCroqDAO(BaseDados.getBD(this));
        TbVarreduraDiagDAO tbVarreduraDiagDAO =  new TbVarreduraDiagDAO(BaseDados.getBD(this));
        TbVarreduraProgDAO tbVarreduraProgDAO =  new TbVarreduraProgDAO(BaseDados.getBD(this));
        TbVarreduraRetDAO tbVarreduraRetDAO =  new TbVarreduraRetDAO(BaseDados.getBD(this));
        TbVarreduraTuboDAO tbVarreduraTuboDAO =  new TbVarreduraTuboDAO(BaseDados.getBD(this));
        FtVarreduraDAO ftVarreduraDAO =  new FtVarreduraDAO(BaseDados.getBD(this));
        VwVarreduraCDPDAO vwVarreduraCDPDAO =  new VwVarreduraCDPDAO(BaseDados.getBD(this));

        tbEquipeDAO.criarTabela();
        tbVarreduraPavDAO.criarTabela();
        vwVarreduraDAO.criarTabela();
        tbVarreduraClasDAO.criarTabela();
        tbVarreduraCroqDAO.criarTabela();
        tbVarreduraDiagDAO.criarTabela();
        tbVarreduraProgDAO.criarTabela();
        tbVarreduraRetDAO.criarTabela();
        tbVarreduraTuboDAO.criarTabela();
        ftVarreduraDAO.criarTabela();
        vwVarreduraCDPDAO.criarTabela();
        //vwVarreduraDAO.limparTabela();
    }

    public void onClick(View view) {
        new Thread(this).start();
    }
    public void run() {

        EditText edlogin;
        EditText edsenha;
        String login = "";
        String senha = "";

        if(direto){
            login = tbEquipe.getLogin();
            senha = tbEquipe.getSenha();
            direto = false;
        }
        else{
            edlogin = (EditText) findViewById(R.id.edlogin);
            edsenha = (EditText) findViewById(R.id.edsenha);
            login = edlogin.getText().toString().trim();
            senha = edsenha.getText().toString().trim();
        }


        try {
            String vName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            int vCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;

            //CAPTURA NUMERO
            String cel;
            try {
                TelephonyManager tMgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                if(tMgr.getLine1Number() == null){
                    cel = "0";
                }
                else {
                    cel = tMgr.getLine1Number();
                }
            }
            catch (Exception e) {
                cel = "-1";
            }

            //DESIGNACAO APP C = COBRANCA ; P = PERFORM
            String app = "P";

            //NIVEL DA BATERIA
            String bateria;
            try {
                bateria = String.valueOf(Auxiliar.nivelBateria(this));
            }
            catch (Exception e) {
                bateria = "-1";
            }

            //VERIFICACAO GPS
            String gps;
            try {
                if(Auxiliar.gpsHabilitado(this)){
                    gps = "S";
                }
                else{
                    gps = "N";
                }
            }
            catch (Exception e) {
                gps = "E";
            }
            resposta = tbEquipeDAO.autenticar(Http.getUrlServlet() + "/"+Http.getServletRequest()+"",
                    login,
                    senha,
                    Auxiliar.capturarImei(this),
                    String.valueOf(vCode),
                    vName,
                    cel,
                    app,
                    Auxiliar.dataAtual(),
                    bateria,
                    gps);
            try {
                tbEquipe = new Gson().fromJson(resposta, TbEquipe.class);
            }
            catch (JsonParseException e) {
                System.out.println(e);
                tbEquipe = null;
                //Log.d("login", "erro gsson " + e.toString());
            }

            handler.post(new Runnable() {

                public void run() {
                    try {
                        if(tbEquipe != null){
                            if(tbEquipe.getSituacao().equals("I")){
                                System.out.println("LOGIN INATIVOOOOOOOOO!!!!!!!!!!!!!!!!!");
                                //USUARIO INATIVADO - DESTROY ALL SITUACAO = I ou A para D
                                tbEquipeDAO.destroyRegistroTabela(tbEquipe);
                                Intent i = new Intent(LoginActivity.this, LoginActivity.class);
                                startActivity(i);
                            }
                            else{
                                if(!tbEquipeDAO.existeEquipeBySituacao("A")){
                                    tbEquipeDAO.inserir(tbEquipe);
                                }
                                Intent i = new Intent(LoginActivity.this, MenuActivity.class);
                                i.putExtra("tbEquipe", tbEquipe);
                                startActivity(i);
                                LoginActivity.this.finish();
                            }
                        }
                        else{
                            TextView txtErro = findViewById(R.id.erroText1);


                            if(resposta.equals("1")){
                                AlertDialog.Builder dialogo = new AlertDialog.Builder(LoginActivity.this);
                                dialogo.setTitle("Falha no login (9)");
                                dialogo.setMessage("USUARIO/SENHA INCORRETOS")
                                        .setCancelable(false)
                                        .setPositiveButton("Encerrar", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                LoginActivity.this.finish();
                                            }
                                        });
//                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int id) {
//                                                dialog.cancel();
//                                            }
//                                        });
                                AlertDialog alert = dialogo.create();
                                alert.show();
                            }
                            else if(resposta.equals("2")){
                                AlertDialog.Builder dialogo = new AlertDialog.Builder(LoginActivity.this);
                                dialogo.setTitle("Falha no login (9)");
                                dialogo.setMessage("AJUSTE A DATA DO CELULAR")
                                        .setCancelable(false)
                                        .setPositiveButton("Encerrar", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                LoginActivity.this.finish();
                                            }
                                        });
//                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int id) {
//                                                dialog.cancel();
//                                            }
//                                        });
                                AlertDialog alert = dialogo.create();
                                alert.show();
                            }
                            else if(offLine){
                                System.out.println("LOGINNNNNNNNNNN OFFLINE");
                                tbEquipe = tbEquipeDAO.getModelo("A");
                                Intent i = new Intent(LoginActivity.this, MenuActivity.class);
                                i.putExtra("tbEquipe", tbEquipe);
                                startActivity(i);
                                LoginActivity.this.finish();
                            }
                            else{
                                AlertDialog.Builder dialogo = new AlertDialog.Builder(LoginActivity.this);
                                dialogo.setTitle("Falha no login (9)");
                                dialogo.setMessage("VERIFIQUE A CONEXAO COM A INTERNET")
                                        .setCancelable(false)
                                        .setPositiveButton("Encerrar", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                LoginActivity.this.finish();
                                            }
                                        });
//                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int id) {
//                                                dialog.cancel();
//                                            }
//                                        });
                                AlertDialog alert = dialogo.create();
                                alert.show();
                                System.out.println("NAO LOGOU NENHUMA VEZ");

                            }
                        }

                        System.out.println("GOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOD "+tbEquipe);
                    }
                    catch (Exception e) {


                    }
                }
            });
        }
        catch (Exception e) {
            AlertDialog.Builder dialogo = new AlertDialog.Builder(LoginActivity.this);
            dialogo.setTitle("Falha no login (9)");
            dialogo.setMessage(e.getMessage() + " " + e);
            dialogo.setNeutralButton("OK", null);
            dialogo.show();
        }
        finally {
            //dialog.dismiss();
        }


    }
}
