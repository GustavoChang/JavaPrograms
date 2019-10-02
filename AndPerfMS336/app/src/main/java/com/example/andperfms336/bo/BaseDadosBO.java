package com.example.andperfms336.bo;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.example.andperfms336.auxiliar.Auxiliar;
import com.example.andperfms336.auxiliar.BaseDados;
import com.example.andperfms336.auxiliar.GPS;
import com.example.andperfms336.auxiliar.GPSNew;
import com.example.andperfms336.dao.TbEquipeDAO;
import com.example.andperfms336.dao.TbVarreduraClasDAO;
import com.example.andperfms336.dao.TbVarreduraCroqDAO;
import com.example.andperfms336.dao.TbVarreduraDiagDAO;
import com.example.andperfms336.dao.TbVarreduraPavDAO;
import com.example.andperfms336.dao.TbVarreduraProgDAO;
import com.example.andperfms336.dao.TbVarreduraRetDAO;
import com.example.andperfms336.dao.TbVarreduraTuboDAO;
import com.example.andperfms336.dao.VwVarreduraCDPDAO;
import com.example.andperfms336.dao.VwVarreduraDAO;
import com.example.andperfms336.http.Http;
import com.example.andperfms336.modelo.VwVarredura;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class BaseDadosBO {
    private static Timer timerAtual = new Timer();
    private static TimerTask task;
    private static final Handler handler = new Handler();
    static int cont = 0;
    public static boolean atualizaTable = false;
    public static boolean envioDados = false;
    public static Context context = null;
    public static boolean backUp = false;
    public static boolean conexao = false;
    public static Map params;

    public static void startProcedimento(){
        ativaTimer();
    }
    private static void ativaTimer() {
        task = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        new EnvioRetornoTask().execute();
                    }
                });
            }};
        timerAtual.schedule(task, 300, 15000);
    }


    private static class EnvioRetornoTask extends AsyncTask<String, Void, String> {

        String resultado;

        protected String doInBackground(String... urls) {
            try {

                String resposta = "";
                //VERIFICANDO NOVOS SERVICOS
                resposta = baixar(Http.getUrlServlet() + "/"+Http.getServletRequest()+"", "getServicoVarredura");
                if(resposta != null && !resposta.trim().equals("null") && !resposta.trim().equals("")){
                    VwVarreduraDAO vwVarreduraDAO = new VwVarreduraDAO(BaseDados.getBD(context));
                    boolean inseridoBD = vwVarreduraDAO.processar(resposta);
                    if(inseridoBD){
                        baixar(Http.getUrlServlet() + "/"+Http.getServletRequest()+"", "confServicoVarredura");
                    }
                }
                conexao = true;

                if(atualizaTable){
                    //ATUALIZAR REGISTRO DAS TABELAS DOS FORMULARIOS
                    //BAIXANDO TbVarreduraPav
                    resposta = baixar(Http.getUrlServlet() + "/"+Http.getServletRequest()+"", "getTbVarreduraPav");
                    TbVarreduraPavDAO tbVarreduraPavDAO = new TbVarreduraPavDAO(BaseDados.getBD(context));
                    tbVarreduraPavDAO.processar(resposta);

                    //BAIXANDO TbVarreduraClas
                    resposta = baixar(Http.getUrlServlet() + "/"+Http.getServletRequest()+"", "getTbVarreduraClas");
                    TbVarreduraClasDAO tbVarreduraClasDAO = new TbVarreduraClasDAO(BaseDados.getBD(context));
                    tbVarreduraClasDAO.processar(resposta);

                    //BAIXANDO TbVarreduraCroq
                    resposta = baixar(Http.getUrlServlet() + "/"+Http.getServletRequest()+"", "getTbVarreduraCroq");
                    TbVarreduraCroqDAO tbVarreduraCroqDAO = new TbVarreduraCroqDAO(BaseDados.getBD(context));
                    tbVarreduraCroqDAO.processar(resposta);

                    //BAIXANDO TbVarreduraDiag
                    resposta = baixar(Http.getUrlServlet() + "/"+Http.getServletRequest()+"", "getTbVarreduraDiag");
                    TbVarreduraDiagDAO tbVarreduraDiagDAO = new TbVarreduraDiagDAO(BaseDados.getBD(context));
                    tbVarreduraDiagDAO.processar(resposta);

                    //BAIXANDO TbVarreduraProg
                    resposta = baixar(Http.getUrlServlet() + "/"+Http.getServletRequest()+"", "getTbVarreduraProg");
                    TbVarreduraProgDAO tbVarreduraProgDAO = new TbVarreduraProgDAO(BaseDados.getBD(context));
                    tbVarreduraProgDAO.processar(resposta);

                    //BAIXANDO TbVarreduraRet
                    resposta = baixar(Http.getUrlServlet() + "/"+Http.getServletRequest()+"", "getTbVarreduraRet");
                    TbVarreduraRetDAO tbVarreduraRetDAO = new TbVarreduraRetDAO(BaseDados.getBD(context));
                    tbVarreduraRetDAO.processar(resposta);

                    //BAIXANDO TbVarreduraTubo
                    resposta = baixar(Http.getUrlServlet() + "/"+Http.getServletRequest()+"", "getTbVarreduraTubo");
                    TbVarreduraTuboDAO tbVarreduraTuboDAO = new TbVarreduraTuboDAO(BaseDados.getBD(context));
                    tbVarreduraTuboDAO.processar(resposta);

                    resposta = baixar(Http.getUrlServlet() + "/"+Http.getServletRequest()+"", "getVwVarreduraCDP");
                    VwVarreduraCDPDAO vwVarreduraCDPDAO = new VwVarreduraCDPDAO(BaseDados.getBD(context));
                    vwVarreduraCDPDAO.processar(resposta);

                    atualizaTable = false;
                }

                //envioDados = TRUE sempre que algum servico for feito pelo celular
                if(envioDados){
                    VwVarreduraDAO vwVarreduraDAO = new VwVarreduraDAO(BaseDados.getBD(context));
                    if(vwVarreduraDAO.existeServicoByRecebido("P")){

                        List<VwVarredura> list = vwVarreduraDAO.getListByRecebido("P");
                        params = new HashMap();
                        System.out.println("akivai");
                        params.put("sendVwVarredura", new Gson().toJson(list));
                        resposta = enviar(Http.getUrlServlet() + "/"+Http.getServletRequest()+"", "sendVwVarredura");
                        System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ ENVIO -"+resposta);
                        if(resposta.equals("OK")){
                            System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ ENVIO - EU SOU GENIO DEMAIS");
                            vwVarreduraDAO.marcaEnviado(list);
                        }
                    }

                    envioDados = false;
                }

//                String respRetLigacaoVistoria = "";
//                List<RetLigacaoVistoria> listRetLigacaoVistoria = retLigacaoVistoriaDAO.listarPorSituacao("1");
//                for(RetLigacaoVistoria retLigacaoVistoria: listRetLigacaoVistoria) {
//
//                    respRetLigacaoVistoria = retLigacaoVistoria.enviar(Http.getUrlServlet() + "/retligacaovistoriabo", retLigacaoVistoria);
//                    if(respRetLigacaoVistoria != null && respRetLigacaoVistoria.trim().equals("ok")) {
//
//                        retLigacaoVistoria.setSituacao(2l);
//                        retLigacaoVistoriaDAO.alterar(retLigacaoVistoria);
//                    }
//                    Log.d("menu", "respRetLigacaoVistoria " + respRetLigacaoVistoria);
//                }
            }
            catch (Exception e) {
                //ERRO COMUNICACAO - SEM INTERNET OU SERVIDOR DOWN
                conexao = false;
                System.out.println("sdhfksjhfkjhfkjdhfkjdhkfjhkdjfhksdjfhkjdshfkdksdjhfjksdhfjksdhfsjkhfdkhfj servidor off "+e.toString());
            }

            return resultado;
        }

        protected void onPostExecute(String resposta) {
            Log.d("menu", "fim envio retorno "+GPSNew.latitude+"/"+GPSNew.longitude);

            //Toast.makeText(getApplicationContext(), resposta, Toast.LENGTH_LONG).show();
            if(backUp){
                //realizando backup
                BaseDados.fazerBackUp(context);
                backUp = false;
            }
        }

        public String baixar(String url, String r) throws IOException {
            params = new HashMap();
            params.put("r", r);
            params.put("idEquipe", Auxiliar.getLogin().getIdEquipe());
            String resposta = Http.getInstance(Http.NORMAL).doPost(url, params);

            return resposta;
        }

        public String enviar(String url, String r) throws IOException {

            params.put("r", r);
            String resposta = Http.getInstance(Http.NORMAL).doPost(url, params);

            return resposta;
        }
    }

}
