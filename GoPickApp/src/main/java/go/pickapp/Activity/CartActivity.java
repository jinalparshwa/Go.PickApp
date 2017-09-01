package go.pickapp.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import go.pickapp.Adapter.cartAdapter;
import go.pickapp.Controller.Activity_indicator;
import go.pickapp.Controller.Config;
import go.pickapp.Controller.Connection;
import go.pickapp.Controller.DialogBox;
import go.pickapp.Controller.Edittext;
import go.pickapp.Controller.Textview;
import go.pickapp.Model.Model_cart;
import go.pickapp.Model.Model_pass;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

/**
 * Created by Admin on 6/16/2017.
 */

public class CartActivity extends AppCompatActivity {

    Context context = this;
    Pref_Master pref;
    RecyclerView recycle_cart;
    cartAdapter adapter;
    ArrayList<Model_cart> array_cart = new ArrayList<>();
    RelativeLayout rr_checkout;
    Activity_indicator obj_dialog;
    Textview txt_subtotal, txt_total_cart;
    String restaurant = "";
    Textview no_data;
    ArrayList<Model_pass> array_pass = new ArrayList<>();
    //  Textview txt_discount, txt_voucher, popup_dis;
    Edittext input;
    String disamt = "";
    ImageView img_back;
    String build = "";
    public static String str = "";
    String mobile = "";
    android.support.v7.app.AlertDialog alert;
    String userid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        pref = new Pref_Master(context);
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        Log.e("Login_flag", pref.getStr_login_flag());

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
        get_cart();

        adapter = new cartAdapter(context, array_cart, CartActivity.this, api_f, pref);
        recycle_cart.setAdapter(adapter);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(CartActivity.this, RestaurantActivity.class);
                startActivity(i);
                finish();
            }
        });
        rr_checkout = (RelativeLayout) findViewById(R.id.rr_checkout);
        rr_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model_pass modelPass = new Model_pass();
                for (int i = 0; i < array_cart.size(); i++) {
                    modelPass.setProduct_id(array_cart.get(i).getProduct_id());
                    modelPass.setRate(array_cart.get(i).getTotal_add());
                    modelPass.setQty(array_cart.get(i).getCount());
                    modelPass.setProduct_name(array_cart.get(i).getProduct_name());
                    array_pass.add(modelPass);

                }
                if (pref.getStr_login_flag().equals("login")) {
                    Get_profile();


                } else {
                    Intent i = new Intent(context, LoginActivity.class);
                    i.putExtra("Value", "1");
                    startActivity(i);
                }


            }
        });
        txt_subtotal = (Textview) findViewById(R.id.txt_subtotal);
        txt_total_cart = (Textview) findViewById(R.id.txt_total_cart);
        no_data = (Textview) findViewById(R.id.no_data);
        // txt_discount = (Textview) findViewById(R.id.txt_discount);
        // txt_voucher = (Textview) findViewById(R.id.txt_voucher);
        // popup_dis = (Textview) findViewById(R.id.popup_dis);


