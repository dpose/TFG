package com.dpose.trackingandwarningsystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditarDatos extends Activity {

	EditText nombreC, nombreP;
	EditText apellidosC, apellidosP;
	EditText dniC, dniP;
	EditText direccionC, direccionP;
	EditText mar, mod, col, mat;
	EditText cia, poliza;
	EditText check;
	boolean warn;
	String datos;
	String nomC = "";
	String apeC = "";
	String num_dniC = "";
	String dirC = "";
	String nomP = "";
	String apeP = "";
	String num_dniP = "";
	String dirP = "";
	String marca = "";
	String modelo = "";
	String color = "";
	String matricula = "";
	String aseguradora = "";
	String num_poliza = "";
	int tam;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.editar_datos);
		
		nombreC = (EditText) findViewById(R.id.nombre2C);
		apellidosC = (EditText) findViewById(R.id.apellidos2C);
		dniC = (EditText) findViewById(R.id.dni2C);
		direccionC = (EditText) findViewById(R.id.dir2C);
		
		nombreP = (EditText) findViewById(R.id.nombre2P);
		apellidosP = (EditText) findViewById(R.id.apellidos2P);
		dniP = (EditText) findViewById(R.id.dni2P);
		direccionP = (EditText) findViewById(R.id.dir2P);
		
		mar = (EditText) findViewById(R.id.marca2);
		mod = (EditText) findViewById(R.id.modelo2);
		col = (EditText) findViewById(R.id.color2);
		mat = (EditText) findViewById(R.id.matricula2);
		
		cia = (EditText) findViewById(R.id.aseguradora2);
		poliza = (EditText) findViewById(R.id.numpoliza2);
		
		warn = comprobarFichero("misdatos");
		if(warn){
			leerFichero(check);
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
				Log.e("EditarDatos", "Error al cerrar fichero en comprobación");
			}
        return true;
        }
        catch (FileNotFoundException e){
        	Log.e("EditarDatos", "El fichero de configuración no existe");
		return false;
		}
		
	}
	
	public void leerFichero(View view){

		try{
			BufferedReader file = new BufferedReader(
					new InputStreamReader(openFileInput("misdatos"))
			);

			//datos = file.readLine();
			//tam = Integer.parseInt(datos.substring(0,1));
			//datos = datos .substring(1);
			//nomC = datos.substring(0,tam);
			nomC = file.readLine();
			//datos = datos .substring(tam);
			//tam = Integer.parseInt(datos.substring(0,1));
			//datos = datos .substring(1);
			//apeC = datos.substring(0,tam);
			apeC = file.readLine();
			//datos = datos .substring(tam);
			//tam = Integer.parseInt(datos.substring(0,1));
			//datos = datos .substring(1);
			//num_dniC = datos.substring(0,tam);
			num_dniC = file.readLine();
			//datos = datos .substring(tam);
			//tam = Integer.parseInt(datos.substring(0,1));
			//datos = datos .substring(1);
			//dirC = datos.substring(0,tam);
			dirC = file.readLine();
			//datos = datos .substring(tam);
			
			//tam = Integer.parseInt(datos.substring(0,1));
			//datos = datos .substring(1);
			//nomP = datos.substring(0,tam);
			nomP = file.readLine();
			//datos = datos .substring(tam);
			//tam = Integer.parseInt(datos.substring(0,1));
			//datos = datos .substring(1);
			//apeP = datos.substring(0,tam);
			apeP = file.readLine();
			//datos = datos .substring(tam);
			//tam = Integer.parseInt(datos.substring(0,1));
			//datos = datos .substring(1);
			//num_dniP = datos.substring(0,tam);
			num_dniP = file.readLine();
			//datos = datos .substring(tam);
			//tam = Integer.parseInt(datos.substring(0,1));
			//datos = datos .substring(1);
			//dirP = datos.substring(0,tam);
			dirP = file.readLine();
			//datos = datos .substring(tam);
			
			//tam = Integer.parseInt(datos.substring(0,1));
			//datos = datos .substring(1);
			//marca = datos.substring(0,tam);
			marca = file.readLine();
			//datos = datos .substring(tam);
			//tam = Integer.parseInt(datos.substring(0,1));
			//datos = datos .substring(1);
			//modelo = datos.substring(0,tam);
			modelo = file.readLine();
			//datos = datos .substring(tam);
			//tam = Integer.parseInt(datos.substring(0,1));
			//datos = datos .substring(1);
			//color = datos.substring(0,tam);
			color = file.readLine();
			//datos = datos .substring(tam);
			//tam = Integer.parseInt(datos.substring(0,1));
			//datos = datos .substring(1);
			//matricula = datos.substring(0,tam);
			matricula = file.readLine();
			//datos = datos .substring(tam);
			
			//tam = Integer.parseInt(datos.substring(0,1));
			//datos = datos .substring(1);
			//aseguradora = datos.substring(0,tam);
			aseguradora = file.readLine();
			//datos = datos .substring(tam);
			//tam = Integer.parseInt(datos.substring(0,1));
			//datos = datos .substring(1);
			//num_poliza = datos.substring(0,tam);
			num_poliza = file.readLine();
			//datos = datos .substring(tam);
			
			file.close();
		}
		catch (Exception ex)
		{
			Log.e("EditarDatos", "Error al leer fichero desde memoria interna");
		}

		nombreC.setText(nomC);
		apellidosC.setText(apeC);
		dniC.setText(num_dniC);
		direccionC.setText(dirC);
		
		nombreP.setText(nomP);
		apellidosP.setText(apeP);
		dniP.setText(num_dniP);
		direccionP.setText(dirP);
		
		mar.setText(marca);
		mod.setText(modelo);
		col.setText(color);
		mat.setText(matricula);
		
		cia.setText(aseguradora);
		poliza.setText(num_poliza);

	}
	
	public void guardarFichero(View view){
		
		nomC = nombreC.getText().toString().trim();
		apeC = apellidosC.getText().toString().trim();
		num_dniC = dniC.getText().toString().trim();
		dirC = direccionC.getText().toString().trim();
		
		nomP = nombreP.getText().toString().trim();
		apeP = apellidosP.getText().toString().trim();
		num_dniP = dniP.getText().toString().trim();
		dirP = direccionP.getText().toString().trim();
		
		marca = mar.getText().toString().trim();
		modelo = mod.getText().toString().trim();
		color = col.getText().toString().trim();
		matricula = mat.getText().toString().trim();
		
		aseguradora = cia.getText().toString().trim();
		num_poliza = poliza.getText().toString().trim();
		
		//tam = nomC.length();
		//datos = String.valueOf(tam);
		datos = /*datos + */nomC +"\n";
		//tam = apeC.length();
		//datos = datos + String.valueOf(tam);
		datos = datos + apeC + "\n";
		//tam = num_dniC.length();
		//datos = datos + String.valueOf(tam);
		datos = datos + num_dniC +"\n";
		//tam = dirC.length();
		//datos = datos + String.valueOf(tam);
		datos = datos + dirC +"\n";
		
		//tam = nomP.length();
		//datos = datos + String.valueOf(tam);
		datos = datos + nomP +"\n";
		//tam = apeP.length();
		//datos = datos + String.valueOf(tam);
		datos = datos + apeP +"\n";
		//tam = num_dniP.length();
		//datos = datos + String.valueOf(tam);
		datos = datos + num_dniP +"\n";
		//tam = dirP.length();
		//datos = datos + String.valueOf(tam);
		datos = datos + dirP +"\n";
		
		//tam = marca.length();
		//datos = datos + String.valueOf(tam);
		datos = datos + marca +"\n";
		//tam = modelo.length();
		//datos = datos + String.valueOf(tam);
		datos = datos + modelo +"\n";
		//tam = color.length();
		//datos = datos + String.valueOf(tam);
		datos = datos + color +"\n";
		//tam = matricula.length();
		//datos = datos + String.valueOf(tam);
		datos = datos + matricula +"\n";
		
		//tam = aseguradora.length();
		//datos = datos + String.valueOf(tam);
		datos = datos + aseguradora +"\n";
		//tam = num_poliza.length();
		//datos = datos + String.valueOf(tam);
		datos = datos + num_poliza +"\n";
		
		if(warn){
			File file = new File("misdatos");
			file.delete();
		}

		try
		{
			OutputStreamWriter file=
					new OutputStreamWriter(
							openFileOutput("misdatos", Context.MODE_PRIVATE));
			file.write(datos);
			file.flush();
			file.close();
		}
		catch (Exception ex)
		{
			Log.e("EditarDatos", "Error al escribir fichero a memoria interna");
		}

	}
}
