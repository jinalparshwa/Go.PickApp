package go.pickapp.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import go.pickapp.Controller.Activity_indicator;
import go.pickapp.Controller.Config;
import go.pickapp.Controller.Connection;
import go.pickapp.Controller.CustomTypefaceSpan;
import go.pickapp.Controller.DialogBox;
import go.pickapp.Controller.GPSTracker;
import go.pickapp.Controller.Textview;
import go.pickapp.Fragments.Aboutus;
import go.pickapp.Fragments.Account;
import go.pickapp.Fragments.ChangepwdFragment;
import go.pickapp.Fragments.Deals;
import go.pickapp.Fragments.Location;
import go.pickapp.Fragments.MainFragment;
import go.pickapp.Fragments.My_order;
import go.pickapp.Fragments.Notification;
import go.pickapp.Fragments.Order_complete;
import go.pickapp.Fragments.Order_detail_for_guest;
import go.pickapp.Fragments.Order_history;
import go.pickapp.JSON.JSON;
import go.pickapp.Model.Model_restaurant;
import go.pickapp.Model.Model_user;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

import static go.pickapp.Fragments.MainFragment.res_list;
import static go.pickapp.Activity.FilterActivity.cusine_new;
import static go.pickapp.Activity.FilterActivity.filter_new;


public class MainActivity extends AppCompatActivity {

