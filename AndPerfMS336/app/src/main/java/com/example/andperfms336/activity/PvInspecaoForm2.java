
package com.example.andperfms336.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andperfms336.R;
import com.example.andperfms336.auxiliar.Auxiliar;
import com.example.andperfms336.auxiliar.BaseDados;
import com.example.andperfms336.auxiliar.CustomAdapter;
import com.example.andperfms336.auxiliar.DecodeFoto;
import com.example.andperfms336.auxiliar.GPS;
import com.example.andperfms336.auxiliar.GPSNew;
import com.example.andperfms336.bo.BaseDadosBO;
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
import com.example.andperfms336.modelo.FtVarredura;
import com.example.andperfms336.modelo.TbVarreduraClas;
import com.example.andperfms336.modelo.TbVarreduraCroq;
import com.example.andperfms336.modelo.TbVarreduraDiag;
import com.example.andperfms336.modelo.TbVarreduraPav;
import com.example.andperfms336.modelo.TbVarreduraProg;
import com.example.andperfms336.modelo.TbVarreduraRet;
import com.example.andperfms336.modelo.TbVarreduraTubo;
import com.example.andperfms336.modelo.VwVarredura;
import com.example.andperfms336.modelo.VwVarreduraCDP;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class PvInspecaoForm2 extends Activity implements AdapterView.OnItemSelectedListener {

    ImageView imvcamera;
    TextView tvfotolista;
    EditText edobs;
    EditText eddiam1;
    EditText eddiam2;
    EditText eddiam3;
    EditText eddiam4;
    EditText edprof1;
    EditText edprof2;
    EditText edprof3;
    EditText edprof4;
    Button btgravar;
    Spinner spretorno;
    Spinner spclas;
    Spinner spdiag;
    Spinner spprog;
    Spinner sppav;
    Spinner spcroq;
    Spinner sptubo1;
    Spinner sptubo2;
    Spinner sptubo3;
    Spinner sptubo4;
    GridView grid_foto;
    LinearLayout lldiag;
    LinearLayout llprog;
    LinearLayout llformretorno;
    LinearLayout lltubo1;
    LinearLayout lltubo2;
    LinearLayout lltubo3;
    LinearLayout lltubo4;
    TextView tvtubo1;
    TextView tvtubo2;
    TextView tvtubo3;
    TextView tvtubo4;
    String[] spinnerPopulation;
    VwVarredura vwVarredura;
    String pathFoto = BaseDados.getPathFotos();
    FtVarreduraDAO ftVarreduraDAO =  new FtVarreduraDAO(BaseDados.getBD(this));
    TbVarreduraRetDAO tbVarreduraRetDAO = new TbVarreduraRetDAO(BaseDados.getBD(this));
    TbVarreduraClasDAO tbVarreduraClasDAO = new TbVarreduraClasDAO(BaseDados.getBD(this));
    TbVarreduraDiagDAO tbVarreduraDiagDAO = new TbVarreduraDiagDAO(BaseDados.getBD(this));
    TbVarreduraProgDAO tbVarreduraProgDAO = new TbVarreduraProgDAO(BaseDados.getBD(this));
    TbVarreduraPavDAO tbVarreduraPavDAO = new TbVarreduraPavDAO(BaseDados.getBD(this));
    TbVarreduraCroqDAO tbVarreduraCroqDAO = new TbVarreduraCroqDAO(BaseDados.getBD(this));
    TbVarreduraTuboDAO tbVarreduraTuboDAO = new TbVarreduraTuboDAO(BaseDados.getBD(this));
    VwVarreduraCDPDAO vwVarreduraCDPDAO = new VwVarreduraCDPDAO(BaseDados.getBD(this));
    VwVarreduraDAO vwVarreduraDAO = new VwVarreduraDAO(BaseDados.getBD(this));
    List<FtVarredura> listFoto;
    TbVarreduraRet retTbVarreduraRet;
    TbVarreduraClas retTbVarreduraClas;
    TbVarreduraDiag retTbVarreduraDiag;
    TbVarreduraProg retTbVarreduraProg;
    TbVarreduraCroq retTbVarreduraCroq;
    TbVarreduraPav retTbVarreduraPav;
    TbVarreduraTubo retTbVarreduraTubo1;
    TbVarreduraTubo retTbVarreduraTubo2;
    TbVarreduraTubo retTbVarreduraTubo3;
    TbVarreduraTubo retTbVarreduraTubo4;
    VwVarreduraCDP vwVarreduraCDP = new VwVarreduraCDP();


    public void getElementosForm() {
        spretorno = findViewById(R.id.spretorno);
        spretorno.requestFocus();
        llformretorno = findViewById(R.id.llformretorno);
        spclas = findViewById(R.id.spclas);
        spdiag = findViewById(R.id.spdiag);
        spprog = findViewById(R.id.spprog);
        sppav = findViewById(R.id.sppav);
        spcroq = findViewById(R.id.spcroq);
        sptubo1 = findViewById(R.id.sptubo1);
        sptubo2 = findViewById(R.id.sptubo2);
        sptubo3 = findViewById(R.id.sptubo3);
        sptubo4 = findViewById(R.id.sptubo4);


        lldiag = findViewById(R.id.lldiag);
        llprog = findViewById(R.id.llprog);

        lltubo1 = findViewById(R.id.lltubo1);
        lltubo2 = findViewById(R.id.lltubo2);
        lltubo3 = findViewById(R.id.lltubo3);
        lltubo4 = findViewById(R.id.lltubo4);
        tvtubo1 = findViewById(R.id.tvtubo1);
        tvtubo2 = findViewById(R.id.tvtubo2);
        tvtubo3 = findViewById(R.id.tvtubo3);
        tvtubo4 = findViewById(R.id.tvtubo4);

        eddiam1 = findViewById(R.id.eddiam1);
        eddiam2 = findViewById(R.id.eddiam2);
        eddiam3 = findViewById(R.id.eddiam3);
        eddiam4 = findViewById(R.id.eddiam4);

        edprof1 = findViewById(R.id.edprof1);
        edprof2 = findViewById(R.id.edprof2);
        edprof3 = findViewById(R.id.edprof3);
        edprof4 = findViewById(R.id.edprof4);

        imvcamera = findViewById(R.id.imvcamera);
        tvfotolista = findViewById(R.id.tvfotolista);
        edobs = findViewById(R.id.edobs);
        btgravar = findViewById(R.id.btgravar);

    }

    public void setListenerSpinner() {
        spretorno.setOnItemSelectedListener(this);
        spclas.setOnItemSelectedListener(this);
        spdiag.setOnItemSelectedListener(this);
        spprog.setOnItemSelectedListener(this);
        spcroq.setOnItemSelectedListener(this);
        sppav.setOnItemSelectedListener(this);
        sptubo1.setOnItemSelectedListener(this);
        sptubo2.setOnItemSelectedListener(this);
        sptubo3.setOnItemSelectedListener(this);
        sptubo4.setOnItemSelectedListener(this);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_pvinspecao);

        Intent it = getIntent();
        if (it != null) {
            vwVarredura = (VwVarredura) it.getSerializableExtra("vwVarredura");
        }

        try {
            getElementosForm();
            setListenerSpinner();


            //ESTUDAR DEPOIS - MINI FOTOS, AMPLIAR E APAGAR
//            File temp = new File(pathFoto + "temp.jpg");
//            imvcamera = (ImageView) findViewById(R.id.imvcamera);
//            imvcamera.setImageBitmap(DecodeFoto.decodeSampledBitmapFromFile(temp.getAbsolutePath(), 500, 250));

//            grid_foto = (GridView)findViewById(R.id.GridView01);
//            grid_foto.setAdapter(new ImageAdapter(this));
//            grid_foto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                public void onItemClick(AdapterView<?> parent, View v,
//                                        int position, long id) {
//                    MyCustomAlertDialog();
//                }
//            });



            ArrayAdapter<TbVarreduraRet> dataAdapterRet = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tbVarreduraRetDAO.listar());
            dataAdapterRet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spretorno.setAdapter(dataAdapterRet);

            //dataAdapterRet.getPosition(tbVarreduraRet)
            //cria selected dos spinners

            ArrayAdapter<TbVarreduraClas> dataAdapterClas = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tbVarreduraClasDAO.listar());
            dataAdapterClas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spclas.setAdapter(dataAdapterClas);

            ArrayAdapter<TbVarreduraPav> dataAdapterPav = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tbVarreduraPavDAO.listar());
            dataAdapterPav.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sppav.setAdapter(dataAdapterPav);

            //montar list Croqui
            montarListCroqui();

            ArrayAdapter<TbVarreduraTubo> dataAdapterTubo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tbVarreduraTuboDAO.listar());
            dataAdapterTubo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sptubo1.setAdapter(dataAdapterTubo);
            sptubo2.setAdapter(dataAdapterTubo);
            sptubo3.setAdapter(dataAdapterTubo);
            sptubo4.setAdapter(dataAdapterTubo);

            //TIRAR FOTO
            imvcamera.setOnClickListener(new OnClickListener() {

                public void onClick(View arg0) {

                    Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
                    File file = new File(pathFoto, "temp.jpg");
                    i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                    startActivityForResult(i, 1);
                }
            });
            listarFotos();
            //FIM TIRAR FOTO

            if (vwVarredura.getRecebidoServidor().equals("S") || vwVarredura.getRecebidoServidor().equals("A")){
                criaSelectedSpinner();
            }


            btgravar.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {

                    try {
                        if(validaFormulario()){
                            vwVarredura.setIdVarreduraRet(retTbVarreduraRet.getIdVarreduraRet());
                            vwVarredura.setObs(edobs.getText().toString());
                            vwVarredura.setData(Auxiliar.dataAtualCompleta());
                            vwVarredura.setVarCoordX(GPSNew.latitude);
                            vwVarredura.setVarCoordY(GPSNew.longitude);
                            vwVarredura.setRecebidoServidor("P");

                            if (retTbVarreduraRet.getIdVarreduraRet() ==1){
                                //ATUALIZAR REGISTRO

                                vwVarredura.setIdVarreduraPav(retTbVarreduraPav.getIdVarreduraPav());
                                vwVarredura.setIdVarreduraCroq(retTbVarreduraCroq.getIdVarreduraCroq());
                                vwVarreduraCDP.setIdVarreduraClas(retTbVarreduraClas.getIdVarreduraClas());
                                vwVarreduraCDP.setIdVarreduraDiag(retTbVarreduraDiag.getIdVarreduraDiag());
                                vwVarreduraCDP.setIdVarreduraProg(retTbVarreduraProg.getIdVarreduraProg());
                                vwVarreduraCDP = vwVarreduraCDPDAO.getModeloByFK(vwVarreduraCDP);
                                vwVarredura.setIdVarreduraCDP(vwVarreduraCDP.getIdVarreduraCDP());


                                if(retTbVarreduraTubo1.getIdVarreduraTubo() == 0){vwVarredura.setIdVarreduraTubo1(null);}else{vwVarredura.setIdVarreduraTubo1(retTbVarreduraTubo1.getIdVarreduraTubo());}
                                if(retTbVarreduraTubo2.getIdVarreduraTubo() == 0){vwVarredura.setIdVarreduraTubo2(null);}else{vwVarredura.setIdVarreduraTubo2(retTbVarreduraTubo2.getIdVarreduraTubo());}
                                if(retTbVarreduraTubo3.getIdVarreduraTubo() == 0){vwVarredura.setIdVarreduraTubo3(null);}else{vwVarredura.setIdVarreduraTubo3(retTbVarreduraTubo3.getIdVarreduraTubo());}
                                if(retTbVarreduraTubo4.getIdVarreduraTubo() == 0){vwVarredura.setIdVarreduraTubo4(null);}else{vwVarredura.setIdVarreduraTubo4(retTbVarreduraTubo4.getIdVarreduraTubo());}

                                vwVarredura.setDiam1(Auxiliar.formataNumeroForm(eddiam1.getText().toString()));
                                vwVarredura.setDiam2(Auxiliar.formataNumeroForm(eddiam2.getText().toString()));
                                vwVarredura.setDiam3(Auxiliar.formataNumeroForm(eddiam3.getText().toString()));
                                vwVarredura.setDiam4(Auxiliar.formataNumeroForm(eddiam4.getText().toString()));

                                vwVarredura.setProf1(Auxiliar.formataNumeroForm(edprof1.getText().toString()));
                                vwVarredura.setProf2(Auxiliar.formataNumeroForm(edprof2.getText().toString()));
                                vwVarredura.setProf3(Auxiliar.formataNumeroForm(edprof3.getText().toString()));
                                vwVarredura.setProf4(Auxiliar.formataNumeroForm(edprof4.getText().toString()));

                                vwVarredura.setTp1(retTbVarreduraCroq.getTp1());
                                vwVarredura.setTp2(retTbVarreduraCroq.getTp2());
                                vwVarredura.setTp3(retTbVarreduraCroq.getTp3());
                                vwVarredura.setTp4(retTbVarreduraCroq.getTp4());
                            }

                            vwVarreduraDAO.alterar(vwVarredura);

                            BaseDadosBO.envioDados = true;
                            Intent i = new Intent(PvInspecaoForm2.this, MapaActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);

                            //PvInspecaoForm.this.finish();
                        }
//                        setResult(1);
//                        finish();
                    }
                    catch (Exception e) {

                        exibirMensagem("Atenção","Falha " + e.toString() );
                    }
                }
            });

        }
        catch (Exception e) {

            exibirMensagem("Falha(2)", e.toString());
        }



    }


    public boolean validaFormulario(){
        boolean resposta = true;

        //VERIFICANDO SE EXISTE PELO MENOS 1 FOTO
        if (retTbVarreduraRet.getIdVarreduraRet() ==0){
            resposta = false;
            exibirMensagem("Validação de Formulário", "Selecione o Retorno.");
        }
        else if (retTbVarreduraRet.getIdVarreduraRet() ==1){
            if(listFoto.size() == 0){
                resposta = false;
                exibirMensagem("Validação de Formulário", "Pelo menos 1 FOTO é obrigatória.");
            }
            else if(retTbVarreduraPav.getIdVarreduraPav() == 0){
                resposta = false;
                exibirMensagem("Validação de Formulário", "Selecione o Pavimento.");
            }
            else if(retTbVarreduraClas.getIdVarreduraClas() == 0){
                resposta = false;
                exibirMensagem("Validação de Formulário", "Selecione a Classificação.");
            }
            else if(retTbVarreduraDiag.getIdVarreduraDiag() == 0){
                resposta = false;
                exibirMensagem("Validação de Formulário", "Selecione a Situação.");
            }
            else if(retTbVarreduraProg.getIdVarreduraProg() == 0){
                resposta = false;
                exibirMensagem("Validação de Formulário", "Selecione o Prognóstico.");
            }
            else if(retTbVarreduraCroq.getIdVarreduraCroq() == 0){
                resposta = false;
                exibirMensagem("Validação de Formulário", "Selecione o Croqui.");
            }
            else if(retTbVarreduraCroq.getQtde() > 0){
                if(retTbVarreduraCroq.getQtde() == 1){
                    if(retTbVarreduraTubo1.getIdVarreduraTubo() == 0){
                        resposta = false;
                        exibirMensagem("Validação de Formulário", "Selecione o Tubo.");
                    }
                }
                else if(retTbVarreduraCroq.getQtde() == 2){
                    if(retTbVarreduraTubo1.getIdVarreduraTubo() == 0
                            || retTbVarreduraTubo2.getIdVarreduraTubo() == 0){
                        resposta = false;
                        exibirMensagem("Validação de Formulário", "Selecione o Tubo.");
                    }
                }
                else if(retTbVarreduraCroq.getQtde() == 3){
                    if(retTbVarreduraTubo1.getIdVarreduraTubo() == 0
                            || retTbVarreduraTubo2.getIdVarreduraTubo() == 0
                            || retTbVarreduraTubo3.getIdVarreduraTubo() == 0){
                        resposta = false;
                        exibirMensagem("Validação de Formulário", "Selecione o Tubo.");
                    }
                }
                else if(retTbVarreduraCroq.getQtde() == 4){
                    if(retTbVarreduraTubo1.getIdVarreduraTubo() == 0
                            || retTbVarreduraTubo2.getIdVarreduraTubo() == 0
                            || retTbVarreduraTubo3.getIdVarreduraTubo() == 0
                            || retTbVarreduraTubo4.getIdVarreduraTubo() == 0){
                        resposta = false;
                        exibirMensagem("Validação de Formulário", "Selecione o Tubo.");
                    }
                }
            }
        }
        else {
            if(listFoto.size() == 0){
                resposta = false;
                exibirMensagem("Validação de Formulário", "Pelo menos 1 FOTO é obrigatória.");
            }
        }

        return resposta;
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.spretorno){
            retTbVarreduraRet = (TbVarreduraRet) adapterView.getItemAtPosition(i);
            if(retTbVarreduraRet.getIdVarreduraRet() == 1){
                llformretorno.setVisibility(View.VISIBLE);
            }
            else{
                llformretorno.setVisibility(View.GONE);
            }

        }
        else if(adapterView.getId() == R.id.spclas){
            retTbVarreduraClas= (TbVarreduraClas) adapterView.getItemAtPosition(i);
            if(retTbVarreduraClas.getIdVarreduraClas() > 0){
                ArrayAdapter<TbVarreduraDiag> dataAdapterDiag = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tbVarreduraDiagDAO.listarByClas(retTbVarreduraClas.getIdVarreduraClas()));
                dataAdapterDiag.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spdiag.setAdapter(dataAdapterDiag);
                lldiag.setVisibility(View.VISIBLE);
                llprog.setVisibility(View.VISIBLE);
            }
            else{
                lldiag.setVisibility(View.GONE);
                llprog.setVisibility(View.GONE);
            }

        }
        else if(adapterView.getId() == R.id.spdiag){
            retTbVarreduraDiag = (TbVarreduraDiag) adapterView.getItemAtPosition(i);
            if(retTbVarreduraDiag.getIdVarreduraDiag() > 0){
                ArrayAdapter<TbVarreduraProg> dataAdapterDiag = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tbVarreduraProgDAO.listarByDiag(retTbVarreduraDiag.getIdVarreduraDiag()));
                dataAdapterDiag.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spprog.setAdapter(dataAdapterDiag);
                llprog.setVisibility(View.VISIBLE);
            }
            else{
                llprog.setVisibility(View.GONE);
            }
        }
        else if(adapterView.getId() == R.id.spcroq){
            retTbVarreduraCroq = tbVarreduraCroqDAO.getModeloById(Long.parseLong(spinnerPopulation[i]));
            if(Integer.parseInt(spinnerPopulation[i]) > 0){
                if(retTbVarreduraCroq.getQtde() == 0){
                    lltubo1.setVisibility(View.GONE);
                    lltubo2.setVisibility(View.GONE);
                    lltubo3.setVisibility(View.GONE);
                    lltubo4.setVisibility(View.GONE);
                }
                else if(retTbVarreduraCroq.getQtde() == 1){
                    if(retTbVarreduraCroq.getTp1().equals("E")){tvtubo1.setText("Tubo Entrada");}else{tvtubo1.setText("Tubo Saída");}
                    lltubo1.setVisibility(View.VISIBLE);
                    lltubo2.setVisibility(View.GONE);
                    lltubo3.setVisibility(View.GONE);
                    lltubo4.setVisibility(View.GONE);
                }
                else if(retTbVarreduraCroq.getQtde() == 2){
                    if(retTbVarreduraCroq.getTp1().equals("E")){tvtubo1.setText("Tubo Entrada");}else{tvtubo1.setText("Tubo Saída");}
                    if(retTbVarreduraCroq.getTp2().equals("E")){tvtubo2.setText("Tubo Entrada");}else{tvtubo2.setText("Tubo Saída");}
                    lltubo1.setVisibility(View.VISIBLE);
                    lltubo2.setVisibility(View.VISIBLE);
                    lltubo3.setVisibility(View.GONE);
                    lltubo4.setVisibility(View.GONE);
                }
                else if(retTbVarreduraCroq.getQtde() == 3){
                    if(retTbVarreduraCroq.getTp1().equals("E")){tvtubo1.setText("Tubo Entrada");}else{tvtubo1.setText("Tubo Saída");}
                    if(retTbVarreduraCroq.getTp2().equals("E")){tvtubo2.setText("Tubo Entrada");}else{tvtubo2.setText("Tubo Saída");}
                    if(retTbVarreduraCroq.getTp3().equals("E")){tvtubo3.setText("Tubo Entrada");}else{tvtubo3.setText("Tubo Saída");}
                    lltubo1.setVisibility(View.VISIBLE);
                    lltubo2.setVisibility(View.VISIBLE);
                    lltubo3.setVisibility(View.VISIBLE);
                    lltubo4.setVisibility(View.GONE);
                }
                else if(retTbVarreduraCroq.getQtde() == 4){
                    if(retTbVarreduraCroq.getTp1().equals("E")){tvtubo1.setText("Tubo Entrada");}else{tvtubo1.setText("Tubo Saída");}
                    if(retTbVarreduraCroq.getTp2().equals("E")){tvtubo2.setText("Tubo Entrada");}else{tvtubo2.setText("Tubo Saída");}
                    if(retTbVarreduraCroq.getTp3().equals("E")){tvtubo3.setText("Tubo Entrada");}else{tvtubo3.setText("Tubo Saída");}
                    if(retTbVarreduraCroq.getTp4().equals("E")){tvtubo4.setText("Tubo Entrada");}else{tvtubo4.setText("Tubo Saída");}
                    lltubo1.setVisibility(View.VISIBLE);
                    lltubo2.setVisibility(View.VISIBLE);
                    lltubo3.setVisibility(View.VISIBLE);
                    lltubo4.setVisibility(View.VISIBLE);
                }
            }
            else{
                lltubo1.setVisibility(View.GONE);
                lltubo2.setVisibility(View.GONE);
                lltubo3.setVisibility(View.GONE);
                lltubo4.setVisibility(View.GONE);
            }

        }
        else if(adapterView.getId() == R.id.spprog){
            retTbVarreduraProg = (TbVarreduraProg) adapterView.getItemAtPosition(i);
        }
        else if(adapterView.getId() == R.id.sppav){
            retTbVarreduraPav = (TbVarreduraPav) adapterView.getItemAtPosition(i);
        }
        else if(adapterView.getId() == R.id.sptubo1){
            retTbVarreduraTubo1 = (TbVarreduraTubo) adapterView.getItemAtPosition(i);
        }
        else if(adapterView.getId() == R.id.sptubo2){
            retTbVarreduraTubo2 = (TbVarreduraTubo) adapterView.getItemAtPosition(i);
        }
        else if(adapterView.getId() == R.id.sptubo3){
            retTbVarreduraTubo3 = (TbVarreduraTubo) adapterView.getItemAtPosition(i);
        }
        else if(adapterView.getId() == R.id.sptubo4){
            retTbVarreduraTubo4 = (TbVarreduraTubo) adapterView.getItemAtPosition(i);
        }

