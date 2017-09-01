package go.pickapp.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import go.pickapp.Activity.Choose_area;
import go.pickapp.Activity.RestaurantActivity;
import go.pickapp.Controller.Activity_indicator;
import go.pickapp.Controller.Config;
import go.pickapp.Controller.Connection;
import go.pickapp.Controller.DialogBox;
import go.pickapp.Controller.GPSTracker;
import go.pickapp.Controller.Textview;
import go.pickapp.Model.Model_restaurant;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

import static go.pickapp.Activity.MainActivity.value_new;

/**
 * Created by Admin on 7/1/2017.
 */

public class Location extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    Textview txt_manully;
    Context context;
    Boolean GpsStatus;
    LocationManager locationManager;
    private static final int REQUEST_LOCATION = 2;
    Pref_Master pref;
    GoogleApiClient mGoogleApiClient;
    GoogleMap googleMap;
    Activity_indicator obj_dialog;
    GPSTracker gpsTracker;
    ArrayList<Model_restaurant> Array_res = new ArrayList<>();
    private Marker customMarker;
    ImageView img_skip;
    ImageView img_filter;
    Dialog alertDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_location, container, false);
        context = getActivity();
        pref = new Pref_Master(context);
        gpsTracker = new GPSTracker(context);
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        txt_manully = (Textview) v.findViewById(R.id.txt_manully);

        img_filter = (ImageView) getActivity().findViewById(R.id.img_filter);
//        img_filter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.e("jinal_map__", "Hellolo");
//                value_new = 1;
//                searchonmapdistancefilter();
//            }
//        });
//        CheckGpsStatus();
//        showPermiosssion();
        txt_manully.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, Choose_area.class);
                pref.setBack(1);
                startActivity(i);
            }
        });

        if (value_new == 0) {
            get_restaurant();
        } else {
            searchonmapdistancefilter();
        }

        Init();

        if (pref.getStr_tips().equals("")) {
            tip_popup();
        }


        return v;
    }

    public void tip_popup() {
        alertDialog = new Dialog(context);
        alertDialog.setContentView(R.layout.first_tip);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);


        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER | Gravity.LEFT;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.MATCH_PARENT);


        alertDialog.show();

        RelativeLayout rr_first = (RelativeLayout) alertDialog.findViewById(R.id.rr_first);

        rr_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                final Dialog alertDialog_second = new Dialog(context);
                alertDialog_second.setContentView(R.layout.second_tip);
                alertDialog_second.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog_second.setCancelable(false);


                Window window = alertDialog_second.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();

                wlp.gravity = Gravity.CENTER | Gravity.RIGHT;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
                window.setAttributes(wlp);
                alertDialog_second.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

                alertDialog_second.show();

                RelativeLayout rr_second = (RelativeLayout) alertDialog_second.findViewById(R.id.rr_second);

                rr_second.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog_second.dismiss();
                        final Dialog alertDialog_third = new Dialog(context);
                        alertDialog_third.setContentView(R.layout.third);
                        alertDialog_third.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        alertDialog_third.setCancelable(false);


                        Window window = alertDialog_third.getWindow();
                        WindowManager.LayoutParams wlp = window.getAttributes();

                        wlp.gravity = Gravity.CENTER | Gravity.BOTTOM;
                        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
                        window.setAttributes(wlp);
                        alertDialog_third.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                        alertDialog_third.show();
                        RelativeLayout rr_third = (RelativeLayout) alertDialog_third.findViewById(R.id.rr_third);
                        rr_third.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog_third.dismiss();
                                pref.setTips("1");
                            }
                        });


                    }
                });

            }
        });

    }

    public void Init() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


