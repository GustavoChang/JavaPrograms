package br.com.obramo334.auxiliar;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.os.BatteryManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.InputMismatchException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.graphics.Matrix;

public class Auxiliar {

    public static void salvarArquivo(String arquivo, String nomeArq) {

        File arq;
        byte[] dados;

        try {

            arq = new File(Environment.getExternalStorageDirectory(), nomeArq);
            FileOutputStream fos;

            // transforma o texto digitado em array de bytes
            dados = arquivo.getBytes();

            fos = new FileOutputStream(arq);

            // escreve os dados e fecha o arquivo
            fos.write(dados);
            fos.flush();
            fos.close();

        }
        catch (Exception e) {

            System.out.println("Erro : " + e.getMessage());
        }
    }

    public static void lerArquivo(String nomeArq) {

        File arq;
        String lstrlinha;

        try {

            arq = new File(Environment.getExternalStorageDirectory(), nomeArq);
            //BufferedReader br = new BufferedReader(new FileReader(arq));
            BufferedReader br = new BufferedReader(new FileReader(arq));
            // efetua uma leitura linha a linha do arquivo a carrega
            // a caixa de texto com a informação lida
            while ((lstrlinha = br.readLine()) != null) {

                String[] colunas = lstrlinha.split("\\|");
                // lstrlinha.substring(0, 5);
            }
        }
        catch (Exception e) {

            System.out.println("Erro : " + e.getMessage());
        }
    }

    public static String formataDinheiro(String valor) {

        if (valor == null) {

            valor = "0,00";
        }
        else if (Integer.parseInt(valor) < 100) {

            valor = "0," + valor;
        }
        else if (Integer.parseInt(valor) < 1000) {

            valor = valor.substring(0, 1) + "," + valor.substring(1, 3);
        }
        else if (Integer.parseInt(valor) < 10000) {

            valor = valor.substring(0, 2) + "," + valor.substring(2, 4);
        }
        else if (Integer.parseInt(valor) < 100000) {

            valor = valor.substring(0, 3) + "," + valor.substring(3, 5);
        }
        else if (Integer.parseInt(valor) < 1000000) {

            valor = valor.substring(0, 1) + "." + valor.substring(1, 4) + "," + valor.substring(4, 6);
        }
        else if (Integer.parseInt(valor) < 10000000) {

            valor = valor.substring(0, 2) + "." + valor.substring(2, 5) + "," + valor.substring(5, 7);
        }
        else if (Integer.parseInt(valor) < 100000000) {

            valor = valor.substring(0, 3) + "." + valor.substring(3, 6) + "," + valor.substring(6, 8);
        }
        else if (Integer.parseInt(valor) < 1000000000) {

            valor = valor.substring(0, 1) + "." + valor.substring(1, 4) + "." + valor.substring(4, 7) + "," + valor.substring(7, 9);
        }
        else if ((Integer.parseInt(valor) / 10) < 1000000000) {

            valor = valor.substring(0, 2) + "." + valor.substring(2, 5) + "." + valor.substring(5, 8) + "," + valor.substring(8, 10);
        }

        return valor;
    }

    public static String dataAtual() {
        //recupera data e hora atual do sistema
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        String mes = String.valueOf(cal.get(Calendar.MONTH) + 1);
        String dia = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        String ano = String.valueOf(cal.get(Calendar.YEAR));
        String horas = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
        String minutos = String.valueOf(cal.get(Calendar.MINUTE));
        String segundos = String.valueOf(cal.get(Calendar.SECOND));
        //
        cal = null;
        date = null;
        //
        //formata a data de modo que o tamanho do resultado seja sempre fixo
        //dia
        if (dia.length() < 2) { dia = "0" + dia; } //mes
        if (mes.length() < 2) { mes = "0" + mes; } //horas
        if (horas.length() < 2) { horas = "0" + horas; } //minutos
        if (minutos.length() < 2) { minutos = "0" + minutos; } //segundos
        if (segundos.length() < 2) { segundos = "0" + segundos; } //

        //return dia + "/" + mes + "/" + ano + " " + horas + ":" + minutos + ":" + segundos;
        return ano + mes + dia ;
    }

