package com.example.andperfms336.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andperfms336.R;
import com.example.andperfms336.auxiliar.BaseDados;
import com.example.andperfms336.dao.FtVarreduraDAO;
import com.example.andperfms336.http.HttpFileUploader;
import com.example.andperfms336.modelo.FtVarredura;

import java.io.File;
import java.util.List;


public class FotoEnvioActivity extends Activity {

	ProgressBar pbenviofoto;
	TextView tvfotoqtde2;
	TextView tvfotoenviada2;
	TextView tvfotoenviar2;
	Button btavancar;
	Context context = this;
	
	FtVarreduraDAO ftVarreduraDAO = new FtVarreduraDAO(BaseDados.getBD(this));
	File dir;
	String pathFoto = BaseDados.getPathFotos();

	long fotoqtde = 0;
	long fotoqtdeenviada = 0;
	long fotoqtdeenviar = 0;
	long fracaoProgresso = 0;
	long valorBarra = 0;
	boolean enviando = false;
	
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_fotoenvio);
        
//        Intent it = getIntent();
//		if (it != null) {
//
//			cdtEquipe = (CdtEquipe) it.getSerializableExtra("cdtEquipe");
//		}

        pbenviofoto = (ProgressBar)findViewById(R.id.pbenviofoto);
        tvfotoqtde2 = (TextView) findViewById(R.id.tvfotoqtde2);
        tvfotoenviada2 = (TextView) findViewById(R.id.tvfotoenviada2);
        tvfotoenviar2 = (TextView) findViewById(R.id.tvfotoenviar2);
        btavancar = (Button) findViewById(R.id.btavancar);

        
        try {
        	
			fotoqtde = ftVarreduraDAO.getQtdeTotal();
			tvfotoqtde2.setText(String.valueOf(fotoqtde));
			
	        fotoqtdeenviada = ftVarreduraDAO.getQtdeEnviada();
	        tvfotoenviada2.setText(String.valueOf(fotoqtdeenviada));
	        
	        fotoqtdeenviar = ftVarreduraDAO.getQtdePendente();
	        tvfotoenviar2.setText(String.valueOf(fotoqtdeenviar));
        } 
        catch (Exception e) {

        	exibirMensagem("Falha(1)", e.toString());
		}       
        
        if(fotoqtdeenviar > 0) {
        
        	fracaoProgresso = 100 / fotoqtdeenviar;
        	valorBarra = fracaoProgresso;
        }

        dir = new File(pathFoto);
        dir.mkdir();

        btavancar.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {


				if(enviando == true) {
					
					exibirMensagem("Atenção!", "Por favor aguarde o fim do envio");
				}
				else if(fotoqtdeenviar > 0) {

					enviando = true;
					pbenviofoto.setVisibility(View.VISIBLE);
					new EnvioFotoTask().execute();					
				}
				else {
					enviando = true;
					pbenviofoto.setVisibility(View.VISIBLE);
					new EnvioFotoTask().execute();

					Toast.makeText(getApplicationContext(), "Não existem fotos para envio", Toast.LENGTH_SHORT).show();
				}
			}
		});
    }
        
    private class EnvioFotoTask extends AsyncTask<String, String, String> {

	    @SuppressLint("WrongThread")
		protected String doInBackground(String... urls) {
	    	
	    	String resultado = "";

	    	try {
				if(fotoqtdeenviar > 0) {
					String respFotoLigacao = "";
					List<FtVarredura> listFoto = ftVarreduraDAO.listarBySituacao("N");
					if(listFoto.size() > 0) {
						for(FtVarredura listaFoto: listFoto) {

							try {

								respFotoLigacao = new HttpFileUploader().enviarFoto(
										pathFoto + listaFoto.getArquivo(),
										listaFoto.getIdVarredura().toString(),
										listaFoto.getArquivo(),
										listaFoto.getCoordX(),
										listaFoto.getCoordY()
								);
								System.out.println("dksjflkjdslkfjsdlkfjlsdkjflsdkjflkdjflksdjflkjdflksdjlfkjdlkfkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk = "+respFotoLigacao);
								if(respFotoLigacao != null && respFotoLigacao.trim().equals("ok")) {
									ftVarreduraDAO.enviado(listaFoto);
									fotoqtdeenviada = fotoqtdeenviada + 1;
									fotoqtdeenviar = fotoqtdeenviar - 1 ;
								}
							}
							catch (Exception e) {
								System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee1 = "+e.toString());
							}

							publishProgress(String.valueOf(fotoqtdeenviada), String.valueOf(fotoqtdeenviar));
						}
					}
				}


				//envio db para servidor
				String respExport = "";
				try {
					respExport = new HttpFileUploader().enviarBD(
							BaseDados.getPathDb()
					);
				}
				catch (Exception e) {
					System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee1 = "+e.toString());
				}
				return resultado;
			} 
	        catch (Exception e) {
				System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee = "+e.toString());
        		return e.toString();
			}
	    }
	    
	    @Override
	    protected void onProgressUpdate(String... values) {
	    	
			tvfotoenviada2.setText(values[0]);
			tvfotoenviar2.setText(values[1]);
			pbenviofoto.setProgress((int)valorBarra);
			valorBarra += fracaoProgresso; 
	    }

	    protected void onPostExecute(String resultado) {

	    	enviando = false;
	    	pbenviofoto.setVisibility(View.GONE);
	    }
	}
    
	public void exibirMensagem(String titulo, String texto) {
		
		AlertDialog.Builder dialogo = new AlertDialog.Builder(FotoEnvioActivity.this);
		dialogo.setTitle(titulo);
		dialogo.setMessage(texto);
		dialogo.setNeutralButton("OK", null);
		dialogo.show();
	}	
	
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			   
			/*
			if(envioFotoTask!= null && !envioFotoTask.isCancelled()){
				
				envioFotoTask.cancel(true);
			}*/
			
	        return super.onKeyDown(keyCode, event);
	    }
		else {
			
			return super.onKeyDown(keyCode, event);
		}
	}
}
