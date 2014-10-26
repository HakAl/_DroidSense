package com.jacmobile.droidsense.injection;

import android.app.Activity;
import android.content.Context;

import com.jacmobile.droidsense.activities.ABaseActivity;
import com.jacmobile.droidsense.activities.DSActivity;
import com.jacmobile.droidsense.fragments.MainFragment;
import com.jacmobile.droidsense.fragments.SensorFragment;
import com.jacmobile.droidsense.fragments.SensorListFragment;
import com.jacmobile.droidsense.injection.annotations.ForActivity;
import com.jacmobile.droidsense.interfaces.ContentView;

import java.util.Timer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        complete = true,    // Here we enable object graph validation
        library = true,
        addsTo = ApplicationScopeModule.class, // Important for object graph validation at compile time
        injects = {
                DSActivity.class,
                MainFragment.class,
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
}