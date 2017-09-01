package go.pickapp.Fragments;

import android.content.Context;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import go.pickapp.Adapter.DealAdapter;
import go.pickapp.Adapter.Order_existing;
import go.pickapp.Controller.Activity_indicator;
import go.pickapp.Controller.Config;
import go.pickapp.Controller.Connection;
import go.pickapp.Controller.DialogBox;
import go.pickapp.Controller.Textview;
import go.pickapp.Model.Model_deal;
import go.pickapp.Model.Model_order;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

/**
 * Created by Admin on 6/8/2017.
 */

public class Deals extends Fragment {

    Activity_indicator obj_dialog;
    Context context;
    Pref_Master pref;
    RecyclerView recycle_deals;
    DealAdapter adapter;
    ArrayList<Model_deal>array_deal=new ArrayList<>();
    Textview notext;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.deals_fragment, container, false);
        context = getActivity();
        pref = new Pref_Master(context);
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        notext = (Textview) v.findViewById(R.id.notext);
        recycle_deals = (RecyclerView) v.findViewById(R.id.recycle_deals);
        int numberOfColumns = 1;
        recycle_deals.setLayoutManager(new GridLayoutManager(context, numberOfColumns));
        adapter = new DealAdapter(getActivity(), array_deal, pref);
        recycle_deals.setAdapter(adapter);
        get_deals();
        return v;
    }

    public void get_deals() {

        obj_dialog.show();

        String url = Config.app_url + Config.Getdeals;
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

                        array_deal.clear();

                        for (int i = 0; i < jarray.length(); i++) {
                            Model_deal model = new Model_deal();
                            JSONObject jobj1 = jarray.getJSONObject(i);
                            model.setRes_id(jobj1.getString("restid"));
                            model.setName(jobj1.getString("dealname"));
                            model.setCode(jobj1.getString("couponcode"));
                            model.setDiscount(jobj1.getString("coupondiscount"));
                            model.setStatement(jobj1.getString("dealstatement"));
                            model.setImage(jobj1.getString("image"));
                            array_deal.add(model);

                        }
                        adapter.notifyDataSetChanged();
                        if (array_deal.size() == 0) {
                            notext.setVisibility(View.VISIBLE);
                            recycle_deals.setVisibility(View.INVISIBLE);
                        } else {
                            notext.setVisibility(View.INVISIBLE);
                            recycle_deals.setVisibility(View.VISIBLE);
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
        Connection.postconnection(url, param, header, context, lis_res, lis_error);

    }


}
