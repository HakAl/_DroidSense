package com.jacmobile.sensorpanellite.activities.ad;

import android.app.FragmentTransaction;
import android.os.Bundle;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.jacmobile.sensorpanellite.R;
import com.jacmobile.sensorpanellite.activities.ABaseActivity;
import com.jacmobile.sensorpanellite.fragments.SensorFragment;
import com.jacmobile.sensorpanellite.fragments.SensorListFragment;
import com.jacmobile.sensorpanellite.interfaces.Navigator;

import javax.inject.Inject;

/**
 * Created by alex on 10/12/14.
 */
public class SensorActivity extends ABaseActivity implements Navigator
{
    private static final String SENSOR_FRAGMENT = "com.jacmobile.sensorpanellite.sensorfragment";

    @Inject InterstitialAd interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, SensorListFragment.newInstance())
                    .commit();
        }
    }

    public void showAd()
    {
        if (this.interstitial.isLoaded()) {
            this.interstitial.setAdListener(new AdListener()
            {
                @Override
                public void onAdClosed()
                {
                    super.onAdClosed();
                    interstitial.loadAd(new AdRequest.Builder().build());
                }
            });
            this.interstitial.show();
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        if (Math.random() < .25) {
            this.showAd();
        }
    }

    @Override
    public void onTransition(int... which)
    {
        this.newSensorFragment(which[0]);
    }

    private void newSensorFragment(int sensor)
    {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, SensorFragment.newInstance(sensor), SENSOR_FRAGMENT);
        transaction.addToBackStack(null).commitAllowingStateLoss();
    }
}