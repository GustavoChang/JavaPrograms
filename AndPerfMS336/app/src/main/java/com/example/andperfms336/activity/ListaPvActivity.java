package com.example.andperfms336.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andperfms336.R;
import com.example.andperfms336.modelo.VwVarredura;


public class ListaPvActivity extends Activity {

    ImageView menupvInspecao;
    TextView menupvInspecaoText;
    VwVarredura vwVarredura;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menupv);

        Intent it = getIntent();
        if (it != null) {
            vwVarredura = (VwVarredura) it.getSerializableExtra("vwVarredura");
        }

        TextView tvnome;
        tvnome = findViewById(R.id.tvnome);
        tvnome.setText(vwVarredura.getNome());

        TextView tvbacia;
        tvbacia = findViewById(R.id.tvbacia);
        tvbacia.setText(vwVarredura.getBacia());

        TextView tvarea;
        tvarea = findViewById(R.id.tvarea);
        tvarea.setText(vwVarredura.getArea());

        TextView tvreferencia;
        tvreferencia = findViewById(R.id.tvreferencia);
        tvreferencia.setText(vwVarredura.getReferencia());

        try {


            //clicar na imagem
            menupvInspecao = findViewById(R.id.menupvInspecao);
            menupvInspecao.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    //System.out.println("jhxxhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhsdfljhdfkjhfkjhfkjdhfjkdhfjkdhkdhjhhhh me amo!");
                    Intent i = new Intent(ListaPvActivity.this, PvInspecaoForm.class);
                    i.putExtra("vwVarredura", vwVarredura);
                    startActivity(i);
                }
            });
            //clicar no texto
            menupvInspecaoText = findViewById(R.id.menupvInspecaoText);
            menupvInspecaoText.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    //System.out.println("jhxxhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhsdfljhdfkjhfkjhfkjdhfjkdhfjkdhkdhjhhhh me amo22222222222222!");
                    Intent i = new Intent(ListaPvActivity.this, PvInspecaoForm.class);
                    i.putExtra("vwVarredura", vwVarredura);
                    startActivity(i);
                }
            });
        }
        catch (Exception e) {

            exibirMensagem("Falha(2)", e.toString());
        }
    }

    public void exibirMensagem(String titulo, String texto) {

        AlertDialog.Builder dialogo = new AlertDialog.Builder(ListaPvActivity.this);
        dialogo.setTitle(titulo);
        dialogo.setMessage(texto);
        dialogo.setNeutralButton("OK", null);
        dialogo.show();
    }
}