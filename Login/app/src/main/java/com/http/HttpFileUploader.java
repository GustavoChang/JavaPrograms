package br.com.obramo334.http;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpFileUploader {
	
	HttpURLConnection connection = null;
	DataOutputStream outputStream = null;
	DataInputStream inputStream = null;

	//String pathToOurFile = "/data/file_to_send.mp3";
	
	//arq = new File(Environment.getExternalStorageDirectory(), "TB_TAREFA.txt");
	
	//String urlServer = "http://192.168.1.1/handle_upload.php";
	
	String lineEnd = "\r\n";
	String twoHyphens = "--";
	String boundary =  "*****";

	int bytesRead, bytesAvailable, bufferSize;
	byte[] buffer;
	int maxBufferSize = 1 * 1024 * 1024;
	
	String resposta;
	
	public String enviarFoto(
								String pathToOurFile, 
								String idFoto,
								String srvSS,
								String ssNum,
								String ssAno,
							    String rgi,
							    String etapa,
								String tpFoto,
								String arquivo,
								String situacao,	
								String dtInsert
			) throws Exception {

		String urlServer = Http.getUrlServlet() + "/fotobo?acao=1" +
													"&idFoto=" + idFoto +
													"&srvSS=" + srvSS +
													"&ssNum=" + ssNum +
													"&ssAno=" + ssAno +
													"&rgi=" + rgi +
													"&etapa=" + etapa +
													"&tpFoto=" + tpFoto +
													"&arquivo=" + arquivo +
													"&situacao=" + situacao +
													"&dtInsert=" + dtInsert ;
		
		try {

			FileInputStream fileInputStream = new FileInputStream( new File(pathToOurFile) );
			//FileInputStream fileInputStream = new FileInputStream( new File(Environment.getExternalStorageDirectory(), "TB_TAREFA.txt") );
			URL url = new URL(urlServer);
			connection = (HttpURLConnection) url.openConnection();
			
			// Allow Inputs & Outputs
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setConnectTimeout(5000);
			
			// Enable POST method
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
			
			outputStream = new DataOutputStream( connection.getOutputStream() );
			outputStream.writeBytes(twoHyphens + boundary + lineEnd);
			outputStream.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + pathToOurFile +"\"" + lineEnd);
			outputStream.writeBytes(lineEnd);
			
			bytesAvailable = fileInputStream.available();
			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			buffer = new byte[bufferSize];
			
			// Read file
			bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			
			while (bytesRead > 0) {
			
			outputStream.write(buffer, 0, bufferSize);
			bytesAvailable = fileInputStream.available();
			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			}
			
			outputStream.writeBytes(lineEnd);
			outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
			
			// Responses from the server (code and message)
			int serverResponseCode = connection.getResponseCode();
			String serverResponseMessage = connection.getResponseMessage();
			
			InputStream inputStream = connection.getInputStream();
			resposta = readString(inputStream);
			
			fileInputStream.close();
			outputStream.flush();
			outputStream.close();
			}
			catch (Exception ex) {
			
			//Exception handling
			//resposta = "Erro ao enviar arquivo!";
			throw ex;
		}
			
		return resposta;
	}

	// Faz a leitura do texto da InputStream retornada
	private String readString(InputStream in) throws IOException {

		byte[] bytes = readBytes(in);
		String texto = new String(bytes);
		return texto;
	}
		
	// Faz a leitura do array de bytes da InputStream retornada
	private byte[] readBytes(InputStream in) throws IOException {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			
			byte[] buffer = new byte[1024];
			int len;
			
			while ((len = in.read(buffer)) > 0) {
				bos.write(buffer, 0, len);
			}
			
			byte[] bytes = bos.toByteArray();
			
			return bytes;
		} 
		finally {
		
			bos.close();
			in.close();
		}
	}
}

