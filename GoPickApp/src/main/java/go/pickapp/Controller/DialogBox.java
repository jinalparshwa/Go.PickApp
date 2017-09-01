package go.pickapp.Controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import go.pickapp.Activity.LoginActivity;
import go.pickapp.Activity.MainActivity;
import go.pickapp.Activity.Otp_forgotActivity;
import go.pickapp.Activity.RestaurantActivity;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;


public class DialogBox extends Activity {
    public static Context context;
    public static Pref_Master pref;
    public static Spinner txt_city;
    public static String userid = "";


    public static void dialog_connection(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.no_internet);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create();
        builder.show();
    }

//    public static void buildAlertMessageNoGps(final Context context) {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setMessage(R.string.Location_message)
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//                    }
//                });
//
//        final AlertDialog alert = builder.create();
//        alert.show();
//    }

    public static void setPopup(final Context context, String msg) {
        LayoutInflater li = LayoutInflater.from(context);
        View v = li.inflate(R.layout.alert_popup, null);
        final android.support.v7.app.AlertDialog alert = new android.support.v7.app.AlertDialog.Builder(context).setView(v).show();
        alert.setCancelable(false);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Textview cancel = (Textview) v.findViewById(R.id.con_ok);
        Textview Msg = (Textview) v.findViewById(R.id.msglaert);
        Msg.setText(msg);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });

    }

    public static void setregister_popup(final Context context, String msg) {
        LayoutInflater li = LayoutInflater.from(context);
        View v = li.inflate(R.layout.alert_popup, null);
        final android.support.v7.app.AlertDialog alert = new android.support.v7.app.AlertDialog.Builder(context).setView(v).show();
        alert.setCancelable(false);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Textview cancel = (Textview) v.findViewById(R.id.con_ok);
        Textview Msg = (Textview) v.findViewById(R.id.msglaert);
        Msg.setText(msg);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                Intent i = new Intent(context, MainActivity.class);
                context.startActivity(i);
            }
        });

    }

    public static void setreview_popup(final Context context, String msg, final int value) {
        LayoutInflater li = LayoutInflater.from(context);
        View v = li.inflate(R.layout.alert_popup, null);
        final android.support.v7.app.AlertDialog alert = new android.support.v7.app.AlertDialog.Builder(context).setView(v).show();
        alert.setCancelable(false);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Textview cancel = (Textview) v.findViewById(R.id.con_ok);
        Textview Msg = (Textview) v.findViewById(R.id.msglaert);
        Msg.setText(msg);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                Intent i = new Intent(context, MainActivity.class);
                i.putExtra("fragmentcode", Config.Fragment_ID.MY_order);
                i.putExtra("value", value);
                context.startActivity(i);
            }
        });

    }

    public static void setorder_detail_popup(final Context context, String msg) {
        LayoutInflater li = LayoutInflater.from(context);
        View v = li.inflate(R.layout.alert_popup, null);
        final android.support.v7.app.AlertDialog alert = new android.support.v7.app.AlertDialog.Builder(context).setView(v).show();
        alert.setCancelable(false);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Textview cancel = (Textview) v.findViewById(R.id.con_ok);
        Textview Msg = (Textview) v.findViewById(R.id.msglaert);
        Msg.setText(msg);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                Intent i = new Intent(context, MainActivity.class);
                i.putExtra("fragmentcode", Config.Fragment_ID.MY_order);
                i.putExtra("value", 0);
                context.startActivity(i);
            }
        });

    }

    public static void setorder_detail_popup_second(final Context context, String msg) {
        LayoutInflater li = LayoutInflater.from(context);
        View v = li.inflate(R.layout.alert_popup, null);
        final android.support.v7.app.AlertDialog alert = new android.support.v7.app.AlertDialog.Builder(context).setView(v).show();
        alert.setCancelable(false);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Textview cancel = (Textview) v.findViewById(R.id.con_ok);
        Textview Msg = (Textview) v.findViewById(R.id.msglaert);
        Msg.setText(msg);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                Intent i = new Intent(context, MainActivity.class);
                i.putExtra("fragmentcode", Config.Fragment_ID.MY_order);
                i.putExtra("value", 0);
                context.startActivity(i);
            }
        });

    }

    public static void setorder_popup(final Context context, String msg, String oredr_no) {
        LayoutInflater li = LayoutInflater.from(context);
        View v = li.inflate(R.layout.alert_order_popup, null);
        final android.support.v7.app.AlertDialog alert = new android.support.v7.app.AlertDialog.Builder(context).setView(v).show();
        alert.setCancelable(false);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Textview cancel = (Textview) v.findViewById(R.id.con_ok);
        Textview Msg = (Textview) v.findViewById(R.id.msglaert);
        Msg.setText(msg + " " + oredr_no);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });

    }

    public static void setfilter_popup(final Context context, String msg) {
        LayoutInflater li = LayoutInflater.from(context);
        View v = li.inflate(R.layout.alert_order_popup, null);
        final android.support.v7.app.AlertDialog alert = new android.support.v7.app.AlertDialog.Builder(context).setView(v).show();
        alert.setCancelable(false);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Textview cancel = (Textview) v.findViewById(R.id.con_ok);
        Textview Msg = (Textview) v.findViewById(R.id.msglaert);
        Msg.setText(msg);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                Intent i = new Intent(context, MainActivity.class);
                i.putExtra("fragmentcode", Config.Fragment_ID.home);
                context.startActivity(i);
            }
        });

    }

    public static void setsecond_popup(final Context context, String msg) {
        LayoutInflater li = LayoutInflater.from(context);
        View v = li.inflate(R.layout.alert_popup, null);
        final android.support.v7.app.AlertDialog alert = new android.support.v7.app.AlertDialog.Builder(context).setView(v).show();
        alert.setCancelable(false);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Textview cancel = (Textview) v.findViewById(R.id.con_ok);
        Textview Msg = (Textview) v.findViewById(R.id.msglaert);
        Msg.setText(msg);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                Intent i = new Intent(context, RestaurantActivity.class);
                context.startActivity(i);
            }
        });

    }

    public static void set_order_popup(final Context context, String msg) {
        LayoutInflater li = LayoutInflater.from(context);
        View v = li.inflate(R.layout.alert_popup, null);
        final android.support.v7.app.AlertDialog alert = new android.support.v7.app.AlertDialog.Builder(context).setView(v).show();
        alert.setCancelable(false);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Textview cancel = (Textview) v.findViewById(R.id.con_ok);
        Textview Msg = (Textview) v.findViewById(R.id.msglaert);
        Msg.setText(msg);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                Intent i = new Intent(context, MainActivity.class);
                i.putExtra("fragmentcode", Config.Fragment_ID.MY_order);
                context.startActivity(i);
            }
        });

    }

    public static void setremove_cart(final Context context, String msg) {
        pref = new Pref_Master(context);
        LayoutInflater li = LayoutInflater.from(context);
        View v = li.inflate(R.layout.alert_remove_cart_popup, null);
        final android.support.v7.app.AlertDialog alert = new android.support.v7.app.AlertDialog.Builder(context).setView(v).show();
        alert.setCancelable(false);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Textview ok = (Textview) v.findViewById(R.id.con_ok);
        Textview cancel = (Textview) v.findViewById(R.id.con_cancel);
        Textview Msg = (Textview) v.findViewById(R.id.msglaert);
        Msg.setText(msg);

        if (pref.getUID().equals("")) {
            userid = pref.getStr_guest_UID();
        } else {
            userid = pref.getUID();
        }
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = Config.app_url + Config.Removecart;
                JSONObject jobj_loginuser = new JSONObject();
                try {

                    JSONObject jobj_row = new JSONObject();

                    jobj_row.put("cartid", pref.getCart_id());
                    jobj_row.put("userid", userid);
                    jobj_row.put("restid", pref.getStr_res_id());


                    JSONArray jarray_loginuser = new JSONArray();
                    jarray_loginuser.put(jobj_row);

                    jobj_loginuser.put("removecart", jarray_loginuser);
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
                        try {
                            JSONObject jobj = new JSONObject(response);
                            String res_flag = jobj.getString("status");
                            if (res_flag.equals("200")) {

                                pref.setRes_id("");
                                pref.setCart_id("");
                                pref.setCart_count(0);

                                Intent i = new Intent(context, MainActivity.class);
                                i.putExtra("fragmentcode", Config.Fragment_ID.res_list);
                                context.startActivity(i);

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
                    }
                };

                Connection.postconnection(url, params, header, context, lis_res, lis_error);
                alert.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
            }
        });

    }

    public static void change_pwd_Popup(final Context context, String msg) {
        LayoutInflater li = LayoutInflater.from(context);
        View v = li.inflate(R.layout.alert_popup, null);
        final android.support.v7.app.AlertDialog alert = new android.support.v7.app.AlertDialog.Builder(context).setView(v).show();
        alert.setCancelable(false);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Textview cancel = (Textview) v.findViewById(R.id.con_ok);
        Textview Msg = (Textview) v.findViewById(R.id.msglaert);
        Msg.setText(msg);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                Intent i = new Intent(context, MainActivity.class);
                i.putExtra("fragmentcode", Config.Fragment_ID.account);
                context.startActivity(i);
            }
        });

    }

    public static void Otp_popup(final Context context, String msg) {
        LayoutInflater li = LayoutInflater.from(context);
        View v = li.inflate(R.layout.alert_popup, null);
        final android.support.v7.app.AlertDialog alert = new android.support.v7.app.AlertDialog.Builder(context).setView(v).show();
        alert.setCancelable(false);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Textview cancel = (Textview) v.findViewById(R.id.con_ok);
        Textview Msg = (Textview) v.findViewById(R.id.msglaert);
        Msg.setText(msg);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                Intent i = new Intent(context, Otp_forgotActivity.class);
                context.startActivity(i);
            }
        });

    }

    public static void setlogin(final Context context, String msg) {
        LayoutInflater li = LayoutInflater.from(context);
        View v = li.inflate(R.layout.alert_popup, null);
        final android.support.v7.app.AlertDialog alert = new android.support.v7.app.AlertDialog.Builder(context).setView(v).show();
        alert.setCancelable(false);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Textview cancel = (Textview) v.findViewById(R.id.con_ok);
        Textview Msg = (Textview) v.findViewById(R.id.msglaert);
        Msg.setText(msg);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                Intent i = new Intent(context, LoginActivity.class);
                context.startActivity(i);
            }
        });

    }


}