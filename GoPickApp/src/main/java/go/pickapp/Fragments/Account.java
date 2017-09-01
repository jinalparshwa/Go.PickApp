package go.pickapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import go.pickapp.Activity.MainActivity;
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

public class Account extends Fragment {

    Context context;
    Pref_Master pref;
    LinearLayout change_password;
    Edittext input_fname, input_lname, input_email, input_mobile;
    Activity_indicator obj_dialog;
    RelativeLayout rr_Edit_account;
    ArrayList<Model_user> array_update = new ArrayList<>();
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.account_fragment, container, false);
        context = getActivity();
        pref = new Pref_Master(context);
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        change_password = (LinearLayout) v.findViewById(R.id.change_password);
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MainActivity.class);
                i.putExtra("fragmentcode", Config.Fragment_ID.Change_password);
                startActivity(i);
            }
        });

        input_fname = (Edittext) v.findViewById(R.id.input_fname);
        input_fname.setFilters(new InputFilter[]{filter});
        input_lname = (Edittext) v.findViewById(R.id.input_lname);
        input_lname.setFilters(new InputFilter[]{filter});
        input_email = (Edittext) v.findViewById(R.id.input_email);
        input_mobile = (Edittext) v.findViewById(R.id.input_mobile);
        rr_Edit_account = (RelativeLayout) v.findViewById(R.id.rr_Edit_account);

        rr_Edit_account.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                Validate();
            }
        });


        if (pref.getStr_login_type().

                equals("1"))

        {
            change_password.setVisibility(View.VISIBLE);
        } else

        {
            change_password.setVisibility(View.GONE);
        }

        Get_profile();
        return v;
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
        else {
            array_update.clear();
            Model_user model = new Model_user();
            model.setFname(input_fname.getText().toString().trim());
            model.setLname(input_lname.getText().toString().trim());
            model.setEmail(input_email.getText().toString().trim());
            model.setMobile(input_mobile.getText().toString().trim());
            array_update.add(model);
            pref.setUser_name(input_fname.getText().toString() + " " + input_lname.getText().toString());
            Log.e("Username_profile", pref.getStr_username());
            Update_profile();
        }
    }

    public void Get_profile() {

        obj_dialog.show();

        String url = Config.app_url + Config.Getuserprofile + "/" + pref.getUID();
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
                        JSONArray jarray = new JSONArray(jobj.getString("data"));

                        for (int i = 0; i < jarray.length(); i++) {
                            JSONObject jobj1 = jarray.getJSONObject(i);
                            input_fname.setText(jobj1.getString("fname"));
                            input_lname.setText(jobj1.getString("lname"));
                            input_email.setText(jobj1.getString("email"));
                            input_mobile.setText(jobj1.getString("mobile"));

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
        Connection.getconnectionVolley(url, param, header, context, lis_res, lis_error);
    }

    public void Update_profile() {

        obj_dialog.show();

        String url = Config.app_url + Config.Edituserprofile;
        String json = "";


        json = JSON.add_user(array_update, pref, "edituserprofile");

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