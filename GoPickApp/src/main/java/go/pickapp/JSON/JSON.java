package go.pickapp.JSON;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import go.pickapp.Model.Model_Product;
import go.pickapp.Model.Model_cart;
import go.pickapp.Model.Model_option;
import go.pickapp.Model.Model_restaurant;
import go.pickapp.Model.Model_user;
import go.pickapp.Shared.Pref_Master;

/**
 * Created by Admin on 8/30/2016.
 */
public class JSON {

    public static String add_user(ArrayList<Model_user> array_user, Pref_Master pref, String array_nm) {
        JSONObject jobj_data = new JSONObject();
        JSONObject jobj_main = new JSONObject();

        try {
            if (array_nm.equals("loginuser")) {
                for (int i = 0; i < array_user.size(); i++) {
                    Model_user model = array_user.get(i);
                    jobj_main.put("useremail", model.getEmail());
                    jobj_main.put("name", model.getName());
                    jobj_main.put("userpass", model.getPassword());
                    jobj_main.put("devicetype", "2");
                    jobj_main.put("deviceid", model.getDeviceid());
                    jobj_main.put("devicetoken", model.getDevicetoken());
                    jobj_main.put("logintype", model.getLoginstatus());
                    jobj_main.put("socialuniqueid", model.getId());
                }

            }
            if (array_nm.equals("registeruser")) {
                for (int i = 0; i < array_user.size(); i++) {
                    Model_user model = array_user.get(i);
                    jobj_main.put("useremail", model.getEmail());
                    jobj_main.put("userpass", model.getPassword());
                    jobj_main.put("fname", model.getFname());
                    jobj_main.put("lname", model.getLname());
                    jobj_main.put("mobile", model.getMobile());
                    jobj_main.put("language", pref.getLanguage());
                    jobj_main.put("logintype", "1");
                    jobj_main.put("deviceid", model.getDeviceid());
                    jobj_main.put("devicetype", "2");
                    jobj_main.put("devicetoken", model.getDevicetoken());
                }

            }
            if (array_nm.equals("sendotptouser")) {
                for (int i = 0; i < array_user.size(); i++) {
                    Model_user model = array_user.get(i);
                    jobj_main.put("email", model.getEmail());
                    jobj_main.put("fname", model.getFname());
                    jobj_main.put("lname", model.getLname());
                }

            }
            if (array_nm.equals("edituserprofile")) {
                for (int i = 0; i < array_user.size(); i++) {
                    Model_user model = array_user.get(i);
                    jobj_main.put("userid", pref.getUID());
                    jobj_main.put("email", model.getEmail());
                    jobj_main.put("mobile", model.getMobile());
                    jobj_main.put("fname", model.getFname());
                    jobj_main.put("lname", model.getLname());
                }

            }
            if (array_nm.equals("changepassword")) {
                for (int i = 0; i < array_user.size(); i++) {
                    Model_user model = array_user.get(i);
                    jobj_main.put("userid", pref.getUID());
                    jobj_main.put("oldpass", model.getOld_pwd());
                    jobj_main.put("newpass", model.getNew_pwd());

                }

            }
            if (array_nm.equals("forgotpassword")) {
                for (int i = 0; i < array_user.size(); i++) {
                    Model_user model = array_user.get(i);
                    jobj_main.put("useremail", model.getEmail());
                    jobj_main.put("process", model.getProcess());
                    jobj_main.put("newpass", model.getNew_pwd());

                }

            }
            if (array_nm.equals("logoutuser")) {
                jobj_main.put("userid", pref.getUID());
            }

            if (array_nm.equals("addguestuser")) {
                for (int i = 0; i < array_user.size(); i++) {
                    Model_user model = array_user.get(i);
                    jobj_main.put("fname", model.getFname());
                    jobj_main.put("lname", model.getLname());
                    jobj_main.put("mobile", model.getMobile());
                    jobj_main.put("devicetype", "2");
                    jobj_main.put("devicetoken", model.getDevicetoken());
                }

            }
            JSONArray jarray_main = new JSONArray();
            jarray_main.put(jobj_main);

            jobj_data.put(array_nm, jarray_main);

        } catch (
                JSONException e
                )

        {
            e.printStackTrace();
        }

        Log.i("Json request", jobj_data.toString().

                replaceAll("\\\\", "")

        );

        return jobj_data.toString().

                replaceAll("\\\\", "");
    }

