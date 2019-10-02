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
import java.util.List;

public class GapInspecaoForm extends Activity implements AdapterView.OnItemSelectedListener {
    ImageView imvcamera;
    TextView tvfotolista;
    EditText edobs;
    Button btgravar;
    Spinner spretorno;
    Spinner spclas;
    Spinner spdiag;
    Spinner spprog;
    Spinner sppav;
    GridView grid_foto;
    LinearLayout lldiag;
    LinearLayout llprog;
    LinearLayout llformretorno;
    String[] spinnerPopulation;
    VwVarredura vwVarredura;
    String pathFoto = BaseDados.getPathFotos();
    FtVarreduraDAO ftVarreduraDAO =  new FtVarreduraDAO(BaseDados.getBD(this));
    TbVarreduraRetDAO tbVarreduraRetDAO = new TbVarreduraRetDAO(BaseDados.getBD(this));
    TbVarreduraClasDAO tbVarreduraClasDAO = new TbVarreduraClasDAO(BaseDados.getBD(this));
    TbVarreduraDiagDAO tbVarreduraDiagDAO = new TbVarreduraDiagDAO(BaseDados.getBD(this));
    TbVarreduraProgDAO tbVarreduraProgDAO = new TbVarreduraProgDAO(BaseDados.getBD(this));
    VwVarreduraCDPDAO vwVarreduraCDPDAO = new VwVarreduraCDPDAO(BaseDados.getBD(this));
    VwVarreduraDAO vwVarreduraDAO = new VwVarreduraDAO(BaseDados.getBD(this));
    List<FtVarredura> listFoto;
    TbVarreduraRet retTbVarreduraRet;
    TbVarreduraClas retTbVarreduraClas;
    TbVarreduraDiag retTbVarreduraDiag;
    TbVarreduraProg retTbVarreduraProg;
    VwVarreduraCDP vwVarreduraCDP = new VwVarreduraCDP();


    public void getElementosForm() {
        spretorno = findViewById(R.id.spretorno);
        spretorno.requestFocus();
        llformretorno = findViewById(R.id.llformretorno);
        spclas = findViewById(R.id.spclas);
        spdiag = findViewById(R.id.spdiag);
        spprog = findViewById(R.id.spprog);
        sppav = findViewById(R.id.sppav);


        lldiag = findViewById(R.id.lldiag);
        llprog = findViewById(R.id.llprog);

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

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_gapinspecao);

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

            ArrayAdapter<TbVarreduraClas> dataAdapterClas = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tbVarreduraClasDAO.listar());
            dataAdapterClas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spclas.setAdapter(dataAdapterClas);

//            ArrayAdapter<TbVarreduraDiag> dataAdapterDiag = new ArrayAdapter<TbVarreduraDiag>(this, android.R.layout.simple_spinner_item, tbVarreduraDiagDAO.listar());
//            dataAdapterDiag.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spdiag.setAdapter(dataAdapterDiag);

            //TIRAR FOTO
            imvcamera.setOnClickListener(new View.OnClickListener() {

                public void onClick(View arg0) {

                    Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
                    File file = new File(pathFoto, "temp.jpg");
                    i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                    startActivityForResult(i, 1);
                }
            });
            listarFotos();
            //FIM TIRAR FOTO


            btgravar.setOnClickListener(new View.OnClickListener() {
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

                                vwVarreduraCDP.setIdVarreduraClas(retTbVarreduraClas.getIdVarreduraClas());
                                vwVarreduraCDP.setIdVarreduraDiag(retTbVarreduraDiag.getIdVarreduraDiag());
                                vwVarreduraCDP.setIdVarreduraProg(retTbVarreduraProg.getIdVarreduraProg());
                                vwVarreduraCDP = vwVarreduraCDPDAO.getModeloByFK(vwVarreduraCDP);
                                vwVarredura.setIdVarreduraCDP(vwVarreduraCDP.getIdVarreduraCDP());

                            }

                            vwVarreduraDAO.alterar(vwVarredura);

                            BaseDadosBO.envioDados = true;
                            Intent i = new Intent(GapInspecaoForm.this, MapaActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);

                            //GapInspecaoForm.this.finish();
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
        else if(adapterView.getId() == R.id.spprog){
            retTbVarreduraProg = (TbVarreduraProg) adapterView.getItemAtPosition(i);
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

        String nomeFoto = vwVarredura.getIdVarredura()+"_"+ Auxiliar.dataAtualCompleta()+".jpg";
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

        AlertDialog.Builder dialogo = new AlertDialog.Builder(GapInspecaoForm.this);
        dialogo.setTitle(titulo);
        dialogo.setMessage(texto);
        dialogo.setNeutralButton("OK", null);
        dialogo.show();
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

}
