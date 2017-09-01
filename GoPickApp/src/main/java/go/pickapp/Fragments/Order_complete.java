package go.pickapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import go.pickapp.Activity.MainActivity;
import go.pickapp.Controller.Activity_indicator;
import go.pickapp.Controller.Config;
import go.pickapp.Controller.DialogBox;
import go.pickapp.Controller.Textview;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

/**
 * Created by Admin on 5/30/2017.
 */

public class Order_complete extends Fragment {

    Context context;
    Pref_Master pref;
    Textview txt_order_no;
    Activity_indicator obj_dialog;
    String Order_id = "";
    String Order_no = "";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.order_complete_fragment, container, false);
        context = getActivity();
        pref = new Pref_Master(context);
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        Bundle extras = getActivity().getIntent().getExtras();

        if (extras != null) {
            Order_id = extras.getString("Order_id");
            Order_no = extras.getString("Order_no");
        }

        txt_order_no = (Textview) v.findViewById(R.id.txt_order_no);
        txt_order_no.setText(Order_no);
        return v;

    }

    @Override
    public void onResume() {
        super.onResume();

        if (getView() == null) {
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_UP && i == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("fragmentcode", Config.Fragment_ID.res_list);
                    startActivity(intent);
                    return true;
                }
                return false;
            }

        });
    }
}
