package go.pickapp.Activity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import go.pickapp.Adapter.Expande_optionAdapter;
import go.pickapp.Adapter.Reorder_expandableAdapter;
import go.pickapp.Controller.Activity_indicator;
import go.pickapp.Controller.Config;
import go.pickapp.Controller.Connection;
import go.pickapp.Controller.DialogBox;
import go.pickapp.Controller.Edittext;
import go.pickapp.Controller.Textview;
import go.pickapp.Model.Model_edit_option;
import go.pickapp.Model.Model_option;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

import static go.pickapp.Activity.RestaurantActivity.availability;
import static go.pickapp.Adapter.Expande_optionAdapter.value_check_uncheck;

public class Edit_orderActivity extends AppCompatActivity {

    Pref_Master pref;
    Context context = this;
    ImageView product_img;
    ImageView img_minus, img_plus;
    Textview txt_value;
    int count = 0;
    Textview txt_desc, txt_rate, txt_total, txt_product_name;
    // Textview_Bold txt_order;
    Double total = 0.0;
    int value;
    RelativeLayout rr_update;
    Activity_indicator obj_dialog;
    ExpandableListView lv_option;
    Reorder_expandableAdapter listAdapter;
    ArrayList<Model_edit_option.Productoption> array_product = new ArrayList<>();
    DisplayMetrics metrics;
    int width;
    Double temp = 0.0;
    Double tempp = 0.0;
    ImageView img_back;
    CoordinatorLayout cordinator_layout;
    String add_value = "";
    String minus_value = "";
    FrameLayout frame;
    String orderdetailid = "";
    String productid = "";
    String restid = "";
    String orderid = "";
    String temp_arabic;
    String total_arabic;
    Edittext et_special;
    RelativeLayout rr_sepcial;
    ImageView img_special_one;
    ImageView img_special_two;
    RelativeLayout rr_instruction;
    int val = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);
        pref = new Pref_Master(context);
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            orderdetailid = extras.getString("orderdetailid");
            productid = extras.getString("productid");
            restid = extras.getString("restid");
            orderid = extras.getString("orderid");

        }


        cordinator_layout = (CoordinatorLayout) findViewById(R.id.cordinator_layout);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_minus = (ImageView) findViewById(R.id.img_minus);
        img_plus = (ImageView) findViewById(R.id.img_plus);
        txt_value = (Textview) findViewById(R.id.txt_value);
        product_img = (ImageView) findViewById(R.id.product_img);
        txt_desc = (Textview) findViewById(R.id.txt_desc);
        txt_rate = (Textview) findViewById(R.id.txt_rate);
        txt_total = (Textview) findViewById(R.id.txt_total);
        txt_product_name = (Textview) findViewById(R.id.txt_product_name);
        et_special = (Edittext) findViewById(R.id.et_special);
        //txt_order = (Textview_Bold) findViewById(R.id.txt_order);
        rr_update = (RelativeLayout) findViewById(R.id.rr_update);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(Product_detailsActivity.this, RestaurantActivity.class);
//                startActivity(i);
                finish();
            }
        });

        img_special_two = (ImageView) findViewById(R.id.img_special_two);
        img_special_one = (ImageView) findViewById(R.id.img_special_one);
        rr_sepcial = (RelativeLayout) findViewById(R.id.rr_sepcial);
        rr_instruction = (RelativeLayout) findViewById(R.id.rr_instruction);

        rr_instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (val == 0) {
                    rr_sepcial.setVisibility(View.GONE);
                    img_special_two.setVisibility(View.VISIBLE);
                    img_special_one.setVisibility(View.GONE);
                    val = 1;
                } else {
                    img_special_one.setVisibility(View.VISIBLE);
                    img_special_two.setVisibility(View.GONE);
                    rr_sepcial.setVisibility(View.VISIBLE);
                    val = 0;

                }
            }
        });

        rr_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Addcartvalidation();
            }
        });

        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;

        lv_option = (ExpandableListView) findViewById(R.id.lv_option);
        lv_option.setIndicatorBounds(width - GetDipsFromPixel(60), width - GetDipsFromPixel(5));


        img_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (value <= 1) {
                } else {
                    value--;
                    txt_value.setText(String.valueOf(value));
                    value = Integer.parseInt(txt_value.getText().toString());
                    total = Double.valueOf(String.valueOf(temp * value));
                    total_arabic = String.valueOf(total);
                    total = (int) Math.round(total * 100) / (double) 100;
                    txt_total.setText(String.valueOf(total));
                }
            }
        });

        img_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (value != 50) {
                    value++;
                    txt_value.setText(String.valueOf(value));
                    value = Integer.parseInt(txt_value.getText().toString());
                    total = Double.valueOf(String.valueOf(temp * value));
                    total_arabic = String.valueOf(total);
                    total = (int) Math.round(total * 100) / (double) 100;
                    txt_total.setText(String.valueOf(total));
                }
            }
        });

        lv_option.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                if (array_product.get(i).getProductoptiondetail().get(i1).selected) {
                    array_product.get(i).getProductoptiondetail().get(i1).setChecked("false");
                    array_product.get(i).getProductoptiondetail().get(i1).selected = false;
                    minus_value = array_product.get(i).getProductoptiondetail().get(i1).getRate();
                    Log.e("minus_value", minus_value);
                    settotal_count_minus(minus_value);

                } else {
                    array_product.get(i).getProductoptiondetail().get(i1).setChecked("true");
                    array_product.get(i).getProductoptiondetail().get(i1).selected = true;
                    add_value = array_product.get(i).getProductoptiondetail().get(i1).getRate();
                    Log.e("add_value", add_value);
                    settotal_count_plus(add_value);

                }
                listAdapter.notifyDataSetChanged();
                return true;
            }
        });
        final int[] size = {array_product.size()};
        String string = String.valueOf(size[0]);
        boolean arrraaay = Boolean.valueOf(string);

       // lv_option.setNestedScrollingEnabled(arrraaay);
        frame = (FrameLayout) findViewById(R.id.frame);

        get_product_details();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent i = new Intent(Product_detailsActivity.this, RestaurantActivity.class);
