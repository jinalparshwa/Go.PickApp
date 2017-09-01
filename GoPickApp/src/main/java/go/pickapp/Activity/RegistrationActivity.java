package go.pickapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.firebase.iid.FirebaseInstanceId;

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

public class RegistrationActivity extends AppCompatActivity {

    Edittext input_fname, input_lname, input_email, input_mobile, input_password, input_conf_password;
    RelativeLayout rr_Send_otp;
    Context context = this;
    Pref_Master pref;
    ArrayList<Model_user> Array_user = new ArrayList<>();
    public static String reg_id = "";
    Activity_indicator obj_dialog;
    public static String otp = "";
    private String blockCharacterSet = "0123456789";
    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        pref = new Pref_Master(context);
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        try {
            reg_id = FirebaseInstanceId.getInstance().getToken();
        } catch (Exception e) {
            e.printStackTrace();
            reg_id = FirebaseInstanceId.getInstance().getToken();
        }


        rr_Send_otp = (RelativeLayout) findViewById(R.id.rr_Send_otp);
        input_fname = (Edittext) findViewById(R.id.input_fname);
        input_fname.setFilters(new InputFilter[]{filter});
        input_lname = (Edittext) findViewById(R.id.input_lname);
        input_lname.setFilters(new InputFilter[]{filter});
        input_email = (Edittext) findViewById(R.id.input_email);
        input_mobile = (Edittext) findViewById(R.id.input_mobile);
        input_password = (Edittext) findViewById(R.id.input_password);
        input_conf_password = (Edittext) findViewById(R.id.input_conf_password);
        rr_Send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validate();
            }
        });

        if (Array_user.size() == 0) {
        } else {
            input_fname.setText(Array_user.get(0).getFname());
            input_lname.setText(Array_user.get(0).getLname());
            input_email.setText(Array_user.get(0).getEmail());
            input_mobile.setText(Array_user.get(0).getMobile());
            input_password.setText(Array_user.get(0).getPassword());
        }
    }

    public void Validate() {
        if (input_fname.getText().toString().equals(""))
            DialogBox.setPopup(context, getResources().getString(R.string.Enter_first_name));
        else if (input_lname.getText().toString().equals(""))
            DialogBox.setPopup(context, getResources().getString(R.string.Enter_last_name));
        else if (input_email.getText().toString().equals(""))
            DialogBox.setPopup(context, getResources().getString(R.string.Enter_Email_address));
        else if (!Config.isValidEmailAddress(input_email.getText().toString()))
            DialogBox.setPopup(context, getResources().getString(R.string.Invalid_email));
        else if (input_mobile.getText().toString().equals(""))
            DialogBox.setPopup(context, getResources().getString(R.string.Enter_mobile));
        else if (input_mobile.getText().toString().length() < 8)
            DialogBox.setPopup(context, getResources().getString(R.string.Invalid_mobile));
        else if (input_password.getText().toString().equals(""))
            DialogBox.setPopup(context, getResources().getString(R.string.Enter_Password));
        else if (!(input_password.getText().toString().length() >= 6))
            DialogBox.setPopup(context, getResources().getString(R.string.Password_should_be_more_than_6_digit));
        else if (input_conf_password.getText().toString().equals(""))
            DialogBox.setPopup(context, getResources().getString(R.string.Enter_Confirm_password));
        else if (!input_password.getText().toString().equals(input_conf_password.getText().toString())) {
            DialogBox.setPopup(context, getResources().getString(R.string.Password_does_not_match));
        } else {
            Array_user.clear();
            Model_user model = new Model_user();
            model.setEmail("" + input_email.getText().toString().trim());
            model.setPassword("" + input_password.getText().toString().trim());
            model.setFname("" + input_fname.getText().toString().trim());
            model.setLname("" + input_lname.getText().toString().trim());
            model.setMobile("" + input_mobile.getText().toString().trim());
            model.setDeviceid("" + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
            model.setDevicetoken(reg_id);
            Array_user.add(model);
            Send_OTP();

        }

    }

    private void Send_OTP() {

        String url = Config.app_url + Config.Send_OTP;
        JSONObject jobj_loginuser = new JSONObject();
        obj_dialog.show();
        try {

            JSONObject jobj_row = new JSONObject();

            jobj_row.put("email", input_email.getText().toString());
            jobj_row.put("fname", input_fname.getText().toString());
            jobj_row.put("lname", input_lname.getText().toString());


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

                        Intent i = new Intent(RegistrationActivity.this, Otp_sign_up.class);
                        i.putExtra("Arraylist", Array_user);
                        startActivity(i);

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
