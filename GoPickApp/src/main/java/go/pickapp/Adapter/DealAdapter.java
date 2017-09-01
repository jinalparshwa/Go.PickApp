package go.pickapp.Adapter;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import go.pickapp.Activity.MainActivity;
import go.pickapp.Activity.RestaurantActivity;
import go.pickapp.Controller.Config;
import go.pickapp.Controller.DialogBox;
import go.pickapp.Controller.Textview;
import go.pickapp.Controller.Textview_Bold;
import go.pickapp.Model.Model_deal;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

/**
 * Created by Admin on 6/8/2017.
 */

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.Viewholder> {

    FragmentActivity activity;
    ArrayList<Model_deal> arrayList = new ArrayList<>();
    Pref_Master pref;


    public DealAdapter(FragmentActivity activity, ArrayList<Model_deal> arraylist, Pref_Master pref) {
        super();
        this.activity = activity;
        this.arrayList = arraylist;
        this.pref = pref;
    }

    @Override
    public DealAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_deals_item, parent, false);
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(DealAdapter.Viewholder holder, int position) {
        final Model_deal model = arrayList.get(position);

        holder.deal_title.setText(model.getName());
        holder.deal_code.setText(model.getCode());
        holder.deal_statemnt.setText(model.getStatement());
        Glide.with(activity).load(model.getImage()).into(holder.deal_image);
        holder.ll_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pref.getCart_count()==0) {
                    Intent i = new Intent(activity, RestaurantActivity.class);
                    pref.setRes_id(model.getRes_id());
                    activity.startActivity(i);
                    activity.finish();
                } else {
                    if (!pref.getStr_res_id().equals(model.getRes_id())) {
                        DialogBox.setremove_cart(activity, activity.getResources().getString(R.string.Remove_cart_popup));
                    } else {
                        Intent i = new Intent(activity, RestaurantActivity.class);
                        pref.setRes_id(model.getRes_id());
                        activity.startActivity(i);
                        activity.finish();
                    }
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

        ImageView deal_image;
        Textview_Bold deal_title;
        Textview deal_code;
        Textview deal_statemnt;
        LinearLayout ll_deal;


        public Viewholder(View view) {
            super(view);

            deal_image = (ImageView) view.findViewById(R.id.deal_image);
            deal_title = (Textview_Bold) view.findViewById(R.id.deal_title);
            deal_code = (Textview) view.findViewById(R.id.deal_code);
            deal_statemnt = (Textview) view.findViewById(R.id.deal_statemnt);
            ll_deal = (LinearLayout) view.findViewById(R.id.ll_deal);

        }
    }
}
