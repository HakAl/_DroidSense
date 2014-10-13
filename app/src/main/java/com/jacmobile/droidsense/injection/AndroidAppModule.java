package com.jacmobile.droidsense.injection;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.view.LayoutInflater;


import com.jacmobile.droidsense.injection.annotations.ForApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        complete = false,
        library = true,
        injects = {
        }
)
public class AndroidAppModule
{

    /* package */ static Context sApplicationContext = null;

    /**
     * Allow the application context to be injected but require that it be annotated with
     * {@link com.jacmobile.droidsense.injection.annotations.ForApplication @Annotation} to explicitly differentiate it from an activity context.
     */
    @Provides
    @Singleton
    @ForApplication
    Context provideApplicationContext()
    {
        return sApplicationContext;
    }

    @Provides
    @Singleton
    LocationManager provideLocationManager()
    {
        return (LocationManager) sApplicationContext.getSystemService(Context.LOCATION_SERVICE);
    }

    @Provides
    @Singleton
    LayoutInflater provideLayoutInflater()
    {
        return LayoutInflater.from(sApplicationContext);
    }

    @Provides
    @Singleton
    SensorManager provideSensorManager()
    {
        return (SensorManager) sApplicationContext.getSystemService(Activity.SENSOR_SERVICE);
    }
}