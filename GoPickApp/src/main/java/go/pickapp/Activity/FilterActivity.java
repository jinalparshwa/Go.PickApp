package go.pickapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import go.pickapp.Adapter.CusineAdapter;
import go.pickapp.Adapter.FilterbyAdapter;
import go.pickapp.Controller.Activity_indicator;
import go.pickapp.Controller.Config;
import go.pickapp.Controller.Connection;
import go.pickapp.Controller.DialogBox;
import go.pickapp.Controller.Textview;
import go.pickapp.Interface.filter_data;
import go.pickapp.Model.Model_Cusine_filter;
import go.pickapp.Model.Model_filter;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

public class FilterActivity extends AppCompatActivity implements filter_data {

    ImageView img_back;
    RecyclerView recycle_cusine, recycle_filter;
    Context context = this;
    Pref_Master pref;
    CusineAdapter adapter;
    FilterbyAdapter filteradapter;
    ArrayList<Model_Cusine_filter> array_cusine = new ArrayList<>();
    ArrayList<Model_filter> array_filter = new ArrayList<>();
    String city = "";
    RelativeLayout rr_apply;
    Activity_indicator obj_dialog;
    Textview txt_clear;
    SQLiteDatabase db;
    String str = "";
    ArrayList<String> name_arraylist = new ArrayList<>();
    ArrayList<String> name_arraylist_filter = new ArrayList<>();
    public static String filter_new = "";
    public static String cusine_new = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        pref = new Pref_Master(context);
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        Intent i = getIntent();
        str = i.getStringExtra("Str");


        city = pref.getStr_city_id();
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MainActivity.class);
                i.putExtra("fragmentcode", Config.Fragment_ID.res_list);
                startActivity(i);
                finish();
            }
        });

        txt_clear = (Textview) findViewById(R.id.txt_clear);
        txt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                filter_new = "";
                cusine_new = "";
                Intent i = new Intent(FilterActivity.this, FilterActivity.class);
                startActivity(i);
                finish();

            }
        });

        recycle_cusine = (RecyclerView) findViewById(R.id.recycle_cusine);
        int numberOfColumns = 1;
        recycle_cusine.setLayoutManager(new GridLayoutManager(context, numberOfColumns));
        // get_cuisine();
        get_cuisine_one();

        adapter = new CusineAdapter(context, array_cusine, this, db, pref);
        recycle_cusine.setAdapter(adapter);

        recycle_filter = (RecyclerView) findViewById(R.id.recycle_filter);
        int column = 1;
        recycle_filter.setLayoutManager(new GridLayoutManager(context, column));
        //get_filter();

        filteradapter = new FilterbyAdapter(context, array_filter, this);
        recycle_filter.setAdapter(filteradapter);

        rr_apply = (RelativeLayout) findViewById(R.id.rr_apply);
        rr_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FilterActivity.this, MainActivity.class);
                i.putExtra("fragmentcode", Config.Fragment_ID.res_list);
                i.putExtra("cusine_new", cusine_new);
                i.putExtra("filter_new", filter_new);
                startActivity(i);
                finish();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(context, MainActivity.class);
        i.putExtra("fragmentcode", Config.Fragment_ID.res_list);
        startActivity(i);
        finish();
    }

    public void get_cuisine_one() {

        obj_dialog.show();

        String url = Config.app_url + Config.getcuisineandroid;

        JSONObject jobj_loginuser = new JSONObject();
        obj_dialog.show();
        try {

            JSONObject jobj_row = new JSONObject();

            jobj_row.put("searchstring", str);
            jobj_row.put("cuisinearray", cusine_new);
            jobj_row.put("filterarray", filter_new);
            jobj_row.put("cityid", city);


            JSONArray jarray_loginuser = new JSONArray();
            jarray_loginuser.put(jobj_row);

            jobj_loginuser.put("getcuisine", jarray_loginuser);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HashMap<String, String> param = new HashMap<>();
        param.put("data", "" + jobj_loginuser.toString().replaceAll("\\\\", ""));
        Log.e("jinal", ":" + param.toString());

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

                        JSONObject jsonObject = jarray.getJSONObject(0);
                        JSONArray jsonArray = new JSONArray(jsonObject.getString("cuisinearray"));
                        JSONArray jsonArray_data = new JSONArray(jsonObject.getString("filterarray"));


                        array_cusine.clear();
                        array_filter.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            Model_Cusine_filter model = new Model_Cusine_filter();
                            JSONObject jobj1 = jsonArray.getJSONObject(i);
                            model.setCusine(jobj1.getString("cuisine"));
                            model.setColor(jobj1.getString("selectedcuisine"));

                            array_cusine.add(model);

                        }
                        adapter.notifyDataSetChanged();

                        for (int i = 0; i < jsonArray_data.length(); i++) {
                            Model_filter model = new Model_filter();
                            JSONObject jobj1 = jsonArray_data.getJSONObject(i);
                            model.setId(jobj1.getString("id"));
                            model.setValue(jobj1.getString("name"));
                            model.setColor(jobj1.getString("selectedfilter"));
                            array_filter.add(model);
                        }
                        filteradapter.notifyDataSetChanged();
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
        Connection.postconnection(url, param, header, context, lis_res, lis_error);

    }

    @Override
    public void mydata(Model_Cusine_filter model) {
        Log.e("position", ":" + model.getId());
        Log.e("name", ":" + model.getName());
        Log.e("selcted", ":" + model.isSelected());

        if (model.isSelected() == true) {
            name_arraylist.add(model.getName());
        } else {
            name_arraylist.remove(model.getName());
        }

        StringBuilder builder = new StringBuilder();
        String build = "";
        for (int i = 0; i < name_arraylist.size(); i++) {
            if (i != (name_arraylist.size() - 1)) {
                build = "" + name_arraylist.get(i) + ",";
                Log.e("if_cusine", build);
            } else {
                build = "" + name_arraylist.get(i);
                Log.e("Else_cusine", build);
            }
            Log.e("build", build);
            builder.append(build);
            cusine_new = builder.toString();
            Log.e("result", cusine_new);
        }

    }

    @Override
    public void mydata_filter(Model_filter model) {
        Log.e("position", ":" + model.getId());
        Log.e("name", ":" + model.getValue());
        Log.e("selcted", ":" + model.isSelected());

        if (model.isSelected() == true) {
            name_arraylist_filter.add(model.getId());
        } else {
            name_arraylist_filter.remove(model.getId());
        }

        StringBuilder builder = new StringBuilder();
        String build = "";
        for (int i = 0; i < name_arraylist_filter.size(); i++) {
            if (i != (name_arraylist_filter.size() - 1)) {
                build = "" + name_arraylist_filter.get(i) + ",";
                Log.e("if_filter", build);
            } else {
                build = "" + name_arraylist_filter.get(i);
                Log.e("Else_filter", build);
            }
            Log.e("build_filter", build);
            builder.append(build);
            filter_new = builder.toString();
            Log.e("result_filter", filter_new);
        }
    }

}
