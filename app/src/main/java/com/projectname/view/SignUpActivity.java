package com.projectname.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.projectname.R;
import com.projectname.util.Utils;


/****************************************************************************
 * SignUpActivity
 *
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class is user for SignUp to user.
 ***************************************************************************/

public class SignUpActivity extends BaseActivity implements OnClickListener {


    private EditText etFirstName;
    private EditText etCompanyName;
    private EditText etPhoneNo;
    private EditText etAddress;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etPinCode;

    private TextInputLayout tilFirstname;
    private TextInputLayout tilAddress;
    private TextInputLayout tilPhone;
    private TextInputLayout tilPin;
    private TextInputLayout tilCompnayNmae;
    private TextInputLayout tilpassword;
    private TextInputLayout tilEmail;

    private Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_signup);

        initComponents();
        setupToolbar();

    }

    @Override
    public void initComponents() {

        btnSubmit = (Button) findViewById(R.id.activity_signup_btnSignUp);
        etFirstName = (EditText) findViewById(R.id.activity_signup_etFirstname);
        etCompanyName = (EditText) findViewById(R.id.activity_signup_etCompanyName);
        etPhoneNo = (EditText) findViewById(R.id.activity_signup_etPhoneNo);
        etAddress = (EditText) findViewById(R.id.activity_signup_etAddress);
        etEmail = (EditText) findViewById(R.id.activity_signup_etEmail);
        etPinCode = (EditText) findViewById(R.id.activity_signup_etPin);
        etPassword = (EditText) findViewById(R.id.activity_signup_etPassword);

        tilFirstname = (TextInputLayout) findViewById(R.id.activity_signup_tilFirstname);
        tilCompnayNmae = (TextInputLayout) findViewById(R.id.activity_signup_tilCompanyName);
        tilEmail = (TextInputLayout) findViewById(R.id.activity_signup_tilEmail);
        tilpassword = (TextInputLayout) findViewById(R.id.activity_signup_tilPassword);
        tilPhone = (TextInputLayout) findViewById(R.id.activity_signup_tilPhoneNo);
        tilPin = (TextInputLayout) findViewById(R.id.activity_signup_tilPin);
        tilAddress = (TextInputLayout) findViewById(R.id.activity_signup_tilAddress);

        addComponents();
    }

    @Override
    public void addComponents() {


        etEmail.addTextChangedListener(new MyTextWatcher(etEmail));
        etPassword.addTextChangedListener(new MyTextWatcher(etPassword));
        btnSubmit.setOnClickListener(this);
    }



    private void setupToolbar()
    {
        // Initializing Toolbar and setting it as the actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_menubar_toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        TextView mTitle = (TextView) toolbar.findViewById(R.id.actionbar_tvTitle);
        mTitle.setText(getString(R.string.sign_up));

        if (actionBar != null) {

            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            finish();
            overridePendingTransition(R.anim.anim_left_in, R.anim.anim_right_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    /**
     * Validating form
     */

    private void submitForm()
    {

        if (!validateField(etFirstName,tilFirstname,getString(R.string.err_msg_firstname)))
        {
            return;
        }
        else if (!validateField(etCompanyName,tilCompnayNmae,getString(R.string.err_msg_compnayname)))
        {
            return;
        }
        else  if (!validateEmail())
        {
            return;
        }
        else if (!validateField(etPhoneNo,tilPhone,getString(R.string.err_msg_phonenumber)))
        {
            return;
        }
        else if (!validateField(etPinCode,tilPin,getString(R.string.err_msg_pinnumber)))
        {
            return;
        }
        else if (!validateField(etAddress,tilAddress,getString(R.string.err_msg_address)))
        {
            return;
        }
        else if (!validateField(etPassword,tilpassword,getString(R.string.err_msg_password)))
        {
            return;
        }

        else
        {
            if (Utils.isOnline(SignUpActivity.this, true)) {

            }
        }


    }

    private boolean validateEmail()
    {
        String email = etEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email))
        {
            tilEmail.setError(getString(R.string.err_msg_email));
            requestFocus(etEmail);
            return false;
        } else {
            tilEmail.setErrorEnabled(false);
        }



        return true;
    }

    private boolean validateField(EditText etFielde, TextInputLayout tilField, String errMsg)
    {

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

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable)
        {
            switch (view.getId()) {

                case R.id.activity_signup_etFirstname:
                    validateField(etFirstName,tilFirstname,getString(R.string.err_msg_firstname));
                    break;
                case R.id.activity_signup_etCompanyName:
                    validateField(etCompanyName,tilCompnayNmae,getString(R.string.err_msg_compnayname));
                    break;
                case R.id.activity_signup_etEmail:
                    validateEmail();
                    break;
                case R.id.activity_signup_etPhoneNo:
                    validateField(etPhoneNo,tilPhone,getString(R.string.err_msg_phonenumber));
                    break;
                case R.id.activity_signup_etPin:
                    validateField(etPinCode,tilPin,getString(R.string.err_msg_pinnumber));
                    break;
                case R.id.activity_signup_etAddress:
                    validateField(etAddress,tilAddress,getString(R.string.err_msg_address));
                    break;
                case R.id.activity_signup_etPassword:
                    validateField(etPassword,tilpassword,getString(R.string.err_msg_password));
                    break;
            }
        }
    }



    @Override
    public void onClick(View v) {

        Utils.hideKeyboard(SignUpActivity.this);

        switch (v.getId()) {
            case R.id.activity_signup_btnSignUp:
                submitForm();
                break;

        }

    }
}