    public static String Restaurant(ArrayList<Model_restaurant> array_user, Pref_Master pref, String array_nm) {
        JSONObject jobj_data = new JSONObject();
        JSONObject jobj_main = new JSONObject();

        try {
            if (array_nm.equals("getrestaurantsbycity")) {
                for (int i = 0; i < array_user.size(); i++) {
                    Model_restaurant model = array_user.get(i);
                    jobj_main.put("filterby", model.getFilter());
                    jobj_main.put("sortby", model.getSort());
                    jobj_main.put("cusines", model.getCusines());
                    jobj_main.put("cityid", model.getCity());
                    jobj_main.put("latofuser", model.getLatitude());
                    jobj_main.put("longofuser", model.getLongitude());
                    jobj_main.put("status", model.getStatus());
                    jobj_main.put("str", model.getStr());

                }

            }

            JSONArray jarray_main = new JSONArray();
            jarray_main.put(jobj_main);

            jobj_data.put(array_nm, jarray_main);

        } catch (
                JSONException e
                )

        {
            e.printStackTrace();
        }

        Log.i("Json request", jobj_data.toString().

                replaceAll("\\\\", "")

        );

        return jobj_data.toString().

                replaceAll("\\\\", "");
    }


    public static String place_order(ArrayList<Model_cart> array_user, Pref_Master pref, String array_nm, ArrayList<Model_cart> array_cart) {
        JSONObject jobj_data = new JSONObject();

        JSONObject jobj_main = new JSONObject();

        try {
            switch (array_nm) {
                case "placeorder":
                    for (int i = 0; i < array_user.size(); i++) {
                        Model_cart model = array_user.get(i);
                        jobj_main.put("customerid", pref.getUID());
                        jobj_main.put("restid", pref.getStr_res_id());
                        jobj_main.put("couponcode", model.getCopoun_code());
                        jobj_main.put("total", model.getTotal_cart());
                        jobj_main.put("discount", model.getDiscount());
                        jobj_main.put("discountper", model.getDis_per());
                        jobj_main.put("serviceper", model.getService_per());
                        jobj_main.put("serviceamt", model.getService_amt());
                        jobj_main.put("finaltotal", model.getFinal_total());
                        jobj_main.put("paymenttypeid", model.getPay_id());


                    }
                    break;

            }

            JSONArray jarray_user = new JSONArray();

            for (int i = 0; i < array_cart.size(); i++) {
                Model_cart model = array_cart.get(i);
                JSONObject jobj_user = new JSONObject();
                jobj_user.put("productid", model.getProduct_id());
                jobj_user.put("qty", model.getQty());
                jobj_user.put("rate", model.getRate());
                jarray_user.put(jobj_user);

            }
            Log.e("Arrayyy_jinn", ":" + jarray_user);


            jobj_main.put("product", jarray_user);
            JSONArray jarray_main = new JSONArray();
            jarray_main.put(jobj_main);

            jobj_data.put(array_nm, jarray_main);

        } catch (
                JSONException e
                )

        {
            e.printStackTrace();
        }

        Log.e("Json request", jobj_data.toString().

                replaceAll("\\\\", "")

        );

        return jobj_data.toString().

                replaceAll("\\\\", "");
    }

    public static String add_cart(ArrayList<Model_restaurant> array_user, Pref_Master pref, String array_nm, ArrayList<Model_option.Productoption> array_product, String arry, String productoptiondetail) {
        JSONObject jobj_data = new JSONObject();

        JSONObject jobj_main = new JSONObject();


        try {
            if (array_nm.equals("addcart")) {
                for (int i = 0; i < array_user.size(); i++) {
                    Model_restaurant model = array_user.get(i);
                    jobj_main.put("cartid", pref.getCart_id());
                    jobj_main.put("userid", pref.getUID());
                    jobj_main.put("restid", model.getRes_id());
                    jobj_main.put("productid", model.getProduct_id());
                    jobj_main.put("qty", model.getQty());
                    jobj_main.put("rate", model.getRate());
                    jobj_main.put("total", model.getTotal());
                }
            }

            JSONArray jarray_user = new JSONArray();
            if (arry.equals("productoption")) {
                for (int i = 0; i < array_product.size(); i++) {
                    Model_option.Productoption model = array_product.get(i);
                    JSONObject jobj_user = new JSONObject();
                    jobj_user.put("productoptionid", model.getProductoptionid());
                    jarray_user.put(jobj_user);

                    JSONArray jarry = new JSONArray();
                    if (productoptiondetail.equals("productoptiondetail")) {
                        for (int j = 0; j < array_product.get(i).getProductoptiondetail().size(); j++) {
                            Model_option.Productoption modell = array_product.get(j);
                            // if (array_product.get(j).getProductoptiondetail().get(j).isSelected()) {
                            JSONObject obj = new JSONObject();
                            obj.put("productoptiondetailid", modell.getProductoptiondetail().get(j).getProductoptiondetailid());
                            obj.put("optionrate", modell.getProductoptiondetail().get(j).getRate());
                            jarry.put(obj);
                            // }
                        }
                    }
                    jobj_user.put(productoptiondetail, jarry);
                }

            }
            jobj_main.put(arry, jarray_user);

            JSONArray jarray_main = new JSONArray();
            jarray_main.put(jobj_main);

            jobj_data.put(array_nm, jarray_main);

        } catch (
                JSONException e
                )

        {
            e.printStackTrace();
        }

        Log.e("Json request", jobj_data.toString().

                replaceAll("\\\\", "")

        );

        return jobj_data.toString().

                replaceAll("\\\\", "");
    }


}
