package go.pickapp.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import go.pickapp.Activity.Order_detailsActivity;
import go.pickapp.Controller.Textview;
import go.pickapp.Controller.Textview_Bold;
import go.pickapp.Model.Model_cart;
import go.pickapp.Model.Model_order;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

/**
 * Created by Admin on 5/31/2017.
 */

public class Order_detailsAdapter extends RecyclerView.Adapter<Order_detailsAdapter.Viewholder> {

    Context context;
    ArrayList<Model_cart> arrayList = new ArrayList<>();
    Pref_Master pref;


    public Order_detailsAdapter(Context context, ArrayList<Model_cart> arraylist, Pref_Master pref) {
        super();
        this.context = context;
        this.arrayList = arraylist;
        this.pref = pref;
    }

    @Override
    public Order_detailsAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_order_details, parent, false);
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(Order_detailsAdapter.Viewholder holder, int position) {
        final Model_cart model = arrayList.get(position);

        Glide.with(context).load(model.getProduct_image()).into(holder.dish_image);
        holder.dish_title.setText(model.getProduct_name());
        holder.dish_desc.setText(Html.fromHtml(model.getShort_desc()));
        holder.txt_qty.setText(model.getQty());
        holder.txt_price.setText(model.getTotal_add());


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


        public Viewholder(View view) {
            super(view);

            dish_image = (ImageView) view.findViewById(R.id.dish_image);
            dish_title = (Textview_Bold) view.findViewById(R.id.dish_title);
            dish_desc = (Textview) view.findViewById(R.id.dish_desc);
            txt_qty = (Textview) view.findViewById(R.id.txt_qty);
            txt_price = (Textview) view.findViewById(R.id.txt_price);


        }
    }
}