//        popup_dis.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (disamt.equals("0")) {
//                    LayoutInflater li = LayoutInflater.from(context);
//                    View v = li.inflate(R.layout.code_popup, null);
//                    alert = new android.support.v7.app.AlertDialog.Builder(context).setView(v).show();
//                    alert.setCancelable(false);
//                    alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    Textview ok = (Textview) v.findViewById(R.id.con_ok);
//                    Textview cancel = (Textview) v.findViewById(R.id.con_cancel);
//                    input = (Edittext) v.findViewById(R.id.input);
//
//                    ok.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (input.getText().toString().equals("")) {
//                                DialogBox.setPopup(context, getResources().getString(R.string.Please_enter_any_promocode));
//                            } else {
//                                Get_discount();
//
//                            }
//
//                        }
//                    });
//                    cancel.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            alert.dismiss();
//                        }
//                    });
//                } else {
//                    // Toast.makeText(context, "You have used Coupon code", Toast.LENGTH_SHORT).show();
//                    // DialogBox.setPopup(context, getResources().getString(R.string.you_have_already_used_this_copoun_code));
//                }
//
//            }
//        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(CartActivity.this, RestaurantActivity.class);
        startActivity(i);
        finish();
    }

    public void Get_profile() {

        obj_dialog.show();

        String url = Config.app_url + Config.Getuserprofile + "/" + userid;
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

                        for (int i = 0; i < jarray.length(); i++) {
                            JSONObject jobj1 = jarray.getJSONObject(i);
                            mobile = jobj1.getString("mobile");
                        }

                        if (mobile.equals("")) {
                            Intent i = new Intent(CartActivity.this, Add_mobile_no.class);
                            startActivity(i);

                        } else {
                            Intent intent = new Intent(context, Payment_methodActivity.class);
                            startActivity(intent);
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


    Runnable api_f = new Runnable() {
        @Override
        public void run() {
            array_cart.clear();
            get_cart();
        }
    };


    public void get_cart() {

        //obj_dialog.show();


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
                // obj_dialog.dismiss();

                try {
                    JSONObject jobj = new JSONObject(response);
                    String res_flag = jobj.getString("status");
                    if (res_flag.equals("200")) {
                        if (jobj.has("data")) {
                            JSONArray jarray = new JSONArray(jobj.getString("data"));

                            array_cart.clear();

                            for (int i = 0; i < jarray.length(); i++) {
                                JSONObject job = jarray.getJSONObject(i);
                                restaurant = job.getString("restid");

                                JSONArray jarry = job.getJSONArray("product");
                                for (int j = 0; j < jarry.length(); j++) {
                                    Model_cart model = new Model_cart();
                                    JSONObject jobj1 = jarry.getJSONObject(j);
                                    model.setProduct_image(jobj1.getString("thumb"));
                                    model.setShort_desc(jobj1.getString("shortdesc"));
                                    model.setProduct_name(jobj1.getString("productname"));
                                    model.setRate(jobj1.getString("rate"));
                                    model.setSub_total(jobj1.getString("subtotal"));
                                    model.setQty(jobj1.getString("qty"));
                                    model.setCount(Integer.parseInt(jobj1.getString("qty")));
                                    model.setProduct_id(jobj1.getString("productid"));
                                    model.setUsercarttempid(jobj1.getString("usercarttempid"));

                                    JSONArray jsonArray = jobj1.getJSONArray("productoption");
                                    for (int k = 0; k < jsonArray.length(); k++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(k);
                                        model.setProduct_option_name(jsonObject.getString("productoptionname"));

                                        JSONArray jsonArray1 = jsonObject.getJSONArray("productoptiondetail");
                                        for (int l = 0; l < jsonArray1.length(); l++) {
                                            JSONObject jsonObject1 = jsonArray1.getJSONObject(l);
                                            model.setOption_detail(jsonObject1.getString("optiondetail"));

                                        }

                                    }
                                    model.setProduct_string(jobj1.getString("productoptionstring"));
                                    model.setSpecialinstruction(jobj1.getString("specialinstruction"));
                                    array_cart.add(model);

                                }
//                                if (job.getString("discountper").equals("0")) {
//                                    popup_dis.setText(getResources().getString(R.string.have_voucher_copoun));
//                                    txt_voucher.setText("0");
//                                } else {
//                                    popup_dis.setText(getResources().getString(R.string.Coupon_discount));
//                                    txt_discount.setText("(" + job.getString("discountper") + ")");
//                                }
                                disamt = job.getString("discountamt");
                                // txt_voucher.setText(job.getString("discountamt"));
                                txt_subtotal.setText(job.getString("grandTotal"));
                                txt_total_cart.setText(job.getString("cartpagetotal"));
                                adapter.notifyDataSetChanged();
                            }
                        }

                        if (array_cart.size() == 0) {
                            no_data.setVisibility(View.VISIBLE);
                            recycle_cart.setVisibility(View.INVISIBLE);
                            txt_subtotal.setText("0");
                            txt_total_cart.setText("0");
                            pref.setCart_id("");
                            pref.setCart_count(0);

                            Intent i = new Intent(context, RestaurantActivity.class);
                            startActivity(i);
                        } else {
                            no_data.setVisibility(View.INVISIBLE);
                            recycle_cart.setVisibility(View.VISIBLE);

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
                //  obj_dialog.dismiss();
            }
        };
        Connection.getconnectionVolley(url, param, header, context, lis_res, lis_error);

    }

    public void Remove_product(String product_id, String temp_id, final int id) {

        obj_dialog.show();

        String url = Config.app_url + Config.Removecartproduct;
        JSONObject jobj_loginuser = new JSONObject();
        try {

            JSONObject jobj_row = new JSONObject();

            jobj_row.put("cartid", pref.getCart_id());
            jobj_row.put("userid", userid);
            jobj_row.put("restid", restaurant);
            jobj_row.put("productid", product_id);
            jobj_row.put("usercarttempid", temp_id);

            JSONArray jarray_loginuser = new JSONArray();
            jarray_loginuser.put(jobj_row);

            jobj_loginuser.put("removecartproduct", jarray_loginuser);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final Map<String, String> params = new HashMap<>();
        params.put("data", "" + jobj_loginuser.toString().replaceAll("\\\\", ""));
        Log.e("jinal_request", ":" + params.toString());

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


                        if (id == 1) {
                            Intent i = new Intent(context, RestaurantActivity.class);
                            startActivity(i);
                        } else {
                            api_f.run();
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

    public void Get_discount() {

        //obj_dialog.show();

        String url = Config.app_url + Config.Getdiscount;
        JSONObject jobj_loginuser = new JSONObject();
        try {

            JSONObject jobj_row = new JSONObject();

            jobj_row.put("restid", pref.getStr_res_id());
            jobj_row.put("couponcode", input.getText().toString());
            jobj_row.put("total", txt_subtotal.getText().toString());
            jobj_row.put("cartid", pref.getCart_id());

            JSONArray jarray_loginuser = new JSONArray();
            jarray_loginuser.put(jobj_row);

            jobj_loginuser.put("getdiscount", jarray_loginuser);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final Map<String, String> params = new HashMap<>();
        params.put("data", "" + jobj_loginuser.toString().replaceAll("\\\\", ""));
        Log.e("jinal_request", ":" + params.toString());

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
                        get_cart();
                        JSONArray jarray = new JSONArray(jobj.getString("data"));

                        for (int i = 0; i < jarray.length(); i++) {
                            JSONObject jobj1 = jarray.getJSONObject(i);
                            // txt_discount.setText("(" + jobj1.getString("discountper") + ")");
                            //popup_dis.setText(getResources().getString(R.string.Coupon_discount));

                        }
                        alert.dismiss();


                        DialogBox.setPopup(context, getResources().getString(R.string.Promocode_applied));

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
                // obj_dialog.dismiss();
            }
        };
        Connection.postconnection(url, params, header, context, lis_res, lis_error);
    }


}
