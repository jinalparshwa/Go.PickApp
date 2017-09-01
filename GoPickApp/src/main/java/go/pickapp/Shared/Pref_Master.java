package go.pickapp.Shared;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Pref_Master {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private String loginFlag = "loginflag";
    private String d_login_flag = "0";

    private String str_user_id = "uid";
    private String user_id = "0";

    private String str_login_type = "type";
    private String login_type = "0";

    private String str_Status_Sp = "sp_status";
    private String StatsuSp = "0";

    private String stre_spiid = "sp_id";
    private String sp_id = "0";

    private String str_language = "language";
    private String language = "";

    private String str_image = "image";
    private String image = "jp";

    private String str_cart_id = "cartid";
    private String cart_id = "";

    private String str_city_id = "city_id";
    private String city_id = "";

    private String str_city_name = "city_name";
    private String city_name = "";

    private String str_res_id = "res_id";
    private String res_id = "";

    private String str_res_name = "res_name";
    private String res_name = "";

    private String str_product_id = "product_id";
    private String product_id = "";

    private String str_username = "username";
    private String user_name = "";

    private String str_mobile = "mobile_no";
    private String mobile_no = "";

    private String str_cart_count = "count";
    private int cart_count = 0;

    private String str_back = "back";
    private int back = 0;

    private String str_login_value = "value";
    private int login_value = 0;

    private String str_refresh_back = "refresh_back";
    private int refresh_back = 0;

    private String str_guest_UID = "uid";
    private String guest_uid = "0";


    private String str_tips = "tips";
    private String tips = "";

    private String str_filter = "filter";
    private String filter = "";


    private String str_back_map = "back_map";
    private String back_map = "";


    // 0 = English , 1 = Arabic , 3 = first
    Context context;

    public Pref_Master(Context context) {
        this.context = context;
        pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getStr_login_flag() {
        return pref.getString(loginFlag, d_login_flag);
    }

    public String getStatusSp() {
        return pref.getString(str_Status_Sp, StatsuSp);
    }

    public String getUID() {
        return pref.getString(str_user_id, user_id);
    }

    public String getCart_id() {
        return pref.getString(str_cart_id, cart_id);
    }

    public String getSPID() {
        return pref.getString(stre_spiid, sp_id);
    }

    public String getLanguage() {
        return pref.getString(str_language, language);
    }

    public String getStr_login_type() {
        return pref.getString(str_login_type, login_type);
    }

    public String getStr_image() {
        return pref.getString(str_image, image);
    }

    public String getStr_city_id() {
        return pref.getString(str_city_id, city_id);
    }

    public String getStr_city_name() {
        return pref.getString(str_city_name, city_name);
    }

    public String getStr_res_name() {
        return pref.getString(str_res_name, res_name);
    }

    public String getStr_res_id() {
        return pref.getString(str_res_id, res_id);
    }

    public String getStr_mobile() {
        return pref.getString(str_mobile, mobile_no);
    }

    public String getStr_product_id() {
        return pref.getString(str_product_id, product_id);
    }

    public String getStr_username() {
        return pref.getString(str_username, user_name);
    }

    public int getCart_count() {
        return pref.getInt(str_cart_count, cart_count);
    }

    public int getBack() {
        return pref.getInt(str_back, back);
    }

    public int getRefresh_back() {
        return pref.getInt(str_refresh_back, refresh_back);
    }

    public int getLogin_value() {
        return pref.getInt(str_login_value, login_value);
    }

    public String getStr_guest_UID() {
        return pref.getString(str_guest_UID, guest_uid);
    }

    public String getStr_tips() {
        return pref.getString(str_tips, tips);
    }

    public String getStr_filter() {
        return pref.getString(str_filter, filter);
    }

    public String getStr_back_map() {
        return pref.getString(str_back_map, back_map);
    }


    public void setLanguage(String name) {
        editor = pref.edit();
        editor.putString(str_language, name);
        editor.apply();
    }

    public void setMobile_no(String name) {
        editor = pref.edit();
        editor.putString(str_mobile, name);
        editor.apply();
    }


    public void setLogin_Flag(String name) {
        editor = pref.edit();
        editor.putString(loginFlag, name);
        editor.apply();
    }

    public void set_Status_Sp(String name) {
        editor = pref.edit();
        editor.putString(str_Status_Sp, name);
        editor.apply();
    }

    public void setUID(String name) {
        editor = pref.edit();
        editor.putString(str_user_id, name);
        editor.apply();
    }

    public void setCart_id(String name) {
        editor = pref.edit();
        editor.putString(str_cart_id, name);
        editor.apply();
    }

    public void setSP_ID(String name) {
        editor = pref.edit();
        editor.putString(stre_spiid, name);
        editor.apply();
    }

    public void setLogin_type(String name) {
        editor = pref.edit();
        editor.putString(str_login_type, name);
        editor.apply();
    }

    public void setImage(String name) {
        editor = pref.edit();
        editor.putString(str_image, name);
        editor.apply();
    }

    public void setCity_id(String name) {
        editor = pref.edit();
        editor.putString(str_city_id, name);
        editor.apply();
    }

    public void setCity_name(String name) {
        editor = pref.edit();
        editor.putString(str_city_name, name);
        editor.apply();
    }

    public void setRes_id(String name) {
        editor = pref.edit();
        editor.putString(str_res_id, name);
        editor.apply();
    }

    public void setProduct_id(String name) {
        editor = pref.edit();
        editor.putString(str_product_id, name);
        editor.apply();
    }

    public void setRes_name(String name) {
        editor = pref.edit();
        editor.putString(str_res_name, name);
        editor.apply();
    }

    public void setUser_name(String name) {
        editor = pref.edit();
        editor.putString(str_username, name);
        editor.apply();
    }

    public void setCart_count(int name) {
        editor = pref.edit();
        editor.putInt(str_cart_count, name);
        editor.apply();
    }

    public void setBack(int name) {
        editor = pref.edit();
        editor.putInt(str_back, name);
        editor.apply();
    }

    public void setStr_refresh_back(int name) {
        editor = pref.edit();
        editor.putInt(str_refresh_back, name);
        editor.apply();
    }

    public void setLogin_value(int name) {
        editor = pref.edit();
        editor.putInt(str_login_value, name);
        editor.apply();
    }

    public void setGuest_uid(String name) {
        editor = pref.edit();
        editor.putString(str_guest_UID, name);
        editor.apply();
    }

    public void setTips(String name) {
        editor = pref.edit();
        editor.putString(str_tips, name);
        editor.apply();
    }

    public void setFilter(String name) {
        editor = pref.edit();
        editor.putString(str_filter, name);
        editor.apply();
    }

    public void setBack_map(String name) {
        editor = pref.edit();
        editor.putString(str_back_map, name);
        editor.apply();
    }


    public void clear_pref() {
        pref.edit().clear().apply();
    }
}
