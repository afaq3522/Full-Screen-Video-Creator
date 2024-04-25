package com.appstationua.videocreator.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.appstationua.videocreator.R;
import com.appstationua.videocreator.Utills.UnityAdConst;
import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.UnityAds;
import com.victor.loading.newton.NewtonCradleLoading;

public class ActivityLoading extends AppCompatActivity implements IUnityAdsInitializationListener {

    Context context;
    NewtonCradleLoading newtonCradleLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        context=ActivityLoading.this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        newtonCradleLoading=findViewById(R.id.newton_cradle_loading);
        newtonCradleLoading.start();

        UnityAds.initialize(
                this,
                UnityAdConst.unityGameID,
                UnityAdConst.testMode,
                this
        );
        if(!isNetworkAvailable()){
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                AdBaseActivity.Companion.setWithAds(false);
                goToMainActivity();
            },3000);
        }
    }
    void goToMainActivity(){
        Intent intent = new Intent(context, ActivityHome.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onInitializationComplete() {
        goToMainActivity();
    }

    @Override
    public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {
        AdBaseActivity.Companion.setWithAds(false);
        goToMainActivity();
    }
    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
