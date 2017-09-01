package go.pickapp.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.Locale;

import go.pickapp.Controller.Config;
import go.pickapp.Controller.Connection;
import go.pickapp.Controller.DialogBox;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

public class SplashActivity extends AppCompatActivity {

    ImageView img_logo;
    Pref_Master pref;
    Context context = this;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    Boolean GpsStatus;
    private static int SPLASH_TIME_OUT = 1500;
    String abc = "";
    private GoogleApiClient googleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        pref = new Pref_Master(context);
        //  showPermiosssionn();

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//
//        if (GpsStatus == false) {
//            Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            startActivity(i);
//        }
        img_logo = (ImageView) findViewById(R.id.img_logo);

        Animation RightSwipe = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fade_in);
        img_logo.startAnimation(RightSwipe);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pref = new Pref_Master(context);

//                if (pref.getLanguage().equals("")) {
//                    Log.e("abc", "" + pref.getLanguage());
//                    if (Locale.getDefault().getLanguage().equals("en")) {
//                        pref.setLanguage("en");
//                        abc = (pref.getLanguage());
//                    } else {
//                        pref.setLanguage("ar");
//                        abc = (pref.getLanguage());
//                    }
//                } else {
//
//                    Log.e("def", "" + pref.getLanguage());
//                    new Config().Change_Language(context, pref.getLanguage().equals("ar") ? "1" : "0");
//                }

                if (GpsStatus == false) {
                    // startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1);
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    Log.e("language", pref.getLanguage());
                    if (pref.getLanguage().equals("")) {
                        Intent intent = new Intent(SplashActivity.this, Choose_Language.class);
                        startActivity(intent);
                        finish();


                    } else {
                        abc = (pref.getLanguage());
                        new Config().Change_Language(context, pref.getLanguage().equals("ar") ? "1" : "0");
                        pref.setLanguage(abc);
                        Intent i = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                }


            }

        }, SPLASH_TIME_OUT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("helloooo", "OnActivity Result...");
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == REQUEST_LOCATION) {
            switch (requestCode) {
                case REQUEST_LOCATION:
                    //super.onCreate(inst);
                    // super.onResume();
                    //super.finish();
                    break;
            }
        }
    }

    public void showPermiosssionn() {

        if (Build.VERSION.SDK_INT >= 19) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.i("Hello", "Wasssss up");
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_SMS)) {
                    Log.e("YESS", "yes");
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                } else {
                    Log.e("NO", "no");
                    Log.i("Coming in this block", "Wasssss up");
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Checking the request code of our request
        if (requestCode == REQUEST_LOCATION) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                // Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

}
