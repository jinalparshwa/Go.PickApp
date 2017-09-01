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

import go.pickapp.Adapter.NotificationAdapter;
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
 * Created by Admin on 6/5/2017.
 */

public class Notification extends Fragment {

    Context context;
    Activity_indicator obj_dialog;
    Pref_Master pref;
    RecyclerView recycle_notification;
    NotificationAdapter adapter;
    ArrayList<Model_order> array_noti = new ArrayList<>();
    Textview notext;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.notification_fragment, container, false);
        context = getActivity();
        pref = new Pref_Master(context);
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        notext=(Textview)v.findViewById(R.id.notext);
        recycle_notification = (RecyclerView) v.findViewById(R.id.recycle_notification);
        int numberOfColumns = 1;
        recycle_notification.setLayoutManager(new GridLayoutManager(context, numberOfColumns));
        adapter = new NotificationAdapter(getActivity(), array_noti, pref);
        recycle_notification.setAdapter(adapter);
        get_notification();

        return v;
    }

    public void get_notification() {

        obj_dialog.show();

        String url = Config.app_url + Config.Getnotification + "/" + pref.getUID();
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

                        array_noti.clear();

                        for (int i = 0; i < jarray.length(); i++) {
                            Model_order model = new Model_order();
                            JSONObject jobj1 = jarray.getJSONObject(i);
                            model.setMsg(jobj1.getString("msg"));
                            model.setDate(jobj1.getString("datettime"));
                            array_noti.add(model);

                        }
                        adapter.notifyDataSetChanged();
                        if (array_noti.size() == 0) {
                            notext.setVisibility(View.VISIBLE);
                            recycle_notification.setVisibility(View.INVISIBLE);
                        } else {
                            notext.setVisibility(View.INVISIBLE);
                            recycle_notification.setVisibility(View.VISIBLE);
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


}
