package com.dpose.trackingandwarningsystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MenuPrincipal extends Activity {

	Button misdatos;
	Button editardatos;
	Button mostrarcajanegra;
	ToggleButton cajanegra;
	Button listapartes;
	Button darparte;
	Button salir;
	boolean warn;
	public int SCANNER_REQUEST_CODE = 123;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Quitamos barra de titulo de la aplicacion
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//Quitamos barra de notificaciones
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
			setContentView(R.layout.menu_principal_vertical);
		}else {        
			setContentView(R.layout.menu_principal_horizontal);
		}

		misdatos = (Button) findViewById(R.id.mostrardatos);
		misdatos.setOnClickListener(new View.OnClickListener(){
			public void onClick(View view){
				Intent intent = new Intent(MenuPrincipal.this, MisDatos.class);
				startActivity(intent);
			}
		});

		editardatos = (Button) findViewById(R.id.editardatos);
		editardatos.setOnClickListener(new View.OnClickListener(){
			public void onClick(View view){
				Intent intent = new Intent(MenuPrincipal.this, EditarDatos.class);
				startActivity(intent);
			}
		});

		mostrarcajanegra = (Button) findViewById(R.id.mostrarcajanegra);
		mostrarcajanegra.setOnClickListener(new View.OnClickListener(){
			public void onClick(View view){
				Intent intent = new Intent(MenuPrincipal.this, MostrarCajaNegra.class);
				startActivity(intent);
			}
		});

		cajanegra = (ToggleButton) findViewById(R.id.activarcajanegra);
		warn = comprobarFichero("estado");
		if(warn){
			boolean estado = leerEstado();
			cajanegra.setChecked(estado);
		}
		cajanegra.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					// The toggle is enabled
					guardarEstado("Encendido");
					startService(new Intent(getBaseContext(), CajaNegra.class));
				} else {
					// The toggle is disabled
					guardarEstado("Apagado");
					stopService(new Intent(getBaseContext(), CajaNegra.class));
				}
			}
		});

		listapartes = (Button) findViewById(R.id.listarpartes);
		listapartes.setOnClickListener(new View.OnClickListener(){
			public void onClick(View view){
				Intent intent = new Intent(MenuPrincipal.this, MostrarPartes.class);
				startActivity(intent);
			}
		});
		
		darparte = (Button) findViewById(R.id.darparte);
		darparte.setOnClickListener(new View.OnClickListener(){
			public void onClick(View view){
				//Intent intent = new Intent(MenuPrincipal.this, DarParte.class);
				//startActivity(intent);
				//Intent intent = new Intent("com.google.zxing.client.android.SCAN");
				//startActivityForResult(intent, 0);
				Intent intent = new Intent("com.google.zxing.client.android.SCAN");
				intent.putExtra("SCAN_MODE", "SCAN_MODE");
				startActivityForResult(intent, SCANNER_REQUEST_CODE);
			}
		});
		
		salir = (Button) findViewById(R.id.salir);
        salir.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View view){
        		finish();
        	}
        });

	}

	public boolean comprobarFichero(String filename) {

		try{
			BufferedReader file = new BufferedReader(
					new InputStreamReader(openFileInput(filename))
					);
			try {
				file.close();
			} catch (IOException e) {
				Log.e("MenuPrincipal", "Error al cerrar fichero en comprobación");
			}
			return true;
		}
		catch (FileNotFoundException e){
			Log.e("MenuPrincipal", "El fichero de posicionamiento no existe");
			return false;
		}

	}

	public void guardarEstado(String estado){

		try
		{
			OutputStreamWriter file=
					new OutputStreamWriter(
							openFileOutput("estado", Context.MODE_PRIVATE));
			file.write(estado);
			file.flush();
			file.close();
		}
		catch (Exception ex)
		{
			Log.e("MenuPrincipal", "Error al escribir fichero a memoria interna");
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
			Log.e("MenuPrincipal", "Error al leer fichero desde memoria interna");
		}

		return estado;
	}

	public String leerDatosPersonales(){

		String mis_datos = "Datos Personales\n\n";

		String leido;

		try{
			BufferedReader file = new BufferedReader(
					new InputStreamReader(openFileInput("misdatos"))
					);

			leido = file.readLine();
			while(leido != null){
				mis_datos = mis_datos + leido + "\n";
				leido = file.readLine();
			}

			file.close();
		}
		catch (Exception ex)
		{
			Log.e("MenuPrincipal", "Error al leer fichero desde memoria interna");
		}

		return mis_datos;
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
			Log.e("MenuPrincipal", "Error al leer fichero desde memoria interna");
		}

		return datos;
	}

	public void guardarParte(String datos_contrario){

		Time time = new Time(Time.getCurrentTimezone());
		time.setToNow();
		String fecha = time.monthDay + "-" + (time.month + 1) + "-" + time.year;
		String hora = time.hour + ":" + time.minute + ":" + time.second;

		String filename = fecha + " " + hora;

		String mis_datos = leerDatosPersonales();
		String datos_caja_negra = leerCajaNegra();

		String datos = mis_datos + "\n\n";
		datos = datos + "Datos del Contrario\n\n";
		datos = datos + datos_contrario + "\n\n";
		datos = datos + "Datos Caja Negra\n\n";
		datos = datos + datos_caja_negra;

		try
		{
			OutputStreamWriter file=
					new OutputStreamWriter(
							openFileOutput(filename, Context.MODE_PRIVATE));
			file.write(datos);
			file.flush();
			file.close();
		}
		catch (Exception ex)
		{
			Log.e("MenuPrincipal", "Error al escribir fichero a memoria interna");
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {

		if (requestCode == SCANNER_REQUEST_CODE) {
			// Handle scan intent
			if (resultCode == Activity.RESULT_OK) {
				// Handle successful scan
				String contents = intent.getStringExtra("SCAN_RESULT");

				guardarParte(contents);

				Toast toast1 = Toast.makeText(getApplicationContext(), "Parte guardado correctamente", Toast.LENGTH_SHORT);
				toast1.show();

			} else if (resultCode == Activity.RESULT_CANCELED) {
				// Handle cancel
			}
		} else {
			// Handle other intents
		}

	}
}
