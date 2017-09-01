package go.pickapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import go.pickapp.Adapter.Place_Order;
import go.pickapp.Controller.Activity_indicator;
import go.pickapp.Controller.Config;
import go.pickapp.Controller.Connection;
import go.pickapp.Controller.DialogBox;
import go.pickapp.Controller.Textview;
import go.pickapp.JSON.JSON;
import go.pickapp.Model.Model_cart;
import go.pickapp.Model.Model_option;
import go.pickapp.Model.Model_order;
import go.pickapp.Model.Model_place_order;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

/**
 * Created by Admin on 6/16/2017.
 */

public class PlaceOrderActivity extends AppCompatActivity {

    Context context = this;
    Pref_Master pref;
    Activity_indicator obj_dialog;
    RecyclerView recycle_cart;
    Place_Order adapter;
    ArrayList<Model_place_order.Product> array_place_order = new ArrayList<>();
    //    ArrayList<Model_place_order.Productoption> array_option = new ArrayList<>();
    Textview txt_grand_total;
    Textview txt_place_total;
    RelativeLayout rr_place_order;
    Textview txt_pay_type;
    Textview txt_res_name;
    String Payment_type = "";
    String Payment_id = "";
    String order_id = "";
    String oredr_no = "";
    // Textview txt_copoun_dis, txt_discountt;
    // Textview txt_service_tax, txt_service;
    String dis_per = "";
    String service_per = "";
    String copoun_code = "";
    ImageView img_back;
    String userid = "";
    String finaltotal = "";
    String res_lat;
    String res_long;
    String message = "";
    String res_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.placeorder_fragment);
        pref = new Pref_Master(context);
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            Payment_type = extras.getString("Payment_type");
            Payment_id = extras.getString("Payment_id");
        }
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(PlaceOrderActivity.this, Payment_methodActivity.class);
//                startActivity(i);
                finish();
            }
        });

        if (pref.getLogin_value() == 2) {
            userid = pref.getStr_guest_UID();
            Log.e("User_id_guest", pref.getStr_guest_UID());
        } else {
            userid = pref.getUID();
            Log.e("user_id___", pref.getUID());
        }

        recycle_cart = (RecyclerView) findViewById(R.id.recycle_cart);
        int numberOfColumns = 1;
        recycle_cart.setLayoutManager(new GridLayoutManager(context, numberOfColumns));

        txt_res_name = (Textview) findViewById(R.id.txt_res_name);
        txt_grand_total = (Textview) findViewById(R.id.txt_grand_total);
        txt_place_total = (Textview) findViewById(R.id.txt_place_total);
        txt_pay_type = (Textview) findViewById(R.id.txt_pay_type);
        // txt_copoun_dis = (Textview) findViewById(R.id.txt_copoun_dis);
        // txt_discountt = (Textview) findViewById(R.id.txt_discountt);
        // txt_service_tax = (Textview) findViewById(R.id.txt_service_tax);
        // txt_service = (Textview) findViewById(R.id.txt_service);
        txt_pay_type.setText(Payment_type);
        rr_place_order = (RelativeLayout) findViewById(R.id.rr_place_order);
        rr_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PLace_order();


            }
        });
        get_cart();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent i = new Intent(PlaceOrderActivity.this, Payment_methodActivity.class);
