package go.pickapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import go.pickapp.Activity.About_us_details;
import go.pickapp.Controller.Activity_indicator;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

/**
 * Created by Admin on 5/31/2017.
 */

public class Aboutus extends Fragment {

    Context context;
    Pref_Master pref;
    Activity_indicator obj_dialog;
    RelativeLayout rr_About_us;
    RelativeLayout rr_terms_condition;
    RelativeLayout rr_privacy;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.about_us_fragment, container, false);
        context = getActivity();
        pref = new Pref_Master(context);
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));


        rr_About_us = (RelativeLayout) v.findViewById(R.id.rr_About_us);
        rr_terms_condition = (RelativeLayout) v.findViewById(R.id.rr_terms_condition);
        rr_privacy = (RelativeLayout) v.findViewById(R.id.rr_privacy);

        rr_About_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, About_us_details.class);
                i.putExtra("id", "1");
                i.putExtra("name",getResources().getString(R.string.About_goPickApp));
                context.startActivity(i);

            }
        });

        rr_terms_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, About_us_details.class);
                i.putExtra("id", "2");
                i.putExtra("name",getResources().getString(R.string.Terms_and_condition));
                context.startActivity(i);
            }
        });

        rr_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, About_us_details.class);
                i.putExtra("id", "3");
                i.putExtra("name",getResources().getString(R.string.Privacy));
                context.startActivity(i);

            }
        });
        return v;
    }


}
