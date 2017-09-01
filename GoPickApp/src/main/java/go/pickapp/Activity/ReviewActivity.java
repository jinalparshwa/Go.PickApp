package go.pickapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import go.pickapp.Adapter.Order_detailsAdapter;
import go.pickapp.Adapter.ReviewAdapter;
import go.pickapp.Controller.Activity_indicator;
import go.pickapp.Controller.Config;
import go.pickapp.Controller.Connection;
import go.pickapp.Controller.DialogBox;
import go.pickapp.Controller.Textview;
import go.pickapp.Controller.Textview_Bold;
import go.pickapp.Model.Model_cart;
import go.pickapp.Model.Model_restaurant;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

public class ReviewActivity extends AppCompatActivity {

    Pref_Master pref;
    Context context = this;
    ImageView img_back;
    RecyclerView recycle_rating;
    Activity_indicator obj_dialog;
    ReviewAdapter adapter;
    Textview txt_name_one;
    SimpleRatingBar ratingbar_one;
    Textview_Bold txt_rating_one;
    ArrayList<Model_restaurant> array_list = new ArrayList<>();
    Textview notext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        pref = new Pref_Master(context);
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        recycle_rating = (RecyclerView) findViewById(R.id.recycle_rating);
        int numberOfColumns = 1;
        recycle_rating.setLayoutManager(new GridLayoutManager(context, numberOfColumns));

        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ReviewActivity.this, Restaurant_detailsActivity.class);
                startActivity(i);
                finish();
            }
        });
        txt_name_one = (Textview) findViewById(R.id.txt_name_one);
      //  txt_name_two = (Textview) findViewById(R.id.txt_name_two);
       // txt_name_three = (Textview) findViewById(R.id.txt_name_three);
        notext = (Textview) findViewById(R.id.notext);

        txt_rating_one = (Textview_Bold) findViewById(R.id.txt_rating_one);
       // txt_rating_two = (Textview_Bold) findViewById(R.id.txt_rating_two);
       // txt_rating_three = (Textview_Bold) findViewById(R.id.txt_rating_three);

        ratingbar_one = (SimpleRatingBar) findViewById(R.id.ratingbar_one);
       // ratingbar_two = (SimpleRatingBar) findViewById(R.id.ratingbar_two);
       // ratingbar_three = (SimpleRatingBar) findViewById(R.id.ratingbar_three);

        get_review_list();

        adapter = new ReviewAdapter(context, array_list, pref);
        recycle_rating.setAdapter(adapter);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ReviewActivity.this, Restaurant_detailsActivity.class);
        startActivity(i);
        finish();
    }

    public void get_review_list() {

        obj_dialog.show();

        String url = Config.app_url + Config.Reviewlist + "/" + pref.getStr_res_id();
        String json = "";


        HashMap<String, String> param = new HashMap<>();
        param.put("data", json);

        Map<String, String> header = new HashMap<>();
        header.put("apikey", Config.headkey);
        header.put("username", Config.headunm);
        header.put(Config.Language, pref.getLanguage());

        Response.Listener<String> lis_res = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                obj_dialog.dismiss();

                try {
                    JSONObject jobj = new JSONObject(response);
                    String res_flag = jobj.getString("status");
                    if (res_flag.equals("200")) {
                        if (jobj.has("data")) {
                            JSONArray jarray = new JSONArray(jobj.getString("data"));

                            for (int i = 0; i < jarray.length(); i++) {
                                JSONObject job = jarray.getJSONObject(i);


                                txt_name_one.setText(job.getString("ratingnameone"));
                              //  txt_name_two.setText(job.getString("ratingnametwo"));
                              //  txt_name_three.setText(job.getString("ratingnamethree"));
                                txt_rating_one.setText(job.getString("ratingvalueone"));
                              //  txt_rating_two.setText(job.getString("ratingvaluetwo"));
                              //  txt_rating_three.setText(job.getString("ratingvaluethree"));
                                ratingbar_one.setRating(Float.parseFloat(job.getString("ratingvalueone")));
                              //  ratingbar_two.setRating(Float.parseFloat(job.getString("ratingvaluetwo")));
                               // ratingbar_three.setRating(Float.parseFloat(job.getString("ratingvaluethree")));

                                array_list.clear();

                                JSONArray jarry = job.getJSONArray("reviewlist");
                                for (int j = 0; j < jarry.length(); j++) {
                                    Model_restaurant model = new Model_restaurant();
                                    JSONObject jobj1 = jarry.getJSONObject(j);
                                    model.setRating_username(jobj1.getString("username"));
                                    model.setReview(jobj1.getString("review"));
                                    model.setReview_date(jobj1.getString("reviewdatetime"));
                                    model.setOverallrating(jobj1.getString("overallrating"));
                                    array_list.add(model);

                                }

                                adapter.notifyDataSetChanged();

                                if (array_list.size() == 0) {
                                    notext.setVisibility(View.VISIBLE);
                                    recycle_rating.setVisibility(View.INVISIBLE);
                                } else {
                                    notext.setVisibility(View.INVISIBLE);
                                    recycle_rating.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                        adapter.notifyDataSetChanged();


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
                //  signup.setClickable(true);
                obj_dialog.dismiss();
            }
        };
        Connection.getconnectionVolley(url, param, header, context, lis_res, lis_error);

    }

}
