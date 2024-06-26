package com.appstationua.videocreator.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.appstationua.videocreator.R;
import com.appstationua.videocreator.Utills.Constance;


import java.io.File;

public class ActivityVideoPreview extends AdBaseActivity {

    Context context;
    String Video_Path;
    VideoView vv_playvideo;
    MediaController mediacontroller;
    ProgressDialog pDialog;
    LinearLayout facbook_ad_banner;
    ImageView iv_vp_play;
    ImageView iv_video_pic;
    boolean df = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_preview);

        context = ActivityVideoPreview.this;
        Video_Path = getIntent().getExtras().getString("Video_Path");
        // Toast.makeText(context, "path:-" + Video_Path, Toast.LENGTH_LONG).show();
        vv_playvideo = findViewById(R.id.vv_playvideo);
        facbook_ad_banner = findViewById(R.id.facbook_ad_banner);
        iv_vp_play = findViewById(R.id.iv_vp_play);
        iv_video_pic = findViewById(R.id.iv_video_pic);

       /* Glide.with(context).load(selectedimages.get(0).getSingleimagepath())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(iv_video_pic);*/

        if (Constance.adType.equals("Ad Mob")) {
            displayAdMob();
            Log.d("ADssss", "Ad Mob");
        } else {
            interstitialFacbookAd();
            Log.d("ADssss", "Facebook");
        }

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
     /*   iv_video_pic.setVisibility(View.GONE);
        iv_vp_play.setVisibility(View.GONE);
*/
        iv_vp_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog.dismiss();
                Log.d("wseqwqwqwqwqwqwqw", "sdss");
                vv_playvideo.start();
                iv_video_pic.setVisibility(View.GONE);
                iv_vp_play.setVisibility(View.GONE);
                df = true;

            }
        });
        try {
            mediacontroller = new MediaController(this);
            mediacontroller.setAnchorView(vv_playvideo);
            vv_playvideo.setMediaController(mediacontroller);
            vv_playvideo.setVideoPath(Video_Path);

        } catch (Exception e) {
            e.printStackTrace();
        }

        vv_playvideo.requestFocus();

        if (Constance.adType.equals("Ad Mob")) {
            foradvertise();

        } else {
            facebookAd();
        }

        vv_playvideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                //vv_playvideo.start();


            }
        });
        vv_playvideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            public void onCompletion(MediaPlayer mp) {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                    mp.stop();
                }
                //mp.stop();
                vv_playvideo.pause();
                //iv_video_pic.setVisibility(View.VISIBLE);
                iv_vp_play.setVisibility(View.VISIBLE);
            }
        });
        loadBanner(ActivityVideoPreview.this,findViewById(R.id.adView));

    }


    public void foradvertise() {
        facbook_ad_banner.setVisibility(View.GONE);

    }


    public void facebookAd() {



    }

    public void displayAdMob() {

    }

    public void interstitialFacbookAd() {


    }

    @Override
    protected void onResume() {
        super.onResume();
      /*  vv_playvideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                //vv_playvideo.start();


            }
        });
        vv_playvideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            public void onCompletion(MediaPlayer mp) {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                    mp.stop();
                }
                //mp.stop();
                vv_playvideo.pause();
                //iv_video_pic.setVisibility(View.VISIBLE);
                 iv_vp_play.setVisibility(View.VISIBLE);
            }
        });*/
    }

    public void onclickItems(View view) {
        switch (view.getId()) {
            case R.id.iv_backarrow:
                onBackPressed();
                //  startActivity(new Intent(context,ActivityHome.class));
                break;
            case R.id.iv_MyVideo:
                startActivity(new Intent(context, ActivityMyVideo.class));
                break;
            case R.id.btn_delete:
                showDeleteDailog();
                break;
        }
    }



    public void showDeleteDailog() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        final AlertDialog alertDialog = builder.create();
        builder.setMessage("Are you sure to delete this video")
                .setCancelable(false)
                .setTitle("Delete ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        File file = new File(Video_Path);
                        file.delete();
                        dialog.dismiss();
                        onBackPressed();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

}
