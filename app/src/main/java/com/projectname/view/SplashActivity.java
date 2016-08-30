package com.projectname.view;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.projectname.R;
import com.projectname.TemplateAplication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/****************************************************************************
 * SplashActivity
 *
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class is user for SplashActivity.
 ***************************************************************************/

public class SplashActivity extends AppCompatActivity {
    private int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        new SplashTask().execute();
    }

    /**
     * AsycTask for setting splash screen by sleep thread for some time
     */
    private class SplashTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(SPLASH_TIME_OUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);


            boolean isLogin = TemplateAplication.getmInstance().getSharedPreferences().getBoolean(getString((R.string.prefe_IsLogin)), false);


            if (isLogin) {

                Intent mLoginIntent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(mLoginIntent);

                overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
                finish();

            } else {
                Intent mLoginIntent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(mLoginIntent);

                overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
                finish();
            }


        }
    }
}
