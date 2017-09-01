package go.pickapp.Fragments;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by Admin on 5/25/2017.
 */

public class ChangepwdFragment extends Fragment {

    Context context;
    Pref_Master pref;
    Edittext input_old_pwd, input_new_pwd, input_conf_password;
    RelativeLayout rr_change_pwd;
    ArrayList<Model_user> array_change_pwd = new ArrayList<>();
    Activity_indicator obj_dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.changepwd_fragment, container, false);
        context = getActivity();
        pref = new Pref_Master(context);
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));


        rr_change_pwd = (RelativeLayout) v.findViewById(R.id.rr_change_pwd);
        input_old_pwd = (Edittext) v.findViewById(R.id.input_old_pwd);
        input_new_pwd = (Edittext) v.findViewById(R.id.input_new_pwd);
        input_conf_password = (Edittext) v.findViewById(R.id.input_conf_password);
        rr_change_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validate();
            }
        });
        return v;
    }

    public void Validate() {
        if (input_old_pwd.getText().toString().equals("")) {
            DialogBox.setPopup(context, getResources().getString(R.string.Enter_old_pwd));
        } else if (input_new_pwd.getText().toString().equals("")) {
            DialogBox.setPopup(context, getResources().getString(R.string.Enter_new_pwd));
        } else if (input_new_pwd.getText().toString().length() <= 6) {
            DialogBox.setPopup(context, getResources().getString(R.string.Password_should_be_more_than_6_digit));
        }else if(input_old_pwd.getText().toString().trim().equals(input_new_pwd.getText().toString().trim())){
            DialogBox.setPopup(context, getResources().getString(R.string.Old_and_new_password_are_Same));
        } else if (input_conf_password.getText().toString().equals("")) {
            DialogBox.setPopup(context, getResources().getString(R.string.Enter_Confirm_password));
        } else if (!input_new_pwd.getText().toString().trim().equals(input_conf_password.getText().toString().trim())) {
            DialogBox.setPopup(context, getResources().getString(R.string.Password_does_not_match));
        } else {
            array_change_pwd.clear();
            Model_user model = new Model_user();
            model.setNew_pwd(input_new_pwd.getText().toString().trim());
            model.setOld_pwd(input_old_pwd.getText().toString().trim());
            model.setConfirm_pwd(input_conf_password.getText().toString().trim());
            array_change_pwd.add(model);
            Change_Password();
        }
    }

    public void Change_Password() {

        obj_dialog.show();

        String url = Config.app_url + Config.Changepassword;
        String json = "";


        json = JSON.add_user(array_change_pwd, pref, "changepassword");

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
                        DialogBox.change_pwd_Popup(context, jobj.getString("msg").toString());

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
