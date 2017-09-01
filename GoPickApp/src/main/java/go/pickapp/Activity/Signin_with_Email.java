package go.pickapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.firebase.iid.FirebaseInstanceId;

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

public class Signin_with_Email extends AppCompatActivity {

    Edittext input_email, input_password;
    Context context = this;
    Pref_Master pref;
    ArrayList<Model_user> Array_user = new ArrayList<>();
    RelativeLayout rr_login;
    public static String reg_id = "";
    Activity_indicator obj_dialog;
    LinearLayout forgot_password;
    String value = "";
    String mobile = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_with_email);
        pref = new Pref_Master(context);
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("Value");
            Log.e("Value", value);
        }


        try {
            reg_id = FirebaseInstanceId.getInstance().getToken();
        } catch (Exception e) {
            e.printStackTrace();
            reg_id = FirebaseInstanceId.getInstance().getToken();
        }

        input_email = (Edittext) findViewById(R.id.input_email);
        input_password = (Edittext) findViewById(R.id.input_password);
        rr_login = (RelativeLayout) findViewById(R.id.rr_login);
        rr_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validate();
            }
        });

        forgot_password = (LinearLayout) findViewById(R.id.forgot_password);
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Signin_with_Email.this, Forgot_pwdActivity.class);
                startActivity(i);
            }
        });
    }

    public void Validate() {
        if (input_email.getText().toString().equals("")) {
            DialogBox.setPopup(context, getResources().getString(R.string.Enter_Email_address));
        } else if (input_password.getText().toString().equals("")) {
            DialogBox.setPopup(context, getResources().getString(R.string.Enter_Password));
        } else {
            Array_user.clear();
            Model_user model = new Model_user();
            model.setEmail("" + input_email.getText().toString().trim());
            model.setPassword("" + input_password.getText().toString().trim());
            model.setDeviceid("" + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
            model.setDevicetoken(reg_id);
            model.setName("");
            model.setLoginstatus("1");
            model.setId("");
            Array_user.add(model);
            Login_api();
        }
    }


    public void Login_api() {

        obj_dialog.show();

        String url = Config.app_url + Config.Login;
        String json = "";

        json = JSON.add_user(Array_user, pref, "loginuser");

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
                            mobile = jobj1.getString("mobile");


                            pref.setUID(jobj1.getString("userid"));
                            pref.setLogin_type(jobj1.getString("logintype"));
                            pref.setLogin_value(1);
                            Array_user.add(model);


                        }
                        pref.setLogin_Flag("login");
                        if (value.equals("1")) {
                            if (mobile.equals("")) {
                                Intent i = new Intent(context, Add_mobile_no.class);
                                startActivity(i);
                                finish();
                            } else {
                                Intent i = new Intent(context, Payment_methodActivity.class);
                                startActivity(i);
                            }
                        } else {
                            Intent i = new Intent(context, MainActivity.class);
                            startActivity(i);
                        }

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
