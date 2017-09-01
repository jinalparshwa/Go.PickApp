package go.pickapp.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import go.pickapp.Controller.Activity_indicator;
import go.pickapp.Controller.Config;
import go.pickapp.Controller.DirectionsJSONParser;
import go.pickapp.Controller.GPSTracker;
import go.pickapp.Controller.PermissionUtils;
import go.pickapp.Controller.Textview;
import go.pickapp.Model.Model_order;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

public class Order_confirmationActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {

    Context context = this;
    Pref_Master pref;
    GPSTracker gpsTracker;
    GoogleApiClient mGoogleApiClient;
    ImageView img_back;
    Activity_indicator obj_dialog;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    double lat;
    double lang;
    GoogleMap map;
    private boolean mPermissionDenied = false;
    LatLngBounds.Builder builder;
    ArrayList<LatLng> markerPoints;
    CameraUpdate cu;
    Model_order model;
    Textview txt_message, txt_order_num, txt_res_name, txt_payment_method, txt_total, txt_view_order, txt_home;
    String res_lat;
    String res_long;
    String Order_id = "";
    String Order_no = "";
    String payment_type = "";
    String final_total = "";
    String message = "";
    String val = "";
    String back = "";
    String res_name = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        pref = new Pref_Master(context);
        gpsTracker = new GPSTracker(context);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            Order_id = extras.getString("Order_id");
            Order_no = extras.getString("Order_no");
            payment_type = extras.getString("payment_type");
            final_total = extras.getString("final_total");
            res_lat = extras.getString("res_lat");
            res_long = extras.getString("res_long");
            message = extras.getString("message");
            val = extras.getString("val");
            back = extras.getString("back");
            res_name = extras.getString("res_name");

        }


        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (back.equals("1")) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("fragmentcode", Config.Fragment_ID.res_list);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("fragmentcode", Config.Fragment_ID.MY_order);
                    intent.putExtra("value", 1);
                    startActivity(intent);
                    finish();
                }
            }
        });
        MapsInitializer.initialize(getApplicationContext());
        markerPoints = new ArrayList<LatLng>();
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
        txt_message = (Textview) findViewById(R.id.txt_message);
        txt_order_num = (Textview) findViewById(R.id.txt_order_num);
        txt_res_name = (Textview) findViewById(R.id.txt_res_name);
        txt_payment_method = (Textview) findViewById(R.id.txt_payment_method);
        txt_total = (Textview) findViewById(R.id.txt_total);
        txt_view_order = (Textview) findViewById(R.id.txt_view_order);
        txt_home = (Textview) findViewById(R.id.txt_home);

        txt_message.setText(message);
        txt_order_num.setText(Order_no);
        txt_res_name.setText(res_name);
        txt_payment_method.setText(payment_type);
        txt_total.setText(final_total);

        txt_view_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Order_confirmationActivity.this, Order_detailsActivity.class);
                i.putExtra("Order_id", Order_id);
                i.putExtra("back", "1");
                i.putExtra("val", val);
                startActivity(i);
            }
        });

        txt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (back.equals("1")) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("fragmentcode", Config.Fragment_ID.home);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("fragmentcode", Config.Fragment_ID.MY_order);
                    intent.putExtra("value", 0);
                    startActivity(intent);
                    finish();
                }
            }
        });


        lat = gpsTracker.getLatitude();
        lang = gpsTracker.getLongitude();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (back.equals("1")) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("fragmentcode", Config.Fragment_ID.res_list);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("fragmentcode", Config.Fragment_ID.MY_order);
            intent.putExtra("value", 0);
            startActivity(intent);
            finish();
        }
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        map.setOnMyLocationButtonClickListener(this);
        //   enableMyLocation();
        requestLocationPermission();

        /*map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(lat, lang), 0));
*/
        CameraPosition cameraPosition = CameraPosition.builder().target(new LatLng(lat, lang)).zoom(16).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        MovetoPlace();

    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
    }

    private void MovetoPlace() {
        int height = 45;
        int width = 45;
        BitmapDrawable current = (BitmapDrawable) getResources().getDrawable(R.drawable.map_marker_gray);
        Bitmap b = current.getBitmap();

        BitmapDrawable map_marker = (BitmapDrawable) getResources().getDrawable(R.drawable.logo_map_marker);
        Bitmap b2 = map_marker.getBitmap();

        Bitmap c_marker = Bitmap.createScaledBitmap(b, width, height, false);
        Bitmap m_marker = Bitmap.createScaledBitmap(b2, 45, 45, false);
        List<Marker> markersList = new ArrayList<Marker>();
        Marker Cust_marker = map.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromBitmap(m_marker))
                .position(new LatLng(Double.parseDouble(res_lat), Double.parseDouble(res_long))));

        Marker Current_marker = map.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromBitmap(c_marker))
                .position(new LatLng(lat, lang)));

        markerPoints.add(new LatLng(lat, lang));
        markerPoints.add(new LatLng(Double.parseDouble(res_lat), Double.parseDouble(res_long)));
        markersList.add(Current_marker);
        markersList.add(Cust_marker);


        //  PolylineOptions polyline_options = new PolylineOptions()
        //        .add(new LatLng(lat, lang))
        //        .add(new LatLng(Double.parseDouble(Cust_lat), Double.parseDouble(Cust_lang))).color(Color.parseColor("#cb9d00")).width(3);
        // map.addPolyline(polyline_options);

        /**create for loop for get the latLngbuilder from the marker list*/
        builder = new LatLngBounds.Builder();
        for (Marker m : markersList) {
            builder.include(m.getPosition());
        }
        /**initialize the padding for map boundary*/
        int padding = 145;
        /**create the bounds from latlngBuilder to set into map camera*/
        LatLngBounds bounds = builder.build();
        /**create the camera with bounds and padding to set into map*/
        cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        /**call the map call back to know map is loaded or not*/
        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                /**set animated zoom camera into map*/
                map.animateCamera(cu);

            }
        });
        if (markerPoints.size() >= 2) {
            ShowRoute();
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            //  enableMyLocation();
            requestLocationPermission();
        } else {
            mPermissionDenied = true;
        }
    }

    private void ShowRoute() {

        LatLng origin = markerPoints.get(0);
        LatLng dest = markerPoints.get(1);
        // Getting URL to the Google Directions API
        String url = getDirectionsUrl(origin, dest);

        DownloadTask downloadTask = new DownloadTask();
        // Start downloading json data from Google Directions API
        downloadTask.execute(url);
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Waypoints
        String waypoints = "";
        for (int i = 2; i < markerPoints.size(); i++) {
            LatLng point = (LatLng) markerPoints.get(i);
            if (i == 2)
                waypoints = "waypoints=";
            waypoints += point.latitude + "," + point.longitude + "|";
        }


        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + waypoints;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {

        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();
            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {

//            ArrayList<LatLng> points = null;
//            PolylineOptions lineOptions = null;
            ArrayList<LatLng> points = new ArrayList<LatLng>();
            PolylineOptions lineOptions = new PolylineOptions();
            ;
            MarkerOptions markerOptions = new MarkerOptions();
            LatLng position = null;
            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {

                List<HashMap<String, String>> path = result.get(i);
                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    position = new LatLng(lat, lng);
                    points.add(position);
                }
                // Adding all the points in the route to LineOptions

                lineOptions.addAll(points);
                lineOptions.width(3);
                lineOptions.color(Color.RED);
            }

            Log.e("Points", ":" + points.size());
            // Drawing polyline in the Google Map for the i-th route
            if (points.size() != 0) map.addPolyline(lineOptions);//to avoid crash
        }
    }


}
