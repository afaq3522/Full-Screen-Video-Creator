package com.appstationua.videocreator.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.appstationua.videocreator.R;
import com.appstationua.videocreator.Utills.Constance;



public class ActivityAboutUs extends AdBaseActivity {

    TextView tv_aboutus;
    ImageView iv_backtohome;
LinearLayout facbook_ad_banner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        tv_aboutus = findViewById(R.id.tv_aboutus);
        iv_backtohome = findViewById(R.id.iv_backtohome);
        facbook_ad_banner = findViewById(R.id.facbook_ad_banner);
        foradvertise();


        if(Constance.adType.equals("Ad Mob"))
        {
            foradvertise();

        }
        else {
            facebookAd();
        }

        tv_aboutus.setText(Constance.aboutUs);
        iv_backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        loadBanner(ActivityAboutUs.this,findViewById(R.id.adView));
    }

    public void foradvertise() {


    }


    public void facebookAd() {



    }

}