//    Runnable api_f = new Runnable() {
//        @Override
//        public void run() {
//            Array_res.clear();
//
//        }
//    };


    public void showPermiosssion() {

        if (Build.VERSION.SDK_INT >= 19) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.i("Hello", "Wasssss up");
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.READ_SMS)) {
                    Log.e("YESS", "yes");
                    ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                } else {
                    Log.e("NO", "no");
                    Log.i("Coming in this block", "Wasssss up");
                    ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                }
            }
        }
    }

    public void CheckGpsStatus() {

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (GpsStatus == false) {
            Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(i);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()), 15));
        // BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.app_icon);
        googleMap.addMarker(new MarkerOptions()
                        .title("You are here")
                        .position(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                // .position(new LatLng(model.getLatitude(),model.getLongitude()))
        );
//        googleMap.addMarker(new MarkerOptions()
//                .position(new LatLng(latitude, longitude))
//                .title(store_name)
//                .icon(BitmapDescriptorFactory.fromBitmap(writeTextOnDrawable(R.drawable.logo_map_marker, ""))));

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

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                //&& pref.getStr_res_id().equals(Array_res.get(0).getRes_id())
                if (pref.getCart_count() == 0) {
                    for (int i = 0; i < Array_res.size(); i++) {

                        if (Array_res.get(i).getRes_name().equals(marker.getTitle())) {
                            Intent intent = new Intent(context, RestaurantActivity.class);
                            pref.setRes_id(Array_res.get(i).getRes_id());
                            pref.setCity_id(Array_res.get(i).getCity());
                            pref.setCity_name(Array_res.get(i).getCity_name());
                            pref.setBack_map("1");
                            startActivity(intent);
                        }

                    }
                } else {
                    for (int i = 0; i < Array_res.size(); i++) {
                        if (Array_res.get(i).getRes_name().equals(marker.getTitle())) {
                            if (!pref.getStr_res_id().equals(Array_res.get(i).getRes_id())) {
                                DialogBox.setremove_cart(context, getResources().getString(R.string.Remove_cart_popup));
                            } else {
                                Intent intent = new Intent(context, RestaurantActivity.class);
                                pref.setRes_id(Array_res.get(i).getRes_id());
                                pref.setCity_id(Array_res.get(i).getCity());
                                pref.setCity_name(Array_res.get(i).getCity_name());
                                pref.setBack_map("1");
                                startActivity(intent);
                            }
                        }

                    }

                }

            }
        });
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
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

    public void get_restaurant() {
        value_new = 0;

        String url = Config.app_url + Config.Getallrestaurantlist;
        JSONObject jobj_loginuser = new JSONObject();

        try {

            JSONObject jobj_row = new JSONObject();

            jobj_row.put("latitude", gpsTracker.getLatitude());
            jobj_row.put("longitude", gpsTracker.getLongitude());


            JSONArray jarray_loginuser = new JSONArray();
            jarray_loginuser.put(jobj_row);

            jobj_loginuser.put("getallrestaurantlist", jarray_loginuser);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final Map<String, String> params = new HashMap<>();
        params.put("data", "" + jobj_loginuser.toString().replaceAll("\\\\", ""));
        Log.e("vikas_request", ":" + params.toString());


        Map<String, String> header = new HashMap<>();
        header.put("apikey", Config.headkey);
        header.put("username", Config.headunm);
        header.put(Config.Language, pref.getLanguage());

        Response.Listener<String> lis_pat = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                //   obj_dialog.dismiss();

                try {
                    JSONObject jobj = new JSONObject(response);
                    String res_flag = jobj.getString("status");

                    if (res_flag.equals("200")) {

                        // obj_dialog.dismiss();
                        JSONArray jsonarray = new JSONArray(jobj.getString("data"));

                        Array_res.clear();
                        for (int i = 0; i < jsonarray.length(); i++) {
                            final Model_restaurant model = new Model_restaurant();
                            final JSONObject jobj1 = jsonarray.getJSONObject(i);

                            model.setRes_id(jobj1.getString("restid"));
                            model.setRes_name(jobj1.getString("storename"));
                            model.setLatitude(jobj1.getString("latitude"));
                            model.setLongitude(jobj1.getString("longitude"));
                            model.setCity(jobj1.getString("cityid"));
                            model.setCity_name(jobj1.getString("cityname"));
                            model.setLogo(jobj1.getString("logo"));

                            final double your_lat = Double.parseDouble(jobj1.getString("latitude"));
                            final double your_longt = Double.parseDouble(jobj1.getString("longitude"));


                            Array_res.add(model);

                            final View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);
                            ImageView marker_image = (ImageView) marker.findViewById(R.id.marker_image);
                            Picasso.with(context).load(jobj1.getString("logo")).into(marker_image, new Callback() {
                                @Override
                                public void onSuccess() {

                                    customMarker = googleMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(your_lat, your_longt))
                                            .title(model.getRes_name())
                                            .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(context, marker))));
                                }

                                @Override
                                public void onError() {

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
                Toast.makeText(context, "" + Config.Message, Toast.LENGTH_SHORT).show();
            }
        };
        Connection.postconnection(url, params, header, context, lis_pat, lis_error);
    }

    public void searchonmapdistancefilter() {

        //obj_dialog.show();
        String url = Config.app_url + Config.searchonmapdistancefilter;
        JSONObject jobj_loginuser = new JSONObject();
        obj_dialog.show();
        try {

            JSONObject jobj_row = new JSONObject();

            jobj_row.put("latitude", gpsTracker.getLatitude());
            jobj_row.put("longitude", gpsTracker.getLongitude());

//            jobj_row.put("latitude", 29.085976);
//            jobj_row.put("longitude", 48.034805);


            JSONArray jarray_loginuser = new JSONArray();
            jarray_loginuser.put(jobj_row);

            jobj_loginuser.put("searchonmapdistancefilter", jarray_loginuser);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final Map<String, String> params = new HashMap<>();
        params.put("data", "" + jobj_loginuser.toString().replaceAll("\\\\", ""));
        Log.e("vikas_request", ":" + params.toString());


        Map<String, String> header = new HashMap<>();
        header.put("apikey", Config.headkey);
        header.put("username", Config.headunm);
        header.put(Config.Language, pref.getLanguage());

        Response.Listener<String> lis_pat = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                obj_dialog.dismiss();

                try {
                    JSONObject jobj = new JSONObject(response);
                    String res_flag = jobj.getString("status");

                    if (res_flag.equals("200")) {
                        value_new = 0;
                        // obj_dialog.dismiss();
                        JSONArray jsonarray = new JSONArray(jobj.getString("data"));

                        Array_res.clear();
                        for (int i = 0; i < jsonarray.length(); i++) {
                            final Model_restaurant model = new Model_restaurant();
                            final JSONObject jobj1 = jsonarray.getJSONObject(i);

                            model.setRes_id(jobj1.getString("restid"));
                            model.setRes_name(jobj1.getString("storename"));
                            model.setLatitude(jobj1.getString("latitude"));
                            model.setLongitude(jobj1.getString("longitude"));
                            model.setCity(jobj1.getString("cityid"));
                            model.setCity_name(jobj1.getString("cityname"));
                            model.setLogo(jobj1.getString("logo"));

                            final double your_lat = Double.parseDouble(jobj1.getString("latitude"));
                            final double your_longt = Double.parseDouble(jobj1.getString("longitude"));


                            Array_res.add(model);

                            final View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);
                            ImageView marker_image = (ImageView) marker.findViewById(R.id.marker_image);
                            Picasso.with(context).load(jobj1.getString("logo")).into(marker_image, new Callback() {
                                @Override
                                public void onSuccess() {

                                    customMarker = googleMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(your_lat, your_longt))
                                            .title(model.getRes_name())
                                            .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(context, marker))));
                                }

                                @Override
                                public void onError() {

                                }
                            });


                        }
                    } else if (res_flag.equals("300")) {
                        value_new = 0;
                        DialogBox.setfilter_popup(context, jobj.getString("msg").toString());

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

                Toast.makeText(context, "" + Config.Message, Toast.LENGTH_SHORT).show();
            }
        };
        Connection.postconnection(url, params, header, context, lis_pat, lis_error);
    }

    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    private Bitmap writeTextOnDrawable(int drawableId, String text) {

        Bitmap bm = BitmapFactory.decodeResource(getResources(), drawableId).copy(Bitmap.Config.ARGB_8888, true);
        Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTypeface(tf);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(convertToPixels(context, 11));

        Rect textRect = new Rect();
        paint.getTextBounds(text, 0, text.length(), textRect);

        Canvas canvas = new Canvas(bm);

        //If the text is bigger than the canvas , reduce the font size
        if (textRect.width() >= (canvas.getWidth() - 4))     //the padding on either sides is considered as 4, so as to appropriately fit in the text
            paint.setTextSize(convertToPixels(context, 7));        //Scaling needs to be used for different dpi's

        //Calculate the positions
        int xPos = (canvas.getWidth() / 2) - 2;     //-2 is for regulating the x position offset
        //"- ((paint.descent() + paint.ascent()) / 2)" is the distance from the baseline to the center.
        int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
        canvas.drawText(text, xPos, yPos, paint);
        return bm;
    }

    public static int convertToPixels(Context context, int nDP) {
        final float conversionScale = context.getResources().getDisplayMetrics().density;
        return (int) ((nDP * conversionScale) + 0.5f);

    }


}
