package go.pickapp.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.swipe.SwipeLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import go.pickapp.Adapter.ExpandableListAdapter;
import go.pickapp.Controller.Activity_indicator;
import go.pickapp.Controller.Config;
import go.pickapp.Controller.Connection;
import go.pickapp.Controller.DialogBox;
import go.pickapp.Controller.SwipeDetector;
import go.pickapp.Controller.Textview;
import go.pickapp.Controller.Textview_Bold;
import go.pickapp.Model.Model_New_Product;
import go.pickapp.Model.Model_child;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

/**
 * Created by Admin on 6/16/2017.
 */

public class RestaurantActivity extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    Context context = this;
    String res = "";
    Pref_Master pref;

    Textview_Bold txt_cusine, txt_time, txt_rating;
    ImageView full_img_res;
    ArrayList<Model_New_Product.Product> array_product = new ArrayList<>();
    ArrayList<Model_child> array_list = new ArrayList<>();
    Textview product_name;
    Activity_indicator obj_dialog;
    ImageView img_about;
    ImageView img_back;
    RelativeLayout fab;
    Textview_Bold float_textview;
    public static String availability = "";
    // Textview txt_hours;
    Textview available;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_fragment);
        pref = new Pref_Master(context);
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));


        res = pref.getStr_res_id();
        Log.e("jinal", res);
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        txt_cusine = (Textview_Bold) findViewById(R.id.txt_cusine);
        txt_time = (Textview_Bold) findViewById(R.id.txt_time);
        txt_rating = (Textview_Bold) findViewById(R.id.txt_rating);
        full_img_res = (ImageView) findViewById(R.id.full_img_res);
        product_name = (Textview) findViewById(R.id.product_name);
        img_about = (ImageView) findViewById(R.id.img_about);
        img_back = (ImageView) findViewById(R.id.img_back);
        // txt_hours = (Textview) findViewById(R.id.txt_hours);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                if (pref.getCart_count() == 0) {
                    if (pref.getStr_back_map().equals("1")) {
                        Intent i = new Intent(RestaurantActivity.this, MainActivity.class);
                        i.putExtra("fragmentcode", Config.Fragment_ID.home);
                        startActivity(i);
                        finish();

                    } else {
                        Intent i = new Intent(RestaurantActivity.this, MainActivity.class);
                        i.putExtra("fragmentcode", Config.Fragment_ID.res_list);
                        startActivity(i);
                        finish();
                    }

                } else {
                    DialogBox.setremove_cart(context, getResources().getString(R.string.Remove_cart_popup));


                }

            }
        });
        available = (Textview) findViewById(R.id.available);
        fab = (RelativeLayout) findViewById(R.id.fab);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);
        fab.setAnimation(myAnim);
        float_textview = (Textview_Bold) findViewById(R.id.float_textview);
        String value = String.valueOf(pref.getCart_count());
        float_textview.setText(value);
        get_res_deatils();

        img_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RestaurantActivity.this, Restaurant_detailsActivity.class);
                startActivity(i);
            }
        });
        Log.e("jinal_Cart_count", "" + pref.getCart_count());
        Log.e("jinal_Cart_id", pref.getCart_id());
        if (pref.getCart_count() == 0) {
            fab.setVisibility(View.GONE);
        } else {
            fab.setVisibility(View.VISIBLE);
        }

