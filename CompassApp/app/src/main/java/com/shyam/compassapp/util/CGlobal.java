package com.shyam.compassapp.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

public class CGlobal {
    private static CGlobal instance;
    private static double lat=0.0,lon=0.0;

    private CGlobal() {
    }

    public static synchronized CGlobal getInstance() {
        if (instance == null) {
            instance = new CGlobal();
        }
        return instance;
    }

    public void turnGPSOn1(final Activity activity, GoogleApiClient googleApiClient) {

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);


        builder.setAlwaysShow(true);


        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.d("Block","1");
                       // getLastKnownLocation();

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.d("Block","2");

                        try {

                            status.startResolutionForResult(
                                    activity, 1001);
                        } catch (IntentSender.SendIntentException e) {

                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.d("Block","3");

                        break;
                }
            }
        });
    }


}
