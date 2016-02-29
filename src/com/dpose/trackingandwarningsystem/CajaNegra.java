package com.dpose.trackingandwarningsystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.telephony.SmsManager;

public class CajaNegra extends Service implements SensorEventListener {

	public LocationManager locationManager;
	public MyLocationListener listener;
	public MyLocationListener sms;
	SensorManager manager;
	boolean warn;
	final int X = 0;
    final int Y = 1;
    final int Z = 2;
    float gforcex;
    float gforcey;
    float gforcez;
    float gforce;
    double latitud;
	double longitud;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		warn = comprobarFichero("cajanegra");
		if(warn){
			File file = new File("cajanegra");
			file.delete();
		}
		
		//Obtenemos los "manager" de los sensores del movil
		manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		//Miramos a ver si hay por lo menos uno de tipo ACCELEROMETRO
		if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) {
			//Si es el caso, obtenemos el primero de la lista
			Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
			//Le definimos como listener "this" (nuestra clase) el sensor que hemos obtenido, y le definimos una frequencia SENSOR_DELAY_GAME
			if (!manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)) {
				//Aqui podemos mostrar un mensaje de error en caso de que no se pueda definir el listener
			}
		}

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		listener = new MyLocationListener();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		locationManager.removeUpdates(listener);
	    manager.unregisterListener(this);
		super.onDestroy();
	}

	public boolean comprobarFichero(String filename) {

		try{
			BufferedReader file = new BufferedReader(
					new InputStreamReader(openFileInput(filename))
					);
			try {
				file.close();
			} catch (IOException e) {
				Log.e("CajaNegra", "Error al cerrar fichero en comprobación");
			}
			return true;
		}
		catch (FileNotFoundException e){
			Log.e("CajaNegra", "El fichero de posicionamiento no existe");
			return false;
		}

	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		
		sms = new MyLocationListener();

		gforce = anularGravedad(event);
		if (gforce > 1){
			sms.sendSMS("112", "Accidente de "+gforce+" G en las coordenadas: "+latitud + " " + longitud);
			Toast.makeText(this, "SMS enviado", Toast.LENGTH_SHORT).show();
		}

	}

	public float anularGravedad(SensorEvent event){

		float gravedadX = event.values[X];
		float gravedadY = event.values[Y];
		float gravedadZ = event.values[Z];
		float gravedad = (float) Math.sqrt((Math.pow(gravedadX, 2)) + (Math.pow(gravedadY, 2)) + (Math.pow(gravedadZ, 2)));
		float suma = Math.abs(gravedadX) + Math.abs(gravedadY) + Math.abs(gravedadZ);

		float auxX = (gravedadX/suma) * gravedad;
		float auxY = (gravedadY/suma) * gravedad;
		float auxZ = (gravedadZ/suma) * gravedad;

		float aceleracionX = gravedadX - auxX;
		float aceleracionY = gravedadY - auxY;
		float aceleracionZ = gravedadZ - auxZ;

		float aceleracion = (float) Math.sqrt((Math.pow(aceleracionX, 2)) + (Math.pow(aceleracionY, 2)) + (Math.pow(aceleracionZ, 2)));
		float gforce = aceleracion/gravedad;

		return gforce;
	}

	public class MyLocationListener implements LocationListener {

		public void onLocationChanged(final Location loc) {

			Time time = new Time(Time.getCurrentTimezone());
			time.setToNow();
			String fecha = time.monthDay + "-" + (time.month + 1) + "-" + time.year;
			String hora = time.hour + ":" + time.minute + ":" + time.second;
			latitud = loc.getLatitude();
			longitud = loc.getLongitude();
			String datos = "[" + fecha + " " + hora + "] Posición = " + latitud + " " + longitud;
			datos = datos + "\n";
			guardarCajaNegra(datos);

		}

		public void onProviderDisabled(String provider) {
			Toast.makeText( getApplicationContext(), "GPS Desactivado", Toast.LENGTH_SHORT ).show();
		}


		public void onProviderEnabled(String provider) {
			Toast.makeText( getApplicationContext(), "GPS Activado", Toast.LENGTH_SHORT).show();
		}


		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

		public void guardarCajaNegra(String datos){
			
			try
			{
				OutputStreamWriter file=
						new OutputStreamWriter(
								openFileOutput("cajanegra", Context.MODE_APPEND));
				file.write(datos);
				file.flush();
				file.close();
			}
			catch (Exception ex)
			{
				Log.e("CajaNegra", "Error al escribir fichero a memoria interna");
			}

		}
		
		public void sendSMS(String telf, String mensaje) {
			SmsManager sms = SmsManager.getDefault();
			sms.sendTextMessage(telf, null, mensaje, null, null);
		}
	}

}
