package go.pickapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import go.pickapp.Adapter.Order_detailsAdapter;
import go.pickapp.Adapter.Place_Order;
import go.pickapp.Adapter.Reorder_Adapter;
import go.pickapp.Controller.Activity_indicator;
import go.pickapp.Controller.Config;
import go.pickapp.Controller.Connection;
import go.pickapp.Controller.DialogBox;
import go.pickapp.Controller.Textview;
import go.pickapp.Controller.Textview_Bold;
import go.pickapp.Model.Model_cart;
import go.pickapp.Model.Model_order;
import go.pickapp.Model.Model_place_order;
import go.pickapp.Model.Model_time_payment;
import go.pickapp.Model.Model_view_order;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

public class ReorderActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    Pref_Master pref;
    Context context = this;
    ImageView img_back;
    RecyclerView recycle_order;
    Activity_indicator obj_dialog;
    Reorder_Adapter adapter;
    Textview_Bold res_name;
    Textview res_address, txt_order_no, txt_subtotal, txt_total_cart;
    RelativeLayout rr_pay_one;
    RelativeLayout rr_pay_two;
    RelativeLayout rr_pay_three;
    RadioButton pay_one, pay_two, pay_three;
    ImageView img_one, img_two, img_three;
    RelativeLayout ll_reorder;
    ArrayList<Model_cart> array_cart = new ArrayList<>();
    String order_id = "";
    Textview no_data;
    String Res_id = "";
    String value = "";
    String index = "";
    String paymenttype_id = "";
    String userid = "";
    ArrayList<Model_view_order.Product> array_place_order = new ArrayList<>();
    ArrayList<Model_view_order.Paymentarray> array_payment = new ArrayList<>();
    String Payment_type = "";
    String oredr_no = "";
    String finaltotal = "";
    String res_lat;
    String res_long;
    String message = "";
    String res_nameee = "";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reorder);
        pref = new Pref_Master(context);
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            order_id = extras.getString("Order_id");
            Res_id = extras.getString("Res_id");
            Log.e("Order_id", ":" + order_id);
        }

        if (pref.getLogin_value() == 2) {
            userid = pref.getStr_guest_UID();
            Log.e("User_id_guest", pref.getStr_guest_UID());
        } else {
            userid = pref.getUID();
            Log.e("user_id___", pref.getUID());
        }


        recycle_order = (RecyclerView) findViewById(R.id.recycle_order);
        int numberOfColumns = 1;
        recycle_order.setLayoutManager(new GridLayoutManager(context, numberOfColumns));
        img_back = (ImageView) findViewById(R.id.img_back);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reorder_delete();
                finish();
            }
        });

        no_data = (Textview) findViewById(R.id.no_data);
        res_name = (Textview_Bold) findViewById(R.id.res_name);
        res_address = (Textview) findViewById(R.id.res_address);
        txt_order_no = (Textview) findViewById(R.id.txt_order_no);
        txt_subtotal = (Textview) findViewById(R.id.txt_subtotal);
        txt_total_cart = (Textview) findViewById(R.id.txt_total_cart);
        rr_pay_one = (RelativeLayout) findViewById(R.id.rr_pay_one);
        rr_pay_two = (RelativeLayout) findViewById(R.id.rr_pay_two);
        rr_pay_three = (RelativeLayout) findViewById(R.id.rr_pay_three);
        pay_one = (RadioButton) findViewById(R.id.pay_one);
        pay_one.setOnCheckedChangeListener(this);
        pay_two = (RadioButton) findViewById(R.id.pay_two);
        pay_two.setOnCheckedChangeListener(this);
        pay_three = (RadioButton) findViewById(R.id.pay_three);
        pay_three.setOnCheckedChangeListener(this);
        img_one = (ImageView) findViewById(R.id.img_one);
        img_two = (ImageView) findViewById(R.id.img_two);
        img_three = (ImageView) findViewById(R.id.img_three);
        ll_reorder = (RelativeLayout) findViewById(R.id.ll_reorder);
        ll_reorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PLace_order();
            }
        });

        adapter = new Reorder_Adapter(context, array_place_order, pref, order_id, Res_id);
        recycle_order.setAdapter(adapter);
        get_reorder();
    }

    @Override
    protected void onResume() {
        super.onResume();
        get_reorder();
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
                Glide.with(context).load(array_payment.get(0).getImagenm()).into(img_one);
                Glide.with(context).load(array_payment.get(1).getImagenmgray()).into(img_two);
                Glide.with(context).load(array_payment.get(2).getImagenmgray()).into(img_three);

            }
            if (buttonView.getId() == R.id.pay_two) {
                pay_two.setChecked(true);
                pay_one.setChecked(false);
                pay_three.setChecked(false);
                value = pay_two.getText().toString();
                index = "2";
                Glide.with(context).load(array_payment.get(1).getImagenm()).into(img_two);
                Glide.with(context).load(array_payment.get(0).getImagenmgray()).into(img_one);
                Glide.with(context).load(array_payment.get(2).getImagenmgray()).into(img_three);


            }
            if (buttonView.getId() == R.id.pay_three) {
                pay_three.setChecked(true);
                pay_one.setChecked(false);
                pay_two.setChecked(false);
                value = pay_three.getText().toString();
                index = "3";
                Glide.with(context).load(array_payment.get(2).getImagenm()).into(img_three);
                Glide.with(context).load(array_payment.get(1).getImagenmgray()).into(img_two);
                Glide.with(context).load(array_payment.get(0).getImagenmgray()).into(img_one);


            }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Reorder_delete();
        finish();
    }

    public void get_reorder() {

        String url = Config.app_url + Config.viewreorder;
        JSONObject jobj_loginuser = new JSONObject();
        obj_dialog.show();
        try {

            JSONObject jobj_row = new JSONObject();

            jobj_row.put("orderid", order_id);


            JSONArray jarray_loginuser = new JSONArray();
            jarray_loginuser.put(jobj_row);

            jobj_loginuser.put("viewreorder", jarray_loginuser);
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

                Gson gson = new Gson();
                Model_view_order model = gson.fromJson(response, Model_view_order.class);

                if (model != null) {

                    array_place_order.clear();

                    res_nameee = model.getData().get(0).getRestname();
                    res_name.setText(model.getData().get(0).getRestname());
                    txt_order_no.setText(model.getData().get(0).getOrderno());
                    txt_subtotal.setText(model.getData().get(0).getTotal());
                    txt_total_cart.setText(model.getData().get(0).getFinaltotal());
                    res_address.setText(model.getData().get(0).getRestaddress());
                    paymenttype_id = model.getData().get(0).getPaymenttypeid();
                    array_place_order.addAll(model.getData().get(0).getProduct());
                    array_payment.addAll(model.getData().get(0).getPaymentarray());

                    if (model.getData().get(0).getPaymentarray().size() == 0) {
                        no_data.setVisibility(View.VISIBLE);
                        pay_one.setVisibility(View.INVISIBLE);
                        pay_two.setVisibility(View.INVISIBLE);
                        pay_three.setVisibility(View.INVISIBLE);
                    }

                    pay_one.setText(array_payment.get(0).getPaymenttype());
                    pay_two.setText(array_payment.get(1).getPaymenttype());
                    pay_three.setText(array_payment.get(2).getPaymenttype());

                    if (array_payment.get(0).getChecked().equals("true")) {
                        pay_one.setChecked(true);
                        pay_two.setChecked(false);
                        pay_three.setChecked(false);
                        value = pay_one.getText().toString();
                        index = "1";
                        Glide.with(context).load(array_payment.get(0).getImagenm()).into(img_one);
                        Glide.with(context).load(array_payment.get(1).getImagenmgray()).into(img_two);
                        Glide.with(context).load(array_payment.get(2).getImagenmgray()).into(img_three);

                    } else if (array_payment.get(1).getChecked().equals("true")) {
                        pay_two.setChecked(true);
                        pay_one.setChecked(false);
                        pay_three.setChecked(false);
                        value = pay_two.getText().toString();
                        index = "2";
                        Glide.with(context).load(array_payment.get(1).getImagenm()).into(img_two);
                        Glide.with(context).load(array_payment.get(0).getImagenmgray()).into(img_one);
                        Glide.with(context).load(array_payment.get(2).getImagenmgray()).into(img_three);

                    } else {
                        pay_three.setChecked(true);
                        pay_one.setChecked(false);
                        pay_two.setChecked(false);
                        value = pay_three.getText().toString();
                        index = "3";
                        Glide.with(context).load(array_payment.get(2).getImagenm()).into(img_three);
                        Glide.with(context).load(array_payment.get(1).getImagenmgray()).into(img_two);
                        Glide.with(context).load(array_payment.get(0).getImagenmgray()).into(img_one);
                    }

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
        Connection.postconnection(url, params, header, context, lis_res, lis_error);

    }

    public void Reorder_delete() {

//        obj_dialog.show();

        String url = Config.app_url + Config.reorderdelete + "/" + order_id;
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
                //  obj_dialog.dismiss();

                try {
                    JSONObject jobj = new JSONObject(response);
                    String res_flag = jobj.getString("status");
                    if (res_flag.equals("200")) {

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
        Connection.getconnectionVolley(url, param, header, context, lis_res, lis_error);

    }

    public void PLace_order() {


        String url = Config.app_url + Config.Placeorder;
        JSONObject jobj_loginuser = new JSONObject();
        obj_dialog.show();

        try {
            JSONObject jobj_main = new JSONObject();

            jobj_main.put("customerid", userid);
            jobj_main.put("restid", Res_id);
            jobj_main.put("couponcode", "");
            jobj_main.put("total", txt_subtotal.getText().toString());
            jobj_main.put("discount", "");
            jobj_main.put("discountper", "");
            jobj_main.put("serviceper", "");
            jobj_main.put("serviceamt", "");
            jobj_main.put("finaltotal", txt_total_cart.getText().toString());
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
                        json.put("optionrate", array_place_order.get(i).getProductoption().get(k).getProductoptiondetail().get(j).getOptionrate());
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

                            Intent intent = new Intent(ReorderActivity.this, Order_confirmationActivity.class);
                            intent.putExtra("Order_id", order_id);
                            intent.putExtra("Order_no", oredr_no);
                            intent.putExtra("payment_type", Payment_type);
                            intent.putExtra("final_total", finaltotal);
                            intent.putExtra("res_lat", res_lat);
                            intent.putExtra("res_long", res_long);
                            intent.putExtra("message", message);
                            intent.putExtra("val", "2");
                            intent.putExtra("back", "2");
                            intent.putExtra("res_name", res_nameee);
                            startActivity(intent);

                            Reorder_delete();

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

}
