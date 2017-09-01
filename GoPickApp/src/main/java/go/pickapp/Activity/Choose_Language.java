package go.pickapp.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Locale;

import go.pickapp.Controller.Config;
import go.pickapp.Controller.Textview_Bold;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

public class Choose_Language extends AppCompatActivity {

    Textview_Bold txt_english, txt_arabic;
    Pref_Master pref;
    Context context = this;
    LocationManager locationManager;
    Boolean GpsStatus;
    private static final int REQUEST_LOCATION = 2;
    String languageToLoad = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose__language);
        pref = new Pref_Master(context);
        //showPermiosssion();


        txt_english = (Textview_Bold) findViewById(R.id.txt_english);
        txt_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                languageToLoad = "en";
                pref.setLanguage("en");
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
                Intent i = new Intent(Choose_Language.this, MainActivity.class);
                startActivity(i);
                finish();
                // }


            }
        });

        txt_arabic = (Textview_Bold) findViewById(R.id.txt_arabic);
        txt_arabic.setBackgroundColor(getResources().getColor(R.color.silver));
        txt_arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                txt_english.setBackgroundColor(getResources().getColor(R.color.silver));
                txt_arabic.setBackgroundColor(getResources().getColor(R.color.White));
                txt_arabic.setTextColor(getResources().getColor(R.color.lan_color));
                txt_english.setTextColor(getResources().getColor(R.color.Black));

                languageToLoad = "ar";
                pref.setLanguage("ar");
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
                Intent i = new Intent(Choose_Language.this, MainActivity.class);
                startActivity(i);
                finish();


            }
        });

    }

    public void showPermiosssion() {

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

}
