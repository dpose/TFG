package com.dpose.trackingandwarningsystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.EnumMap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class MisDatos extends TabActivity implements OnClickListener {

	TextView nombreC, nombreP;
	TextView apellidosC, apellidosP;
	TextView dniC, dniP;
	TextView direccionC, direccionP;
	TextView mar, mod, col, mat;
	TextView cia, poliza;
	TextView check;
	boolean warn;
	String datos;
	String datos_qr;
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
	View botonQR;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mis_datos);

		nombreC = (TextView) findViewById(R.id.nombre2C);
		apellidosC = (TextView) findViewById(R.id.apellidos2C);
		dniC = (TextView) findViewById(R.id.dni2C);
		direccionC = (TextView) findViewById(R.id.dir2C);

		nombreP = (TextView) findViewById(R.id.nombre2P);
		apellidosP = (TextView) findViewById(R.id.apellidos2P);
		dniP = (TextView) findViewById(R.id.dni2P);
		direccionP = (TextView) findViewById(R.id.dir2P);

		mar = (TextView) findViewById(R.id.marca2);
		mod = (TextView) findViewById(R.id.modelo2);
		col = (TextView) findViewById(R.id.color2);
		mat = (TextView) findViewById(R.id.matricula2);

		cia = (TextView) findViewById(R.id.aseguradora2);
		poliza = (TextView) findViewById(R.id.numpoliza2);
		
		botonQR = findViewById(R.id.boton_qr);
		botonQR.setOnClickListener(this);

		warn = comprobarFichero("misdatos");
		if(!warn){
			check = (TextView) findViewById(R.id.warn);
			check.setText("Rellene sus datos en la sección 'Editar Datos'");
		}else{
			leerFichero(check);
		}

		TabHost.TabSpec spec;
		TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
		tabs.setup();
		
		spec = tabs.newTabSpec("Tab 1");
		spec.setContent(R.id.tab1);
		spec.setIndicator("Text", null);
		tabs.addTab(spec);
		
		spec = tabs.newTabSpec("Tab 2");
		spec.setContent(R.id.tab2);
		spec.setIndicator("QR", null);
		tabs.addTab(spec);

		tabs.setCurrentTab(0);

	}

	public boolean comprobarFichero(String filename) {

		try{
            BufferedReader file = new BufferedReader(
                    new InputStreamReader(openFileInput(filename))
            );
            try {
				file.close();
			} catch (IOException e) {
				Log.e("MisDatos", "Error al cerrar fichero en comprobación");
			}
        return true;
        }
        catch (FileNotFoundException e){
        	Log.e("MisDatos", "El fichero de configuración no existe");
		return false;
		}

	}

	public void leerFichero(View view){
		
		String leido;
		datos_qr = "";
		
		try{
			BufferedReader file = new BufferedReader(
					new InputStreamReader(openFileInput("misdatos"))
					);

			leido = file.readLine();
			while(leido != null){
				datos_qr = datos_qr + leido + "\n";
				leido = file.readLine();
			}

			file.close();
		}
		catch (Exception ex)
		{
			Log.e("MisDatos", "Error al leer fichero desde memoria interna");
		}

		try{
			BufferedReader file = new BufferedReader(
					new InputStreamReader(openFileInput("misdatos"))
			);

			//datos = file.readLine();
			//datos_qr = file.readLine();
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==findViewById(R.id.boton_qr).getId()){
			encode(String.valueOf(datos_qr));
		}
	}
	
	private void encode(String uniqueID) {
		// TODO Auto-generated method stub
		BarcodeFormat barcodeFormat = BarcodeFormat.QR_CODE;

		int width0 = 500;
		int height0 = 500;

		int colorBack = 0xFF000000;
		int colorFront = 0xFFFFFFFF;

		QRCodeWriter writer = new QRCodeWriter();
		try
		{
			EnumMap<EncodeHintType, Object> hint = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
			hint.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			BitMatrix bitMatrix = writer.encode(uniqueID, barcodeFormat, width0, height0, hint);
			int width = bitMatrix.getWidth();
			int height = bitMatrix.getHeight();
			int[] pixels = new int[width * height];
			for (int y = 0; y < height; y++)
			{
				int offset = y * width;
				for (int x = 0; x < width; x++)
				{

					pixels[offset + x] = bitMatrix.get(x, y) ? colorBack : colorFront;
				}
			}

			Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
			ImageView imageview = (ImageView)findViewById(R.id.codigo_qr);
			imageview.setImageBitmap(bitmap);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