//        startActivity(i);
        finish();

    }

    public int GetDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    public void get_product_details() {

        obj_dialog.show();

        String url = Config.app_url + Config.reorderproductdetail;
        JSONObject jobj_loginuser = new JSONObject();
        obj_dialog.show();
        try {

            JSONObject jobj_row = new JSONObject();

            jobj_row.put("orderid", orderid);
            jobj_row.put("orderdetailid", orderdetailid);
            jobj_row.put("productid", productid);
            jobj_row.put("restid", restid);


            JSONArray jarray_loginuser = new JSONArray();
            jarray_loginuser.put(jobj_row);

            jobj_loginuser.put("reorderproductdetail", jarray_loginuser);
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
                Model_edit_option model = gson.fromJson(response, Model_edit_option.class);

                if (model != null) {
                    et_special.setText(model.getData().get(0).getSpecialinstruction());
                    txt_product_name.setText(model.getData().get(0).getProductname());
                    txt_desc.setText(Html.fromHtml(model.getData().get(0).getLongdesc()));
                    txt_rate.setText(model.getData().get(0).getRate());
                    txt_value.setText(model.getData().get(0).getQty());
                    value = Integer.parseInt(model.getData().get(0).getQty());
                    // rate = Integer.parseInt(model.getData().get(0).getRate());
                    txt_total.setText(model.getData().get(0).getFinalrate());
                    //txt_order.setText(model.getData().get(0).getOrderstatus());
                    temp = Double.valueOf(model.getData().get(0).getRatetotal());
                    Log.e("temp", ":" + temp);
                    tempp = Double.valueOf(model.getData().get(0).getRatetotal());
                    temp_arabic = model.getData().get(0).getRate();

                    Glide.with(context).load(model.getData().get(0).getImage()).placeholder(R.drawable.res_bg_150).diskCacheStrategy(DiskCacheStrategy.ALL).into(product_img);
                    array_product.addAll(model.getData().get(0).getProductoption());

                    listAdapter = new Reorder_expandableAdapter(Edit_orderActivity.this, context, array_product, pref);
                    lv_option.setAdapter(listAdapter);
                    setListViewHeight(lv_option, array_product.size());
                    //setExpandableListViewHeightBasedOnChildren(lv_option);
                    listAdapter.notifyDataSetChanged();
                    Log.e("arraylist_product", ":" + array_product.size());
                    for (int i = 0; i < array_product.size(); i++) {
                        lv_option.expandGroup(i);
                        setListViewHeight(lv_option, array_product.size());
                        //setExpandableListViewHeightBasedOnChildren(lv_option);
                    }

                    if (array_product.size() == 0) {
                        lv_option.setVisibility(View.GONE);
                    } else {
                        lv_option.setVisibility(View.VISIBLE);
                    }

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

    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        // ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 300;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }


    public void settotal_count_plus(String rate) {
        Log.e("english_rate_plus", ":" + rate);
        temp = temp + Double.valueOf(rate);
        Log.e("english_total_plus", ":" + temp);
        temp_arabic = String.valueOf(temp);
        temp = (int) Math.round(temp * 100) / (double) 100;
        Log.e("temp_arabic", ":" + temp);
        txt_rate.setText(String.valueOf(temp));
        value = Integer.parseInt(txt_value.getText().toString());
        total = Double.valueOf(String.valueOf(temp * value));
        total_arabic = String.valueOf(total);
        total = (int) Math.round(total * 100) / (double) 100;
        txt_total.setText(String.valueOf(total));
        value_check_uncheck = "1";

    }

    public void settotal_count_minus(String rate) {
        Log.e("arabic_rate_minus", ":" + rate);
        temp = temp - Double.valueOf(rate);
        Log.e("arabic_total_minus", ":" + temp);
        temp_arabic = String.valueOf(temp);
        temp = (int) Math.round(temp * 100) / (double) 100;
        Log.e("temp_arabic", ":" + temp);
        txt_rate.setText(String.valueOf(temp));
        value = Integer.parseInt(txt_value.getText().toString());
        total = Double.valueOf(String.valueOf(temp * value));
        total_arabic = String.valueOf(total);
        total = (int) Math.round(total * 100) / (double) 100;
        txt_total.setText(String.valueOf(total));
        value_check_uncheck = "0";

    }

    private void Addcartvalidation() {

        String url = Config.app_url + Config.addcartvalidation;
        JSONObject jobj_loginuser = new JSONObject();
        obj_dialog.show();
        try {

            JSONObject jobj_row = new JSONObject();

            jobj_row.put("cartid", "");
            jobj_row.put("userid", pref.getUID());
            jobj_row.put("restid", restid);
            jobj_row.put("productid", productid);
            jobj_row.put("qty", txt_value.getText().toString());
            jobj_row.put("rate", txt_rate.getText().toString());
            jobj_row.put("total", "");


            JSONArray product = new JSONArray();
            for (int i = 0; i < array_product.size(); i++) {
                JSONObject jsonObject = new JSONObject();


                JSONArray jarray = new JSONArray();
                for (int j = 0; j < array_product.get(i).getProductoptiondetail().size(); j++) {
                    if (array_product.get(i).getProductoptiondetail().get(j).isSelected() == true) {
                        JSONObject json = new JSONObject();
                        json.put("productoptiondetailid", array_product.get(i).getProductoptiondetail().get(j).getProductoptiondetailid());
                        json.put("optionrate", array_product.get(i).getProductoptiondetail().get(j).getRate());
                        jarray.put(json);
                    }


                }
                if (jarray.length() > 0) {
                    jsonObject.put("productoptionid", array_product.get(i).getProductoptionid());
                    product.put(jsonObject);
                    jsonObject.put("productoptiondetail", jarray);
                }
            }
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
                        reorderproductupdate();

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

    private void reorderproductupdate() {

        String url = Config.app_url + Config.reorderproductupdate;
        JSONObject jobj_loginuser = new JSONObject();
        obj_dialog.show();
        try {

            JSONObject jobj_row = new JSONObject();

            jobj_row.put("cartid", "");
            jobj_row.put("orderid", orderid);
            jobj_row.put("orderdetailid", orderdetailid);
            jobj_row.put("productid", productid);
            jobj_row.put("qty", txt_value.getText().toString());
            jobj_row.put("rate", txt_rate.getText().toString());
            jobj_row.put("total", "");
            jobj_row.put("specialinstruction", et_special.getText().toString());


            JSONArray product = new JSONArray();
            for (int i = 0; i < array_product.size(); i++) {
                JSONObject jsonObject = new JSONObject();


                JSONArray jarray = new JSONArray();
                for (int j = 0; j < array_product.get(i).getProductoptiondetail().size(); j++) {
                    if (array_product.get(i).getProductoptiondetail().get(j).isSelected() == true) {
                        JSONObject json = new JSONObject();
                        json.put("productoptiondetailid", array_product.get(i).getProductoptiondetail().get(j).getProductoptiondetailid());
                        json.put("optionrate", array_product.get(i).getProductoptiondetail().get(j).getRate());
                        jarray.put(json);
                    }


                }
                if (jarray.length() > 0) {
                    jsonObject.put("productoptionid", array_product.get(i).getProductoptionid());
                    product.put(jsonObject);
                    jsonObject.put("productoptiondetail", jarray);
                }
            }
            jobj_row.put("productoption", product);


            JSONArray jarray_loginuser = new JSONArray();
            jarray_loginuser.put(jobj_row);

            jobj_loginuser.put("reorderproductupdate", jarray_loginuser);
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
                        finish();
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
