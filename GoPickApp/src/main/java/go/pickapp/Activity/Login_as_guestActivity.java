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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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

public class Login_as_guestActivity extends AppCompatActivity {

    ImageView img_back;
    Context context = this;
    Pref_Master pref;
    ArrayList<Model_user> Array_user = new ArrayList<>();
    public static String reg_id = "";
    Activity_indicator obj_dialog;
    Edittext input_fname, input_lname, input_mobile;
    RelativeLayout rr_submit;
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
        setContentView(R.layout.activity_login_as_guest);
        pref = new Pref_Master(context);
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        try {
            reg_id = FirebaseInstanceId.getInstance().getToken();
        } catch (Exception e) {
            e.printStackTrace();
            reg_id = FirebaseInstanceId.getInstance().getToken();
        }


        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        input_fname = (Edittext) findViewById(R.id.input_fname);
        input_fname.setFilters(new InputFilter[]{filter});
        input_lname = (Edittext) findViewById(R.id.input_lname);
        input_lname.setFilters(new InputFilter[]{filter});
        input_mobile = (Edittext) findViewById(R.id.input_mobile);
        rr_submit = (RelativeLayout) findViewById(R.id.rr_submit);
        rr_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validate();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void Validate() {
        if (input_mobile.getText().toString().equals(""))
            DialogBox.setPopup(context, getResources().getString(R.string.Enter_mobile));
        else if (input_mobile.getText().toString().length() < 8)
            DialogBox.setPopup(context, getResources().getString(R.string.Invalid_mobile));
        else {
            Array_user.clear();
            Model_user model = new Model_user();
            model.setFname("" + input_fname.getText().toString().trim());
            model.setLname("" + input_lname.getText().toString().trim());
            model.setMobile("" + input_mobile.getText().toString().trim());
            model.setDevicetoken(reg_id);
            Array_user.add(model);
            Login_guest_user();
        }
    }

    public void Login_guest_user() {

        obj_dialog.show();

        String url = Config.app_url + Config.addguestuser;
        String json = "";

        json = JSON.add_user(Array_user, pref, "addguestuser");

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

                            pref.setGuest_uid(jobj1.getString("userid"));
                            Array_user.add(model);

                            Log.e("user_id_guest", pref.getStr_guest_UID());
                            pref.setLogin_value(2);
                        }
                        Intent i = new Intent(context, Payment_methodActivity.class);
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
                //  signup.setClickable(true);
                obj_dialog.dismiss();
            }
        };
        Connection.postconnection(url, param, header, context, lis_res, lis_error);

    }

}
