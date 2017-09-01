package go.pickapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import go.pickapp.Adapter.Search_restaurantAdapter;
import go.pickapp.Controller.Activity_indicator;
import go.pickapp.Controller.Config;
import go.pickapp.Controller.Connection;
import go.pickapp.Controller.DialogBox;
import go.pickapp.Controller.Edittext;
import go.pickapp.Controller.GPSTracker;
import go.pickapp.Model.Model_restaurant;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

import static go.pickapp.Activity.Filter_restaurantActivity.result_cusine;
import static go.pickapp.Activity.Filter_restaurantActivity.result_filter;

public class Search_restaurantActivity extends AppCompatActivity {

    RecyclerView lv_rest;
    ArrayList<Model_restaurant> array_restaurant = new ArrayList<>();
    Context context = this;
    Pref_Master pref;
    Activity_indicator obj_dialog;
    Edittext search_res;
    ImageView img_back;
    GPSTracker gpsTracker;
    Search_restaurantAdapter adapter;
    ImageView img_filter;
    String Cusine = "";
    String Filter_new = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_restaurant);

        pref = new Pref_Master(context);
        gpsTracker = new GPSTracker(context);
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            Cusine = extras.getString("Cusine");
            Filter_new = extras.getString("Filter");
        }


        search_res = (Edittext) findViewById(R.id.search_res);
        img_back = (ImageView) findViewById(R.id.img_back);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result_cusine = "";
                result_filter = "";
                Intent i = new Intent(Search_restaurantActivity.this, MainActivity.class);
                i.putExtra("fragmentcode", Config.Fragment_ID.home);
                startActivity(i);
                finish();
            }
        });
        img_filter = (ImageView) findViewById(R.id.img_filter);
        img_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Search_restaurantActivity.this, Filter_restaurantActivity.class);
                i.putExtra("Str", search_res.getText().toString());
                startActivity(i);
            }
        });

        lv_rest = (RecyclerView) findViewById(R.id.lv_rest);
        int numberOfColumns = 1;
        lv_rest.setLayoutManager(new GridLayoutManager(context, numberOfColumns));
        searchonmap();

        adapter = new Search_restaurantAdapter(context, array_restaurant, pref);
        lv_rest.setAdapter(adapter);

        search_res.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchonmap();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        result_cusine = "";
        result_filter = "";
        Intent i = new Intent(Search_restaurantActivity.this, MainActivity.class);
        i.putExtra("fragmentcode", Config.Fragment_ID.home);
        startActivity(i);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchonmap();
    }

    public void searchonmap() {

        //obj_dialog.show();
        String url = Config.app_url + Config.searchonmap;
        JSONObject jobj_loginuser = new JSONObject();

        try {

            JSONObject jobj_row = new JSONObject();

            jobj_row.put("filterby", Filter_new);
            jobj_row.put("cusines", Cusine);
            jobj_row.put("searchstring", search_res.getText().toString());
            jobj_row.put("latitude", gpsTracker.getLatitude());
            jobj_row.put("longitude", gpsTracker.getLongitude());


            JSONArray jarray_loginuser = new JSONArray();
            jarray_loginuser.put(jobj_row);

            jobj_loginuser.put("searchonmap", jarray_loginuser);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final Map<String, String> params = new HashMap<>();
        params.put("data", "" + jobj_loginuser.toString().replaceAll("\\\\", ""));
        Log.e("vikas_request", ":" + params.toString());


        Map<String, String> header = new HashMap<>();
        header.put("apikey", Config.headkey);
        header.put("username", Config.headunm);
        header.put("userlanguage", pref.getLanguage());


        Response.Listener<String> lis_pat = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                //obj_dialog.dismiss();

                try {
                    JSONObject jobj = new JSONObject(response);
                    String res_flag = jobj.getString("status");

                    if (res_flag.equals("200")) {
                        obj_dialog.dismiss();
                        JSONArray jsonarray = new JSONArray(jobj.getString("data"));

                        array_restaurant.clear();
                        for (int i = 0; i < jsonarray.length(); i++) {
                            Model_restaurant model = new Model_restaurant();
                            JSONObject jobj1 = jsonarray.getJSONObject(i);

                            model.setMaster_id(jobj1.getString("masterid"));
                            model.setMaster_name(jobj1.getString("mastername"));
                            model.setAreaarraycount(jobj1.getString("areaarraycount"));
                            model.setLogo(jobj1.getString("logo"));


                            JSONArray jarray = new JSONArray(jobj1.getString("areaarray"));

                            for (int j = 0; j < jarray.length(); j++) {
                                JSONObject jsonObject = jarray.getJSONObject(j);

                                model.setRes_id(jsonObject.getString("restid"));
                                model.setCity(jsonObject.getString("areaid"));
                                model.setCity_name(jsonObject.getString("areanm"));
                                model.setChild_master_id(jsonObject.getString("masterid"));
                            }


                            array_restaurant.add(model);

                            adapter.notifyDataSetChanged();

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
}
