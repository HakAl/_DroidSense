package com.jacmobile.sensorpanellite.app;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;

import com.jacmobile.sensorpanellite.R;
import com.jacmobile.sensorpanellite.injection.ForApplication;
import com.jacmobile.sensorpanellite.interfaces.Navigable;
import com.jacmobile.sensorpanellite.util.ImageUrls;
import com.jacmobile.sensorpanellite.util.OmniController;
import com.jacmobile.sensorpanellite.util.SensorListAdapter;
import com.jacmobile.sensorpanellite.util.SensorListItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        complete = false,
        library = true,
        injects = {
//                OmniController.class,
                SensorListAdapter.class
        }
)
public class AndroidAppModule
{
    /* package */ static Context sApplicationContext = null;

    /**
     * Allow the application context to be injected but require that it be annotated with
     * {@link com.jacmobile.sensorpanellite.injection.ForApplication @Annotation} to explicitly
     * differentiate it from an activity context.
     */
    @Provides @Singleton @ForApplication Context provideApplicationContext()
    {
        return sApplicationContext;
    }

    @Provides @Singleton LocationManager provideLocationManager()
    {
        return (LocationManager) sApplicationContext.getSystemService(Context.LOCATION_SERVICE);
    }

    @Provides @Singleton LayoutInflater provideLayoutInflater()
    {
        return LayoutInflater.from(sApplicationContext);
    }

    @Provides @Singleton SensorManager provideSensorManager()
    {
        return (SensorManager) sApplicationContext.getSystemService(Activity.SENSOR_SERVICE);
    }

    @Provides @Singleton Handler provideHandler() {
        return new Handler(Looper.getMainLooper());
    }


    @Provides @Singleton
    ExecutorService provideExecutorService() {
        return Executors.newCachedThreadPool();
    }

    @Provides @Singleton ArrayList<Navigable> provideSensorData(SensorManager sensorManager)
    {
        String[] sensorRanges = sApplicationContext.getResources().getStringArray(R.array.sensor_ranges);
        ArrayList<String> sensorUnitLabels = new ArrayList<String>(Arrays.asList(sApplicationContext.getResources().getStringArray(R.array.sensor_unit_labels)));
        ArrayList<String> sensorNames = new ArrayList<String>(Arrays.asList(sApplicationContext.getResources().getStringArray(R.array.sensors_array)));
        String[] imageUrls = ImageUrls.getImageUrls();
        ArrayList<Navigable> result = new ArrayList<Navigable>();
        for (int i = 1; i < 14; i++) {
            if (sensorManager.getDefaultSensor(i) != null) {
                SensorListItem temp = new SensorListItem();
                temp.setUnitLabel(sensorUnitLabels.get(i));
                temp.setSensor(sensorManager.getDefaultSensor(i));
                temp.setName(sensorNames.get(i));
                temp.setIconUrl(imageUrls[i]);
                temp.setSensorRange(sensorRanges[i].split(","));
                result.add(temp);
            }
        }
        return result;
    }

    @Provides @Singleton SensorListAdapter provideSensorListAdapter(ArrayList<Navigable> sensorData)
    {
        return new SensorListAdapter(sApplicationContext, R.layout.card_sensor, sensorData);
    }
}