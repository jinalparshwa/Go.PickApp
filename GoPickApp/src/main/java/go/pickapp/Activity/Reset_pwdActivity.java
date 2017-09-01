package go.pickapp.Activity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;

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


public class Reset_pwdActivity extends AppCompatActivity {

    Context context = this;
    Pref_Master pref;
    Edittext input_new_pwd, input_conf_password;
    RelativeLayout rr_reset;
    ArrayList<Model_user> array_reset = new ArrayList<>();
    Activity_indicator obj_dialog;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resetpwd_fragment);
        pref = new Pref_Master(context);
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        rr_reset = (RelativeLayout) findViewById(R.id.rr_reset);
        input_new_pwd = (Edittext) findViewById(R.id.input_new_pwd);
        input_conf_password = (Edittext) findViewById(R.id.input_conf_password);
        rr_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validate();
            }
        });

        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void Validate() {
        if (input_new_pwd.getText().toString().trim().equals("")) {
            DialogBox.setPopup(context, getResources().getString(R.string.Enter_new_pwd));
        } else if (input_conf_password.getText().toString().trim().equals("")) {
            DialogBox.setPopup(context, getResources().getString(R.string.Enter_Confirm_password));
        } else if (!input_new_pwd.getText().toString().trim().equals(input_conf_password.getText().toString().trim())) {
            DialogBox.setPopup(context, getResources().getString(R.string.Password_does_not_match));
        } else {
            array_reset.clear();
            Model_user model = new Model_user();
            model.setEmail(email);
            model.setProcess("update");
            model.setNew_pwd(input_new_pwd.getText().toString().trim());
            array_reset.add(model);
            Reset_password();

        }
    }


    public void Reset_password() {

        obj_dialog.show();

        String url = Config.app_url + Config.Forgotpassword;
        String json = "";


        json = JSON.add_user(array_reset, pref, "forgotpassword");

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

                        DialogBox.setlogin(context, jobj.getString("msg").toString());


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
