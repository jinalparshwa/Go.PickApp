package go.pickapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import go.pickapp.Adapter.Expandable_cityAdapter;
import go.pickapp.Controller.Activity_indicator;
import go.pickapp.Controller.Config;
import go.pickapp.Controller.Connection;
import go.pickapp.Controller.Edittext;
import go.pickapp.Model.Model_city;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

import static go.pickapp.Adapter.CusineAdapter.build_cusine;
import static go.pickapp.Adapter.CusineAdapter.cusine;
import static go.pickapp.Adapter.CusineAdapter.cusine_position;

public class Choose_area extends AppCompatActivity {

    Expandable_cityAdapter cityAdapter;
    ExpandableListView lv_area;
    Pref_Master pref;
    Context context = this;
    ArrayList<Model_city.Data> array_city = new ArrayList<>();
    Edittext search_city;
    ImageView img_back;
    Activity_indicator obj_dialog;
    int width;
    DisplayMetrics metrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_area);
        pref = new Pref_Master(context);
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        search_city = (Edittext) findViewById(R.id.search_city);

        lv_area = (ExpandableListView) findViewById(R.id.lv_area);


        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;

        search_city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                search_city();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // if (pref.getBack() == 1) {
                Intent i = new Intent(Choose_area.this, MainActivity.class);
                startActivity(i);
                // finish();
//                } else {
//                    Intent i = new Intent(Choose_area.this, MainActivity.class);
//                    startActivity(i);
//                }
                overridePendingTransition(R.anim.slide_out_up, R.anim.slide_in_up);

            }
        });
        lv_area.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return false;
            }
        });
        lv_area.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                return false;
            }
        });


        getcity_area();


    }

    public int GetDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        if (pref.getBack() == 1) {
        Intent i = new Intent(Choose_area.this, MainActivity.class);
        startActivity(i);
//            finish();
//        } else {
//            Intent i = new Intent(Choose_area.this, MainActivity.class);
//            startActivity(i);
//        }


    }

    public void getcity_area() {

        obj_dialog.show();

        String url = Config.app_url + Config.Getcityarea;
        String json = "";


        HashMap<String, String> param = new HashMap<>();
        param.put("data", json);

        Map<String, String> header = new HashMap<>();
        header.put("apikey", Config.headkey);
        header.put("username", Config.headunm);
        //header.put(Config.Language, "en");
        header.put(Config.Language, pref.getLanguage());


        Response.Listener<String> lis_res = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                obj_dialog.dismiss();


                Gson gson = new Gson();
                Model_city model = gson.fromJson(response, Model_city.class);

                if (model != null) {
                    array_city.clear();

                    array_city.addAll(model.getData());

                    cityAdapter = new Expandable_cityAdapter(context, array_city, pref);
                    lv_area.setAdapter(cityAdapter);
                    lv_area.setIndicatorBounds(width - GetDipsFromPixel(60), width - GetDipsFromPixel(5));
                    cityAdapter.notifyDataSetChanged();
                    for (int i = 0; i < array_city.size(); i++) {
                        lv_area.expandGroup(i);
                    }
                    Log.e("Arry_list", ":" + array_city.size());


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

    public void search_city() {

        String url = Config.app_url + Config.Searchcityarea;
        JSONObject jobj_loginuser = new JSONObject();

        //obj_dialog.show();
        try {

            JSONObject jobj_row = new JSONObject();

            jobj_row.put("areanm", search_city.getText().toString());

            JSONArray jarray_loginuser = new JSONArray();
            jarray_loginuser.put(jobj_row);

            jobj_loginuser.put("searchcityarea", jarray_loginuser);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        HashMap<String, String> param = new HashMap<>();
        param.put("data", "" + jobj_loginuser.toString().replaceAll("\\\\", ""));
        Log.e("vikas_request", ":" + param.toString());

        Map<String, String> header = new HashMap<>();
        header.put("apikey", Config.headkey);
        header.put("username", Config.headunm);
        //header.put(Config.Language, "en");
        header.put(Config.Language, pref.getLanguage());


        Response.Listener<String> lis_res = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                // obj_dialog.dismiss();


                Gson gson = new Gson();
                Model_city model = gson.fromJson(response, Model_city.class);

                if (model != null) {

                    array_city.clear();

                    array_city.addAll(model.getData());

                    cityAdapter = new Expandable_cityAdapter(context, array_city, pref);
                    lv_area.setAdapter(cityAdapter);
                    lv_area.setIndicatorBounds(width - GetDipsFromPixel(60), width - GetDipsFromPixel(5));
                    cityAdapter.notifyDataSetChanged();
                    for (int i = 0; i < array_city.size(); i++) {
                        lv_area.expandGroup(i);
                    }
                    Log.e("Arry_list", ":" + array_city.size());


                }


            }
        };

        Response.ErrorListener lis_error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  signup.setClickable(true);
                // obj_dialog.dismiss();
            }
        };
        Connection.postconnection(url, param, header, context, lis_res, lis_error);

    }


}
