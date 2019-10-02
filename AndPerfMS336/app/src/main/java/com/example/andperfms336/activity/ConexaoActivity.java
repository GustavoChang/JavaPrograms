package com.example.andperfms336.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.andperfms336.R;
import com.example.andperfms336.bo.BaseDadosBO;


public class ConexaoActivity extends Activity implements OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_conexao);
        Button b = (Button) findViewById(R.id.btconexao);
        b.setOnClickListener(this);

    }
    public void onClick(View view) {
        TextView textViewConexao;
        textViewConexao = (TextView) findViewById(R.id.textViewConexao);
        if(BaseDadosBO.conexao){
            textViewConexao.setText("Conexão OK!");
            textViewConexao.setTextColor(Color.GREEN);
        }
        else{
            textViewConexao.setText("Sem Conexão...");
            textViewConexao.setTextColor(Color.RED);
        }
    }
}
