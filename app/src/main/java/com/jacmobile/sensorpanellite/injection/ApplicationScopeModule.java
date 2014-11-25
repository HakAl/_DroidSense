package com.jacmobile.sensorpanellite.injection;

import android.content.Context;

import com.jacmobile.sensorpanellite.app.AndroidAppModule;
import com.jacmobile.sensorpanellite.app.DaggerApplication;
import com.jacmobile.sensorpanellite.util.OmniController;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                DaggerApplication.class
        },
        complete = true,    // Here it enables object graph validation
        library = true,
        addsTo = AndroidAppModule.class // Important for object graph validation at compile time
)
public class ApplicationScopeModule
{
    @Provides
    Picasso providesPicasso(@ForApplication Context context) {
        Picasso picasso = Picasso.with(context);
//        picasso.setDebugging(BuildConfig.DEBUG);
        return picasso;
    }
}