package go.pickapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import go.pickapp.Controller.Textview;
import go.pickapp.Interface.filter_data;
import go.pickapp.Model.Model_Cusine_filter;
import go.pickapp.Model.Model_filter;
import go.pickapp.R;

/**
 * Created by Admin on 5/18/2017.
 */

public class FilterbyAdapter extends RecyclerView.Adapter<FilterbyAdapter.Viewholder> {

    Context context;
    ArrayList<Model_filter> array_cusine = new ArrayList<>();
    boolean all_selected = false;
    filter_data filter_data;


    public FilterbyAdapter(Context activity, ArrayList<Model_filter> array_cusine, filter_data filter_data) {

        this.context = activity;
        this.array_cusine = array_cusine;
        this.filter_data = filter_data;

    }

    @Override
    public FilterbyAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_cusines, parent, false);
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(final FilterbyAdapter.Viewholder holder, final int position) {

        final Model_filter model = array_cusine.get(position);
        Log.e("filter_color", model.getColor());

        if (model.getColor().equals("1")) {
            holder.rr_color.setBackgroundResource(R.drawable.selected_cusine_bg);
            holder.txt_cusine.setTextColor(Color.WHITE);
        } else {
            Log.e("White_bg", "White_bg");
            holder.rr_color.setBackgroundResource(R.drawable.unselected_cusine_bg);
            holder.txt_cusine.setTextColor(Color.GRAY);
        }

        holder.txt_cusine.setText(model.getValue());
        holder.txt_cusine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (all_selected == false) {
                    model.setId(model.getId());
                    model.setValue(model.getValue());
                    model.setSelected(true);
                    filter_data.mydata_filter(model);
                    all_selected = true;
                    holder.rr_color.setBackgroundResource(R.drawable.selected_cusine_bg);
                    holder.txt_cusine.setTextColor(Color.WHITE);


                } else {
                    model.setId(model.getId());
                    model.setValue(model.getValue());
                    model.setSelected(false);
                    filter_data.mydata_filter(model);
                    all_selected = false;
                    holder.rr_color.setBackgroundResource(R.drawable.unselected_cusine_bg);
                    holder.txt_cusine.setTextColor(Color.GRAY);

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return array_cusine.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        Textview txt_cusine;
        RelativeLayout rr_color;

        public Viewholder(View itemView) {
            super(itemView);

            txt_cusine = (Textview) itemView.findViewById(R.id.txt_cusine);
            rr_color = (RelativeLayout) itemView.findViewById(R.id.rr_color);


        }
    }
}
