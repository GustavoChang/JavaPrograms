package com.example.andperfms336.auxiliar;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;

public class GPS extends CountDownTimer {
	
	private static Context context;
	private static double latitude;
	private static double longitude;
	private static LocationManager locationManager;
	private static boolean executando;
	
	public GPS(Context context, long millisInFuture, long countDownInterval) {
		
		super(millisInFuture, countDownInterval);
		
		this.context = context;
		
		locationManager = (LocationManager)context.getSystemService(context.LOCATION_SERVICE);
	    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, locationListener);
	    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

	    GPS.executando = true;
	}
	
	private static LocationListener locationListener = new LocationListener() {
        
		public void onLocationChanged(Location location) {
			
			if(location != null) {
		
				GPS.setLatitude(location.getLatitude());
				GPS.setLongitude(location.getLongitude());
			
				//Toast.makeText(context,  latitude + " " + longitude, Toast.LENGTH_SHORT).show();
				//stopColeta();
			}
		}

		public void onProviderDisabled(String provider) {

		}

		public void onProviderEnabled(String provider) {
    
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
    
		}
    };
    
    public static void stopColeta() {
    	
        locationManager.removeUpdates(locationListener);  
        setExecutando(false);
    }

	@Override
	public void onTick(long millisUntilFinished) {
		System.out.println("--------------------------------------------------------------------------------------------------------------Coordenadas: " + GPS.getLatitude() + " " + GPS.getLongitude());
		//Toast.makeText(context,  "teste: " + millisUntilFinished/1000, Toast.LENGTH_SHORT).show();
		//Toast.makeText(context,  "Coordenadas: " + GPS.getLatitude() + " " + GPS.getLongitude(), Toast.LENGTH_SHORT).show();
	}

	public void onFinish() {
		System.out.println("lllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllonFinish");
		//Toast.makeText(context,  "Prosseguir: " + GPS.getLatitude() + " " + GPS.getLongitude(), Toast.LENGTH_SHORT).show();
		setExecutando(false);
		stopColeta();
	}
    
    public static double getLatitude() {
		return latitude;
	}

	public static void setLatitude(double latitude) {
		GPS.latitude = latitude;
	}

	public static double getLongitude() {
		return longitude;
	}

	public static void setLongitude(double longitude) {
		GPS.longitude = longitude;
	}

	public static boolean isExecutando() {
		return executando;
	}
	
	public static void setExecutando(boolean executando) {
		GPS.executando = executando;
	}
	
	public static boolean isHabilitado() {
		
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}
	
	public static void habilitarGps(Context context) {
		
		Intent intent = new Intent();
		intent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
		intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
		intent.setData(Uri.parse("3"));
		context.sendBroadcast(intent);
	}
}
