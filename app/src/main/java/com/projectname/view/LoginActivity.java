package com.projectname.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.projectname.R;
import com.projectname.TemplateAplication;
import com.projectname.util.Utils;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * *************************************************************************
 * LoginActivity
 *
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class is user for login into application through valid credentials.
 * <p/>
 * *************************************************************************
 */


public class LoginActivity extends BaseActivity implements OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    public String TAG = "LoginActivity";
    private TextView tvSignUp;
    private TextView tvForgotPassword;
    private TextView tvFblogin;
    private TextView tvTwitter;
    private TextView tvGoogle;

    private EditText etEmail;
    private EditText etPassword;

    private TextInputLayout tilEmail;
    private TextInputLayout tilpassword;
    private Button btnLogin;


    //Twitter And Facebook Related components
    private CallbackManager callbackFbManager;
    private TwitterAuthClient mTwitterAuthClient;


    //Google Sign
    private static final int GOOGLE_REQ = 9001;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponents();
        setupToolbar();
        initFacebook();
        generateKeyHash();
        googleConfigration();

    }


    @Override
    public void initComponents()
    {


        Utils.hideKeyboard(LoginActivity.this);

        btnLogin = (Button) findViewById(R.id.activity_login_btnLogin);
        tvSignUp = (TextView) findViewById(R.id.activity_login_tvSignUp);
        tvGoogle= (TextView) findViewById(R.id.activity_login_tvgooglelogin);
        tvForgotPassword = (TextView) findViewById(R.id.activity_login_tvForgotPassword);
        tvFblogin = (TextView) findViewById(R.id.activity_login_tvFblogin);
        tvTwitter = (TextView) findViewById(R.id.activity_login_tvTwitterlogin);
        etEmail = (EditText) findViewById(R.id.activity_login_etEmail);
        etPassword = (EditText) findViewById(R.id.activity_login_etPassword);
        tilEmail = (TextInputLayout) findViewById(R.id.activity_login_tilEmail);
        tilpassword = (TextInputLayout) findViewById(R.id.activity_login_tilPassword);

        addComponents();



    }

    @Override
    public void addComponents() {

        btnLogin.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        tvFblogin.setOnClickListener(this);
        tvGoogle.setOnClickListener(this);
        tvTwitter.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);

        etEmail.addTextChangedListener(new MyTextWatcher(etEmail));
        etPassword.addTextChangedListener(new MyTextWatcher(etPassword));

        //Facebook Login
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackFbManager = CallbackManager.Factory.create();

        //Twitter Client init
        mTwitterAuthClient = new TwitterAuthClient();

    }




    private void setupToolbar() {
        // Initializing Toolbar and setting it as the actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_menubar_toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        TextView mTitle = (TextView) toolbar.findViewById(R.id.actionbar_tvTitle);
        mTitle.setText(getString(R.string.login));

        if (actionBar != null) {

            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
        }


    }


    /**
     * Validating form
     */
    private void submitForm() {

        if (!validateEmail()) {
            return;
        } else if (!validateField(etPassword, tilpassword, getString(R.string.err_msg_password))) {
            return;
        } else {
            if (Utils.isOnline(LoginActivity.this, true)) {

            }
        }


    }

    private boolean validateEmail() {
        String email = etEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            tilEmail.setError(getString(R.string.err_msg_email));
            requestFocus(etEmail);
            return false;
        } else {
            tilEmail.setErrorEnabled(false);
        }


        return true;
    }

    private boolean validateField(EditText etFielde, TextInputLayout tilField, String errMsg) {

        String password = etFielde.getText().toString().trim();

        if (password.isEmpty()) {
            tilField.setError(errMsg);
            requestFocus(etFielde);
            return false;
        } else {
            tilField.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.activity_login_etEmail:
                    validateEmail();
                    break;
                case R.id.activity_login_etPassword:
                    validateField(etPassword, tilpassword, getString(R.string.err_msg_password));
                    break;
            }
        }
    }




    private void onTwitterLogin() {

        mTwitterAuthClient.authorize(this, new com.twitter.sdk.android.core.Callback<TwitterSession>() {

            @Override
            public void success(Result<TwitterSession> twitterSessionResult) {

                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                TwitterSession session = twitterSessionResult.data;
                TemplateAplication.getmInstance().savePreferenceDataString(getString(R.string.prefe_userID), "" + session.getUserId());
                TemplateAplication.getmInstance().savePreferenceDataBoolean(getString(R.string.prefe_IsLogin), true);
                TemplateAplication.getmInstance().savePreferenceDataString(getString(R.string.prefe_userName), "" + session.getUserName());


                Twitter.getApiClient(session).getAccountService()
                        .verifyCredentials(true, false, new Callback<User>() {

                            @Override
                            public void failure(TwitterException e) {

                            }

                            @Override
                            public void success(Result<User> userResult) {

                                User user = userResult.data;
                                String twitterImage = user.profileImageUrl;

                                try {
                                    Log.d("imageurl", user.profileImageUrl);

                                    TemplateAplication.getmInstance().savePreferenceDataString(getString(R.string.prefe_userPic), twitterImage);

                                    Intent mHomeIntent = new Intent(LoginActivity.this, MenuBarActivity.class);
                                    startActivity(mHomeIntent);
                                    overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
                                    finish();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }

                        });


            }

            @Override
            public void failure(TwitterException e) {
                e.printStackTrace();
            }
        });

    }


    private void generateKeyHash() {
        try {
            final PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }


    private void onGoogleLogin() {

        if(mGoogleApiClient!=null)
        {
            if (mGoogleApiClient.isConnected())
            {
                Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>()
                        {
                            @Override
                            public void onResult(Status status) {

                                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                                startActivityForResult(signInIntent, GOOGLE_REQ);
                            }
                        });
            }
            else
            {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, GOOGLE_REQ);
            }
        }



    }

    private void googleConfigration() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    private void onGooleResult()
    {
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            GoogleSignInResult result = opr.get();
            if (result.isSuccess()) {
                // Signed in successfully, show authenticated UI.
                GoogleSignInAccount acct = result.getSignInAccount();
                saveGoogleInfo(acct);
            }

        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage(getString(R.string.please_wait));
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.show();
            }
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.hide();
                    }
                    if (googleSignInResult.isSuccess()) {
                        // Signed in successfully, show authenticated UI.
                        GoogleSignInAccount acct = googleSignInResult.getSignInAccount();
                        saveGoogleInfo(acct);
                    }
                }
            });
        }
    }

    private void saveGoogleInfo(GoogleSignInAccount acct)
    {
        Log.d(TAG, "getDisplayName:" + acct.getDisplayName());
        Log.d(TAG, "getId:" + acct.getId());
        Log.d(TAG, "getEmail:" + acct.getEmail());
        Log.d(TAG, "getPhotoUrl:" + acct.getPhotoUrl());

        TemplateAplication.getmInstance().savePreferenceDataString(getString(R.string.prefe_userID), acct.getId());
        TemplateAplication.getmInstance().savePreferenceDataBoolean(getString(R.string.prefe_IsLogin), true);
        TemplateAplication.getmInstance().savePreferenceDataString(getString(R.string.prefe_userName), acct.getDisplayName());
        TemplateAplication.getmInstance().savePreferenceDataString(getString(R.string.prefe_userPic), ""+acct.getPhotoUrl());

        Intent mHomeIntent = new Intent(LoginActivity.this, MenuBarActivity.class);
        startActivity(mHomeIntent);
        overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
        finish();
    }


    private void initFacebook() {

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackFbManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackFbManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e(TAG, "FB ID " + loginResult.getAccessToken().getUserId());
                Log.e("Full Login Details", "" + loginResult.toString());
                final GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject fbResObj, GraphResponse response) {

                        Log.e("FB GRAPH RESPONSE", fbResObj.toString());
                        String id = null;


                        try {
                            id = fbResObj.getString("id");
                            String emailAddress = fbResObj.optString("email");
                            String first_name = fbResObj.optString("first_name");
                            String last_name = fbResObj.optString("last_name");
                            String gender = fbResObj.optString("gender");
                            String birthday = fbResObj.optString("birthday");

                            URL url = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=200");

                            Log.e("FB GRAPH RESPONSE", "first_name==" + first_name);


                            if (id != null) {

                                TemplateAplication.getmInstance().savePreferenceDataString(getString(R.string.prefe_userID), id);
                                TemplateAplication.getmInstance().savePreferenceDataBoolean(getString(R.string.prefe_IsLogin), true);
                                TemplateAplication.getmInstance().savePreferenceDataString(getString(R.string.prefe_userName), first_name);
                                TemplateAplication.getmInstance().savePreferenceDataString(getString(R.string.prefe_userPic), url.toString());

                                Intent mHomeIntent = new Intent(LoginActivity.this, MenuBarActivity.class);
                                startActivity(mHomeIntent);
                                overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
                                finish();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }

                    }
                });
                final Bundle parameters = new Bundle();
                parameters.putString("fields", "id,first_name,last_name,email,gender,birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        Utils.hideKeyboard(LoginActivity.this);

        switch (v.getId()) {
            case R.id.activity_login_btnLogin:
                submitForm();
                break;
            case R.id.activity_login_tvForgotPassword:
                Intent mHomeIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(mHomeIntent);
                overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
                break;
            case R.id.activity_login_tvSignUp:
                Intent mHomeIntentSign = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(mHomeIntentSign);
                overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
                break;
            case R.id.activity_login_tvFblogin:
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
                break;
            case R.id.activity_login_tvTwitterlogin:
                onTwitterLogin();
                break;
            case R.id.activity_login_tvgooglelogin:
                onGoogleLogin();
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        Log.d("resultCode", "resultCode==" + resultCode);
        Log.d("requestCode", "requestCode==" + requestCode);

        if (requestCode == 140) {
            mTwitterAuthClient.onActivityResult(requestCode, resultCode, data);
        } else if (requestCode == GOOGLE_REQ) {
            onGooleResult();
        } else {
            callbackFbManager.onActivityResult(requestCode, resultCode, data);
        }


    }


}
