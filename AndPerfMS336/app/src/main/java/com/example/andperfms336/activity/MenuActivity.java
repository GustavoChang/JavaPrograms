package com.example.andperfms336.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andperfms336.R;
import com.example.andperfms336.auxiliar.Auxiliar;
import com.example.andperfms336.auxiliar.GPS;
import com.example.andperfms336.auxiliar.GPSNew;
import com.example.andperfms336.bo.BaseDadosBO;
import com.example.andperfms336.modelo.TbEquipe;


public class MenuActivity extends Activity{
    TbEquipe tbEquipe;

    GridView grid_main;
    TextView txtusuario;

    GPS gps;

    private String [] labelicone = {
//            "Trechos",
//            "Ligações",
            "Mapa",
            "Fotos",
            "Conexão"
    };
    private int [] imagemicone = {
//            R.drawable.ic_servico,
//            R.drawable.ic_servico,
            R.drawable.ic_mapa,
            R.drawable.ic_foto,
            R.drawable.ic_foto
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        BaseDadosBO.context = this;
        BaseDadosBO.atualizaTable = true;
        BaseDadosBO.backUp = true;
        BaseDadosBO.envioDados = true;
        BaseDadosBO.startProcedimento();
        iniciarGps();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent it = getIntent();
        if (it != null) {

            tbEquipe = (TbEquipe) it.getSerializableExtra("tbEquipe");
        }
        Auxiliar.setLogin(tbEquipe);
        grid_main = findViewById(R.id.GridView01);
        grid_main.setAdapter(new ImageAdapter(this));
        txtusuario = findViewById(R.id.txtusuario);

        txtusuario.setText(tbEquipe.getNome());


        grid_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if(position == 0) {

                    try {
                        Intent i = new Intent(MenuActivity.this, MapaActivity.class);
                        i.putExtra("tbEquipe", tbEquipe);
                        startActivity(i);
                    }
                    catch (Exception e) {

                        exibirMensagem("Falha(3)", e.toString());
                    }
                }
                else if(position == 1) {

                    try {
                        Intent i = new Intent(MenuActivity.this, FotoEnvioActivity.class);
                        startActivity(i);
                    }
                    catch (Exception e) {

                        exibirMensagem("Falha(3)", e.toString());
                    }
                }
                else if(position == 2) {

                    try {
                        Intent i = new Intent(MenuActivity.this, ConexaoActivity.class);
                        startActivity(i);
                    }
                    catch (Exception e) {

                        exibirMensagem("Falha(3)", e.toString());
                    }
                }

            }
        });
    }

    public class ImageAdapter extends BaseAdapter {

        Context mContext;

        public ImageAdapter(Context c) {

            mContext = c;
        }

        public int getCount() {

            return labelicone.length;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            View v;

            LayoutInflater li = getLayoutInflater();
            v = li.inflate(R.layout.formmenu_icone, null);
            TextView tv = v.findViewById(R.id.icon_text);
            tv.setText(labelicone[position]);
            ImageView iv = v.findViewById(R.id.icon_image);
            iv.setImageResource(imagemicone[position]);

            return v;
        }

        public Object getItem(int arg0) {

            return null;
        }

        public long getItemId(int arg0) {

            return 0;
        }
    }

    public void exibirMensagem(String titulo, String texto) {

        AlertDialog.Builder dialogo = new AlertDialog.Builder(MenuActivity.this);
        dialogo.setTitle(titulo);
        dialogo.setMessage(texto);
        dialogo.setNeutralButton("OK", null);
        dialogo.show();
    }

    public void iniciarGps() {
        startService(new Intent(this, GPSNew.class));
//        if(GPS.isExecutando() == false) {
//            startService(new Intent(this, GPSNew.class));
//            gps = new GPS(MenuActivity.this, 60000l, 5000l);
//            //gps = new GPS(AppMenu.this, 1000, 1000);
//            gps.setLatitude(0);
//            gps.setLongitude(0);
//            gps.start();
//            System.out.println("asljdhalkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk gps="+GPS.isExecutando());
//        }
    }

//    public boolean onKeyDown(int keyCode, KeyEvent event)  {
//
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//
//            if(gps != null) {
//
//                gps.setLatitude(0);
//                gps.setLongitude(0);
//                gps.setExecutando(false);
//                gps.stopColeta();
//                gps.cancel();
//            }
//
//            if(timerAtual != null) {
//
//                timerAtual.cancel();
//            }
//
//            return super.onKeyDown(keyCode, event);
//        }
//        else {
//
//            return super.onKeyDown(keyCode, event);
//        }
//    }

}
