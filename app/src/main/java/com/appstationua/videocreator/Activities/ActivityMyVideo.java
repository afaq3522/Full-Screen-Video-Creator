package com.appstationua.videocreator.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.appstationua.videocreator.Adepters.AdapterMyVideoFileList;
import com.appstationua.videocreator.R;
import com.appstationua.videocreator.Utills.Constance;


import java.io.File;
import java.util.ArrayList;

public class ActivityMyVideo extends AdBaseActivity {

    Context context;
    ImageView iv_backarrow;
    RecyclerView rv_myvideolist;
    private AdapterMyVideoFileList adapterMyVideoFileList;
    private ArrayList<File> fileArrayList;
    public static RelativeLayout rl_videonotfound;
    LinearLayout facbook_ad_banner;
    private final String TAG = ActivityMyVideo.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_video);

        context = ActivityMyVideo.this;
        iv_backarrow = findViewById(R.id.iv_backarrow);
        rv_myvideolist = findViewById(R.id.rv_myvideolist);
        rl_videonotfound = findViewById(R.id.rl_videonotfound);
        facbook_ad_banner = findViewById(R.id.facbook_ad_banner);
        rv_myvideolist.setLayoutManager(new LinearLayoutManager(this));
      //  LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        iv_backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        if(Constance.adType.equals("Ad Mob"))
        {
            foradvertise();

        }
        else {
            facebookAd();
        }
        showInterstitialAds();
        facebookInterstitialAd();
        loadBanner(ActivityMyVideo.this,findViewById(R.id.adView));

    }

    public void foradvertise() {


    }


    public void facebookAd(){


    }

    private void getAllFiles() {
        fileArrayList = new ArrayList<>();
        // Toast.makeText(context,""+Constance.FileDirectory,Toast.LENGTH_LONG).show();

        if (!Constance.FileDirectory.exists()) {
            Constance.FileDirectory.mkdir();

        } else {
            Log.d("jjjjj", "Constance.FileDirectory : " + Constance.FileDirectory);
            File[] files = Constance.FileDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().endsWith(".mp4"))
                    {
                        fileArrayList.add(file);
                    }
                }

                for(int i=0;i<fileArrayList.size();i++){
                    if(fileArrayList.get(i).isDirectory()){
                        fileArrayList.remove(i);
                    }
                }
                adapterMyVideoFileList = new AdapterMyVideoFileList(context, fileArrayList);
                adapterMyVideoFileList.notifyDataSetChanged();
                rv_myvideolist.setAdapter(adapterMyVideoFileList);
            }
            else {
                Toast.makeText(context, "Data Not Found", Toast.LENGTH_LONG).show();
            }
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllFiles();
        if (fileArrayList.size() == 0) {
            rl_videonotfound.setVisibility(View.VISIBLE);
        } else {
            rl_videonotfound.setVisibility(View.GONE);
        }
    }

    public static void showInterstitial() {


    }

    public void showInterstitialAds() {



    }

    void facebookInterstitialAd(){




    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent = new Intent(context, ActivityHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
