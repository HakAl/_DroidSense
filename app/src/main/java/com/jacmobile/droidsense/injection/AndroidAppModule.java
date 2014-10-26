package com.jacmobile.droidsense.injection;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.view.LayoutInflater;


import com.jacmobile.droidsense.R;
import com.jacmobile.droidsense.config.ImageUrls;
import com.jacmobile.droidsense.injection.annotations.ForApplication;
import com.jacmobile.droidsense.interfaces.Navigable;
import com.jacmobile.droidsense.util.SensorListAdapter;
import com.jacmobile.droidsense.util.SensorListItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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

    @Provides
    @Singleton
    ArrayList<Navigable> provideSensorData(SensorManager sensorManager)
    {
        ArrayList<String> sensorNames = new ArrayList<String>(Arrays.asList(sApplicationContext.getResources().getStringArray(R.array.sensors_array)));
        String[] imageUrls = ImageUrls.getImageUrls();
        ArrayList<Navigable> result = new ArrayList<Navigable>();
        HashMap<String, Boolean> resultMap = new HashMap<String, Boolean>();
        for (int i = 1; i < 14; i++) {
            if (sensorManager.getDefaultSensor(i) != null) {
                SensorListItem temp = new SensorListItem();
                temp.setSensor(sensorManager.getDefaultSensor(i));
                temp.setName(sensorNames.get(i));
                temp.setIconUrl(imageUrls[i]);
                result.add(temp);
            }
        }
        return result;
    }

    @Provides
    @Singleton
    SensorListAdapter provideSensorListAdapter(Picasso picasso, LayoutInflater layoutInflater, ArrayList<Navigable> sensorData)
    {
        return new SensorListAdapter(picasso, layoutInflater, sensorData);
    }
}