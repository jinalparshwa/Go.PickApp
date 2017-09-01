package go.pickapp.Adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;


/**
 * Created by Admin on 5/18/2017.
 */

public class CusineAdapter extends RecyclerView.Adapter<CusineAdapter.Viewholder> {

    Context context;
    ArrayList<Model_Cusine_filter> array_cusineee = new ArrayList<>();
    public static ArrayList<String> cusine = new ArrayList<>();
    public static ArrayList<String> cusine_position = new ArrayList<>();
    public static String build_cusine = "";
    String build = "";
    public static String pos = "";
    filter_data filter_data;
    boolean all_selected = false;
    SQLiteDatabase db;
    Pref_Master pref;

    public CusineAdapter(Context activity, ArrayList<Model_Cusine_filter> array_cusine, filter_data filter_data, SQLiteDatabase db, Pref_Master pref) {

        this.context = activity;
        this.array_cusineee = array_cusine;
        this.filter_data = filter_data;
        this.db = db;
        this.pref = pref;

    }

    @Override
    public CusineAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_cusines, parent, false);
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(final CusineAdapter.Viewholder holder, final int position) {
        final Model_Cusine_filter model = array_cusineee.get(position);

        Log.e("size_arry", ":" + array_cusineee.size());


        if (model.getColor().equals("1")) {
            holder.rr_color.setBackgroundResource(R.drawable.selected_cusine_bg);
            holder.txt_cusine.setTextColor(Color.WHITE);
        } else {
            Log.e("White_bg", "White_bg");
            holder.rr_color.setBackgroundResource(R.drawable.unselected_cusine_bg);
            holder.txt_cusine.setTextColor(Color.GRAY);
        }

        holder.txt_cusine.setText(model.getCusine());
        holder.txt_cusine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (all_selected == false) {
                    model.setId(String.valueOf(position));
                    model.setName(model.getCusine());
                    model.setSelected(true);
                    filter_data.mydata(model);
                    all_selected = true;
                    holder.rr_color.setBackgroundResource(R.drawable.selected_cusine_bg);
                    holder.txt_cusine.setTextColor(Color.WHITE);


                } else {
                    model.setId(String.valueOf(position));
                    model.setName(model.getCusine());
                    model.setSelected(false);
                    filter_data.mydata(model);
                    all_selected = false;
                    holder.rr_color.setBackgroundResource(R.drawable.unselected_cusine_bg);
                    holder.txt_cusine.setTextColor(Color.GRAY);

                }


//                pos = String.valueOf(position + 1);
//                model.setSelected(!model.isSelected());
//                holder.rr_color.setBackgroundResource(model.isSelected() ? R.drawable.selected_cusine_bg : R.drawable.unselected_cusine_bg);
//                holder.txt_cusine.setTextColor(model.isSelected() ? Color.WHITE : Color.GRAY);
//                if (model.isSelected()) {
////
//
//
//                    cusine.add(holder.txt_cusine.getText().toString());
//                    cusine_position.add(pos);
//                    model.setColor("0");
//
//                    // cusine.add(new Model_Cusine_filter(holder.txt_cusine.getText().toString(),"0",holder.txt_cusine.getText().toString(),pos));
//                    Log.e("position_add", pos);
//
//                } else {
//                    cusine.remove(holder.txt_cusine.getText().toString());
//                    cusine_position.remove(pos);
////                    Model_Cusine_filter modelCusineFilter = new Model_Cusine_filter();
////                    modelCusineFilter.setName(holder.txt_cusine.getText().toString());
////                    modelCusineFilter.setId(pos);
////                    modelCusineFilter.setColor("0");
////                    cusine.add(modelCusineFilter);
//
//
//                    Log.e("position_remove", pos);
//
//
//                }
//                Log.e("Cusiine_size", ":" + cusine);
//                Log.e("Cusine_position", ":" + cusine_position);
//
//                StringBuilder builder = new StringBuilder();
//
//                for (int i = 0; i < cusine.size(); i++) {
//                    if (i != (cusine.size() - 1)) {
//                        build = "" + cusine.get(i) + ",";
//                        Log.e("if_filter", build);
//                    } else {
//                        build = "" + cusine.get(i);
//                        Log.e("Else_filter", build);
//                    }
//                    Log.e("build", build);
//                    builder.append(build);
//                    build_cusine = builder.toString();
//                    Log.e("jinal_build", build_cusine);
//                }


            }
        });

//        if (cusine.size() == 0) {
//            holder.txt_cusine.setTextColor(Color.GRAY);
//            holder.rr_color.setBackgroundResource(R.drawable.unselected_cusine_bg);
//        }
    }

    @Override
    public int getItemCount() {
        return array_cusineee.size();
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
