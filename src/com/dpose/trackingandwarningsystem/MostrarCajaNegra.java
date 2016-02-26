package com.dpose.trackingandwarningsystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MostrarCajaNegra extends Activity {

	boolean warn;
	TextView mostrarcajanegra;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mostrar_caja_negra);

		warn = comprobarFichero("cajanegra");
		if(!warn){
			Toast.makeText( getApplicationContext(), "No hay ningún recorrido almacenado", Toast.LENGTH_SHORT ).show();
		}else{
			boolean estado = leerEstado();
			if(estado){
				Toast.makeText( getApplicationContext(), "Desactive caja negra para visualizar el recorrido", Toast.LENGTH_SHORT ).show();
			}else{
				mostrarcajanegra = (TextView) findViewById(R.id.recorrido);
				String texto = leerCajaNegra();
				mostrarcajanegra.setText(texto);
			}
		}

	}

	public boolean comprobarFichero(String filename) {

		try{
			BufferedReader file = new BufferedReader(
					new InputStreamReader(openFileInput(filename))
					);
			try {
				file.close();
			} catch (IOException e) {
				Log.e("MostrarCajaNegra", "Error al cerrar fichero en comprobación");
			}
			return true;
		}
		catch (FileNotFoundException e){
			Log.e("MostrarCajaNegra", "El fichero de posicionamiento no existe");
			return false;
		}

	}

	public boolean leerEstado(){

		boolean estado = false;

		try{
			BufferedReader file = new BufferedReader(
					new InputStreamReader(openFileInput("estado"))
					);

			String leido = file.readLine();

			if(leido.equals("Encendido")){
				estado = true;
			}else{
				estado = false;
			}

			file.close();
		}
		catch (Exception ex)
		{
			Log.e("MostrarCajaNegra", "Error al leer fichero desde memoria interna");
		}

		return estado;
	}

	public String leerCajaNegra(){

		String datos = "";
		String leido;

		try{
			BufferedReader file = new BufferedReader(
					new InputStreamReader(openFileInput("cajanegra"))
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
			Log.e("MostrarCajaNegra", "Error al leer fichero desde memoria interna");
		}

		return datos;
	}
}
