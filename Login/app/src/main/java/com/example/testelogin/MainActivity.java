package com.example.testelogin;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {

    EditText edlogin;
    EditText edsenha;
    String login;
    String senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button b = (Button) findViewById(R.id.btentrar);
        b.setOnClickListener(this);

        public void onClick(View view) {

            new Thread(this).start();
        }
        new Thread() {
            @Override
            public  void run(){
                edlogin = (EditText) findViewById(R.id.edlogin);
                edsenha = (EditText) findViewById(R.id.edsenha);
                login = edlogin.getText().toString().trim();
                senha = edsenha.getText().toString().trim();
                try {
                    URL url = new URL("http://200.155.2.66:8080/AndTesteChang/Login")
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
                    http.setRequestMethod("POST");
                    http.addRequestProperty("content-type", "application/binary");
                    http.setDoInput(true);
                    http.setDoOutput(true);
                    http.setConnectTimeout(20000);

                    http.connect();

                    Log.i(TAG, "RETORNO:> " + obTeste.toString());

                   } catch (Exception e) {
                    Log.e(TAG, "MainActivity.oncreate.servet", e)
                   }

                }
              start();

            }
        }
    }
}
