package go.pickapp.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import go.pickapp.Controller.Activity_indicator;
import go.pickapp.Controller.Config;
import go.pickapp.Controller.Connection;
import go.pickapp.Controller.DialogBox;
import go.pickapp.Controller.GPSTracker;
import go.pickapp.Controller.Textview;
import go.pickapp.Model.Model_time_payment;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

public class Restaurant_detailsActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    Context context = this;
    Pref_Master pref;
    String res = "";
    ImageView res_image;
    Textview txt_res_name, txt_cuisine, txt_rating, txt_hours;
    ArrayList<Model_time_payment> array_time = new ArrayList<>();
    ArrayList<Model_time_payment> array_payment = new ArrayList<>();
    RelativeLayout rr_show_map;
    LinearLayout rr_map;
    GoogleMap googleMap;
    GoogleApiClient mGoogleApiClient;
    ImageView img_backkk;
    Textview payment_one, payment_two, payment_three;
    Textview no_payment, txt_review;
    LinearLayout ll_payment;
    Activity_indicator obj_dialog;
    String latitude = "";
    String longitude = "";
    GPSTracker gpsTracker;
    String mobile = "";
    ImageView img_call, img_review;
    private static final int REQUEST_LOCATION = 1;
    Double lat = 0.0;
    Double longg = 0.0;
    RelativeLayout rr_order_online;
    ImageView img_share;
    String shareurl = "";
    String image = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_details);
        pref = new Pref_Master(context);
        gpsTracker = new GPSTracker(context);
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        show_permission();


        res_image = (ImageView) findViewById(R.id.res_image);
        //hours_day = (Spinner) findViewById(R.id.hours_day);
        txt_res_name = (Textview) findViewById(R.id.txt_res_name);
        txt_cuisine = (Textview) findViewById(R.id.txt_cuisine);
        txt_rating = (Textview) findViewById(R.id.txt_rating);
        txt_hours = (Textview) findViewById(R.id.txt_hours);
        rr_show_map = (RelativeLayout) findViewById(R.id.rr_show_map);
        rr_map = (LinearLayout) findViewById(R.id.rr_map);
        img_backkk = (ImageView) findViewById(R.id.img_backkk);
        payment_one = (Textview) findViewById(R.id.payment_one);
        payment_two = (Textview) findViewById(R.id.payment_two);
        payment_three = (Textview) findViewById(R.id.payment_three);
        ll_payment = (LinearLayout) findViewById(R.id.ll_payment);
        no_payment = (Textview) findViewById(R.id.no_payment);
        txt_review = (Textview) findViewById(R.id.txt_review);
        img_call = (ImageView) findViewById(R.id.img_call);
        img_review = (ImageView) findViewById(R.id.img_review);
        rr_order_online = (RelativeLayout) findViewById(R.id.rr_order_online);
        img_share = (ImageView) findViewById(R.id.img_share);

        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.setType("text/plain");
//                String shareSubText = "WhatsApp - The Great Chat App";
//                String shareBodyText = "http://5.189.141.180/fos/restaurantdetail.php?r=1494940181";
//                shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubText);
//                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
//                startActivity(Intent.createChooser(shareIntent, "Share With"));

                final String appPackageName = context.getPackageName();
                Intent sendIntent = new Intent();
                // Uri imageUri = Uri.parse(image);
                sendIntent.setType("text/plain");
                sendIntent.setAction(Intent.ACTION_SEND);
                //sendIntent.putExtra(Intent.EXTRA_STREAM, String.valueOf(imageUri));
                sendIntent.putExtra(Intent.EXTRA_TEXT, shareurl);
                context.startActivity(sendIntent);

            }
        });
        rr_order_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, RestaurantActivity.class);
                startActivity(i);
                finish();
            }
        });

        img_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Restaurant_detailsActivity.this, ReviewActivity.class);
                startActivity(i);
                finish();
            }
        });

        img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 19) {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Log.i("Hello", "Wasssss up");
                        if (ActivityCompat.shouldShowRequestPermissionRationale(Restaurant_detailsActivity.this, Manifest.permission.CALL_PHONE)) {
                            Log.e("YESS", "yes");
                            ActivityCompat.requestPermissions(Restaurant_detailsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_LOCATION);
                        } else {
                            Log.e("NO", "no");
                            Log.i("Coming in this block", "Wasssss up");
                            ActivityCompat.requestPermissions(Restaurant_detailsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_LOCATION);
                        }
                    }
                }
                Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+" + "965" + mobile));
                startActivity(i);
            }
        });

        img_backkk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(context, MainActivity.class);
