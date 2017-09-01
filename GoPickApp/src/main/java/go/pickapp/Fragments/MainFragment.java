package go.pickapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import go.pickapp.Activity.FilterActivity;
import go.pickapp.Adapter.Restaurant_adapter;
import go.pickapp.Controller.Activity_indicator;
import go.pickapp.Controller.Config;
import go.pickapp.Controller.Connection;
import go.pickapp.Controller.DialogBox;
import go.pickapp.Controller.Edittext;
import go.pickapp.Controller.GPSTracker;
import go.pickapp.Controller.Textview;
import go.pickapp.JSON.JSON;
import go.pickapp.Model.Model_restaurant;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;


/**
 * Created by Admin on 5/11/2017.
 */

public class MainFragment extends Fragment {

    RecyclerView restaurant_list;
    Restaurant_adapter adapter;
    Context context;
    Pref_Master pref;
    ArrayList<Model_restaurant> arraylist = new ArrayList<>();
    public static ArrayList<Model_restaurant> res_list = new ArrayList<>();
    String lat;
    String lang;
    ImageView filter;
    String city = "";
    Textview txt_no_data;
    Activity_indicator obj_dialog;
    // SearchView et_text;
    Edittext et_text;
    GPSTracker gpsTracker;
    String filter_new = "";
    String cusine_new = "";

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_list, container, false);
        context = getActivity();
        pref = new Pref_Master(context);
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        gpsTracker = new GPSTracker(context);
        lat = String.valueOf(gpsTracker.getLatitude());
        lang = String.valueOf(gpsTracker.getLongitude());

        Bundle extras = getActivity().getIntent().getExtras();

        if (extras != null) {
            filter_new = extras.getString("filter_new");
            cusine_new = extras.getString("cusine_new");
        }


        city = pref.getStr_city_id();

        restaurant_list = (RecyclerView) v.findViewById(R.id.restaurant_list);
        int numberOfColumns = 2;
        restaurant_list.setLayoutManager(new GridLayoutManager(context, numberOfColumns));
        adapter = new Restaurant_adapter(getActivity(), res_list, pref);
        restaurant_list.setAdapter(adapter);

        txt_no_data = (Textview) v.findViewById(R.id.txt_no_data);

        filter = (ImageView) v.findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), FilterActivity.class);
                i.putExtra("Str", et_text.getText().toString());
                context.startActivity(i);
            }
        });

        et_text = (Edittext) v.findViewById(R.id.et_text);

        et_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                arraylist.clear();
                Model_restaurant model_restaurant = new Model_restaurant();
                model_restaurant.setFilter(filter_new);
                model_restaurant.setSort("");
                model_restaurant.setCusines(cusine_new);
                model_restaurant.setCity(city);
                model_restaurant.setLatitude(lat);
                model_restaurant.setLongitude(lang);
                model_restaurant.setStatus("search");
                model_restaurant.setStr(et_text.getText().toString());
                arraylist.add(model_restaurant);
                get_restaurant_city();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        arraylist.clear();
        Model_restaurant model_restaurant = new Model_restaurant();
        model_restaurant.setFilter(filter_new);
        model_restaurant.setSort("");
        model_restaurant.setCusines(cusine_new);
        model_restaurant.setCity(city);
        model_restaurant.setLatitude(lat);
        model_restaurant.setLongitude(lang);
        model_restaurant.setStatus("other");
        model_restaurant.setStr("");
        arraylist.add(model_restaurant);
        get_restaurant_city();

        return v;
    }

    public void get_restaurant_city() {

        // obj_dialog.show();

        String url = Config.app_url + Config.Getrestaurantsbycity;
        String json = "";

        json = JSON.Restaurant(arraylist, pref, "getrestaurantsbycity");


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
                // obj_dialog.dismiss();

                try {
                    JSONObject jobj = new JSONObject(response);
                    String res_flag = jobj.getString("status");
                    if (res_flag.equals("200")) {
                        JSONArray jarray = new JSONArray(jobj.getString("data"));

                        res_list.clear();

                        for (int i = 0; i < jarray.length(); i++) {
                            Model_restaurant model = new Model_restaurant();
                            JSONObject jobj1 = jarray.getJSONObject(i);
                            model.setRes_id(jobj1.getString("restid"));
                            model.setRes_name(jobj1.getString("name"));
                            model.setDistance(jobj1.getString("distance"));
                            model.setDuration(jobj1.getString("duration"));
                            model.setPrepare_time(jobj1.getString("preparationtime"));
                            model.setRating(jobj1.getString("rating"));
                            model.setLogo(jobj1.getString("logo"));
                            model.setAvailability(jobj1.getString("availability"));
                            res_list.add(model);

                        }
                        adapter.notifyDataSetChanged();
                        if (res_list.size() == 0) {
                            txt_no_data.setVisibility(View.VISIBLE);
                            restaurant_list.setVisibility(View.INVISIBLE);
                        } else {
                            txt_no_data.setVisibility(View.INVISIBLE);
                            restaurant_list.setVisibility(View.VISIBLE);
                        }


                    } else {
                        DialogBox.setPopup(context, jobj.getString("msg").toString());

                    }
                    Log.e("Sizeee", ":" + res_list.size());

                } catch (Exception e) {
                    e.printStackTrace();
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
