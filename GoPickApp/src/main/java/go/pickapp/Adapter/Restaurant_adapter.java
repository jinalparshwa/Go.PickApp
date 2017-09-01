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
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import go.pickapp.Activity.RestaurantActivity;
import go.pickapp.Controller.CustomFilter;
import go.pickapp.Controller.DialogBox;
import go.pickapp.Controller.Textview;
import go.pickapp.Model.Model_restaurant;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

import static go.pickapp.Activity.FilterActivity.cusine_new;
import static go.pickapp.Activity.FilterActivity.filter_new;


/**
 * Created by Admin on 5/10/2017.
 */

public class Restaurant_adapter extends RecyclerView.Adapter<Restaurant_adapter.Viewholder> {

    FragmentActivity activity;
    public ArrayList<Model_restaurant> arrayList = new ArrayList<>();
    ArrayList<Model_restaurant> filterList = new ArrayList<>();
    CustomFilter filter;
    Pref_Master pref;


    public Restaurant_adapter(FragmentActivity activity, ArrayList<Model_restaurant> arraylist, Pref_Master pref) {
        super();
        this.activity = activity;
        this.arrayList = arraylist;
        this.filterList = arraylist;
        this.pref = pref;

    }

    @Override
    public Restaurant_adapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_restaurant_list, parent, false);
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(Restaurant_adapter.Viewholder holder, final int position) {
        final Model_restaurant model = arrayList.get(position);

        holder.txt_duration.setText(model.getPrepare_time());
        holder.txt_distance.setText(model.getDistance());
        holder.txt_rating.setText(model.getRating());
        holder.ll_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter_new = "";
                cusine_new = "";

                if (pref.getCart_count() == 0) {
                    pref.setBack_map("2");
                    pref.setRes_id(arrayList.get(position).getRes_id());
                    pref.setRes_name(arrayList.get(position).getRes_name());
                    Intent intent = new Intent(activity, RestaurantActivity.class);
                    // intent.putExtra("fragmentcode", Config.Fragment_ID.Restaurant);
                    activity.startActivity(intent);
                } else {
                    if (!pref.getStr_res_id().equals(arrayList.get(position).getRes_id())) {
                        DialogBox.setremove_cart(activity, activity.getResources().getString(R.string.Remove_cart_popup));
                    } else {
                        pref.setBack_map("2");
                        pref.setRes_id(arrayList.get(position).getRes_id());
                        pref.setRes_name(arrayList.get(position).getRes_name());
                        Intent intent = new Intent(activity, RestaurantActivity.class);
                        // intent.putExtra("fragmentcode", Config.Fragment_ID.Restaurant);
                        activity.startActivity(intent);

                    }

                }

            }
        });


        Glide.with(activity).load(model.getLogo()).placeholder(R.drawable.res_bg_150).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.res_img);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
//
//    @Override
//    public Filter getFilter() {
//        if (filter == null) {
//            filter = new CustomFilter(filterList, this);
//        }
//        return filter;
//    }


    public class Viewholder extends RecyclerView.ViewHolder {

        Textview txt_duration;
        Textview txt_distance;
        Textview txt_rating;
        ImageView res_img;
        LinearLayout ll_list;

        public Viewholder(View view) {
            super(view);

            txt_duration = (Textview) view.findViewById(R.id.txt_duration);
            txt_distance = (Textview) view.findViewById(R.id.txt_distance);
            txt_rating = (Textview) view.findViewById(R.id.txt_rating);
            res_img = (ImageView) view.findViewById(R.id.res_img);
            ll_list = (LinearLayout) view.findViewById(R.id.ll_list);


        }
    }

//    public class CustomFilter extends Filter {
//        private Restaurant_adapter mAdapter;
//
//        private CustomFilter(Restaurant_adapter mAdapter) {
//            super();
//            this.mAdapter = mAdapter;
//        }
//
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            filteredList.clear();
//            final FilterResults results = new FilterResults();
//            if (constraint.length() == 0) {
//                filteredList.addAll(arrayList);
//            } else {
//                final String filterPattern = constraint.toString().toLowerCase().trim();
//                for (Model_restaurant mWords : arrayList) {
//                    if (mWords.getRes_name().toLowerCase().startsWith(filterPattern)) {
//                        filteredList.add(mWords);
//                    }
//                }
//            }
//            Log.e("Count Number ", ":" + filteredList.size());
//            results.values = filteredList;
//            results.count = filteredList.size();
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            Log.e("Count Number 2 ", ":" + ((ArrayList<Model_restaurant>) results.values).size());
//            this.mAdapter.notifyDataSetChanged();
//        }
//    }
}