//                i.putExtra("fragmentcode", Config.Fragment_ID.Restaurant);
//                startActivity(i);
                finish();

            }
        });

        rr_show_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rr_map.setVisibility(View.VISIBLE);
            }
        });

        res = pref.getStr_res_id();
        Log.e("jinal", res);
        get_restaurant_details();

    }

    public void show_permission() {
        if (Build.VERSION.SDK_INT >= 19) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                Log.i("Hello", "Wasssss up");
                if (ActivityCompat.shouldShowRequestPermissionRationale(Restaurant_detailsActivity.this, Manifest.permission.CALL_PHONE)) {
                    Log.e("YESS", "yes");
                    ActivityCompat.requestPermissions(Restaurant_detailsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_LOCATION);
                } else {
                    Log.e("NO", "no");
                    Log.i("Coming in this block", "Wasssss up");
                    ActivityCompat.requestPermissions(Restaurant_detailsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_LOCATION);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        get_restaurant_details();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent i = new Intent(context, MainActivity.class);
//        i.putExtra("fragmentcode", Config.Fragment_ID.Restaurant);
//        startActivity(i);
        finish();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void get_restaurant_details() {

        obj_dialog.show();

        String url = Config.app_url + Config.Getrestaurantdetails + "/" + res;
        final String json = "";


        HashMap<String, String> param = new HashMap<>();
        param.put("data", json);

        Map<String, String> header = new HashMap<>();
        header.put("apikey", Config.headkey);
        header.put("username", Config.headunm);
        header.put(Config.Language, pref.getLanguage());


        Response.Listener<String> lis_res = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                obj_dialog.dismiss();


                try {
                    JSONObject jobj = new JSONObject(response);
                    String res_flag = jobj.getString("status");
                    if (res_flag.equals("200")) {
                        JSONArray jarray = new JSONArray(jobj.getString("data"));

                        array_time.clear();
                        array_payment.clear();

                        for (int i = 0; i < jarray.length(); i++) {
                            JSONObject jobj1 = jarray.getJSONObject(i);
                            txt_res_name.setText(jobj1.getString("name").toUpperCase());
                            txt_cuisine.setText(jobj1.getString("cuisine"));
                            txt_rating.setText(jobj1.getString("rating"));
                            txt_hours.setText(jobj1.getString("currentdatetime"));
                            txt_review.setText("(" + jobj1.getString("review") + ")");
                            mobile = jobj1.getString("mobile");
                            latitude = jobj1.getString("latitude");
                            longitude = jobj1.getString("longitude");
                            shareurl = jobj1.getString("shareurl");

                            lat = Double.valueOf(latitude);
                            longg = Double.valueOf(longitude);

                            Log.e("Latitude", ":" + lat);
                            Log.e("Longitude", ":" + longg);

                            Glide.with(context).load(jobj1.getString("logo")).placeholder(R.drawable.res_bg_150).diskCacheStrategy(DiskCacheStrategy.ALL).into(res_image);
                            image = jobj1.getString("logo");


                            JSONArray timing = new JSONArray(jobj1.getString("timing"));
                            for (int j = 0; j < timing.length(); j++) {
                                Model_time_payment model = new Model_time_payment();
                                JSONObject jsonObject = timing.getJSONObject(j);
                                model.setHours(jsonObject.getString("hours"));
                                model.setDay(jsonObject.getString("day"));
                                array_time.add(model);
                                Log.e("Array_time", ":" + array_time.size());

                            }
                            JSONArray payment = new JSONArray(jobj1.getString("paymenttype"));
                            for (int k = 0; k < payment.length(); k++) {
                                Model_time_payment modell = new Model_time_payment();
                                JSONObject job = payment.getJSONObject(k);
                                modell.setPayment_type(job.getString("type"));
                                modell.setPayment_id(job.getString("id"));
                                array_payment.add(modell);
                                Log.e("Array_size", ":" + array_payment.size());


                            }

                            if (array_payment.size() == 0) {
                                no_payment.setVisibility(View.VISIBLE);
                                ll_payment.setVisibility(View.GONE);
                            } else {
                                no_payment.setVisibility(View.GONE);
                                ll_payment.setVisibility(View.VISIBLE);

                                payment_one.setText(array_payment.get(0).getPayment_type());
                                payment_two.setText(array_payment.get(1).getPayment_type());
                                payment_three.setText(array_payment.get(2).getPayment_type());

                            }


                            final ArrayList<String> time = new ArrayList<>();

                            time.clear();
                            for (Model_time_payment model : array_time) time.add(model.getHours());
                            Log.e("Time", ":" + time.size());

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter(context, R.layout.spinner_textview, time);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_textview);
                            //  hours_day.setAdapter(dataAdapter);

                            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapp);
                            mapFragment.getMapAsync(new OnMapReadyCallback() {
                                @Override
                                public void onMapReady(GoogleMap Map) {
                                    googleMap = Map;
                                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, longg), 17));
                                    // BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.app_icon);
                                    googleMap.addMarker(new MarkerOptions()
                                                    // .title("You are here")
                                                    .position(new LatLng(lat, longg))
                                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                                            // .position(new LatLng(model.getLatitude(),model.getLongitude()))
                                    );
                                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        if (ContextCompat.checkSelfPermission(context,
                                                Manifest.permission.ACCESS_FINE_LOCATION)
                                                == PackageManager.PERMISSION_GRANTED) {
                                            buildGoogleApiClient();
                                            googleMap.setMyLocationEnabled(true);
                                        }
                                    } else {
                                        buildGoogleApiClient();
                                        googleMap.setMyLocationEnabled(true);
                                    }
                                }
                            });

                        }

                    } else {
                        DialogBox.setPopup(context, jobj.getString("msg").toString());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        Response.ErrorListener lis_error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  signup.setClickable(true);
                obj_dialog.dismiss();
            }
        };
        Connection.getconnectionVolley(url, param, header, context, lis_res, lis_error);

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

}
