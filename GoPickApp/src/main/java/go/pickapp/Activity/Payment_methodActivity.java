package go.pickapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import go.pickapp.Adapter.Place_Order;
import go.pickapp.Controller.Activity_indicator;
import go.pickapp.Controller.Config;
import go.pickapp.Controller.Connection;
import go.pickapp.Controller.DialogBox;
import go.pickapp.Controller.Textview;
import go.pickapp.Model.Model_pass;
import go.pickapp.Model.Model_place_order;
import go.pickapp.Model.Model_time_payment;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

public class Payment_methodActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    RelativeLayout rr_add_payment;
    RadioButton payment_btn;
    RadioButton pay_one, pay_two, pay_three;
    String restaurant = "";
    Context context = this;
    Pref_Master pref;
    ArrayList<Model_time_payment> array_list = new ArrayList<>();
    LinearLayout ll_method;
    Textview no_data;
    Activity_indicator obj_dialog;
    Model_pass model;
    RelativeLayout rr_pay_one, rr_pay_two, rr_pay_three;
    String value = "";
    String index = "";
    ImageView img_one, img_two, img_three;
    String one = "";
    String two = "";
    String three = "";
    String one_grey = "";
    String two_grey = "";
    String three_grey = "";
    ArrayList<Model_place_order.Product> array_place_order = new ArrayList<>();
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
    String final_total = "";
    String order_id = "";
    String oredr_no = "";
    String Payment_type = "";
    String Payment_id = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        pref = new Pref_Master(context);
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        if (pref.getLogin_value() == 2) {
            userid = pref.getStr_guest_UID();
            Log.e("User_id_guest", pref.getStr_guest_UID());
        } else {
            userid = pref.getUID();
            Log.e("user_id___", pref.getUID());
        }

        Log.e("Language", pref.getLanguage());


        restaurant = pref.getStr_res_id();
        pay_one = (RadioButton) findViewById(R.id.pay_one);
        pay_one.setOnCheckedChangeListener(this);
        pay_two = (RadioButton) findViewById(R.id.pay_two);
        pay_two.setOnCheckedChangeListener(this);
        pay_three = (RadioButton) findViewById(R.id.pay_three);
        pay_three.setOnCheckedChangeListener(this);
        rr_add_payment = (RelativeLayout) findViewById(R.id.rr_add_payment);
        ll_method = (LinearLayout) findViewById(R.id.ll_method);
        no_data = (Textview) findViewById(R.id.no_data);
        img_back = (ImageView) findViewById(R.id.img_back);
        rr_pay_one = (RelativeLayout) findViewById(R.id.rr_pay_one);
        rr_pay_two = (RelativeLayout) findViewById(R.id.rr_pay_two);
        rr_pay_three = (RelativeLayout) findViewById(R.id.rr_pay_three);
        img_one = (ImageView) findViewById(R.id.img_one);
        img_two = (ImageView) findViewById(R.id.img_two);
        img_three = (ImageView) findViewById(R.id.img_three);


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, CartActivity.class);
                startActivity(i);
                finish();
            }
        });

        rr_add_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pay_one.isChecked() || pay_two.isChecked() || pay_three.isChecked()) {
//                    Intent i = new Intent(Payment_methodActivity.this, PlaceOrderActivity.class);
//                    // i.putExtra("fragmentcode", Config.Fragment_ID.place_order);
//                    i.putExtra("Payment_type", value);
//                    i.putExtra("Payment_id", index);
//                    startActivity(i);

                    PLace_order();
                    //finish();
                } else {
                    DialogBox.setPopup(context, getResources().getString(R.string.Select_any_Payment_method));
                }

            }
        });


        get_payment();
        get_cart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(context, CartActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            if (buttonView.getId() == R.id.pay_one) {
                pay_one.setChecked(true);
                pay_two.setChecked(false);
                pay_three.setChecked(false);
                value = pay_one.getText().toString();
                index = "1";
                Glide.with(context).load(one).into(img_one);
                Glide.with(context).load(two_grey).into(img_two);
                Glide.with(context).load(three_grey).into(img_three);

            }
            if (buttonView.getId() == R.id.pay_two) {
                pay_two.setChecked(true);
                pay_one.setChecked(false);
                pay_three.setChecked(false);
                value = pay_two.getText().toString();
                index = "2";
                Glide.with(context).load(two).into(img_two);
                Glide.with(context).load(one_grey).into(img_one);
                Glide.with(context).load(three_grey).into(img_three);


            }
            if (buttonView.getId() == R.id.pay_three) {
                pay_three.setChecked(true);
                pay_one.setChecked(false);
                pay_two.setChecked(false);
                value = pay_three.getText().toString();
                index = "3";
                Glide.with(context).load(three).into(img_three);
                Glide.with(context).load(two_grey).into(img_two);
                Glide.with(context).load(one_grey).into(img_one);


            }
        }
    }


    public void get_payment() {

        obj_dialog.show();

        String url = Config.app_url + Config.Getpaymenttype + "/" + restaurant;
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

                        array_list.clear();

                        for (int i = 0; i < jarray.length(); i++) {
                            Model_time_payment model = new Model_time_payment();
                            JSONObject jobj1 = jarray.getJSONObject(i);
                            model.setPayment_id(jobj1.getString("id"));
                            model.setPayment_type(jobj1.getString("paymenttype"));
                            model.setImage(jobj1.getString("imagenm"));
                            model.setImage_grey(jobj1.getString("imagenmgray"));
                            array_list.add(model);

                        }
                        if (array_list.size() == 1) {
                            rr_pay_two.setVisibility(View.GONE);
                            rr_pay_three.setVisibility(View.GONE);
                            rr_pay_one.setVisibility(View.VISIBLE);
                            rr_add_payment.setVisibility(View.VISIBLE);
                            no_data.setVisibility(View.INVISIBLE);

                        } else if (array_list.size() == 2) {
                            rr_pay_one.setVisibility(View.VISIBLE);
                            rr_pay_two.setVisibility(View.VISIBLE);
                            rr_pay_three.setVisibility(View.GONE);
                            rr_add_payment.setVisibility(View.VISIBLE);
                            no_data.setVisibility(View.INVISIBLE);
                        } else if (array_list.size() == 3) {

                            rr_pay_one.setVisibility(View.VISIBLE);
                            rr_pay_two.setVisibility(View.VISIBLE);
                            rr_pay_three.setVisibility(View.VISIBLE);
                            rr_add_payment.setVisibility(View.VISIBLE);
                            no_data.setVisibility(View.INVISIBLE);

                        } else {
                            rr_add_payment.setVisibility(View.INVISIBLE);
                            no_data.setVisibility(View.VISIBLE);
                            pay_one.setVisibility(View.INVISIBLE);
                            pay_two.setVisibility(View.INVISIBLE);
                            pay_three.setVisibility(View.INVISIBLE);

                        }
                        Glide.with(context).load(array_list.get(0).getImage_grey()).into(img_one);
                        Glide.with(context).load(array_list.get(1).getImage_grey()).into(img_two);
                        Glide.with(context).load(array_list.get(2).getImage_grey()).into(img_three);

                        pay_one.setText(array_list.get(0).getPayment_type());
                        pay_two.setText(array_list.get(1).getPayment_type());
                        pay_three.setText(array_list.get(2).getPayment_type());

                        one = array_list.get(0).getImage();
                        one_grey = array_list.get(0).getImage_grey();

                        two = array_list.get(1).getImage();
                        two_grey = array_list.get(1).getImage_grey();

                        three = array_list.get(2).getImage();
                        three_grey = array_list.get(2).getImage_grey();


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
                    //txt_res_name.setText(model.getData().get(0).getRestname());
                    copoun_code = model.getData().get(0).getCouponcode();
                    dis_per = model.getData().get(0).getDiscountper();
                    service_per = model.getData().get(0).getServiceper();
                    //txt_place_total.setText(model.getData().get(0).getGrandTotal());
                    // txt_copoun_dis.setText("(" + model.getData().get(0).getDiscountper() + ")");
                    //txt_discountt.setText(model.getData().get(0).getDiscountamt());
                    //txt_service.setText("(" + model.getData().get(0).getServiceper() + ")");
                    //txt_service_tax.setText(model.getData().get(0).getServiceamt());
                    final_total = model.getData().get(0).getFinaltotal();
                    array_place_order.addAll(model.getData().get(0).getProduct());
//                    for (int i = 0; i < array_place_order.size(); i++) {
//                        array_option.addAll(model.getData().get(0).getProduct().get(i).getProductoption());
//                    }


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
            jobj_main.put("total", final_total);
            jobj_main.put("discount", "");
            jobj_main.put("discountper", dis_per);
            jobj_main.put("serviceper", service_per);
            jobj_main.put("serviceamt", "");
            jobj_main.put("finaltotal", final_total);
            jobj_main.put("paymenttypeid", index);

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
                jsonObject.put("specialinstruction", array_place_order.get(i).getSpecialinstruction());

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

                            Intent intent = new Intent(Payment_methodActivity.this, Order_confirmationActivity.class);
                            intent.putExtra("Order_id", order_id);
                            intent.putExtra("Order_no", oredr_no);
                            intent.putExtra("payment_type", value);
                            intent.putExtra("final_total", finaltotal);
                            intent.putExtra("res_lat", res_lat);
                            intent.putExtra("res_long", res_long);
                            intent.putExtra("message", message);
                            intent.putExtra("val", "2");
                            intent.putExtra("back", "1");
                            intent.putExtra("res_name", res_name);
                            startActivity(intent);
                            finish();

                            Remove_cart();
                            pref.setCart_id("");
                            pref.setCart_count(0);


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
