package go.pickapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.ArrayList;

import go.pickapp.Controller.Textview;
import go.pickapp.Controller.Textview_Bold;
import go.pickapp.Model.Model_cart;
import go.pickapp.Model.Model_restaurant;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

/**
 * Created by Admin on 6/8/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.Viewholder> {

    Context context;
    ArrayList<Model_restaurant> arrayList = new ArrayList<>();
    Pref_Master pref;


    public ReviewAdapter(Context context, ArrayList<Model_restaurant> arraylist, Pref_Master pref) {
        super();
        this.context = context;
        this.arrayList = arraylist;
        this.pref = pref;
    }

    @Override
    public ReviewAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_review_list, parent, false);
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.Viewholder holder, int position) {
        final Model_restaurant model = arrayList.get(position);

        holder.txt_date.setText(model.getReview_date());
        holder.txt_username.setText(model.getRating_username());
        holder.txt_comments.setText(model.getReview());
        holder.ratingbar.setRating(Float.parseFloat(model.getOverallrating()));


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

        SimpleRatingBar ratingbar;
        Textview txt_date,txt_username,txt_comments;

        public Viewholder(View view) {
            super(view);

            ratingbar=(SimpleRatingBar)view.findViewById(R.id.ratingbar);
            txt_date=(Textview)view.findViewById(R.id.txt_date);
            txt_username=(Textview)view.findViewById(R.id.txt_username);
            txt_comments=(Textview)view.findViewById(R.id.txt_comments);



        }
    }

}
