package go.pickapp.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import go.pickapp.Controller.Activity_indicator;
import go.pickapp.Controller.Config;
import go.pickapp.Controller.Connection;
import go.pickapp.Controller.DialogBox;
import go.pickapp.Controller.Edittext;
import go.pickapp.Controller.PrefUtils;
import go.pickapp.Controller.Textview;
import go.pickapp.Controller.User;
import go.pickapp.JSON.JSON;
import go.pickapp.Model.Model_user;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    Textview txt_explore_restaurant;
    public static String reg_id = "";
    ArrayList<Model_user> Array_user = new ArrayList<>();
    private SignInButton signInButton;
    private GoogleSignInOptions gso;
    Pref_Master pref;
    Context context = this;
    RelativeLayout rr_login;
    Edittext et_email, et_password;
    Textview txt_forgot;
    LinearLayout rr_register;
    LinearLayout ll_fb;

    private GoogleApiClient mGoogleApiClient;
    //Signin constant to check the activity result
    private int RC_SIGN_IN = 100;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private ImageView btnLogin;
    private ProgressDialog progressDialog;
    User user;
    private int FB_SIGN_IN = 64206;
    public static String image = "";
    LinearLayout ll_sign_in;
    RelativeLayout rr_sign_in_email;
    LocationManager locationManager;
    Boolean GpsStatus;
    Activity_indicator obj_dialog;
    String username = "";
    String value = "";
    RelativeLayout rr_continue_guest;
    String mobile = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.login_activity);

        pref = new Pref_Master(context);
        obj_dialog = new Activity_indicator(context);
        obj_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getKeyHash();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("Value");
            Log.e("Value", value);
        }


        txt_explore_restaurant = (Textview) findViewById(R.id.txt_explore_restaurant);
        txt_explore_restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // gps();
                CheckGpsStatus();

            }
        });
        try {
            reg_id = FirebaseInstanceId.getInstance().getToken();
        } catch (Exception e) {
            e.printStackTrace();
            reg_id = FirebaseInstanceId.getInstance().getToken();
        }
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setScopes(gso.getScopeArray());

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, LoginActivity.this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Google_plus", "Abc");
                signIn();
            }
        });
        ll_sign_in = (LinearLayout) findViewById(R.id.ll_sign_in);
        ll_sign_in.setOnClickListener(this);
        rr_sign_in_email = (RelativeLayout) findViewById(R.id.rr_sign_in_email);
        rr_sign_in_email.setOnClickListener(this);
        rr_continue_guest = (RelativeLayout) findViewById(R.id.rr_continue_guest);
        rr_continue_guest.setOnClickListener(this);
        if (value.equals("1")) {
            rr_continue_guest.setVisibility(View.VISIBLE);
        } else {
            rr_continue_guest.setVisibility(View.GONE);
        }
    }


    public void CheckGpsStatus() {

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (GpsStatus == false) {
            Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(i);
        } else {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
        }
    }

    private void getKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("Keyhash:",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                break;
            case R.id.ll_sign_in:
                Intent ii = new Intent(LoginActivity.this, Signin_with_Email.class);
                ii.putExtra("Value", value);
                startActivity(ii);
                break;
            case R.id.rr_sign_in_email:
                Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(i);
                break;
            case R.id.rr_continue_guest:
                Intent iii = new Intent(LoginActivity.this, Login_as_guestActivity.class);
                startActivity(iii);
                break;

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.stopAutoManage(this);
        mGoogleApiClient.disconnect();
    }

    private void signIn() {

        Log.e("Google_plus", "Abc");
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            //mGoogleApiClient.disconnect();
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        }
        //mGoogleApiClient.connect();
        //Creating an intent
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);

        //Starting intent for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Facebook Login

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.login_button);

        loginButton.setReadPermissions("public_profile", "email", "user_friends");

        btnLogin = (ImageView) findViewById(R.id.btnLogin);
        ll_fb = (LinearLayout) findViewById(R.id.ll_fb);
        ll_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("Loading...");
                //progressDialog.show();

                loginButton.performClick();

                loginButton.setPressed(true);

                loginButton.invalidate();

                loginButton.registerCallback(callbackManager, mCallBack);

                loginButton.setPressed(false);

                loginButton.invalidate();

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //Calling a new function to handle signin
            handleSignInResult(result);
        } else if (requestCode == FB_SIGN_IN) {
            Log.e("RRRRRR", "" + requestCode);
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }


    }

    private FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {

            progressDialog.dismiss();

            // App code
            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {

                            Log.e("response: ", response + "");
                            try {
                                user = new User();
                                //user.pic=object.getString("public_profile").toString();

                                user.facebookid = object.getString("id").toString();
                                //"https://graph.facebook.com/
                                // Log.e("Profile", "https://graph.facebook.com/" + object.getString("id").toString() + object.getString("/picture"));
                                URL image_value = new URL("http://graph.facebook.com/" + user.facebookid + "/picture?type=large");

                                image = (String.valueOf(image_value));
                                pref.setImage(image);
                                Log.e("Image", "" + image_value);
                                Log.e("Email", object.getString("email").toString());
                                user.email = object.getString("email").toString();
                                user.name = object.getString("name").toString();
                                user.gender = object.getString("gender").toString();
                                PrefUtils.setCurrentUser(user, LoginActivity.this);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            //Toast.makeText(Login.this, "welcome " + user.name, Toast.LENGTH_LONG).show();
                            Array_user.clear();
                            Model_user user1 = new Model_user();
                            user1.setLoginstatus("2");
                            user1.setName(user.name);
                            user1.setEmail(user.email);
                            user1.setId(user.facebookid);
                            user1.setPassword("");
                            user1.setDeviceid("" + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
                            user1.setDevicetoken(reg_id);
                            Array_user.add(user1);
                            Login_api();
                        }

                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender, birthday,picture.type(large)");
            request.setParameters(parameters);
            request.executeAsync();
        }

        @Override
        public void onCancel() {
            progressDialog.dismiss();
        }

        @Override
        public void onError(FacebookException e) {
            progressDialog.dismiss();
        }
    };

    private void handleSignInResult(GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess()) {
            //Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e("IDDDD", acct.getId());
            Log.e("Nameee", acct.getDisplayName());
            Log.e("Emaill", acct.getEmail());
            Log.e("Image", String.valueOf(acct.getPhotoUrl()));

            image = (String.valueOf(acct.getPhotoUrl()));
            pref.setImage(image);

            //Toast.makeText(this, "Welcome " + acct.getEmail(), Toast.LENGTH_LONG).show();
            // Toast.makeText(this, "Welcome " + acct.getId(), Toast.LENGTH_LONG).show();
            Array_user.clear();
            Model_user user1 = new Model_user();
            user1.setLoginstatus("3");
            user1.setName(acct.getDisplayName());
            user1.setId(acct.getId());
            user1.setPassword("");
            user1.setEmail(acct.getEmail());
            user1.setDeviceid("" + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
            user1.setDevicetoken(reg_id);
            Array_user.add(user1);
            Login_api();

        } else {
            //If login fails
            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }


    public void Login_api() {

        obj_dialog.show();

        String url = Config.app_url + Config.Login;
        String json = "";

        json = JSON.add_user(Array_user, pref, "loginuser");

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
                            model.setLoginstatus(jobj1.getString("logintype"));
                            model.setMobile(jobj1.getString("mobile"));
                            model.setName(jobj1.getString("username"));
                            Log.e("name", model.getName());
                            mobile = jobj1.getString("mobile");

                            pref.setUID(jobj1.getString("userid"));
                            pref.setLogin_type(jobj1.getString("logintype"));
                            pref.setUser_name(jobj1.getString("username"));
                            pref.setLogin_value(1);

                            Log.e("jinal_id", pref.getUID());
                            Log.e("jinal_login_type", pref.getStr_login_type());
                            Log.i("Username", pref.getStr_username());
                            Array_user.add(model);


                        }
                        pref.setLogin_Flag("login");

                        if (value.equals("1")) {
                            if (mobile.equals("")) {
                                Intent i = new Intent(context, Add_mobile_no.class);
                                startActivity(i);
                                finish();
                            } else {
                                Intent i = new Intent(context, Payment_methodActivity.class);
                                startActivity(i);
                            }
                        } else {
                            Intent i = new Intent(context, MainActivity.class);
                            startActivity(i);
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
        Connection.postconnection(url, param, header, context, lis_res, lis_error);

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
