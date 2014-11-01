package com.jacmobile.sensorpanellite.injection;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.jacmobile.sensorpanellite.R;
import com.jacmobile.sensorpanellite.activities.ABaseActivity;
import com.jacmobile.sensorpanellite.activities.SensorActivity;
import com.jacmobile.sensorpanellite.fragments.SensorFragment;
import com.jacmobile.sensorpanellite.fragments.SensorListFragment;
import com.jacmobile.sensorpanellite.injection.annotations.ForActivity;
import com.jacmobile.sensorpanellite.interfaces.ContentView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        complete = true,    // Here we enable object graph validation
        library = true,
        addsTo = ApplicationScopeModule.class, // Important for object graph validation at compile time
        injects = {
                SensorActivity.class,
                SensorFragment.class,
                SensorListFragment.class
        }
)
public class ActivityScopeModule {

    private final ABaseActivity mActivity;

    public ActivityScopeModule(ABaseActivity activity) {
        mActivity = activity;
    }

    @Provides
    @Singleton
    @ForActivity
    Context providesActivityContext() {
        return mActivity;
    }

    @Provides
    @Singleton
    Activity providesActivity() {
        return mActivity;
    }


    @Provides @Singleton
    ContentView provideContentView() {
        return ContentView.DEFAULT;
    }

    @Provides @Singleton
    InterstitialAd provideInterstitialAd()
    {
        InterstitialAd interstitial = new InterstitialAd(mActivity);
        interstitial.setAdUnitId(mActivity.getString(R.string.ad_unit_id));
        interstitial.loadAd(new AdRequest.Builder().build());
        return interstitial;
    }
}