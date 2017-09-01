package go.pickapp.Adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import go.pickapp.Activity.CartActivity;
import go.pickapp.Controller.Activity_indicator;
import go.pickapp.Controller.Config;
import go.pickapp.Controller.Connection;
import go.pickapp.Controller.DialogBox;
import go.pickapp.Controller.Textview;
import go.pickapp.Controller.Textview_Bold;
import go.pickapp.Model.Model_cart;
import go.pickapp.Model.Model_cart_option;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

import static go.pickapp.Activity.CartActivity.str;

/**
 * Created by Admin on 5/23/2017.
 */

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.Viewholder> {

    Context context;
    ArrayList<Model_cart> array_cart = new ArrayList<>();
    CartActivity activity;
    Runnable api_f;
    int qty = 0;
    Pref_Master pref;
    int qty_value = 0;
    String plus_mnus = "0";
    Activity_indicator obj_dialog;


    public cartAdapter(Context context, ArrayList<Model_cart> array_cart, CartActivity activity, Runnable api_f, Pref_Master pref) {
        this.context = context;
        this.array_cart = array_cart;
        this.activity = activity;
        this.api_f = api_f;
        this.pref = pref;
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

    }


    @Override
    public cartAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_cart_list, parent, false);
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(final cartAdapter.Viewholder holder, final int position) {

        final Model_cart model = array_cart.get(position);
        // rate = Double.valueOf((model.getRate()));
        holder.dish_title.setText(model.getProduct_name());
        holder.dish_desc.setText(Html.fromHtml(model.getProduct_string()));
        holder.txt_value.setText(model.getQty());
        qty_value = Integer.parseInt(model.getQty());
        holder.dish_rate.setText(model.getSub_total());
        Glide.with(context).load(model.getProduct_image()).into(holder.dish_image);
        holder.remove_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (array_cart.size() == 1) {
                    activity.Remove_product(model.getProduct_id(), model.getUsercarttempid(), 1);
                    //Log.e("Quantity_value", ":" + qty_value);
                    pref.setCart_count(pref.getCart_count() - Integer.parseInt(model.getQty()));

                } else {
                    activity.Remove_product(model.getProduct_id(), model.getUsercarttempid(), 2);
                    //Log.e("Quantity_value", ":" + qty_value);
                    pref.setCart_count(pref.getCart_count() - Integer.parseInt(model.getQty()));
                }
            }
        });
        holder.img_plus.setTag(position);
        holder.img_minus.setTag(position);

        holder.img_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                qty = Integer.parseInt((model.getQty()));
                qty = qty + 1;
                holder.txt_value.setText(String.valueOf(qty));
                holder.dish_rate.setText(model.getSub_total());
                Update_cart(model.getProduct_id(), model.getRate(), model.getUsercarttempid());
                plus_mnus = "0";
                //pref.setCart_count(qty);


            }
        });
        holder.img_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                qty = Integer.parseInt((model.getQty()));
                if (qty <= 1) {

                } else {
                    qty = qty - 1;
                    holder.txt_value.setText(String.valueOf(qty));
                    holder.dish_rate.setText(model.getSub_total());
                    Update_cart(model.getProduct_id(), model.getRate(), model.getUsercarttempid());
                    plus_mnus = "1";
                    // pref.setCart_count(qty);

                }


            }
        });


    }


    @Override
    public int getItemCount() {
        return array_cart.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        Textview remove_product;
        ImageView dish_image;
        Textview_Bold dish_title;
        Textview dish_desc;
        Textview txt_value;
        Textview dish_rate;
        ImageView img_minus;
        ImageView img_plus;

        public Viewholder(View view) {
            super(view);

            remove_product = (Textview) view.findViewById(R.id.remove_product);
            dish_image = (ImageView) view.findViewById(R.id.dish_image);
            dish_title = (Textview_Bold) view.findViewById(R.id.dish_title);
            dish_desc = (Textview) view.findViewById(R.id.dish_desc);
            txt_value = (Textview) view.findViewById(R.id.txt_value);
            dish_rate = (Textview) view.findViewById(R.id.dish_rate);
            img_minus = (ImageView) view.findViewById(R.id.img_minus);
            img_plus = (ImageView) view.findViewById(R.id.img_plus);


        }
    }


    private void Update_cart(String product_id, String rate, String temp_id) {

        obj_dialog.show();

        String url = Config.app_url + Config.Updatecart;
        JSONObject jobj_loginuser = new JSONObject();
        try {

            JSONObject jobj_row = new JSONObject();

            jobj_row.put("cartid", pref.getCart_id());
            jobj_row.put("userid", pref.getUID());
            jobj_row.put("restid", pref.getStr_res_id());
            jobj_row.put("productid", product_id);
            jobj_row.put("qty", qty);
            jobj_row.put("rate", rate);
            jobj_row.put("usercarttempid", temp_id);


            JSONArray jarray_loginuser = new JSONArray();
            jarray_loginuser.put(jobj_row);

            jobj_loginuser.put("updatecart", jarray_loginuser);
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

                        if (plus_mnus.equals("0")) {
                            pref.setCart_count(pref.getCart_count() + 1);
                        } else {
                            if (pref.getCart_count() <= 1) {
                            } else {
                                pref.setCart_count(pref.getCart_count() - 1);
                            }
                        }

                        Log.e("Pref_cart_count_value", "===>" + pref.getCart_count());

                        pref.setCart_id(job.getString("cartid"));
                        api_f.run();


                    } else {
                        DialogBox.setPopup(context, jobj.getString("msg").toString());
                    }

                } catch (
                        Exception e)

                {
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
