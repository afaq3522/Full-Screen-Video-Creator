package com.appstationua.videocreator.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appstationua.videocreator.R;
import com.appstationua.videocreator.Utills.Constance;

import com.hw.photomovie.PhotoMovieFactory;

public class ActivityHome extends AdBaseActivity {

    Context context;
    LinearLayout ll_createvideo;
    private static final int REQUEST_PERMISSIONS = 100;
    LinearLayout ll_myvideo;
    LinearLayout facbook_ad_banner;
    public static ActivityHome instance = null;

    private final String TAG = ActivityHome.class.getSimpleName();

    public ActivityHome() {
        instance = ActivityHome.this;
    }

    public static synchronized ActivityHome getInstance() {
        if (instance == null) {
            instance = new ActivityHome();
        }
        return instance;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorBlack));

        context = ActivityHome.this;


        ll_createvideo = findViewById(R.id.ll_createvideo);
        ll_myvideo = findViewById(R.id.ll_myvideo);
        facbook_ad_banner = findViewById(R.id.facbook_ad_banner);


        if (Constance.adType.equals("Ad Mob")) {
            foradvertise();

        } else {
            facebookAd();
        }

        Constance.selectedimages.clear();
        Constance.new_uri_path = null;
        Constance.elapsedTime = 0;
        Constance.changeDuration = 0;
        Constance.Folderposition=0;
        Constance.durationseek_progress=2;
        Constance.mMovieType = PhotoMovieFactory.PhotoMovieType.HORIZONTAL_TRANS;


        ll_createvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getpermission();
            }
        });
        ll_myvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, ActivityMyVideo.class));
            }
        });

        //// AdMob Ad-------------------------









        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            String prem = android.Manifest.permission.POST_NOTIFICATIONS;
            if(ContextCompat.checkSelfPermission(this,prem)==PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(this, new String[]{prem},111);
            }
        }
        loadBanner(ActivityHome.this,findViewById(R.id.adView));

    }

    public void foradvertise() {
        facbook_ad_banner.setVisibility(View.GONE);


    }


    public void facebookAd() {



    }


    public void getpermission() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            if(!Environment.isExternalStorageManager()){
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 111);
            } else {
                Log.e("Else", "Else");
                startActivity(new Intent(context, ActivitySelectImages.class));
            }
        }else {
            if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                if ((ActivityCompat.shouldShowRequestPermissionRationale(ActivityHome.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(ActivityHome.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE))) {

                } else {
                    ActivityCompat.requestPermissions(ActivityHome.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_PERMISSIONS);
                }
            } else {
                Log.e("Else", "Else");
                startActivity(new Intent(context, ActivitySelectImages.class));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                        startActivity(new Intent(context, ActivitySelectImages.class));
                    } else {
                        Toast.makeText(ActivityHome.this, "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dailog_exit);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setAttributes(lp);


        TextView tv_yes, tv_no;
        RelativeLayout rl_gotoback;
        LinearLayout ll_exitapp;
        tv_yes = dialog.findViewById(R.id.tv_yes);
        tv_no = dialog.findViewById(R.id.tv_no);
        rl_gotoback = dialog.findViewById(R.id.rl_gotoback);
        ll_exitapp = dialog.findViewById(R.id.ll_exitapp);
        rl_gotoback.setVisibility(View.GONE);
        ll_exitapp.setVisibility(View.VISIBLE);

        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });
        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void onClickHome(View view) {
        switch (view.getId()) {
            case R.id.ll_shareapp:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, Constance.shareapp_url + getPackageName());
                intent.setType("text/plain");
                this.startActivity(intent);
                break;
            case R.id.ll_RateUs:
                try {
                    Uri uri = Uri.parse("market://details?id=" + getPackageName());
                    Intent intentrate = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intentrate);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(Constance.Rateapp)));
                }
                break;
            case R.id.ll_Moreapp:
                try {
                    Uri uri = Uri.parse("market://details?id=" + getPackageName());
                    Intent intentmoreapp = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intentmoreapp);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(Constance.Moreapp)));
                }
                break;
            case R.id.ll_AboutUs:
                startActivity(new Intent(context, ActivityAboutUs.class));

                break;
        }
    }
}
