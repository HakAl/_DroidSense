package com.jacmobile.sensorpanellite.injection;

import android.app.Activity;
import android.content.Context;

import com.jacmobile.sensorpanellite.activities.ABaseActivity;
import com.jacmobile.sensorpanellite.activities.PrimaryActivity;
import com.jacmobile.sensorpanellite.fragments.PaletteExample;
import com.jacmobile.sensorpanellite.fragments.SensorProfileFragment;
import com.jacmobile.sensorpanellite.fragments.SensorFragment;
import com.jacmobile.sensorpanellite.fragments.SensorListFragment;
import com.jacmobile.sensorpanellite.fragments.SystemInfoFragment;
import com.jacmobile.sensorpanellite.interfaces.ContentView;
import com.jacmobile.sensorpanellite.util.OmniController;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        complete = true,    // Here we enable object graph validation
        library = true,
        addsTo = ApplicationScopeModule.class, // Important for object graph validation at compile time
        injects = {
                PrimaryActivity.class,
                SensorFragment.class,
                SensorListFragment.class,
                SensorProfileFragment.class,
                SystemInfoFragment.class,
                PaletteExample.class
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
}