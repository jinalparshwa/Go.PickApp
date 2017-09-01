package go.pickapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
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

import static go.pickapp.Activity.RegistrationActivity.otp;

/**
 * Created by Admin on 6/5/2017.
 */

public class Otp_sign_up extends AppCompatActivity {

    Context context = this;
    Pref_Master pref;
    Edittext input_otp;
    RelativeLayout rr_verify;
    LinearLayout ll_resend_otp;
    Activity_indicator obj_dialog;
    ImageView img_back;
    ArrayList<Model_user> Array_user = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_forgot_fragment);
        pref = new Pref_Master(context);
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        Intent i = getIntent();
        Array_user = (ArrayList<Model_user>) i.getSerializableExtra("Arraylist");

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

        rr_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (input_otp.getText().toString().equals("")) {
                    DialogBox.setPopup(context, getString(R.string.Please_enter_otp));
                } else if (!input_otp.getText().toString().equals(otp)) {
                    DialogBox.setPopup(context, getString(R.string.Invalid_otp));
                } else {
                    Model_user model_user = new Model_user();
                    model_user.setEmail(Array_user.get(0).getEmail());
                    model_user.setPassword(Array_user.get(0).getPassword());
                    model_user.setFname(Array_user.get(0).getFname());
                    model_user.setLname(Array_user.get(0).getLname());
                    model_user.setMobile(Array_user.get(0).getMobile());
                    model_user.setLanguage(Array_user.get(0).getLanguage());
                    model_user.setDeviceid(Array_user.get(0).getDeviceid());
                    model_user.setDevicetoken(Array_user.get(0).getDevicetoken());
                    Array_user.add(model_user);
                    Registration();
                }
            }
        });

        ll_resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Resend_OTP();
            }
        });
    }


    public void Registration() {

        obj_dialog.show();

        String url = Config.app_url + Config.Sign_up;
        String json = "";

        json = JSON.add_user(Array_user, pref, "registeruser");

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
                            Model_user model = new Model_user();
                            JSONObject jobj1 = jarray.getJSONObject(i);
                            model.setId(jobj1.getString("userid"));
                            model.setName(jobj1.getString("logintype"));


                            pref.setUID(jobj1.getString("userid"));
                            pref.setLogin_type(jobj1.getString("logintype"));
                            Array_user.add(model);


                        }
                        DialogBox.setregister_popup(context, jobj.getString("msg").toString());
                        pref.setLogin_Flag("login");

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

    private void Resend_OTP() {

        String url = Config.app_url + Config.Send_OTP;
        JSONObject jobj_loginuser = new JSONObject();
        obj_dialog.show();
        try {

            JSONObject jobj_row = new JSONObject();

            jobj_row.put("email", Array_user.get(0).getEmail());
            jobj_row.put("fname", Array_user.get(0).getFname());
            jobj_row.put("lname", Array_user.get(0).getLname());


            JSONArray jarray_loginuser = new JSONArray();
            jarray_loginuser.put(jobj_row);

            jobj_loginuser.put("sendotptouser", jarray_loginuser);
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
                        JSONArray jarray = new JSONArray(jobj.getString("data"));

                        for (int i = 0; i < jarray.length(); i++) {
                            JSONObject jobj1 = jarray.getJSONObject(i);
                            otp = (jobj1.getString("otp"));
                            Log.e("otp_check", "Enter" + otp);
                        }
                        DialogBox.setPopup(context, jobj.getString("msg").toString());

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