    public static String dataAnterior() {
        //recupera data e hora atual do sistema
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, - 1);
        String mes = String.valueOf(cal.get(Calendar.MONTH) + 1);
        String dia = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        String ano = String.valueOf(cal.get(Calendar.YEAR));
        String horas = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
        String minutos = String.valueOf(cal.get(Calendar.MINUTE));
        String segundos = String.valueOf(cal.get(Calendar.SECOND));
        //
        cal = null;
        //
        //formata a data de modo que o tamanho do resultado seja sempre fixo
        //dia
        if (dia.length() < 2) { dia = "0" + dia; } //mes
        if (mes.length() < 2) { mes = "0" + mes; } //horas
        if (horas.length() < 2) { horas = "0" + horas; } //minutos
        if (minutos.length() < 2) { minutos = "0" + minutos; } //segundos
        if (segundos.length() < 2) { segundos = "0" + segundos; } //

        //return dia + "/" + mes + "/" + ano + " " + horas + ":" + minutos + ":" + segundos;
        return ano + mes + dia ;
    }

    public static String horaAtual() {
        //recupera data e hora atual do sistema
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        String mes = String.valueOf(cal.get(Calendar.MONTH) + 1);
        String dia = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        String ano = String.valueOf(cal.get(Calendar.YEAR));
        String horas = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
        String minutos = String.valueOf(cal.get(Calendar.MINUTE));
        String segundos = String.valueOf(cal.get(Calendar.SECOND));
        //
        cal = null;
        date = null;
        //
        //formata a data de modo que o tamanho do resultado seja sempre fixo
        //dia
        if (dia.length() < 2) { dia = "0" + dia; } //mes
        if (mes.length() < 2) { mes = "0" + mes; } //horas
        if (horas.length() < 2) { horas = "0" + horas; } //minutos
        if (minutos.length() < 2) { minutos = "0" + minutos; } //segundos
        if (segundos.length() < 2) { segundos = "0" + segundos; } //

        //return dia + "/" + mes + "/" + ano + " " + horas + ":" + minutos + ":" + segundos;
        return horas + ":" + minutos ;
    }

    public static String converteData(String x) {

        String data = "";

        if(x != null && !x.trim().equals("") && x.length() == 10) {

            data = x.substring(6,10) + x.substring(3,5) + x.substring(0,2);
        }

        return data;
    }

    public static String converteDataBarras(String x){

        String data = "";

        if(x != null && x.length() == 8){

            data = x.substring(6,8) +"/"+x.substring(4,6)+"/"+ x.substring(0,4);
        }

        return data;
    }

    public static double distFrom(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 3958.75;
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;

        //return dist; MILHAS
        return dist * 1609;//METROS
    }

    public static String isNull(String x) {

        if(x == null) {

            return "";
        }
        else {

            return x;
        }
    }

    public static String isEmpty(String x) {

        if(x != null && x.trim().equals("")) {

            return null;
        }
        else {

            return x;
        }
    }

    public static Long isZeroNull(Long entrada) {

        if(entrada != null && entrada.longValue() == 0) {

            entrada = null;
        }

        return entrada;
    }

    public static Double isZeroNull(Double entrada) {

        if(entrada != null
                && ( entrada.longValue() == 0
                || entrada.longValue() == 0.0)) {

            entrada = null;
        }

        return entrada;
    }

    public static String removerCaracteres(String text) {

        if(text != null) {

            return text.replaceAll("[ãâàáä]", "a")
                    .replaceAll("[êèéë]", "e")
                    .replaceAll("[îìíï]", "i")
                    .replaceAll("[õôòóö]", "o")
                    .replaceAll("[ûúùü]", "u")
                    .replaceAll("[ÃÂÀÁÄ]", "A")
                    .replaceAll("[ÊÈÉË]", "E")
                    .replaceAll("[ÎÌÍÏ]", "I")
                    .replaceAll("[ÕÔÒÓÖ]", "O")
                    .replaceAll("[ÛÙÚÜ]", "U")
                    .replace('ç', 'c')
                    .replace('Ç', 'C')
                    .replace('ñ', 'n')
                    .replace('Ñ', 'N')
                    .replaceAll("!", "")
                    .replaceAll("%", "")
                    .replaceAll ("\\[\\´\\`\\?!\\@\\#\\$\\%\\¨\\*"," ")
                    .replaceAll("\\(\\)\\=\\{\\}\\[\\]\\~\\^\\]"," ")
                    .replaceAll("[\\.\\;\\-\\_\\+\\'\\ª\\º\\:\\;\\/]"," ")
                    .replaceAll("  ", " ")
                    .trim();
        }
        else {

            return text;
        }
    }

    public static String capturarImei(Context context) {

        TelephonyManager telephony;

        telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        return telephony.getDeviceId();
    }

    public static void copiarArquivo(String srFile, String dtFile) throws Exception {

        File f1 = new File(srFile);
        File f2 = new File(dtFile);

        InputStream in = new FileInputStream(f1);

        // For Append the file.
        // OutputStream out = new FileOutputStream(f2,true);

        // For Overwrite the file.
        OutputStream out = new FileOutputStream(f2);

        byte[] buf = new byte[1024];
        int len;

        while ((len = in.read(buf)) > 0) {

            out.write(buf, 0, len);
        }

        in.close();
        out.close();
    }

    public static String leftPad(String texto, int tamanho, String caractere) {

        texto = texto.trim();

        while(texto.length() < tamanho) {

            texto = caractere + texto;
        }

        return texto;
    }

    public static boolean haveNumber(String texto) {

        char [] arr = texto.toCharArray();

        for(char caractere: arr) {

            if(!Character.isLetter(caractere)) {

                return true;
            }
        }

        return false;
    }

    public static boolean gpsHabilitado(Context context) {

        LocationManager lm = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);

        if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            return false;
        }

        return true;
    }

    public static void habilitarPacoteDeDados(Context context) {

        try {

            final ConnectivityManager conman = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            final Class conmanClass = Class.forName(conman.getClass().getName());
            final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
            iConnectivityManagerField.setAccessible(true);
            final Object iConnectivityManager = iConnectivityManagerField.get(conman);
            final Class iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
            final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            setMobileDataEnabledMethod.setAccessible(true);

            setMobileDataEnabledMethod.invoke(iConnectivityManager, true);
        }
        catch (Exception e) {

            System.out.println("error is occured in uses data packet enable & disable :-"+e.getMessage());
        }
    }

    public static int validarEndereco(String texto) {

        if(texto == null) {

            return 1;
        }
        if(texto.trim().length() < 4) {

            return 2;
        }
        if(!texto.trim().substring(0, 4).toUpperCase().contains("AL ") && 	//	ALAMEDA
                !texto.trim().substring(0, 4).toUpperCase().contains("AV ") && 	//	AVENIDA
                !texto.trim().substring(0, 4).toUpperCase().contains("B ") && 	//	BECO
                !texto.trim().substring(0, 4).toUpperCase().contains("CAL ") && 	//	CALÇADA
                !texto.trim().substring(0, 4).toUpperCase().contains("EST ") && 	//	ESTRADA
                !texto.trim().substring(0, 4).toUpperCase().contains("GAL ") && 	//	GALERIA
                !texto.trim().substring(0, 4).toUpperCase().contains("JD ") && 	//	JARDIM
                !texto.trim().substring(0, 4).toUpperCase().contains("L ") && 	//	LARGO
                !texto.trim().substring(0, 4).toUpperCase().contains("PC ") && 	//	PRAÇA
                !texto.trim().substring(0, 4).toUpperCase().contains("PQ ") && 	//	PARQUE
                !texto.trim().substring(0, 4).toUpperCase().contains("R ") && 	//	RUA
                !texto.trim().substring(0, 4).toUpperCase().contains("ROD ") && 	//	RODOVIA
                !texto.trim().substring(0, 4).toUpperCase().contains("TV ") && 	//	TRAVESSA
                !texto.trim().substring(0, 4).toUpperCase().contains("V ") && 	//	VIA
                !texto.trim().substring(0, 4).toUpperCase().contains("VD ") && 	//	VIADUTO
                !texto.trim().substring(0, 4).toUpperCase().contains("VL ") 	//	VIELA
                ) {

            return 3;
        }
        if(!haveNumber(texto.replace(" ", "")) && !texto.trim().toUpperCase().substring(texto.trim().length() - 2, texto.trim().length()).equals("SN")) {

            return 4;
        }

        return 0;
    }

    public static String ajustarAbrevEndereco(String texto) {

        if(texto != null) {

            return texto.replace("ALAMEDA", "AL")
                    .replace("Alameda", "AL")
                    .replace("alameda", "AL")
                    .replace("AVENIDA", "AV")
                    .replace("Avenida", "AV")
                    .replace("avenida", "AV")
                    .replace("BECO", "B")
                    .replace("Beco", "B")
                    .replace("beco", "B")
                    .replace("CALÇADA", "CAL")
                    .replace("Calçada", "CAL")
                    .replace("calçada", "CAL")
                    .replace("ESTRADA", "EST")
                    .replace("Estrada", "EST")
                    .replace("estrada", "EST")
                    .replace("GALERIA", "GAL")
                    .replace("Galeria", "GAL")
                    .replace("galeria", "GAL")
                    .replace("JARDIM", "JD")
                    .replace("Jardim", "JD")
                    .replace("jardim", "JD")
                    .replace("LARGO", "L")
                    .replace("Largo", "L")
                    .replace("largo", "L")
                    .replace("PRAÇA", "PC")
                    .replace("Praça", "PC")
                    .replace("praça", "PC")
                    .replace("PARQUE", "PQ")
                    .replace("Parque", "PQ")
                    .replace("parque", "PQ")
                    .replace("RUA", "R")
                    .replace("Rua", "R")
                    .replace("rua", "R")
                    .replace("RODOVIA", "ROD")
                    .replace("Rodovia", "ROD")
                    .replace("rodovia", "ROD")
                    .replace("TRAVESSA", "TV")
                    .replace("Travessa", "TV")
                    .replace("travessa", "TV")
                    .replace("VIA", "V")
                    .replace("Via", "V")
                    .replace("via", "V")
                    .replace("VIADUTO", "VD")
                    .replace("Viaduto", "VD")
                    .replace("viaduto", "VD")
                    .replace("VIELA", "VL")
                    .replace("Viela", "VL")
                    .replace("viela", "VL");
        }
        else  {

            return texto;
        }
    }

    public static boolean validarCpf(String CPF) {

        if (CPF.equals("00000000000") || CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11)) {

            return (false);
        }

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {

            sm = 0;
            peso = 10;

            for (i = 0; i < 9; i++) {

                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);

            if ((r == 10) || (r == 11)) {

                dig10 = '0';
            }
            else {

                dig10 = (char) (r + 48);
            }

            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {

                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {

                dig11 = '0';
            }
            else {

                dig11 = (char) (r + 48);
            }

            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {

                return (true);
            }
            else {

                return (false);
            }
        }
        catch (InputMismatchException erro) {

            return (false);
        }
    }

    public static void reduzirQualidadeImagem(String pathOrigem, String pathDestino) throws Exception {

        Bitmap bmpPic = BitmapFactory.decodeFile(pathOrigem);
        FileOutputStream bmpFile = new FileOutputStream(pathDestino);
        bmpPic.compress(Bitmap.CompressFormat.JPEG, 70, bmpFile);
        bmpFile.flush();
        bmpFile.close();
    }

    public static String imageParaTexto(String path) {

        File file = new File(path);
        FileInputStream imageInFile = null;
        String texto = null;

        try {

            imageInFile = new FileInputStream(file);
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);

            texto = Base64.encodeToString(imageData, Base64.DEFAULT);
        }
        catch (FileNotFoundException e) {

            System.out.println("Image not found" + e);
        }
        catch (IOException ioe) {

            System.out.println("Exception while reading the Image " + ioe);
        }
        finally {

            if(imageInFile != null) {

                try {imageInFile.close();} catch (IOException ex) {}
            }
        }

        return texto;
    }

    public static void textoParaImagem(String path, String texto) {

        FileOutputStream imageOutFile = null;

        try {

            byte[] imageByteArray = Base64.decode(texto, Base64.DEFAULT);

            imageOutFile = new FileOutputStream(path);
            imageOutFile.write(imageByteArray);

            imageOutFile.close();
        }
        catch (FileNotFoundException e) {

            System.out.println("Image not found" + e);
        }
        catch (IOException ioe) {

            System.out.println("Exception while reading the Image " + ioe);
        }
        finally {

            if(imageOutFile != null) {

                try {imageOutFile.close();} catch (IOException ex) {}
            }
        }
    }

    public static void ajustarRotacaoImagem(String path) throws Exception {

        try {

            ExifInterface exif = new ExifInterface(path);
            String orientacaoExif = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
            int orientacaoAjuste = 0;

            if(!TextUtils.isEmpty(orientacaoExif)) {

                if(Integer.parseInt(orientacaoExif) == ExifInterface.ORIENTATION_NORMAL) {

                    orientacaoAjuste = 0;
                }
                else if(Integer.parseInt(orientacaoExif) == ExifInterface.ORIENTATION_ROTATE_90) {

                    orientacaoAjuste = 90;
                }
                else if(Integer.parseInt(orientacaoExif) == ExifInterface.ORIENTATION_ROTATE_180) {

                    orientacaoAjuste = 180;
                }
                else if(Integer.parseInt(orientacaoExif) == ExifInterface.ORIENTATION_ROTATE_270) {

                    orientacaoAjuste = 270;
                }
            }

            Matrix matrix = new Matrix();
            matrix.postRotate(90);

            if(orientacaoAjuste == 90) {

                Bitmap bitmap = BitmapFactory.decodeFile(path);
                Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(),
                        matrix, true);
            }
        }
        catch (Exception e) {

            throw e;
        }
    }

    public static void ajustaFoto(String path) {

        //getContentResolver().notifyChange(outputFileUri, null);
        //ContentResolver cr = getContentResolver();

        Bitmap bitmap = null;
        int w = 0;
        int h = 0;
        Matrix mtx = new Matrix();

        // Ajusta orientação da imagem
        try {
            // joga a imagem em uma variável
            bitmap = BitmapFactory.decodeFile(path);

            // captura as dimensões da imagem
            w = bitmap.getWidth();
            h = bitmap.getHeight();
            mtx = new Matrix();

            // pega o caminho onda a imagem está salva
            ExifInterface exif = new ExifInterface(path);

            // pega a orientação real da imagem
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            // gira a imagem de acordo com a orientação
            switch(orientation) {
                case 3: // ORIENTATION_ROTATE_180
                    mtx.postRotate(180);
                    break;
                case 6: //ORIENTATION_ROTATE_90
                    mtx.postRotate(90);
                    break;
                case 8: //ORIENTATION_ROTATE_270
                    mtx.postRotate(270);
                    break;
                default: //ORIENTATION_ROTATE_0
                    mtx.postRotate(0);
                    break;
            }
        }
        catch(Exception e){}

        // cria variável com a imagem rotacionada
        Bitmap rotatedBmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
        BitmapDrawable bmpd = new BitmapDrawable(rotatedBmp);

        // redimensiona a imagem
        //Integer lateral = 256; // tamanho final da dimensão maior da imagem
        Integer lateral = 800; // tamanho final da dimensão maior da imagem
        try {
            // cria um stream pra salvar o arquivo
            FileOutputStream out = new FileOutputStream(path);

            // uma nova instancia do bitmap rotacionado
            Bitmap bmp = bmpd.getBitmap();

            //define um indice = 1 pois se der erro vai manter a imagem como está.
            Integer idx = 1;

            // reupera as dimensões da imagem
            w = bmp.getWidth();
            h = bmp.getHeight();

            // verifica qual a maior dimensão e divide pela lateral final para definir qual o indice de redução
            if ( w >= h){
                idx = w / lateral;
            } else {
                idx = h / lateral;
            }

            // acplica o indice de redução nas novas dimensões
            w = w / idx;
            h = h / idx;

            // cria nova instancia da imagem já redimensionada
            Bitmap bmpReduzido = Bitmap.createScaledBitmap(bmp, w, h, true);

            // salva a imagem reduzida no disco
            bmpReduzido.compress(Bitmap.CompressFormat.JPEG, 90, out);
        }
        catch(Exception e) {

        }
    }

    public static boolean validarData(String data){

        DateFormat df = new SimpleDateFormat ("dd/MM/yyyy");
        df.setLenient (false); // aqui o pulo do gato
        try {
            df.parse (data);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public static int nivelBateria(Context context){

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level / (float)scale;

        return (int)(batteryPct*100);
    }

    public static String formataDtTela(String data){

        if(data != null) {

            return data.substring(8, 10) + "/" + data.substring(5, 7) + "/" + data.substring(0, 4) + data.substring(10);
        }

        return data;
    }
}
