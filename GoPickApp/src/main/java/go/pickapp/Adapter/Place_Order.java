package go.pickapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import go.pickapp.Controller.Textview;
import go.pickapp.Model.Model_cart;
import go.pickapp.Model.Model_place_order;
import go.pickapp.R;

/**
 * Created by Admin on 5/23/2017.
 */

public class Place_Order extends RecyclerView.Adapter<Place_Order.Viewholder> {

    Context context;
    ArrayList<Model_place_order.Product> array_cart = new ArrayList<>();


    public Place_Order(Context context, ArrayList<Model_place_order.Product> array_cart) {
        this.context = context;
        this.array_cart = array_cart;


    }


    @Override
    public Place_Order.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_place_order_list, parent, false);
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(final Place_Order.Viewholder holder, final int position) {

        final Model_place_order.Product model = array_cart.get(position);

        holder.txt_item.setText(model.getProductname());
        holder.txt_qty.setText(model.getQty());
        holder.txt_price.setText(model.getSubtotal());

    }

    @Override
    public int getItemCount() {
        return array_cart.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        Textview txt_item;
        Textview txt_qty;
        Textview txt_price;


        public Viewholder(View view) {
            super(view);

            txt_item = (Textview) view.findViewById(R.id.txt_item);
            txt_qty = (Textview) view.findViewById(R.id.txt_qty);
            txt_price = (Textview) view.findViewById(R.id.txt_price);


        }
    }

}
