package go.pickapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import go.pickapp.Activity.MainActivity;
import go.pickapp.Adapter.Order_existing;
import go.pickapp.Controller.Activity_indicator;
import go.pickapp.Controller.Config;
import go.pickapp.Controller.Connection;
import go.pickapp.Controller.DialogBox;
import go.pickapp.Controller.Textview;
import go.pickapp.Model.Model_order;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

/**
 * Created by Admin on 5/31/2017.
 */

public class Existing_order extends Fragment {

    RecyclerView recycle_order;
    Order_existing adapter;
    Context context;
    Pref_Master pref;
    Activity_indicator obj_dialog;
    Textview notext;
    ArrayList<Model_order> arry_order = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.order_fragment, container, false);
        context = getActivity();
        pref = new Pref_Master(context);
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        recycle_order = (RecyclerView) v.findViewById(R.id.recycle_order);
        int numberOfColumns = 1;
        recycle_order.setLayoutManager(new GridLayoutManager(context, numberOfColumns));
        adapter = new Order_existing(Existing_order.this, getActivity(), arry_order, pref);
        recycle_order.setAdapter(adapter);
        get_Order_list();

        notext = (Textview) v.findViewById(R.id.notext);
        return v;
    }


    public void get_Order_list() {

        obj_dialog.show();

        String url = Config.app_url + Config.Getorderlist + "/" + pref.getUID() + "/" + 1;
        String json = "";


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

                        arry_order.clear();

                        for (int i = 0; i < jarray.length(); i++) {
                            Model_order model = new Model_order();
                            JSONObject jobj1 = jarray.getJSONObject(i);
                            model.setOrder_id(jobj1.getString("orderid"));
                            model.setOrder_no(jobj1.getString("orderno"));
                            model.setRes_name(jobj1.getString("restname"));
                            model.setDate(jobj1.getString("orderdate"));
                            model.setStatus(jobj1.getString("orderstatus"));
                            model.setStatus_name(jobj1.getString("orderstatusnm"));
                            model.setTotal(jobj1.getString("finaltotal"));
                            model.setCancel_status(jobj1.getString("cancelstatus"));
                            arry_order.add(model);

                        }
                        adapter.notifyDataSetChanged();
                        if (arry_order.size() == 0) {
                            notext.setVisibility(View.VISIBLE);
                            recycle_order.setVisibility(View.INVISIBLE);
                        } else {
                            notext.setVisibility(View.INVISIBLE);
                            recycle_order.setVisibility(View.VISIBLE);
                        }


                    } else {
                        DialogBox.setPopup(context, jobj.getString("msg").toString());

                    }
                    //  Log.e("Sizeee", ":" + res_list.size());

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

    public void Cancel_order(String order_id) {

        String url = Config.app_url + Config.Cancelorder;
        JSONObject jobj_loginuser = new JSONObject();
        obj_dialog.show();
        try {

            JSONObject jobj_row = new JSONObject();

            jobj_row.put("orderid", order_id);


            JSONArray jarray_loginuser = new JSONArray();
            jarray_loginuser.put(jobj_row);

            jobj_loginuser.put("cancelorder", jarray_loginuser);
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

        Response.Listener<String> lis_res = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("login_Response", " : " + response);
                obj_dialog.dismiss();

                try {
                    JSONObject jobj = new JSONObject(response);
                    String res_flag = jobj.getString("status");
                    if (res_flag.equals("200")) {
                        get_Order_list();
                        DialogBox.setPopup(context, jobj.getString("msg").toString());
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
                Log.e("response error", "" + error);
                obj_dialog.dismiss();
            }
        };

        Connection.postconnection(url, params, header, context, lis_res, lis_error);

    }


}
