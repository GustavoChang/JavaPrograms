package com.example.a12182019login.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.a12182019login.R;
import com.example.a12182019login.dao.BancoInternoDAO;
import com.example.a12182019login.dao.SisUserDAO;
import com.example.a12182019login.modelo.SisUser;

public class MainActivity2 extends Activity implements View.OnClickListener, Runnable{

    String resposta;
    //ELEMENTO
    EditText edlogin;
    EditText edsenha;
    EditText ednome;
    EditText edemail;
    EditText edtel;
    EditText edcel;
    EditText edsisuser;
    EditText edsisperfil;
    EditText edsituacao;
    //VARIÁVEL
    String login;
    String senha;
    String nome;
    String email;
    String tel;
    String cel;
    Long sisuser;
    Long sisperfil;
    String situacao;
    String vName;
    int vCode;
    String app;
    String bateria;
    String gps;

    SisUser sisUser;
    SisUserDAO sisUserDAO;


    private Handler handler = new Handler();
    private ProgressDialog dialog;

    @Override
    //Setando a tela
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Seto layout
        setContentView(R.layout.login2);
        //Seto o ativador (trigger)
        Button b = (Button) findViewById(R.id.btentrar);
        b.setOnClickListener(this);
    }

    @Override
    //Caso ative o trigger
    public void onClick(View view) {
        //Toast
        dialog = ProgressDialog.show(this, "Login", "Autenticando usuário, por favor aguarde...", false, true);
        //Inicializo a Thread (olhar o public void run())
        new Thread(this).start();
    }

    @Override
    public void run() {
        //Pego elemento
        edlogin = (EditText) findViewById(R.id.edlogin);
        edsenha = (EditText) findViewById(R.id.edsenha);
        ednome = (EditText) findViewById(R.id.ednome);
        edemail = (EditText) findViewById(R.id.edemail);
        edtel = (EditText) findViewById(R.id.edtel);
        edcel = (EditText) findViewById(R.id.edcel);
        edsisuser = (EditText) findViewById(R.id.edsisuser);
        edsisperfil = (EditText) findViewById(R.id.edsisperfil);
        edsituacao = (EditText) findViewById(R.id.edsituacao);

        //Pego um dos atributos deste elemento e formato antes de salvar numa variável
        login = edlogin.getText().toString().trim();
        senha = edsenha.getText().toString().trim();
        nome = ednome.getText().toString().trim();
        email = edemail.getText().toString().trim();
        tel = edtel.getText().toString().trim();
        cel = edcel.getText().toString().trim();
        sisuser = Long.parseLong(edsisuser.getText().toString().trim());
        sisperfil = Long.parseLong(edsisperfil.getText().toString().trim());
        situacao = edsituacao.getText().toString().trim();
        System.out.println("DADOS CAPTURADOS");

        try {
            //No contexto da MainActivity, ou seja, no contexto da tela de login, por meio da classe BancoInternoDAO, uso o método .criarBanco(context)
            BancoInternoDAO.criarBanco(MainActivity2.this);

            //Crio um novo objeto DAO (banco interno do celular)
            sisUserDAO = new SisUserDAO(this);
            //Crio um novo objeto modelo (forma)
            sisUser = new SisUser();


            sisUser.setLogin(login);
            sisUser.setSenha(senha);
            sisUser.setNome(nome);
            sisUser.setEmail(email);
            sisUser.setTel(tel);
            sisUser.setCel(cel);
            sisUser.setSisUser(sisuser);
            sisUser.setSisPerfil(sisperfil);
            sisUser.setSituacao(situacao);

            sisUserDAO.inserir(sisUser);
            System.out.println("registro inserido");
            handler.post(new Runnable() {

                public void run() {
                    try {
                        Intent i = new Intent(MainActivity2.this, MainActivity.class);
                        startActivity(i);
                    }
                    catch (Exception e) {

                        AlertDialog.Builder dialogo = new AlertDialog.Builder(MainActivity2.this);
                        dialogo.setTitle("Falha no login (8)!");
                        dialogo.setMessage(e.getMessage() + " " + e);
                        dialogo.setNeutralButton("OK", null);
                        dialogo.show();
                    }
                }
            });

        }
        catch (Exception e) {

            AlertDialog.Builder dialogo = new AlertDialog.Builder(MainActivity2.this);
            dialogo.setTitle("Falha no login (9)");
            dialogo.setMessage(e.getMessage() + " " + e);
            dialogo.setNeutralButton("OK", null);
            dialogo.show();
        }
        finally {

            dialog.dismiss();
        }

    }
}