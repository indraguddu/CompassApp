package com.shyam.compassapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.shyam.compassapp.util.PermissionUtil;

public class MainActivity extends AppCompatActivity {
    private static final int INITIAL_REQUEST = 13;


    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);




        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED

            ) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setMessage("This app cannot work without the location permission");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Grant permission",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ActivityCompat.requestPermissions(MainActivity.this, INITIAL_PERMS, INITIAL_REQUEST);
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                if (!MainActivity.this.isFinishing()) {
                    alert11.show();
                }
            } else {


                    gotPermissions();


            }
        } else {

                gotPermissions();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case INITIAL_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (PermissionUtil.verifyPermissions(grantResults)) {


                        gotPermissions();



                } else {

                    requestAllPermission();
                }
            }
        }
    }
    private void gotPermissions()
    {
        Intent in_log = new Intent(MainActivity.this, CompassActivity.class);
        in_log.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(in_log);
        overridePendingTransition(R.anim.enter, R.anim.exit);

    }

    private void requestAllPermission()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)


        ) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
            builder1.setMessage("This app cannot work without the Location permission");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Grant permission",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Log.e("inside","request");
                        }
                    });
            AlertDialog alert11 = builder1.create();
            if (!MainActivity.this.isFinishing()) {
                alert11.show();
            }
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, INITIAL_PERMS, INITIAL_REQUEST);
        }
    }



    @Override
    protected void onResume() {
        super.onResume();

    }

}