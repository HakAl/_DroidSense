package com.jacmobile.droidsense.injection;

import android.app.Activity;
import android.content.Context;

import com.jacmobile.droidsense.activities.ABaseActivity;
import com.jacmobile.droidsense.activities.MainActivity;
import com.jacmobile.droidsense.fragments.MainFragment;
import com.jacmobile.droidsense.fragments.SensorListFragment;
import com.jacmobile.droidsense.injection.annotations.ForActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Here it provides the dependencies those have same lifetime of one activity in your MyCoolApp
 */
@Module(
        complete = true,    // Here we enable object graph validation
        library = true,
        addsTo = ApplicationScopeModule.class, // Important for object graph validation at compile time
        injects = {
                MainActivity.class,
                MainFragment.class,
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
}