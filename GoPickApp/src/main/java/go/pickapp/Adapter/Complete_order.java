package go.pickapp.Adapter;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import go.pickapp.Activity.Add_reviewActivity;
import go.pickapp.Activity.Order_detailsActivity;
import go.pickapp.Activity.ReorderActivity;
import go.pickapp.Controller.DialogBox;
import go.pickapp.Controller.Textview;
import go.pickapp.Controller.Textview_Bold;
import go.pickapp.Fragments.Order_history;
import go.pickapp.Model.Model_order;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

/**
 * Created by Admin on 5/31/2017.
 */

public class Complete_order extends RecyclerView.Adapter<Complete_order.Viewholder> {

    FragmentActivity activity;
    ArrayList<Model_order> arrayList = new ArrayList<>();
    Pref_Master pref;
    String val = "";
    Order_history order_history;


    public Complete_order(Order_history order_history, FragmentActivity activity, ArrayList<Model_order> arraylist, Pref_Master pref) {
        super();
        this.activity = activity;
        this.arrayList = arraylist;
        this.pref = pref;
        this.order_history = order_history;
    }

    @Override
    public Complete_order.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_order_list, parent, false);
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
                if (!model.getStatus().equals("6")) {
                    Intent i = new Intent(activity, Order_detailsActivity.class);
                    i.putExtra("Order_id", model.getOrder_id());
                    i.putExtra("value", 1);
                    i.putExtra("back", "1");
                    i.putExtra("val", "1");
                    pref.setStr_refresh_back(1);
                    activity.startActivity(i);
                } else {
                    DialogBox.setPopup(activity, model.getDialogmsg());
                }
            }
        });

        if (model.getReview_status().equals("Yes")) {
            holder.add_review.setVisibility(View.VISIBLE);
            holder.ll_status.setVisibility(View.VISIBLE);
        } else {
            holder.add_review.setVisibility(View.GONE);
            holder.ll_status.setVisibility(View.GONE);
        }
        if (model.getReorder_status().equals("Yes")) {
            holder.reorder.setVisibility(View.VISIBLE);
            holder.ll_status.setVisibility(View.VISIBLE);
        } else {
            holder.reorder.setVisibility(View.GONE);
            holder.ll_status.setVisibility(View.GONE);
        }
        holder.add_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, Add_reviewActivity.class);
                i.putExtra("Order_id", model.getOrder_id());
                i.putExtra("value", 1);
                activity.startActivity(i);
            }
        });

        holder.reorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.getAvilbe_status().equals("1")) {
                    order_history.Reorder(model.getOrder_id());
                } else {
                    DialogBox.setPopup(activity, activity.getResources().getString(R.string.Right_now_closed));
                }
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
