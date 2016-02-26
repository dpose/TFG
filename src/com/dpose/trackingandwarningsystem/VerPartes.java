package com.dpose.trackingandwarningsystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class VerPartes extends Activity {
	
	TextView contenido;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ver_partes);
		
		String file = getIntent().getExtras().getSerializable("filename").toString();
		String datos = leerPartes(file);
		
		contenido = (TextView)findViewById(R.id.contenido);
		contenido.setText(datos);
		
	}
	
	public String leerPartes(String filename){

		String leido;
		String datos = "";

		try{
			BufferedReader file = new BufferedReader(
					new InputStreamReader(openFileInput(filename))
					);

			leido = file.readLine();
			while(leido != null){
				datos = datos + leido + "\n";
				leido = file.readLine();
			}

			file.close();
		}
		catch (Exception ex)
		{
			Log.e("MostrarPartes", "Error al leer fichero desde memoria interna");
		}

		return datos;
	}
	
}
