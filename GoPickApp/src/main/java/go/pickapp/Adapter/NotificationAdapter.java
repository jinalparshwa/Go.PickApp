package go.pickapp.Adapter;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import go.pickapp.Activity.Order_detailsActivity;
import go.pickapp.Controller.Textview;
import go.pickapp.Controller.Textview_Bold;
import go.pickapp.Model.Model_order;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

/**
 * Created by Admin on 6/5/2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.Viewholder> {

    FragmentActivity activity;
    ArrayList<Model_order> arrayList = new ArrayList<>();
    Pref_Master pref;


    public NotificationAdapter(FragmentActivity activity, ArrayList<Model_order> arraylist, Pref_Master pref) {
        super();
        this.activity = activity;
        this.arrayList = arraylist;
        this.pref = pref;
    }

    @Override
    public NotificationAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_notification_list, parent, false);
        return new NotificationAdapter.Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(NotificationAdapter.Viewholder holder, int position) {
        final Model_order model = arrayList.get(position);

        holder.txt_msg.setText(model.getMsg());
        holder.txt_date.setText(model.getDate());


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

        Textview txt_msg;
        Textview txt_date;


        public Viewholder(View view) {
            super(view);

            txt_msg = (Textview) view.findViewById(R.id.txt_msg);
            txt_date = (Textview) view.findViewById(R.id.txt_date);


        }
    }
}
