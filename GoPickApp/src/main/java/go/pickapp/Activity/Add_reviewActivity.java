package go.pickapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import go.pickapp.Controller.Activity_indicator;
import go.pickapp.Controller.Config;
import go.pickapp.Controller.Connection;
import go.pickapp.Controller.DialogBox;
import go.pickapp.Controller.Edittext;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

public class Add_reviewActivity extends AppCompatActivity {

    ImageView img_backkk;
    Context context = this;
    Pref_Master pref;
    Activity_indicator obj_dialog;
    SimpleRatingBar rating_one;
    //    SimpleRatingBar rating_two;
//    SimpleRatingBar rating_three;
    Edittext edit_comment;
    RelativeLayout rr_submit;
    String order_id = "";
    int value = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        pref = new Pref_Master(context);
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            order_id = extras.getString("Order_id");
            value = extras.getInt("value");
        }


        img_backkk = (ImageView) findViewById(R.id.img_backkk);
        rating_one = (SimpleRatingBar) findViewById(R.id.rating_one);
        // rating_two = (SimpleRatingBar) findViewById(R.id.rating_two);
        // rating_three = (SimpleRatingBar) findViewById(R.id.rating_three);
        edit_comment = (Edittext) findViewById(R.id.edit_comment);
        rr_submit = (RelativeLayout) findViewById(R.id.rr_submit);


        img_backkk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MainActivity.class);
                i.putExtra("fragmentcode", Config.Fragment_ID.MY_order);
                i.putExtra("value", value);
                startActivity(i);
                finish();

            }
        });

        rr_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validate();
            }
        });
    }

    public void Validate() {
        if (rating_one.getRating() == 0) {
            DialogBox.setPopup(context, getResources().getString(R.string.Please_give_rating_yor_order));
//        } else if (rating_two.getRating() == 0) {
//            DialogBox.setPopup(context, getResources().getString(R.string.Please_rate_in_preparation_time));
//        } else if (rating_three.getRating() == 0) {
//            DialogBox.setPopup(context, getResources().getString(R.string.Please_rate_in_quality_of_food));
        } else if (edit_comment.getText().toString().equals("")) {
            DialogBox.setPopup(context, getResources().getString(R.string.Please_enter_review));
        } else {
            add_review();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(context, MainActivity.class);
        i.putExtra("fragmentcode", Config.Fragment_ID.MY_order);
        i.putExtra("value", value);
        startActivity(i);
        finish();
    }

    private void add_review() {

        String url = Config.app_url + Config.Addreview;
        JSONObject jobj_loginuser = new JSONObject();
        obj_dialog.show();
        try {

            JSONObject jobj_row = new JSONObject();

            jobj_row.put("userid", pref.getUID());
            jobj_row.put("rating", rating_one.getRating());
            jobj_row.put("reviewComment", edit_comment.getText().toString());
            jobj_row.put("orderid", order_id);


            JSONArray jarray_loginuser = new JSONArray();
            jarray_loginuser.put(jobj_row);

            jobj_loginuser.put("addreview", jarray_loginuser);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final Map<String, String> params = new HashMap<>();
        params.put("data", "" + jobj_loginuser.toString().replaceAll("\\\\", ""));
        Log.e("vikas_request", ":" + params.toString());

        Map<String, String> header = new HashMap<>();
        header.put("apikey", Config.headkey);
        header.put("username", Config.headunm);
        header.put(Config.Language, pref.getLanguage());

        Response.Listener<String> lis_res = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("login_Response", " : " + response);
                obj_dialog.dismiss();

                try {
                    JSONObject jobj = new JSONObject(response);
                    String res_flag = jobj.getString("status");
                    if (res_flag.equals("200")) {

                        DialogBox.setreview_popup(context,getResources().getString(R.string.Review_added_successfully),value);

                    } else {
                        DialogBox.setPopup(context, jobj.getString("msg").toString());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        };

        Response.ErrorListener lis_error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("response error", "" + error);
                obj_dialog.dismiss();
            }
        };

        Connection.postconnection(url, params, header, context, lis_res, lis_error);

    }


}