//        startActivity(i);
        finish();
    }

    public void get_cart() {

        obj_dialog.show();

        String url = Config.app_url + Config.Getcart + "/" + pref.getCart_id();
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

                Gson gson = new Gson();
                Model_place_order model = gson.fromJson(response, Model_place_order.class);

                if (model != null) {

                    res_name = model.getData().get(0).getRestname();
                    txt_res_name.setText(model.getData().get(0).getRestname());
                    copoun_code = model.getData().get(0).getCouponcode();
                    dis_per = model.getData().get(0).getDiscountper();
                    service_per = model.getData().get(0).getServiceper();
                    txt_place_total.setText(model.getData().get(0).getGrandTotal());
                    // txt_copoun_dis.setText("(" + model.getData().get(0).getDiscountper() + ")");
                    //txt_discountt.setText(model.getData().get(0).getDiscountamt());
                    //txt_service.setText("(" + model.getData().get(0).getServiceper() + ")");
                    //txt_service_tax.setText(model.getData().get(0).getServiceamt());
                    txt_grand_total.setText(model.getData().get(0).getFinaltotal());
                    array_place_order.addAll(model.getData().get(0).getProduct());
//                    for (int i = 0; i < array_place_order.size(); i++) {
//                        array_option.addAll(model.getData().get(0).getProduct().get(i).getProductoption());
//                    }


                    adapter = new Place_Order(context, array_place_order);
                    recycle_cart.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

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

    public void PLace_order() {


        String url = Config.app_url + Config.Placeorder;
        JSONObject jobj_loginuser = new JSONObject();
        obj_dialog.show();

        try {
            JSONObject jobj_main = new JSONObject();

            jobj_main.put("customerid", userid);
            jobj_main.put("restid", pref.getStr_res_id());
            jobj_main.put("couponcode", copoun_code);
            jobj_main.put("total", txt_place_total.getText().toString());
            jobj_main.put("discount", "");
            jobj_main.put("discountper", dis_per);
            jobj_main.put("serviceper", service_per);
            jobj_main.put("serviceamt", "");
            jobj_main.put("finaltotal", txt_grand_total.getText().toString());
            jobj_main.put("paymenttypeid", String.valueOf(Payment_id));

            JSONArray product = new JSONArray();
            for (int i = 0; i < array_place_order.size(); i++) {
                JSONObject jsonObject = new JSONObject();

                JSONArray product_option = new JSONArray();
                for (int k = 0; k < array_place_order.get(i).getProductoption().size(); k++) {
                    JSONArray jarray = new JSONArray();

                    JSONObject product_object = new JSONObject();

                    for (int j = 0; j < array_place_order.get(i).getProductoption().get(k).getProductoptiondetail().size(); j++) {

                        JSONObject json = new JSONObject();
                        json.put("productoptiondetailid", array_place_order.get(i).getProductoption().get(k).getProductoptiondetail().get(j).getProductoptiondetailid());
                        json.put("optionrate", array_place_order.get(i).getProductoption().get(k).getProductoptiondetail().get(j).getRate());
                        jarray.put(json);


                    }
                    if (jarray.length() > 0) {
                        product_object.put("productoptionid", array_place_order.get(i).getProductoption().get(k).getProductoptionid());
                        product_object.put("productoptiondetail", jarray);
                        product_option.put(product_object);

                    }

                }
                jsonObject.put("productoption", product_option);
                jsonObject.put("productid", array_place_order.get(i).getProductid());
                jsonObject.put("qty", array_place_order.get(i).getQty());
                jsonObject.put("rate", array_place_order.get(i).getRate());

                product.put(jsonObject);

            }
            jobj_main.put("product", product);


            JSONArray jarray_loginuser = new JSONArray();
            jarray_loginuser.put(jobj_main);

            jobj_loginuser.put("placeorder", jarray_loginuser);

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
                Log.e("Response", response);
                obj_dialog.dismiss();

                try {
                    JSONObject jobj = new JSONObject(response);
                    String res_flag = jobj.getString("status");
                    if (res_flag.equals("200")) {
                        JSONArray jarray = new JSONArray(jobj.getString("data"));

                        for (int i = 0; i < jarray.length(); i++) {
                            JSONObject jobj1 = jarray.getJSONObject(i);
                            order_id = jobj1.getString("orderid");
                            oredr_no = jobj1.getString("orderno");
                            Payment_type = jobj1.getString("paymentmethod");
                            finaltotal = jobj1.getString("finaltotal");
                            res_lat = jobj1.getString("restlatitude");
                            res_long = jobj1.getString("restlongitude");
                            message = jobj1.getString("msg");

                            Intent intent = new Intent(PlaceOrderActivity.this, Order_confirmationActivity.class);
                            intent.putExtra("Order_id", order_id);
                            intent.putExtra("Order_no", oredr_no);
                            intent.putExtra("payment_type", Payment_type);
                            intent.putExtra("final_total", finaltotal);
                            intent.putExtra("res_lat", res_lat);
                            intent.putExtra("res_long", res_long);
                            intent.putExtra("message", message);
                            intent.putExtra("val", "2");
                            intent.putExtra("back", "1");
                            intent.putExtra("res_name", res_name);
                            startActivity(intent);
                            finish();

                        }

//                        if (pref.getLogin_value() == 2) {
//                            Intent i = new Intent(context, MainActivity.class);
//                            i.putExtra("fragmentcode", Config.Fragment_ID.order_detail);
//                            i.putExtra("Order_id", order_id);
//                            i.putExtra("Order_no", oredr_no);
//                            startActivity(i);
//                            finish();
//                            pref.setGuest_uid("");
//                            pref.setLogin_value(0);
//                        } else {
//                            Intent i = new Intent(context, MainActivity.class);
//                            i.putExtra("fragmentcode", Config.Fragment_ID.Order_complete);
//                            i.putExtra("Order_id", order_id);
//                            i.putExtra("Order_no", oredr_no);
//                            startActivity(i);
//                            finish();
//                        }

                        Remove_cart();
                        pref.setCart_id("");
                        pref.setCart_count(0);


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
        Connection.postconnection(url, params, header, context, lis_res, lis_error);

    }

    public void Remove_cart() {
        //   obj_dialog.show();

        String url = Config.app_url + Config.Removecart;
        JSONObject jobj_loginuser = new JSONObject();
        try {

            JSONObject jobj_row = new JSONObject();

            jobj_row.put("cartid", pref.getCart_id());
            jobj_row.put("userid", userid);
            jobj_row.put("restid", pref.getStr_res_id());


            JSONArray jarray_loginuser = new JSONArray();
            jarray_loginuser.put(jobj_row);

            jobj_loginuser.put("removecart", jarray_loginuser);
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
                try {
                    JSONObject jobj = new JSONObject(response);
                    String res_flag = jobj.getString("status");
                    if (res_flag.equals("200")) {

                        pref.setRes_id("");
                        pref.setCart_id("");
                        pref.setCart_count(0);

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
            }
        };

        Connection.postconnection(url, params, header, context, lis_res, lis_error);
    }


}
