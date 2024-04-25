package com.appstationua.videocreator.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.appstationua.videocreator.Utills.UnityAdConst;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;
import com.unity3d.services.banners.BannerErrorInfo;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;


public class AdBaseActivity extends AppCompatActivity {
    private BannerView bottomBanner;
    private final IUnityAdsLoadListener loadListener = new IUnityAdsLoadListener() {
        public void onUnityAdsAdLoaded(@NotNull String placementId) {
            UnityAds.show(AdBaseActivity.this, UnityAdConst.interstitialAdUnitId, new UnityAdsShowOptions(), AdBaseActivity.this.showListener);
        }

        public void onUnityAdsFailedToLoad(@NotNull String placementId, @NotNull UnityAds.UnityAdsLoadError error, @NotNull String message) {
            Intrinsics.checkNotNullParameter(placementId, "placementId");
            Intrinsics.checkNotNullParameter(error, "error");
            Intrinsics.checkNotNullParameter(message, "message");
            Log.e("UnityAdsExample", "Unity Ads failed to load ad for " + placementId + " with error: [" + error + "] " + message);
        }
    };
    private final IUnityAdsShowListener showListener = new IUnityAdsShowListener() {
        public void onUnityAdsShowFailure(@NotNull String placementId, @NotNull UnityAds.UnityAdsShowError error, @NotNull String message) {
            Intrinsics.checkNotNullParameter(placementId, "placementId");
            Intrinsics.checkNotNullParameter(error, "error");
            Intrinsics.checkNotNullParameter(message, "message");
            Log.e("UnityAdsExample", "Unity Ads failed to show ad for " + placementId + " with error: [" + error + "] " + message);
        }

        public void onUnityAdsShowStart(@NotNull String placementId) {
            Intrinsics.checkNotNullParameter(placementId, "placementId");
            Log.v("UnityAdsExample", "onUnityAdsShowStart: " + placementId);
        }

        public void onUnityAdsShowClick(@NotNull String placementId) {
            Intrinsics.checkNotNullParameter(placementId, "placementId");
            Log.v("UnityAdsExample", "onUnityAdsShowClick: " + placementId);
        }

        public void onUnityAdsShowComplete(@NotNull String placementId, @NotNull UnityAds.UnityAdsShowCompletionState state) {
            Intrinsics.checkNotNullParameter(placementId, "placementId");
            Intrinsics.checkNotNullParameter(state, "state");
            Log.v("UnityAdsExample", "onUnityAdsShowComplete: " + placementId);
        }
    };
    static boolean withAds = true;
    @NotNull
    public static final Companion Companion = new Companion(null);

    private void loadBannerAd(BannerView bannerView, RelativeLayout bannerLayout) {
        bannerView.load();
        bannerLayout.addView(bannerView);
    }

    protected final void loadBanner(Activity activity, final RelativeLayout layout) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(layout, "layout");
        if (withAds) {
            this.bottomBanner = new BannerView(activity, UnityAdConst.bannerAdUnitId, new UnityBannerSize(320, 50));
            BannerView var10000 = this.bottomBanner;
            var10000.setListener(new BannerView.IListener() {
                public void onBannerLoaded(@NotNull BannerView bannerAdView) {
                    Intrinsics.checkNotNullParameter(bannerAdView, "bannerAdView");
                    Log.v("UnityAdsExample", "onBannerLoaded: " + bannerAdView.getPlacementId());
                }

                public void onBannerShown(@Nullable BannerView bannerAdView) {
                }

                public void onBannerFailedToLoad(@NotNull BannerView bannerAdView, @NotNull BannerErrorInfo errorInfo) {
                    Intrinsics.checkNotNullParameter(bannerAdView, "bannerAdView");
                    Intrinsics.checkNotNullParameter(errorInfo, "errorInfo");
                    layout.setVisibility(View.GONE);
                    Log.e("UnityAdsExample", "Unity Ads failed to load banner for " + bannerAdView.getPlacementId() + " with error: [" + errorInfo.errorCode + "] " + errorInfo.errorMessage);
                }

                public void onBannerClick(@NotNull BannerView bannerAdView) {
                    Intrinsics.checkNotNullParameter(bannerAdView, "bannerAdView");
                    Log.v("UnityAdsExample", "onBannerClick: " + bannerAdView.getPlacementId());
                }

                public void onBannerLeftApplication(@NotNull BannerView bannerAdView) {
                    Intrinsics.checkNotNullParameter(bannerAdView, "bannerAdView");
                    Log.v("UnityAdsExample", "onBannerLeftApplication: " + bannerAdView.getPlacementId());
                }
            });

            if (this.bottomBanner != null) {
                BannerView var10001 = this.bottomBanner;
                Intrinsics.checkNotNull(var10001);
                this.loadBannerAd(var10001, layout);
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestAdds();
    }

    protected void DisplayRewardedAd(View mainView) {
        if (!withAds) {
            Toast.makeText(this, "Ads already disabled", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "Request send", Toast.LENGTH_SHORT).show();
        UnityAds.load(UnityAdConst.rewardedAdUnitId, new IUnityAdsLoadListener() {
            @Override
            public void onUnityAdsAdLoaded(String placementId) {
                UnityAds.show(AdBaseActivity.this, UnityAdConst.rewardedAdUnitId, new UnityAdsShowOptions(), new IUnityAdsShowListener() {
                    @Override
                    public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                        Toast.makeText(getApplicationContext(), "Request failed for pro", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onUnityAdsShowStart(String placementId) {

                    }

                    @Override
                    public void onUnityAdsShowClick(String placementId) {

                    }

                    @Override
                    public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
                        if(mainView!=null){
                            mainView.setVisibility(View.GONE);
                        }
                        AdBaseActivity.Companion.setWithAds(false);
                    }
                });

            }

            @Override
            public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
                Toast.makeText(getApplicationContext(), "Request failed for pro", Toast.LENGTH_SHORT).show();

            }
        });
    }

    protected void requestAdds() {
        int rand = (new Random()).nextInt(3);
        if (rand == 1 && withAds) {
            this.displayInterstitialAd();
        }

    }

    private final void displayInterstitialAd() {
        UnityAds.load(UnityAdConst.interstitialAdUnitId, this.loadListener);
    }


    public static final class Companion {
        public boolean getWithAds() {
            return AdBaseActivity.withAds;
        }

        public void setWithAds(boolean var1) {
            AdBaseActivity.withAds = var1;
        }

        private Companion() {
        }

        // $FF: synthetic method
        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