//        System.out.println("adapterView.getSelectedItem() "+adapterView.getSelectedItem());
//        System.out.println("adapterView.getSelectedItemId() "+adapterView.getSelectedItemId());
//        System.out.println("adapterView.getContext().getPackageName() "+adapterView.getContext().getPackageName());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    private int [] imagemicone = {
//            R.drawable.ic_servico,
//            R.drawable.ic_servico,
            R.drawable.ic_mapa,
            R.drawable.ic_foto,
            R.drawable.ic_foto
    };

    public class ImageAdapter extends BaseAdapter {
        Context mContext;
        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return imagemicone.length;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            View v;

            LayoutInflater li = getLayoutInflater();
            v = li.inflate(R.layout.visualizarfoto_icone, null);

            ImageView iv = (ImageView)v.findViewById(R.id.icon_image);
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

    //TIRAR FOTO
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == -1) {

            try {

                salvarImagem();
            }
            catch (Exception e) {

                exibirMensagem("Falha(3)", e.toString());
            }

            listarFotos();
        }
    }

    //TIRAR FOTO
    public void salvarImagem() throws Exception {

        File dir = new File(pathFoto);
        dir.mkdir();

        //int numeroFoto = fotoTrechoDAO.qtde(srvTrecho.getSrvTrecho().toString(), 1l) + 1;

        String nomeFoto = vwVarredura.getIdVarredura()+"_"+Auxiliar.dataAtualCompleta()+".jpg";
//        String nomeFoto =   srvTrecho.getSrvTrecho() + "_" + //SRV_TRECHO
//                            cdtTrecho.getCdtTrecho() + "_" + //CDT_TRECHO
//                            cdtTrechoSeg.getCdtTrechoSeg() + "_" + //CDT_TRECHOSEG
//                            1 + "_" + //ETAPA
//                            fotoTrechoDAO.dataHora().substring(0, 10).replace("-", "") + //DATA
//                            "(" + numeroFoto + ")" + //NUMERO FOTO
//                            ".jpg";

        File temp = new File(pathFoto + "temp.jpg");

        if(temp.exists() && temp.length() > 0) {
            Auxiliar.copiarArquivo(pathFoto + "temp.jpg", pathFoto + "/" + nomeFoto);
            Auxiliar.ajustarRotacaoImagem(pathFoto + "/" + nomeFoto);
            Auxiliar.ajustaFoto(pathFoto + "/" + nomeFoto);

            FtVarredura ftVarredura = new FtVarredura();
            ftVarredura.setId(ftVarreduraDAO.getId());
            ftVarredura.setIdVarredura(vwVarredura.getIdVarredura());
            ftVarredura.setArquivo(nomeFoto);
            ftVarredura.setCoordX(String.valueOf(GPS.getLatitude()));
            ftVarredura.setCoordY(String.valueOf(GPS.getLongitude()));
            ftVarredura.setRecebidoServidor("N");
            ftVarreduraDAO.inserir(ftVarredura);

        }
        else {

            exibirMensagem("Falha(4)", "Arquivo não localizado");
        }
    }

    private void listarFotos() {

        try {

            String fotos = "";

            listFoto = ftVarreduraDAO.listarByIdVarredura(vwVarredura.getIdVarredura());

            if(listFoto.size() > 0) {

                for(FtVarredura listaFoto: listFoto) {

                    fotos += listaFoto.getArquivo() + "\n";
                }
                tvfotolista.setText(fotos);
            }
        }
        catch (Exception e) {

            exibirMensagem("Falha(5)", e.toString());
        }
    }

    public void exibirMensagem(String titulo, String texto) {

        AlertDialog.Builder dialogo = new AlertDialog.Builder(PvInspecaoForm2.this);
        dialogo.setTitle(titulo);
        dialogo.setMessage(texto);
        dialogo.setNeutralButton("OK", null);
        dialogo.show();
    }

    public void montarListCroqui() {
        try{
            List<TbVarreduraCroq> list = tbVarreduraCroqDAO.listar();

            String[] spinnerTitles = tbVarreduraCroqDAO.getSpinnerTitles(list);
            String[] spinnerPopulation = tbVarreduraCroqDAO.getSpinnerPopulation(list);
            this.spinnerPopulation = spinnerPopulation;
            int[] spinnerImages = tbVarreduraCroqDAO.getSpinnerImages(list);

            CustomAdapter mCustomAdapter = new CustomAdapter(PvInspecaoForm2.this, spinnerTitles, spinnerImages, spinnerPopulation);
            spcroq.setAdapter(mCustomAdapter);
        }
        catch (Exception e) {

            exibirMensagem("Falha(2)", e.toString());
        }
    }

    public void MyCustomAlertDialog(){
        final Dialog MyDialog = new Dialog(this);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.visualizarfoto);
        MyDialog.setTitle("TESTE FOTO");



        Button hello = MyDialog.findViewById(R.id.hello);
        Button close = MyDialog.findViewById(R.id.close);

        hello.setEnabled(true);
        close.setEnabled(true);

        hello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Hello, I'm Custom Alert Dialog", Toast.LENGTH_LONG).show();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialog.cancel();
            }
        });

        MyDialog.show();
    }



    public void criaSelectedSpinner(){

        for(int i=0; i<spretorno.getCount(); i++){
            retTbVarreduraRet = (TbVarreduraRet) spretorno.getItemAtPosition(i);
            if(Long.toString(vwVarredura.getIdVarreduraRet()).equals(Long.toString(retTbVarreduraRet.getIdVarreduraRet()))){
                System.out.println(i);
                spretorno.setSelection(i);
            }
        }
        if(vwVarredura.getObs() != null){
            edobs.setText(vwVarredura.getObs());
        }

        if(vwVarredura.getIdVarreduraRet() == 1){
            //impedimentos

        }


    }
}