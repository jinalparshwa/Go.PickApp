package go.pickapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import go.pickapp.Controller.Activity_indicator;
import go.pickapp.Controller.Config;
import go.pickapp.Controller.Connection;
import go.pickapp.Controller.DialogBox;
import go.pickapp.Controller.Edittext;
import go.pickapp.JSON.JSON;
import go.pickapp.Model.Model_user;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

import static go.pickapp.Activity.Forgot_pwdActivity.email;
import static go.pickapp.Activity.Forgot_pwdActivity.otpp;

public class Otp_forgotActivity extends AppCompatActivity {

    Context context = this;
    Pref_Master pref;
    Edittext input_otp;
    RelativeLayout rr_verify;
    LinearLayout ll_resend_otp;
    Activity_indicator obj_dialog;
    ArrayList<Model_user> array_forgot = new ArrayList<>();
    ImageView img_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_forgot_activity);
        pref = new Pref_Master(context);
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));


        input_otp = (Edittext) findViewById(R.id.input_otp);
        rr_verify = (RelativeLayout) findViewById(R.id.rr_verify);
        ll_resend_otp = (LinearLayout) findViewById(R.id.ll_resend_otp);
        img_back = (ImageView) findViewById(R.id.img_back);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ll_resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                array_forgot.clear();
                Model_user model = new Model_user();
                model.setEmail(email);
                model.setProcess("check");
                model.setNew_pwd("");
                array_forgot.add(model);
                Forgot_password();
            }
        });

        rr_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input_otp.getText().toString().equals("")) {
                    DialogBox.setPopup(context, getString(R.string.Please_enter_otp));
                } else if (!input_otp.getText().toString().equals(otpp)) {
                    DialogBox.setPopup(context, getString(R.string.Invalid_otp));
                } else {
                    Intent i = new Intent(context, Reset_pwdActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    public void Forgot_password() {

        obj_dialog.show();

        String url = Config.app_url + Config.Forgotpassword;
        String json = "";


        json = JSON.add_user(array_forgot, pref, "forgotpassword");

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
                        JSONArray jarray = new JSONArray(jobj.getString("data"));

                        for (int i = 0; i < jarray.length(); i++) {
                            JSONObject jobj1 = jarray.getJSONObject(i);
                            otpp = (jobj1.getString("otp"));
                            email = (jobj1.getString("email"));
                            Log.e("otp_check", "Enter" + otpp);
                        }

                        DialogBox.Otp_popup(context, jobj.getString("msg").toString());


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
        Connection.postconnection(url, param, header, context, lis_res, lis_error);
    }

}
