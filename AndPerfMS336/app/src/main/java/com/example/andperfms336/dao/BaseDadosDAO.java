package com.example.andperfms336.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.andperfms336.auxiliar.BaseDados;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

public class BaseDadosDAO {
	
	private static SQLiteDatabase db;
	private static Context context;

    public BaseDadosDAO(SQLiteDatabase db) {
        this.db = db;
    }

	public void delete() {
		context.deleteDatabase(BaseDados.nomeBancoPadrao);
	}

    public void backUpDB() throws Exception {
        final String inFileName = BaseDados.pathDoBanco+"/"+BaseDados.nomeBancoPadrao;
        File dbFile = new File(inFileName);
        FileInputStream fis = new FileInputStream(dbFile);

        String outFileName = Environment.getExternalStorageDirectory()+"/"+BaseDados.nomeBancoPadrao;

        // Open the empty db as the output stream
        OutputStream output = new FileOutputStream(outFileName);

        // Transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer))>0){
            output.write(buffer, 0, length);
        }

        System.out.println("BACKUP REALIZADO");
        // Close the streams
        output.flush();
        output.close();
        fis.close();
    }
}
