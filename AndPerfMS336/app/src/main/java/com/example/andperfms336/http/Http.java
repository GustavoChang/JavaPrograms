package com.example.andperfms336.http;

import java.util.Map;

public abstract class Http {
	
	//utiliza UrlConnection
	public static final int NORMAL 	= 1;
	//Utiliza o Jakarta HttpClient
	public static final int JAKARTA = 2;
	
	public static Http getInstance(int tipo){
		switch (tipo) {
			case NORMAL:
				//UrlConnection
				return new HttpNormalImpl();
		default:
			return new HttpNormalImpl();
		}
	}
	//retorna o texto do arquivo
	public abstract String downloadArquivo(String url);
	//retorna os bytes da imagem
	public abstract byte[] downloadImagem(String url);
	//faz post enviando os par�metros
	public abstract String doPost(String url, Map map);
	
	public static String getUrlServlet() {
		return "http://200.155.2.66:8080/AndVarredura336";
		//return "http://192.168.85.108:8084/AndObraMO334";
	}
	public static String getServletRequest() {
		return "AndVarredura336";
		//return "http://192.168.85.108:8084/AndObraMO334";
	}
}