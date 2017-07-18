package com.example.rxk152130.hackutd;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by rxk152130 on 3/4/2017.
 */

public final class GPSTracker1 implements LocationListener {

    private final Context mContext;

    public boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;

    Location location;
    double latitude;
    double longitude;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;
    private static final long MIN_TIME_BW_UPDATES = 1;

    protected LocationManager locationManager;

    public GPSTracker1(Context context){
        this.mContext = context;
    }

    public Location getLocation() {
        try{
            locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            Log.v("isGPSEnabled","=" + isGPSEnabled);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            Log.v("isNetworkEnabled", "=" + isNetworkEnabled);

            if(isGPSEnabled == false && isNetworkEnabled == false){

            }else{
                this.canGetLocation = true;
                if(isNetworkEnabled) {
                    location = null;
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    Log.d("Network","Network");
                    if(locationManager != null){
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if(location != null){
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                if(isGPSEnabled) {
                    location = null;
                    if(location == null){
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled","GPS Enabled");
                        if(locationManager != null){
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if(location != null){
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }

            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return location;
    }

    public void stopUsingGPS() {
        if(locationManager != null){
            locationManager.removeUpdates(GPSTracker1.this);
        }
    }

    public double getLatitude() {
        if(location != null){
            latitude = location.getLatitude();
        }

        return latitude;
    }

    public double getLongitude() {
        if(location != null){
            latitude = location.getLongitude();
        }

        return longitude;
    }

    public boolean canGetLocation(){
        try {
            locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        } catch (SecurityException e){
            e.printStackTrace();
        }
            return isGPSEnabled;
    }



    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        alertDialog.setTitle("GPS Settings");
        alertDialog.setMessage("GPS is not enabled, Do you want to go to settings?");

        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which){
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        mContext.startActivity(intent);
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();

    }

    @Override
    public void onLocationChanged(Location location){
    }

    @Override
    public void onProviderDisabled(String provider){
        Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        //startActivity(i);
    }

    @Override
    public void onProviderEnabled(String provider){
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras){
    }

}
