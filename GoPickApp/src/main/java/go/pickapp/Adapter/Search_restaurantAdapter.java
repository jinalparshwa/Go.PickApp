package go.pickapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import go.pickapp.Activity.MainActivity;
import go.pickapp.Activity.RestaurantActivity;
import go.pickapp.Activity.Search_restaurantActivity;
import go.pickapp.Controller.Config;
import go.pickapp.Controller.Connection;
import go.pickapp.Controller.DialogBox;
import go.pickapp.Controller.Textview;
import go.pickapp.Controller.Textview_Bold;
import go.pickapp.Model.Model_cart;
import go.pickapp.Model.Model_city;
import go.pickapp.Model.Model_restaurant;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;


import static go.pickapp.Activity.Filter_restaurantActivity.result_cusine;
import static go.pickapp.Activity.Filter_restaurantActivity.result_filter;

/**
 * Created by Admin on 7/6/2017.
 */

public class Search_restaurantAdapter extends RecyclerView.Adapter<Search_restaurantAdapter.Viewholder> {

    Context context;
    ArrayList<Model_restaurant> array_res = new ArrayList<>();
    Pref_Master pref;
    String master_id;
    String master_name;
    String res_id;
    String city_id;
    ArrayList<Model_restaurant> arrayList = new ArrayList<>();


    public Search_restaurantAdapter(Context context, ArrayList<Model_restaurant> array_restaurant, Pref_Master pref) {
        this.context = context;
        this.array_res = array_restaurant;
        this.pref = pref;
    }

    @Override
    public Search_restaurantAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_search_res_list, parent, false);
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(Search_restaurantAdapter.Viewholder holder, int position) {

        final Model_restaurant model = array_res.get(position);
        Glide.with(context).load(model.getLogo()).into(holder.res_logo);
        holder.res_name.setText(model.getMaster_name());
        holder.ll_res_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result_cusine = "";
                result_filter = "";
                master_id = model.getMaster_id();
                master_name = model.getMaster_name();
                res_id = model.getRes_id();
                if (model.getAreaarraycount().equals("1")) {
                    Intent i = new Intent(context, RestaurantActivity.class);
                    pref.setRes_id(model.getRes_id());
                    pref.setCity_id(model.getCity());
                    pref.setCity_name(model.getCity_name());
                    context.startActivity(i);
                } else {
                    pref.setRes_id(model.getRes_id());
                    getmaster();

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return array_res.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        ImageView res_logo;
        Textview res_name;
        LinearLayout ll_res_list;

        public Viewholder(View view) {
            super(view);

            res_logo = (ImageView) view.findViewById(R.id.res_logo);
            res_name = (Textview) view.findViewById(R.id.res_name);
            ll_res_list = (LinearLayout) view.findViewById(R.id.ll_res_list);


        }
    }


    public void getmaster() {

        //obj_dialog.show();

        String url = Config.app_url + Config.getmasterarealist;
        JSONObject jobj_loginuser = new JSONObject();

        try {

            JSONObject jobj_row = new JSONObject();

            jobj_row.put("filterby", result_filter);
            jobj_row.put("cusines", result_cusine);
            jobj_row.put("masterid", master_id);


            JSONArray jarray_loginuser = new JSONArray();
            jarray_loginuser.put(jobj_row);

            jobj_loginuser.put("getmasterarealist", jarray_loginuser);
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
                // obj_dialog.dismiss();

                try {
                    JSONObject jobj = new JSONObject(response);
                    String res_flag = jobj.getString("status");
                    if (res_flag.equals("200")) {
                        if (jobj.has("data")) {
                            JSONArray jarray = new JSONArray(jobj.getString("data"));

                            arrayList.clear();
                            Model_restaurant model1 = new Model_restaurant();
                            model1.setCity("-1");
                            model1.setCity_name("Select a city");
                            arrayList.add(model1);


                            for (int i = 0; i < jarray.length(); i++) {
                                Model_restaurant model = new Model_restaurant();
                                JSONObject jobj1 = jarray.getJSONObject(i);

                                model.setMaster_name(jobj1.getString("masterid"));
                                model.setRes_id(jobj1.getString("restid"));
                                model.setCity(jobj1.getString("areaid"));
                                model.setCity_name(jobj1.getString("areanm"));
                                arrayList.add(model);

                            }


                            LayoutInflater li = LayoutInflater.from(context);
                            View v = li.inflate(R.layout.dialog_city, null);
                            final android.support.v7.app.AlertDialog alert = new android.support.v7.app.AlertDialog.Builder(context).setView(v).show();
                            alert.setCancelable(true);
                            //  alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            Textview mast_name = (Textview) v.findViewById(R.id.mast_name);
                            final Spinner txt_city = (Spinner) v.findViewById(R.id.txt_city);
                            final Textview txt = (Textview) v.findViewById(R.id.txt);
                            Textview txt_ok = (Textview) v.findViewById(R.id.txt_ok);
                            Textview txt_cancel = (Textview) v.findViewById(R.id.txt_cancel);

                            mast_name.setText(master_name);
                            final ArrayList<String> city = new ArrayList<>();
                            city.clear();

                            for (Model_restaurant model : arrayList)
                                city.add(model.getCity_name());

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter(context, R.layout.spinner_textview, city);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_textview);
                            txt_city.setAdapter(dataAdapter);

                            Log.e("spinner_value", txt_city.getSelectedItem().toString());
                            txt.setText(txt_city.getSelectedItem().toString());

                            txt_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    txt.setText(txt_city.getSelectedItem().toString());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

                            txt_ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (txt_city.getSelectedItem().toString().equals("Select a city")) {
                                        txt.setText(R.string.Please_Select_city);
                                    } else {

                                        for (Model_restaurant model : arrayList) {
                                            if (model.getCity_name().equals(txt_city.getSelectedItem())) {
                                                city_id = "" + model.getCity();


                                            }
                                        }

                                        Intent i = new Intent(context, RestaurantActivity.class);
                                        pref.setRes_id(res_id);
                                        pref.setCity_id(city_id);
                                        pref.setCity_name(txt.getText().toString());
                                        context.startActivity(i);

                                    }
                                }
                            });

                            txt_cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alert.dismiss();
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
                //obj_dialog.dismiss();
            }
        };
        Connection.postconnection(url, param, header, context, lis_res, lis_error);

    }


}
