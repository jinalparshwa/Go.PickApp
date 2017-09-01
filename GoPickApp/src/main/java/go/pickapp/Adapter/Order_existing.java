package go.pickapp.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.SimpleResource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import go.pickapp.Activity.MainActivity;
import go.pickapp.Activity.Order_detailsActivity;
import go.pickapp.Controller.Config;
import go.pickapp.Controller.Connection;
import go.pickapp.Controller.DialogBox;
import go.pickapp.Controller.Textview;
import go.pickapp.Controller.Textview_Bold;
import go.pickapp.Fragments.Existing_order;
import go.pickapp.Model.Model_city;
import go.pickapp.Model.Model_order;
import go.pickapp.Model.Model_restaurant;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

/**
 * Created by Admin on 5/31/2017.
 */

public class Order_existing extends RecyclerView.Adapter<Order_existing.Viewholder> {

    FragmentActivity activity;
    ArrayList<Model_order> arrayList = new ArrayList<>();
    Pref_Master pref;
    String val = "";
    Existing_order existing_order;


    public Order_existing(Existing_order existing_order, FragmentActivity activity, ArrayList<Model_order> arraylist, Pref_Master pref) {
        super();
        this.activity = activity;
        this.arrayList = arraylist;
        this.pref = pref;
        this.existing_order = existing_order;
    }

    @Override
    public Order_existing.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_order_complete, parent, false);
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        final Model_order model = arrayList.get(position);

        holder.res_name.setText(model.getRes_name());
        holder.order_no.setText(model.getOrder_no());
        holder.order_date.setText(model.getDate());
        holder.order_total.setText(model.getTotal() + " " + "KD");
        holder.order_status.setText(model.getStatus_name());
        holder.ll_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, Order_detailsActivity.class);
                i.putExtra("Order_id", model.getOrder_id());
                i.putExtra("value", 0);
                i.putExtra("val", "1");
                i.putExtra("back", "2");
                pref.setStr_refresh_back(0);
                activity.startActivity(i);
            }
        });

        if (model.getCancel_status().equals("Yes")) {
            holder.cancel_order.setVisibility(View.VISIBLE);
            holder.ll_status.setVisibility(View.VISIBLE);
        } else {
            holder.cancel_order.setVisibility(View.GONE);
            holder.ll_status.setVisibility(View.GONE);
        }

        holder.cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(activity);
                View v = li.inflate(R.layout.alert_cancel_order, null);
                final android.support.v7.app.AlertDialog alert = new android.support.v7.app.AlertDialog.Builder(activity).setView(v).show();
                alert.setCancelable(false);
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Textview ok = (Textview) v.findViewById(R.id.con_ok);
                Textview cancel = (Textview) v.findViewById(R.id.con_cancel);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        existing_order.Cancel_order(model.getOrder_id());
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


    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder {

        Textview_Bold res_name;
        Textview order_no;
        Textview order_date;
        Textview order_total;
        Textview order_status;
        LinearLayout ll_order;
        Textview add_review, reorder, cancel_order;
        LinearLayout ll_status;


            public Viewholder(View view) {
                super(view);

            res_name = (Textview_Bold) view.findViewById(R.id.res_name);
            order_no = (Textview) view.findViewById(R.id.order_no);
            order_date = (Textview) view.findViewById(R.id.order_date);
            order_total = (Textview) view.findViewById(R.id.order_total);
            order_status = (Textview) view.findViewById(R.id.order_status);
            ll_order = (LinearLayout) view.findViewById(R.id.ll_order);
            add_review = (Textview) view.findViewById(R.id.add_review);
            reorder = (Textview) view.findViewById(R.id.reorder);
            cancel_order = (Textview) view.findViewById(R.id.cancel_order);
            ll_status = (LinearLayout) view.findViewById(R.id.ll_status);


        }
    }


}
