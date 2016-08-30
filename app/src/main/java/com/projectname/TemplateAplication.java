package com.projectname;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.projectname.okhttp.RestClient;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;


/**
 * *************************************************************************
 * TemplateAplication
 *
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This application class to set application level variable and method which
 * used through-out application
 * <p/>
 * *************************************************************************
 */

public class TemplateAplication extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "Pje6n2HrDVNdcdVbrmpB0RBpZ";
    private static final String TWITTER_SECRET = "Y9hPpbCmBMJ0oDtaYJcUVW9ZfiYelOsMjDH82pilZYkpnHBF37";



    private static TemplateAplication mInstance;
    public ImageLoader imageLoader;
    private SharedPreferences sharedPreferences;

    public static TemplateAplication getmInstance() {
        return mInstance;
    }

    public static void setmInstance(TemplateAplication mInstance) {
        TemplateAplication.mInstance = mInstance;
    }


    /**
     * Called when application start
     */
    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        mInstance = this;

        initImageLoader();
        sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);

        new RestClient(this);

    }


      private void initImageLoader() {
        final ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).threadPriority(Thread.MAX_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()).diskCacheSize(50 * 1024 * 1024)
                // 50
                .tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs().build();
        // Initialise ImageLoader with configuration.
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void savePreferenceDataString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void savePreferenceDataBoolean(String key, Boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void savePreferenceDataInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    /**
     * Call when application is close
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mInstance != null) {
            mInstance = null;
        }
    }


}