//        if (pref.getCart_id().equals("")) {
//            // pref.setCart_count(0);
//            fab.setVisibility(View.GONE);
//        } else {
//            fab.setVisibility(View.VISIBLE);
//        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, CartActivity.class);
                // i.putExtra("fragmentcode", Config.Fragment_ID.cart);
                startActivity(i);
            }
        });

    }


    @Override
    public void onBackPressed() {
        if (pref.getCart_count() == 0) {
            if (pref.getStr_back_map().equals("1")) {
                Intent i = new Intent(RestaurantActivity.this, MainActivity.class);
                i.putExtra("fragmentcode", Config.Fragment_ID.home);
                startActivity(i);
                finish();

            } else {
                Intent i = new Intent(RestaurantActivity.this, MainActivity.class);
                i.putExtra("fragmentcode", Config.Fragment_ID.res_list);
                startActivity(i);
                finish();
            }

        } else {
            DialogBox.setremove_cart(context, getResources().getString(R.string.Remove_cart_popup));


        }
    }

    public void get_res_deatils() {

        // obj_dialog.show();

        String url = Config.app_url + Config.Getproductlist + "/" + res;
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

                Gson gson = new Gson();
                Model_New_Product modelProduct = gson.fromJson(response, Model_New_Product.class);

                if (modelProduct != null) {

                    availability = modelProduct.getData().get(0).getAvailabilitystatus();
                    product_name.setText(modelProduct.getData().get(0).getName().toUpperCase());
                    txt_cusine.setText(modelProduct.getData().get(0).getCuisine());
                    txt_time.setText(modelProduct.getData().get(0).getPreparationtime());
                    txt_rating.setText(modelProduct.getData().get(0).getRating());
                    // txt_hours.setText(modelProduct.getData().get(0).getCurrentdatetime());
                    available.setText(modelProduct.getData().get(0).getAvailabilitystr());
                    Glide.with(context).load(modelProduct.getData().get(0).getLogo()).placeholder(R.drawable.res_bg_150).diskCacheStrategy(DiskCacheStrategy.ALL).into(full_img_res);

                    array_product.addAll(modelProduct.getData().get(0).getProduct());

                    listAdapter = new ExpandableListAdapter(RestaurantActivity.this, context, array_product, pref);
                    expListView.setAdapter(listAdapter);
                    listAdapter.notifyDataSetChanged();

                    Log.e("Array_product_size", ":" + array_product.size());
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
        Connection.getconnectionVolley(url, param, header, context, lis_res, lis_error);

    }

    public void Add_cart(String product_id, String rate, String total) {

        String url = Config.app_url + Config.Addcart;
        JSONObject jobj_loginuser = new JSONObject();
        obj_dialog.show();
        try {

            JSONObject jobj_row = new JSONObject();

            jobj_row.put("cartid", pref.getCart_id());
            jobj_row.put("userid", pref.getUID());
            jobj_row.put("restid", pref.getStr_res_id());
            jobj_row.put("productid", product_id);
            jobj_row.put("qty", "1");
            jobj_row.put("rate", rate);
            jobj_row.put("total", total);


            JSONArray product = new JSONArray();
//            for (int i = 0; i < array_product.size(); i++) {
//                JSONObject jsonObject = new JSONObject();
//
//
//                JSONArray jarray = new JSONArray();
//                for (int j = 0; j < array_product.get(i).getProductoptiondetail().size(); j++) {
//                    if (array_product.get(i).getProductoptiondetail().get(j).isSelected() == true) {
//                        JSONObject json = new JSONObject();
//                        json.put("productoptiondetailid", array_product.get(i).getProductoptiondetail().get(j).getProductoptiondetailid());
//                        json.put("optionrate", array_product.get(i).getProductoptiondetail().get(j).getRate());
//                        jarray.put(json);
//                    }
//
//
//                }
//                if (jarray.length() > 0) {
//                    jsonObject.put("productoptionid", array_product.get(i).getProductoptionid());
//                    product.put(jsonObject);
//                    jsonObject.put("productoptiondetail", jarray);
//                }
//            }
            jobj_row.put("productoption", product);

            JSONArray jarray_loginuser = new JSONArray();
            jarray_loginuser.put(jobj_row);

            jobj_loginuser.put("addcart", jarray_loginuser);
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
                        JSONObject job = jobj.getJSONObject("data");

                        pref.setCart_id(job.getString("cartid"));
                        // res_res_id = (job.getString("restid"));
                        //Log.e("RES_ID", "Enter" + res_res_id);
                        Intent i = new Intent(context, RestaurantActivity.class);
                        startActivity(i);
                        pref.setCart_count(pref.getCart_count() + Integer.parseInt("1"))
                        ;

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
