package com.appstationua.videocreator.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.appstationua.videocreator.Models.Model_images;
import com.appstationua.videocreator.Adepters.Adapter_ListSelectedPhotos;
import com.appstationua.videocreator.R;
import com.appstationua.videocreator.Utills.Constance;


import java.util.ArrayList;

public class ActivityListSelectedPhotos extends AdBaseActivity {

    static Context context;
    static int newuriposition;
    static Adapter_ListSelectedPhotos adapter_listSelectedPhotos;
    public static ArrayList<Model_images> list_selectedimages = new ArrayList<>();
    RecyclerView rv_listselectedimage;
    ImageView iv_backarrow_listselectedimage, iv_nextarrow_listselectedarrow;
    boolean checkForEditResult;
LinearLayout facbook_ad_banner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_selected_photos);

        context = ActivityListSelectedPhotos.this;
        bindView();


        if(Constance.adType.equals("Ad Mob"))
        {
            foradvertise();

        }
        else {
            facebookAd();
        }


        rv_listselectedimage.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(this, 2);
        rv_listselectedimage.setLayoutManager(layoutManager1);

        list_selectedimages = Constance.selectedimages;

        checkForEditResult = getIntent().getBooleanExtra("checkForEditResult", false);


        iv_backarrow_listselectedimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        iv_nextarrow_listselectedarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constance.selectedimages=list_selectedimages;
                if (checkForEditResult) {
                    // onBackPressed();
                    setResult(RESULT_OK);
                    finish();

                } else {
                    startActivity(new Intent(context, DemoActivity.class));
                }
            }
        });
        loadBanner(ActivityListSelectedPhotos.this,findViewById(R.id.adView));

    }

    public static void removeItemOnrecyclerview(int position) {

        list_selectedimages.remove(position);
        adapter_listSelectedPhotos.notifyDataSetChanged();

    }

    public static void EditItemOnrecyclerview(int position, String editimagepath) {
        newuriposition = position;
        Intent i = new Intent(context, ActivityImageEditor.class);
        i.putExtra("editimagepath", editimagepath);
        context.startActivity(i);
    }

    public void bindView() {
        rv_listselectedimage = findViewById(R.id.rv_listselectedimage);
        iv_nextarrow_listselectedarrow = findViewById(R.id.iv_nextarrow_listselectedarrow);
        iv_backarrow_listselectedimage = findViewById(R.id.iv_backarrow_listselectedimage);
        facbook_ad_banner = findViewById(R.id.facbook_ad_banner);

    }

    public void foradvertise() {
        facbook_ad_banner.setVisibility(View.GONE);

    }


    public void facebookAd(){

        facbook_ad_banner.setVisibility(View.VISIBLE);


    }


    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        list_selectedimages.get(newuriposition).setSingleimagepath(cursor.getString(column_index));
        adapter_listSelectedPhotos.notifyItemChanged(newuriposition);
        return cursor.getString(column_index);
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (Constance.new_uri_path != null) {
            //Glide.with(context).load(Constance.new_uri_path).into(imagdemo);
            Log.d("kkkk", "Constance.new_uri_path :-" + Constance.new_uri_path);
            Log.d("kkkk", "Before list_selectedimages :-" + list_selectedimages.get(newuriposition).getSingleimagepath());
            getPath(Constance.new_uri_path);
            // Toast.makeText(context,"hello resume"+list_selectedimages.get(newuriposition).getSingleimagepath(),Toast.LENGTH_LONG).show();
            Log.d("kkkk", "list_selectedimages :-" + list_selectedimages.get(newuriposition).getSingleimagepath());
        }
        adapter_listSelectedPhotos = new Adapter_ListSelectedPhotos(context, list_selectedimages);
        rv_listselectedimage.setAdapter(adapter_listSelectedPhotos);

    }

}
