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

import go.pickapp.Adapter.Order_detailsAdapter;
import go.pickapp.Controller.Activity_indicator;
import go.pickapp.Controller.Config;
import go.pickapp.Controller.Connection;
import go.pickapp.Controller.DialogBox;
import go.pickapp.Controller.Textview;
import go.pickapp.Controller.Textview_Bold;
import go.pickapp.Model.Model_cart;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

/**
 * Created by Admin on 7/8/2017.
 */

public class Order_detail_for_guest extends Fragment {

    Pref_Master pref;
    Context context;
    RecyclerView recycle_order;
    Activity_indicator obj_dialog;
    Order_detailsAdapter adapter;
    Textview_Bold res_name;
    Textview txt_order_no, order_status, txt_order_date, txt_payment, txt_total, txt_subtotal, txt_total_cart, res_address;
    //Textview txt_service_fee, txt_service;
    ArrayList<Model_cart> array_cart = new ArrayList<>();
    String order_id = "";
    // Textview txt_dis_per,txt_discount;
    int value = 0;
    String Order_no = "";

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.order_details_fragment, container, false);
        context = getActivity();
        pref = new Pref_Master(context);

        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        Bundle extras = getActivity().getIntent().getExtras();

        if (extras != null) {
            order_id = extras.getString("Order_id");
            Order_no = extras.getString("Order_no");
            Log.e("value", ":" + value);
        }

        recycle_order = (RecyclerView) v.findViewById(R.id.recycle_order);
        int numberOfColumns = 1;
        recycle_order.setLayoutManager(new GridLayoutManager(context, numberOfColumns));

        res_name = (Textview_Bold) v.findViewById(R.id.res_name);
        order_status = (Textview) v.findViewById(R.id.order_status);
        txt_order_date = (Textview) v.findViewById(R.id.txt_order_date);
        txt_payment = (Textview) v.findViewById(R.id.txt_payment);
        txt_total = (Textview) v.findViewById(R.id.txt_total);
        txt_order_no = (Textview) v.findViewById(R.id.txt_order_no);
        txt_subtotal = (Textview) v.findViewById(R.id.txt_subtotal);
        // txt_discount = (Textview) v.findViewById(R.id.txt_discount);
        // txt_service_fee = (Textview) v.findViewById(R.id.txt_service_fee);
        // txt_service = (Textview) v.findViewById(R.id.txt_service);
        txt_total_cart = (Textview) v.findViewById(R.id.txt_total_cart);
        res_address = (Textview) v.findViewById(R.id.res_address);
        //txt_dis_per = (Textview) v.findViewById(R.id.txt_dis_per);

        get_order_details();

        adapter = new Order_detailsAdapter(context, array_cart, pref);
        recycle_order.setAdapter(adapter);

        DialogBox.setorder_popup(context, getResources().getString(R.string.Your_order_number_is), Order_no);
        return v;

    }

    public void get_order_details() {

        obj_dialog.show();

        String url = Config.app_url + Config.Getorderdetails + "/" + order_id;
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
                        if (jobj.has("data")) {
                            JSONArray jarray = new JSONArray(jobj.getString("data"));

                            for (int i = 0; i < jarray.length(); i++) {
                                JSONObject job = jarray.getJSONObject(i);


                                res_name.setText(job.getString("restname"));
                                txt_order_no.setText(job.getString("orderno"));
                                order_status.setText(job.getString("orderstatusnm"));
                                txt_order_date.setText(job.getString("orderdate"));
                                txt_payment.setText(job.getString("paymenttype"));
                                txt_total.setText(job.getString("finaltotal"));
                                txt_subtotal.setText(job.getString("total"));
                                // txt_discount.setText(job.getString("discount"));
                                // txt_dis_per.setText("(" + job.getString("discountper") + ")");
                                // txt_service_fee.setText("(" + job.getString("servicefeeper") + ")");
                                //txt_service.setText(job.getString("servicefee"));
                                txt_total_cart.setText(job.getString("finaltotal"));
                                res_address.setText(job.getString("restaddress"));


                                array_cart.clear();

                                JSONArray jarry = job.getJSONArray("product");
                                for (int j = 0; j < jarry.length(); j++) {
                                    Model_cart model = new Model_cart();
                                    JSONObject jobj1 = jarry.getJSONObject(j);
                                    model.setProduct_image(jobj1.getString("image"));
                                    model.setProduct_name(jobj1.getString("productname"));
                                    model.setShort_desc(jobj1.getString("shortdesc"));
                                    model.setQty(jobj1.getString("qty"));
                                    model.setTotal_add(jobj1.getString("producttotal"));
                                    array_cart.add(model);

                                    Log.e("array_cart", ":" + array_cart.size());
                                }

                                adapter.notifyDataSetChanged();
                            }
                        }

                        adapter.notifyDataSetChanged();


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
