package go.pickapp.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import go.pickapp.Activity.Edit_orderActivity;
import go.pickapp.Controller.Textview;
import go.pickapp.Controller.Textview_Bold;
import go.pickapp.Model.Model_cart;
import go.pickapp.Model.Model_view_order;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

/**
 * Created by Admin on 5/31/2017.
 */

public class Reorder_Adapter extends RecyclerView.Adapter<Reorder_Adapter.Viewholder> {

    Context context;
    ArrayList<Model_view_order.Product> arrayList = new ArrayList<>();
    Pref_Master pref;
    String order_id = "";
    String res_id = "";

    public Reorder_Adapter(Context context, ArrayList<Model_view_order.Product> arraylist, Pref_Master pref, String order_id, String res_id) {
        super();
        this.context = context;
        this.arrayList = arraylist;
        this.pref = pref;
        this.order_id = order_id;
        this.res_id = res_id;
    }

    @Override
    public Reorder_Adapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_reorder_items, parent, false);
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(Reorder_Adapter.Viewholder holder, int position) {
        final Model_view_order.Product model = arrayList.get(position);


        Glide.with(context).load(model.getImage()).into(holder.dish_image);
        holder.dish_title.setText(model.getProductname());
        holder.dish_desc.setText(Html.fromHtml(model.getShortdesc()));
        holder.txt_qty.setText(model.getQty());
        holder.txt_price.setText(model.getRate());
        holder.txt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, Edit_orderActivity.class);
                i.putExtra("orderdetailid", model.getOrderdetailid());
                i.putExtra("productid", model.getProductid());
                i.putExtra("restid", res_id);
                i.putExtra("orderid", order_id);
                context.startActivity(i);
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

        ImageView dish_image;
        Textview_Bold dish_title;
        Textview dish_desc;
        Textview txt_qty;
        Textview txt_price;
        Textview txt_edit;


        public Viewholder(View view) {
            super(view);

            dish_image = (ImageView) view.findViewById(R.id.dish_image);
            dish_title = (Textview_Bold) view.findViewById(R.id.dish_title);
            dish_desc = (Textview) view.findViewById(R.id.dish_desc);
            txt_qty = (Textview) view.findViewById(R.id.txt_qty);
            txt_price = (Textview) view.findViewById(R.id.txt_price);
            txt_edit = (Textview) view.findViewById(R.id.txt_edit);


        }
    }
}
