package com.projectname.view;

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
import android.widget.TextView;

import com.projectname.R;
import com.projectname.util.Utils;


/****************************************************************************
 * ForgotPasswordActivity
 *
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class is user for Forgot password.
 ***************************************************************************/

public class ForgotPasswordActivity extends BaseActivity implements OnClickListener {


    private EditText etEmail;
    private Button btnSubmit;
    private TextInputLayout tilEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initComponents();
        setupToolbar();

    }

    @Override
    public void initComponents() {

        btnSubmit = (Button) findViewById(R.id.activity_forgot_password_btnSubmit);
        etEmail = (EditText) findViewById(R.id.activity_forgot_password_etEmail);
        tilEmail = (TextInputLayout) findViewById(R.id.activity_forgot_password_tilEmail);

        addComponents();
    }

    @Override
    public void addComponents() {

        btnSubmit.setOnClickListener(this);
        etEmail.addTextChangedListener(new MyTextWatcher(etEmail));
    }


    private void setupToolbar() {
        // Initializing Toolbar and setting it as the actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_menubar_toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        TextView mTitle = (TextView) toolbar.findViewById(R.id.actionbar_tvTitle);
        mTitle.setText(getString(R.string.forgot_password));

        if (actionBar != null) {

            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.anim_left_in, R.anim.anim_right_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void submitForm() {

        if (!validateEmail()) {
            return;
        } else {

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

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {


                case R.id.activity_forgot_password_etEmail:
                    validateEmail();
                    break;

            }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_left_in, R.anim.anim_right_out);
    }


    @Override
    public void onClick(View v) {
        Utils.hideKeyboard(ForgotPasswordActivity.this);

        switch (v.getId()) {
            case R.id.activity_forgot_password_btnSubmit:
                submitForm();
                break;


        }
    }
}
