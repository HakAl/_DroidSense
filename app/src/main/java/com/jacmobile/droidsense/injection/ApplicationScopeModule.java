package com.jacmobile.droidsense.injection;

import android.content.Context;

import com.jacmobile.droidsense.BuildConfig;
import com.jacmobile.droidsense.injection.annotations.ForApplication;
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

        // some app-wide common settings
        picasso.setDebugging(BuildConfig.DEBUG);

        return picasso;
    }
}
