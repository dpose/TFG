package com.dpose.trackingandwarningsystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MostrarPartes extends Activity {

	private List<String> filelist = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mostrar_partes);

		//Defino la ruta donde busco los ficheros
		File file = new File(Environment.getDataDirectory().getPath()+"/data/com.dpose.trackingandwarningsystem/files");
		//Creo el array de tipo File con el contenido de la carpeta
		File[] files = file.listFiles();

		//Hacemos un Loop por cada fichero para extraer el nombre de cada uno
		for (int i = 0; i < files.length; i++){
			//Sacamos del array files un fichero
			File f = files[i];

			//Si es directorio...
			if(f.isFile() && f.getName().matches("[0-9]{1,2}-[0-9]{1,2}-[0-9]{4} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}")){
				filelist.add(f.getName());
			}

		}
		
		//Localizamos y llenamos la lista con el array
        ListView listapartes = (ListView) findViewById(R.id.listapartes);
        ArrayAdapter<String> listaficheros = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, filelist);
        listapartes.setAdapter(listaficheros);
 
        // Accion para realizar al pulsar sobre un item de la lista
        listapartes.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView adapter, View v, int position, long id){
                 
                //Devolvemos el resultado de la selección
                //Intent data = new Intent();
                //data.putExtra("filename", filelist.get(position));
                //setResult(RESULT_OK, data);
            	
            	String filename = filelist.get(position);
            	                
                Intent intent = new Intent(MostrarPartes.this, VerPartes.class);
                intent.putExtra("filename", filename);
				startActivity(intent);
                
            }
        });
	}
}