    ImageView notify_bar, img_about;
    DrawerLayout drawer;
    int fragmentcode = 1;
    Textview headerText;
    Context context = this;
    Pref_Master pref;
    Textview txt_city;
    ImageView img_city;
    ArrayList<Model_restaurant> arraylist = new ArrayList<>();
    ArrayList<Model_user> array_user = new ArrayList<>();
    String lat;
    String lang;
    Activity_indicator obj_dialog;
    NavigationView navigationView;
    Textview txt_head;
    boolean doubleBackToExitPressedOnce = false;
    ImageView img_filter;
    int value = 0;
    RecyclerView list;
    FrameLayout frame;
    ArrayList<Integer> array_image = new ArrayList<>();
    ArrayList<String> array_menu = new ArrayList<>();
    U_NavigationAdapter adapter;
    ArrayList<Model_restaurant> Array_res = new ArrayList<>();
    private static final int REQUEST_LOCATION = 199;
    Boolean GpsStatus;
    LocationManager locationManager;
    private GoogleApiClient googleApiClient;
    GPSTracker gpsTracker;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    ImageView img_refresh;
    ImageView img_search;
    public static int value_new = 0;
    String lan = "";
    // ImageView img_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // gps_on();
        pref = new Pref_Master(context);
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));



        gpsTracker = new GPSTracker(context);
        pref.setBack(2);
        // CheckGpsStatus();
        showPermiosssion();

        list = (RecyclerView) findViewById(R.id.list);
        txt_head = (Textview) findViewById(R.id.txt_head);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            // mDrawerToggle = new ActionBarDrawerToggle(ServiceProvider_home.this, mDrawerLayout, R.string.openDrawer, R.string.openDrawer) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                cusine_new = "";
                filter_new = "";

                if (pref.getStr_login_flag().equals("login")) {
                    Get_profile();

                } else {
                    txt_head.setText(R.string.Login_create);
                }
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.setDrawerIndicatorEnabled(true);


        if (pref.getStr_login_flag().equals("login")) {
        } else {
            txt_head.setText(R.string.Login_create);
            txt_head.setClickable(true);
            txt_head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            });
        }

        txt_city = (Textview) findViewById(R.id.txt_city);
        txt_city.setText(pref.getStr_city_name());

        headerText = (Textview) findViewById(R.id.headertext);
        notify_bar = (ImageView) findViewById(R.id.notify_bar);
        img_about = (ImageView) findViewById(R.id.img_about);
        img_city = (ImageView) findViewById(R.id.img_city);
        img_filter = (ImageView) findViewById(R.id.img_filter);
        img_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("jinal_map__", "Hellolo");
                value_new = 1;
                setfragment(getString(R.string.Search_restaurant), new Location());
                headerText.setVisibility(View.VISIBLE);
                img_city.setVisibility(View.GONE);
                txt_city.setVisibility(View.GONE);
                img_about.setVisibility(View.GONE);
                img_filter.setVisibility(View.VISIBLE);
                img_refresh.setVisibility(View.GONE);
                img_search.setVisibility(View.VISIBLE);

            }
        });
        img_search = (ImageView) findViewById(R.id.img_search);
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Hello", "jinal");
                Intent i = new Intent(MainActivity.this, Search_restaurantActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                finish();
            }
        });

        headerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (headerText.getText().toString().equals(getResources().getString(R.string.Search_restaurant))) {
                    Intent i = new Intent(MainActivity.this, Search_restaurantActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    finish();
                }
            }
        });
        img_refresh = (ImageView) findViewById(R.id.img_refresh);
        img_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setfragment(getString(R.string.My_orders), new My_order());
                headerText.setVisibility(View.VISIBLE);
                img_city.setVisibility(View.GONE);
                txt_city.setVisibility(View.GONE);
                img_about.setVisibility(View.GONE);
                img_filter.setVisibility(View.GONE);
                img_search.setVisibility(View.GONE);
            }
        });

        adapter = new U_NavigationAdapter();
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(context));
        list.setHasFixedSize(true);


        txt_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                res_list.clear();
                filter_new = "";
                cusine_new = "";
                Intent i = new Intent(MainActivity.this, Choose_area.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                finish();
            }
        });

        img_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                res_list.clear();
                Intent i = new Intent(MainActivity.this, Choose_area.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                finish();
            }
        });


        notify_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        img_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Restaurant_detailsActivity.class);
                startActivity(i);

            }
        });

        final int[] size = {array_menu.size()};
        String string = String.valueOf(size[0]);
        boolean arrraaay = Boolean.valueOf(string);

        list.setNestedScrollingEnabled(arrraaay);
        frame = (FrameLayout) findViewById(R.id.frame);

        if (getIntent().getExtras() != null) {

            fragmentcode = getIntent().getIntExtra("fragmentcode", 1);
            Log.e("Jinal_fragment", "" + fragmentcode);

            switch (fragmentcode) {
                case Config.Fragment_ID.home:
                    setfragment(getString(R.string.Search_restaurant), new Location());
                    headerText.setVisibility(View.VISIBLE);
                    img_city.setVisibility(View.GONE);
                    txt_city.setVisibility(View.GONE);
                    img_about.setVisibility(View.GONE);
                    img_filter.setVisibility(View.VISIBLE);
                    img_refresh.setVisibility(View.GONE);
                    img_search.setVisibility(View.VISIBLE);
                    break;
                case Config.Fragment_ID.Change_password:
                    setfragment(getString(R.string.Change_pwd), new ChangepwdFragment());
                    headerText.setVisibility(View.VISIBLE);
                    img_city.setVisibility(View.GONE);
                    txt_city.setVisibility(View.GONE);
                    img_about.setVisibility(View.GONE);
                    img_filter.setVisibility(View.GONE);
                    img_search.setVisibility(View.GONE);
                    img_refresh.setVisibility(View.GONE);
                    break;
                case Config.Fragment_ID.Order_complete:
                    setfragment(getString(R.string.Place_order), new Order_complete());
                    headerText.setVisibility(View.VISIBLE);
                    img_city.setVisibility(View.GONE);
                    txt_city.setVisibility(View.GONE);
                    img_about.setVisibility(View.GONE);
                    img_filter.setVisibility(View.GONE);
                    img_refresh.setVisibility(View.GONE);
                    img_search.setVisibility(View.GONE);
                    break;
                case Config.Fragment_ID.MY_order:
                    pref.setStr_refresh_back(0);
                    setfragment(getString(R.string.My_orders), new My_order());
                    headerText.setVisibility(View.VISIBLE);
                    img_city.setVisibility(View.GONE);
                    txt_city.setVisibility(View.GONE);
                    img_about.setVisibility(View.GONE);
                    img_filter.setVisibility(View.GONE);
                    img_refresh.setVisibility(View.VISIBLE);
                    img_search.setVisibility(View.GONE);
                    break;
                case Config.Fragment_ID.About:
                    setfragment(getString(R.string.About), new Aboutus());
                    headerText.setVisibility(View.VISIBLE);
                    img_city.setVisibility(View.GONE);
                    txt_city.setVisibility(View.GONE);
                    img_about.setVisibility(View.GONE);
                    img_filter.setVisibility(View.GONE);
                    img_refresh.setVisibility(View.GONE);
                    img_search.setVisibility(View.GONE);
                    break;
                case Config.Fragment_ID.Deals:
                    setfragment(getString(R.string.deals), new Deals());
                    headerText.setVisibility(View.VISIBLE);
                    img_city.setVisibility(View.GONE);
                    txt_city.setVisibility(View.GONE);
                    img_about.setVisibility(View.GONE);
                    img_filter.setVisibility(View.GONE);
                    img_refresh.setVisibility(View.GONE);
                    img_search.setVisibility(View.GONE);
                    break;
                case Config.Fragment_ID.location:
                    setfragment(getResources().getString(R.string.Search_restaurant), new Location());
                    headerText.setVisibility(View.VISIBLE);
                    img_city.setVisibility(View.GONE);
                    txt_city.setVisibility(View.GONE);
                    img_about.setVisibility(View.GONE);
                    img_filter.setVisibility(View.VISIBLE);
                    img_refresh.setVisibility(View.GONE);
                    img_search.setVisibility(View.GONE);
                    break;
                case Config.Fragment_ID.account:
                    setfragment(getString(R.string.Account), new Account());
                    headerText.setVisibility(View.VISIBLE);
                    img_city.setVisibility(View.GONE);
                    txt_city.setVisibility(View.GONE);
                    img_about.setVisibility(View.GONE);
                    img_filter.setVisibility(View.GONE);
                    img_refresh.setVisibility(View.GONE);
                    img_search.setVisibility(View.GONE);
                    break;
                case Config.Fragment_ID.order_detail:
                    setfragment(getString(R.string.Order_details), new Order_detail_for_guest());
                    headerText.setVisibility(View.VISIBLE);
                    img_city.setVisibility(View.GONE);
                    txt_city.setVisibility(View.GONE);
                    img_about.setVisibility(View.GONE);
                    img_filter.setVisibility(View.GONE);
                    img_refresh.setVisibility(View.GONE);
                    img_search.setVisibility(View.GONE);
                    break;

                case Config.Fragment_ID.res_list:
                    setfragment(getResources().getString(R.string.Home), new MainFragment());
                    img_about.setVisibility(View.GONE);
                    img_filter.setVisibility(View.GONE);
                    img_refresh.setVisibility(View.GONE);
                    img_search.setVisibility(View.GONE);
                    break;

                case Config.Fragment_ID.history:
                    setfragment("", new Order_history());
                    img_about.setVisibility(View.GONE);
                    img_filter.setVisibility(View.GONE);
                    img_refresh.setVisibility(View.GONE);
                    img_search.setVisibility(View.GONE);
                    break;

            }

        } else {
            setfragment(getResources().getString(R.string.Search_restaurant), new Location());
            headerText.setVisibility(View.VISIBLE);
            img_city.setVisibility(View.GONE);
            txt_city.setVisibility(View.GONE);
            img_about.setVisibility(View.GONE);
            img_filter.setVisibility(View.VISIBLE);
            img_refresh.setVisibility(View.GONE);
            img_search.setVisibility(View.VISIBLE);


        }

    }

    public void showPermiosssion() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // All good!
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                } else {
                    //Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    public void Get_profile() {

        // obj_dialog.show();

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
                //obj_dialog.dismiss();

                try {
                    JSONObject jobj = new JSONObject(response);
                    String res_flag = jobj.getString("status");
                    if (res_flag.equals("200")) {
                        JSONArray jarray = new JSONArray(jobj.getString("data"));

                        for (int i = 0; i < jarray.length(); i++) {
                            JSONObject jobj1 = jarray.getJSONObject(i);
                            txt_head.setText(getResources().getString(R.string.Welcome) + " " + jobj1.getString("fname") + " " + jobj1.getString("lname"));
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
                // obj_dialog.dismiss();
            }
        };
        Connection.getconnectionVolley(url, param, header, context, lis_res, lis_error);
    }


    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Regular.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    public void setfragment(String title, Fragment fragment) {
        headerText.setText(title);
        Bundle bundle = new Bundle();
        //  bundle.putInt("value",1);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();


    }

    public class U_NavigationAdapter extends RecyclerView.Adapter<U_NavigationHolder> {


        public U_NavigationAdapter() {

            if (pref.getStr_login_flag().equals("login")) {

                array_image.add(R.drawable.new_menu_map);
                array_image.add(R.drawable.new_menu_restaurants);
                // array_image.add(R.drawable.new_menu_deals);
                array_image.add(R.drawable.new_menu_orders);
                array_image.add(R.drawable.notification);
                array_image.add(R.drawable.new_menu_account);
                array_image.add(R.drawable.chage_language);
                array_image.add(R.drawable.new_menu_about);
                array_image.add(R.drawable.new_menu_logout);

                array_menu.add(getResources().getString(R.string.Map));
                array_menu.add(getResources().getString(R.string.Restaurants));
                //array_menu.add(getResources().getString(R.string.deals));
                array_menu.add(getResources().getString(R.string.Orders));
                array_menu.add(getResources().getString(R.string.Notification));
                array_menu.add(getResources().getString(R.string.Account));
                array_menu.add(getResources().getString(R.string.Change_language));
                array_menu.add(getResources().getString(R.string.About));
                array_menu.add(getResources().getString(R.string.Logout));

            } else {
                array_image.add(R.drawable.new_menu_map);
                array_image.add(R.drawable.new_menu_restaurants);
                //array_image.add(R.drawable.new_menu_deals);
                // array_image.add(R.drawable.new_menu_orders);
                // array_image.add(R.drawable.notification);
                // array_image.add(R.drawable.new_menu_account);
                array_image.add(R.drawable.chage_language);
                array_image.add(R.drawable.new_menu_about);
                // array_image.add(R.drawable.new_menu_logout);

                array_menu.add(getResources().getString(R.string.Map));
                array_menu.add(getResources().getString(R.string.Restaurants));
                // array_menu.add(getResources().getString(R.string.deals));
                //array_menu.add(getResources().getString(R.string.Orders));
                // array_menu.add(getResources().getString(R.string.Notification));
                // array_menu.add(getResources().getString(R.string.Account));
                array_menu.add(getResources().getString(R.string.Change_language));
                array_menu.add(getResources().getString(R.string.About));
                // array_menu.add(getResources().getString(R.string.Logout));
            }
        }

        @Override
        public U_NavigationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new U_NavigationHolder(LayoutInflater.from(context).inflate(R.layout.u_navigation_listrow, null));
        }


        @Override
        public void onBindViewHolder(final U_NavigationHolder holder, final int position) {
            holder.u_navigation_row_image.setImageResource(array_image.get(position));
            holder.u_navigation_row_name.setText(array_menu.get(position));
            holder.ll_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (position) {
                        case 0:
                            setfragment(getResources().getString(R.string.Search_restaurant), new Location());
                            headerText.setVisibility(View.VISIBLE);
                            img_city.setVisibility(View.GONE);
                            txt_city.setVisibility(View.GONE);
                            img_about.setVisibility(View.GONE);
                            img_filter.setVisibility(View.VISIBLE);
                            img_search.setVisibility(View.VISIBLE);
                            img_refresh.setVisibility(View.GONE);
                            drawer.closeDrawers();
                            break;

                        case 1:
                            if (pref.getCart_id().equals("")) {

                              /*  if (pref.getStr_city_id().equals("")) {
                                    Intent i = new Intent(MainActivity.this, Choose_area.class);
                                    startActivity(i);
                                    finish();

                                } else {
                                    setfragment("", new MainFragment());
                                    headerText.setVisibility(View.GONE);
                                    img_about.setVisibility(View.GONE);
                                    img_filter.setVisibility(View.GONE);
                                    img_city.setVisibility(View.VISIBLE);
                                    txt_city.setVisibility(View.VISIBLE);
                                    img_refresh.setVisibility(View.GONE);
                                }
*/


                                Intent i = new Intent(MainActivity.this, Search_restaurantActivity.class);
                                startActivity(i);
                                finish();

                            } else {
                                DialogBox.setremove_cart(context, getResources().getString(R.string.Remove_cart_popup));
                            }
                            drawer.closeDrawers();
                            break;
//                        case 2:
//                            setfragment(getString(R.string.deals), new Deals());
//                            headerText.setVisibility(View.VISIBLE);
//                            img_city.setVisibility(View.GONE);
//                            txt_city.setVisibility(View.GONE);
//                            img_about.setVisibility(View.GONE);
//                            img_filter.setVisibility(View.GONE);
//                            drawer.closeDrawers();
//                            break;
                        case 2:
                            if (pref.getStr_login_flag().equals("login")) {
                                pref.setStr_refresh_back(0);
                                setfragment(getString(R.string.My_orders), new My_order());
                                headerText.setVisibility(View.VISIBLE);
                                img_city.setVisibility(View.GONE);
                                txt_city.setVisibility(View.GONE);
                                img_about.setVisibility(View.GONE);
                                img_filter.setVisibility(View.GONE);
                                img_search.setVisibility(View.GONE);
                                img_refresh.setVisibility(View.VISIBLE);
                                drawer.closeDrawers();
                                break;

                            } else {
                                LayoutInflater li = LayoutInflater.from(context);
                                View v = li.inflate(R.layout.dialog_language, null);
                                final android.support.v7.app.AlertDialog alert = new android.support.v7.app.AlertDialog.Builder(context).setView(v).show();
                                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                alert.setCancelable(true);

                                final ArrayList<LinearLayout> Arr_ll_cat = new ArrayList<>();
                                final ArrayList<ImageView> Arr_Chk_cat = new ArrayList<>();

                                Arr_ll_cat.add(0, (LinearLayout) v.findViewById(R.id.ll_l_English));
                                Arr_ll_cat.add(1, (LinearLayout) v.findViewById(R.id.ll_l_Arabic));

                                Arr_Chk_cat.add(0, (ImageView) v.findViewById(R.id.chk_l_english));
                                Arr_Chk_cat.add(1, (ImageView) v.findViewById(R.id.chk_l_arabic));

                                Textview okbtn = (Textview) v.findViewById(R.id.okbtn);
                                Textview cancel = (Textview) v.findViewById(R.id.cancel);

                                final String[] pos_lang = {"0"};
                                if (pref.getLanguage().equals("ar")) {
                                    Config.Select_Status(Arr_ll_cat, 1, Arr_Chk_cat);
                                    pos_lang[0] = "1";
                                } else {
                                    Config.Select_Status(Arr_ll_cat, 0, Arr_Chk_cat);
                                    pos_lang[0] = "0";
                                }

                                Arr_ll_cat.get(0).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Config.Select_Status(Arr_ll_cat, 0, Arr_Chk_cat);
                                        pos_lang[0] = "0";
                                    }
                                });

                                Arr_ll_cat.get(1).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Config.Select_Status(Arr_ll_cat, 1, Arr_Chk_cat);
                                        pos_lang[0] = "1";
                                    }
                                });

                                okbtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String languageToLoad = "en";
                                        if (pos_lang[0].equals("1")) {
                                            languageToLoad = "ar";
                                            pref.setLanguage("ar");

                                        } else {
                                            languageToLoad = "en";
                                            pref.setLanguage("en");
                                        }
                                        Locale locale = new Locale(languageToLoad);
                                        Locale.setDefault(locale);
                                        Configuration config = new Configuration();
                                        config.locale = locale;
                                        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
                                        alert.dismiss();
                                        Intent refresh = new Intent(context, MainActivity.class);
                                        context.startActivity(refresh);
                                        ((Activity) context).finish();
                                    }
                                });
                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        alert.dismiss();
                                    }
                                });
                                drawer.closeDrawers();
                                break;
                            }


                        case 3:
                            if (pref.getStr_login_flag().equals("login")) {
                                setfragment(getString(R.string.Notification), new Notification());
                                headerText.setVisibility(View.VISIBLE);
                                img_city.setVisibility(View.GONE);
                                txt_city.setVisibility(View.GONE);
                                img_about.setVisibility(View.GONE);
                                img_filter.setVisibility(View.GONE);
                                img_search.setVisibility(View.GONE);
                                img_refresh.setVisibility(View.GONE);
                                drawer.closeDrawers();
                                break;

                            } else {

                                setfragment(getString(R.string.About), new Aboutus());
                                headerText.setVisibility(View.VISIBLE);
                                img_city.setVisibility(View.GONE);
                                txt_city.setVisibility(View.GONE);
                                img_about.setVisibility(View.GONE);
                                img_filter.setVisibility(View.GONE);
                                img_refresh.setVisibility(View.GONE);
                                img_search.setVisibility(View.GONE);
                                drawer.closeDrawers();
                                break;
                            }
                        case 4:
                            setfragment(getString(R.string.Account), new Account());
                            headerText.setVisibility(View.VISIBLE);
                            img_city.setVisibility(View.GONE);
                            txt_city.setVisibility(View.GONE);
                            img_about.setVisibility(View.GONE);
                            img_filter.setVisibility(View.GONE);
                            img_refresh.setVisibility(View.GONE);
                            img_search.setVisibility(View.GONE);
                            drawer.closeDrawers();
                            break;
                        case 5:
                            LayoutInflater li = LayoutInflater.from(context);
                            View v = li.inflate(R.layout.dialog_language, null);
                            final android.support.v7.app.AlertDialog alert = new android.support.v7.app.AlertDialog.Builder(context).setView(v).show();
                            alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            alert.setCancelable(true);

                            final ArrayList<LinearLayout> Arr_ll_cat = new ArrayList<>();
                            final ArrayList<ImageView> Arr_Chk_cat = new ArrayList<>();

                            Arr_ll_cat.add(0, (LinearLayout) v.findViewById(R.id.ll_l_English));
                            Arr_ll_cat.add(1, (LinearLayout) v.findViewById(R.id.ll_l_Arabic));

                            Arr_Chk_cat.add(0, (ImageView) v.findViewById(R.id.chk_l_english));
                            Arr_Chk_cat.add(1, (ImageView) v.findViewById(R.id.chk_l_arabic));

                            Textview okbtn = (Textview) v.findViewById(R.id.okbtn);
                            Textview cancel = (Textview) v.findViewById(R.id.cancel);

                            final String[] pos_lang = {"0"};
                            if (pref.getLanguage().equals("ar")) {
                                Config.Select_Status(Arr_ll_cat, 1, Arr_Chk_cat);
                                pos_lang[0] = "1";
                            } else {
                                Config.Select_Status(Arr_ll_cat, 0, Arr_Chk_cat);
                                pos_lang[0] = "0";
                            }

                            Arr_ll_cat.get(0).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Config.Select_Status(Arr_ll_cat, 0, Arr_Chk_cat);
                                    pos_lang[0] = "0";
                                }
                            });

                            Arr_ll_cat.get(1).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Config.Select_Status(Arr_ll_cat, 1, Arr_Chk_cat);
                                    pos_lang[0] = "1";
                                }
                            });

                            okbtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String languageToLoad = "en";
                                    if (pos_lang[0].equals("1")) {
                                        languageToLoad = "ar";
                                        pref.setLanguage("ar");

                                    } else {
                                        languageToLoad = "en";
                                        pref.setLanguage("en");
                                    }
                                    Locale locale = new Locale(languageToLoad);
                                    Locale.setDefault(locale);
                                    Configuration config = new Configuration();
                                    config.locale = locale;
                                    context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
                                    alert.dismiss();
                                    Intent refresh = new Intent(context, MainActivity.class);
                                    context.startActivity(refresh);
                                    ((Activity) context).finish();
                                }
                            });
                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alert.dismiss();
                                }
                            });
                            drawer.closeDrawers();
                            break;
                        case 6:
                            setfragment(getString(R.string.About), new Aboutus());
                            headerText.setVisibility(View.VISIBLE);
                            img_city.setVisibility(View.GONE);
                            txt_city.setVisibility(View.GONE);
                            img_about.setVisibility(View.GONE);
                            img_filter.setVisibility(View.GONE);
                            img_refresh.setVisibility(View.GONE);
                            img_search.setVisibility(View.GONE);
                            drawer.closeDrawers();
                            break;
                        case 7:
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage(getResources().getString(R.string.exit_popup))
                                    .setCancelable(false)
                                    .setPositiveButton(getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Sign_out();

                                        }
                                    })
                                    .setNegativeButton(getResources().getString(R.string.No), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alertt = builder.create();
                            alertt.show();

                            String myclr = "#eb2629";
                            Button bq = alertt.getButton(DialogInterface.BUTTON_POSITIVE);
                            bq.setTextColor(Color.parseColor(myclr));

                            Button bq1 = alertt.getButton(DialogInterface.BUTTON_NEGATIVE);
                            bq1.setTextColor(Color.parseColor(myclr));
                    }

                }
            });


        }

        @Override
        public int getItemCount() {
            return array_image.size();
        }
    }

    public class U_NavigationHolder extends RecyclerView.ViewHolder {

        ImageView u_navigation_row_image;
        Textview u_navigation_row_name;
        LinearLayout ll_click;
        View v;

        public U_NavigationHolder(View itemView) {
            super(itemView);
            this.v = itemView;
            u_navigation_row_image = (ImageView) itemView.findViewById(R.id.u_navigation_row_image);
            u_navigation_row_name = (Textview) itemView.findViewById(R.id.u_navigation_row_name);
            ll_click = (LinearLayout) itemView.findViewById(R.id.ll_click);
        }
    }


    @Override
    public void onBackPressed() {
        setfragment(getResources().getString(R.string.Search_restaurant), new Location());
        headerText.setVisibility(View.VISIBLE);
        img_about.setVisibility(View.GONE);
        img_city.setVisibility(View.GONE);
        txt_city.setVisibility(View.GONE);
        img_filter.setVisibility(View.VISIBLE);
        img_search.setVisibility(View.VISIBLE);
        img_refresh.setVisibility(View.GONE);
//        finish();

        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            System.exit(0);
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getResources().getString(R.string.Press_again_to_exit), Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);


    }

    public void Sign_out() {

        obj_dialog.show();

        String url = Config.app_url + Config.Logoutuser;
        String json = "";

        json = JSON.add_user(array_user, pref, "logoutuser");

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

                        Intent i = new Intent(context, LoginActivity.class);
                        startActivity(i);
                        pref.setBack(0);
                        pref.setLogin_Flag("");
                        pref.setUID("");
                        pref.setLogin_type("");
                        pref.setCart_id("");
                        pref.setImage("");
                        pref.setRes_id("");
                        pref.setProduct_id("");
                        pref.setRes_name("");
                        pref.setUser_name("");
                        pref.setCart_count(0);
                        pref.setLogin_value(0);
                        pref.setStr_refresh_back(0);
                        pref.setFilter("");
                        pref.setBack_map("");

                        array_image.clear();
                        array_menu.clear();

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
