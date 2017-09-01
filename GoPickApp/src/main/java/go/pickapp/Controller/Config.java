package go.pickapp.Controller;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

/**
 * Created by Admin on 12/5/2016.
 */

public class Config {

    //public static String app_url = "http://5.189.141.180/fosbranch/apistest/index.php/";
    public static String app_url = "http://gopickapp.com/beta/webservices/index.php/";


    // public static String app_url = "http://gopickapp.com/beta/apis/index.php/";

    public static String headunm = "parshwa";
    public static String headkey = "564565fdgdfgghffg154";
    public static String Language = "userlanguage";


    public static String Message = "Server Error....Please try again...!!!";

    public final static class Fragment_ID {
        public final static int home = 1;
        public final static int Change_password = 6;
        public final static int Order_complete = 7;
        public final static int MY_order = 8;
        public final static int About = 9;
        public final static int Deals = 10;
        public final static int existing = 11;
        public final static int history = 12;
        public final static int location = 13;
        public final static int res_list = 14;
        public final static int account = 15;
        public final static int order_detail = 16;
    }

    public static String Login = "loginuser";
    public static String Sign_up = "registeruser";
    public static String Send_OTP = "sendotptouser";
    public static String Getuserprofile = "getuserprofile";
    public static String Edituserprofile = "edituserprofile";
    public static String Changepassword = "changepassword";
    public static String Forgotpassword = "forgotpassword";
    public static String Getrestaurantsbycity = "getrestaurantsbycity";
    public static String Getcity = "getcity";
    public static String Getproductlist = "getproductlist";
    public static String Getproductdetails = "getproductdetails";
    public static String Getrestaurantdetails = "getrestaurantdetails";
    public static String Getcuisine = "getcuisine";
    public static String Getpaymenttype = "getpaymenttype";
    public static String Logoutuser = "logoutuser";
    public static String Addcart = "addcart";
    public static String Getcart = "getcart";
    public static String Removecartproduct = "removecartproduct";
    public static String Updatecart = "updatecart";
    public static String Placeorder = "placeorder";
    public static String Removecart = "removecart";
    public static String Getorderlist = "getorderlist";
    public static String Cmspages = "cmspages";
    public static String Getorderdetails = "getorderdetails";
    public static String Getnotification = "getnotification";
    public static String Getallrestaurantlist = "getallrestaurantlist";
    public static String Getdeals = "getdeals";
    public static String Reviewlist = "reviewlist";
    public static String Getcityarea = "getcityarea";
    public static String Searchcityarea = "searchcityarea";
    public static String Getdiscount = "getdiscount";
    public static String Addreview = "addreview";
    public static String Cancelorder = "cancelorder";
    public static String Reorder = "reorder";
    public static String addcartvalidation = "addcartvalidation";
    public static String Updatemobile = "updatemobile";
    public static String searchallrestaurant = "searchallrestaurant";
    public static String searchonmap = "searchonmap";
    public static String addguestuser = "addguestuser";
    public static String getfilterterms = "getfilterterms";
    public static String viewreorder = "viewreorder";
    public static String reorderproductdetail = "reorderproductdetail";
    public static String reorderdelete = "reorderdelete";
    public static String reorderproductupdate = "reorderproductupdate";
    public static String getmasterarealist = "getmasterarealist";
    public static String searchonmapdistancefilter = "searchonmapdistancefilter";
    public static String searchonmapfilter = "searchonmapfilter";
    public static String searchonmapfilterterms = "searchonmapfilterterms";
    public static String searchonmapfiltertermsandroid = "searchonmapfiltertermsandroid";
    public static String getcuisineandroid = "getcuisineandroid";

    // code to define map header
    public static Map<String, String> getHeaderParam() {
        Map<String, String> header = new HashMap<>();
        header.put("username", Config.headunm);
        header.put("apikey", Config.headkey);
        return header;
    }

    public static Map<String, String> getHeaderParam1(Context context) {
        Pref_Master pref_master = new Pref_Master(context);
        Map<String, String> header = new HashMap<>();
        header.put("username", Config.headunm);
        header.put("apikey", Config.headkey);
        header.put("userlanguage", "" + pref_master.getLanguage());
        return header;
    }

    public static boolean isValidEmailAddress(String emailAddress) {
        String emailRegEx;
        Pattern pattern;
        // Regex for a valid email address
        emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
        // Compare the regex with the email address
        pattern = Pattern.compile(emailRegEx);
        Matcher matcher = pattern.matcher(emailAddress);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }

    public void Change_Language(Context context, String lang) {
        //0 = English 1 = Arabic
        Locale locale = new Locale(lang.equals("1") ? "ar" : "en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    public static String Check_Language() {
        // 0 = English , 1 = Arabic
        return Locale.getDefault().getDisplayLanguage().equals("Arabic") ? "1" : "0";
    }


    public static String MobilePattern = "[0-9]{10}";

    public static class Utility {
        public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public static boolean checkPermission(final Context context) {
            int currentAPIVersion = Build.VERSION.SDK_INT;
            if (currentAPIVersion >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                        alertBuilder.setCancelable(true);
                        alertBuilder.setTitle("Permission necessary");
                        alertBuilder.setMessage("External storage permission is necessary");
                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                            }
                        });
                        AlertDialog alert = alertBuilder.create();
                        alert.show();
                    } else {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
    }

    public static void Select_Status(ArrayList<LinearLayout> ll, int pos, ArrayList<ImageView> Arr_Chk_cat) {
        for (int i = 0; i < ll.size(); i++) {
            Arr_Chk_cat.get(i).setImageResource((i == pos) ? R.drawable.radio_selected : R.drawable.radio_unselected);
        }

    }

    public boolean Check_GPS(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

}
