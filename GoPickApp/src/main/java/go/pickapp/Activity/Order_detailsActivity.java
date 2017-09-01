package go.pickapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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

import go.pickapp.Adapter.Order_detailsAdapter;
import go.pickapp.Adapter.Place_Order;
import go.pickapp.Controller.Activity_indicator;
import go.pickapp.Controller.Config;
import go.pickapp.Controller.Connection;
import go.pickapp.Controller.DialogBox;
import go.pickapp.Controller.Textview;
import go.pickapp.Controller.Textview_Bold;
import go.pickapp.Model.Model_cart;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

public class Order_detailsActivity extends AppCompatActivity {

    Pref_Master pref;
    Context context = this;
    ImageView img_back;
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
    RelativeLayout ll_status;
    Textview txt_cancelled, txt_review, txt_reorder;
    String new_order_id = "";
    String Res_id = "";
    String availability = "";
    String back = "";
    String val = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        pref = new Pref_Master(context);
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            order_id = extras.getString("Order_id");
            value = extras.getInt("value");
            back = extras.getString("back");
            val = extras.getString("val");
            Log.e("value", ":" + val);
        }


        recycle_order = (RecyclerView) findViewById(R.id.recycle_order);
        int numberOfColumns = 1;
        recycle_order.setLayoutManager(new GridLayoutManager(context, numberOfColumns));
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (back.equals("1")) {
                    finish();
                } else {
                    Intent i = new Intent(context, MainActivity.class);
                    i.putExtra("fragmentcode", Config.Fragment_ID.MY_order);
                    i.putExtra("value", value);
                    Log.e("jinal_value", ":" + value);
                    startActivity(i);
                    finish();
                }
            }
        });
        res_name = (Textview_Bold) findViewById(R.id.res_name);
        order_status = (Textview) findViewById(R.id.order_status);
        txt_order_date = (Textview) findViewById(R.id.txt_order_date);
        txt_payment = (Textview) findViewById(R.id.txt_payment);
        txt_total = (Textview) findViewById(R.id.txt_total);
        txt_order_no = (Textview) findViewById(R.id.txt_order_no);
        txt_subtotal = (Textview) findViewById(R.id.txt_subtotal);
        //txt_discount = (Textview) findViewById(R.id.txt_discount);
        // txt_service_fee = (Textview) findViewById(R.id.txt_service_fee);
        // txt_service = (Textview) findViewById(R.id.txt_service);
        txt_total_cart = (Textview) findViewById(R.id.txt_total_cart);
        res_address = (Textview) findViewById(R.id.res_address);
        //txt_dis_per = (Textview) findViewById(R.id.txt_dis_per);
        ll_status = (RelativeLayout) findViewById(R.id.ll_status);
        txt_cancelled = (Textview) findViewById(R.id.txt_cancelled);
        txt_cancelled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(context);
                View v = li.inflate(R.layout.alert_cancel_order, null);
                final android.support.v7.app.AlertDialog alert = new android.support.v7.app.AlertDialog.Builder(context).setView(v).show();
                alert.setCancelable(false);
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Textview ok = (Textview) v.findViewById(R.id.con_ok);
                Textview cancel = (Textview) v.findViewById(R.id.con_cancel);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cancel_order(order_id);
                        alert.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alert.dismiss();
                    }
                });

            }
        });
        txt_review = (Textview) findViewById(R.id.txt_review);
        txt_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, Add_reviewActivity.class);
                i.putExtra("Order_id", order_id);
                i.putExtra("value", 1);
                startActivity(i);
            }
        });
        txt_reorder = (Textview) findViewById(R.id.txt_reorder);
        txt_reorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (availability.equals("1")) {
                    Reorder(order_id);
                } else {
                    DialogBox.setPopup(context, getResources().getString(R.string.Right_now_closed));
                }
            }
        });

        if (val.equals("2")) {
            ll_status.setVisibility(View.GONE);
        } else {
            ll_status.setVisibility(View.VISIBLE);
        }

        get_order_details();

        adapter = new Order_detailsAdapter(context, array_cart, pref);
        recycle_order.setAdapter(adapter);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (back.equals("1")) {
            finish();
        } else {
            Intent i = new Intent(context, MainActivity.class);
            i.putExtra("fragmentcode", Config.Fragment_ID.MY_order);
            i.putExtra("value", value);
            startActivity(i);
            finish();
        }
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
                        DialogBox.setorder_detail_popup(context, jobj.getString("msg").toString());
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

    public void Reorder(String order_id) {

        String url = Config.app_url + Config.Reorder;
        JSONObject jobj_loginuser = new JSONObject();
        obj_dialog.show();
        try {

            JSONObject jobj_row = new JSONObject();

            jobj_row.put("orderid", order_id);


            JSONArray jarray_loginuser = new JSONArray();
            jarray_loginuser.put(jobj_row);

            jobj_loginuser.put("reorder", jarray_loginuser);
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
                        JSONArray jarray = new JSONArray(jobj.getString("data"));
                        JSONObject jobj1 = jarray.getJSONObject(0);

                        new_order_id = jobj1.getString("orderid");
                        Intent i = new Intent(context, ReorderActivity.class);
                        i.putExtra("Order_id", new_order_id);
                        i.putExtra("Res_id", Res_id);
                        startActivity(i);
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

                                availability = job.getString("availabilitystatus");
                                Res_id = job.getString("restid");
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
                                // txt_service.setText(job.getString("servicefee"));
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

                                if (job.getString("cancelstatus").equals("No")) {
                                    txt_cancelled.setVisibility(View.GONE);
                                } else {
                                    txt_cancelled.setVisibility(View.VISIBLE);
                                }

                                if (job.getString("reorderstatus").equals("Yes")) {
                                    txt_reorder.setVisibility(View.VISIBLE);
                                } else {
                                    txt_reorder.setVisibility(View.GONE);
                                }

                                if (job.getString("reviewstatus").equals("Yes")) {
                                    txt_review.setVisibility(View.VISIBLE);
                                } else {
                                    txt_review.setVisibility(View.GONE);
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
