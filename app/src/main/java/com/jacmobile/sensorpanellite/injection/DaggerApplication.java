package com.jacmobile.sensorpanellite.injection;

import android.app.Application;

import com.jacmobile.sensorpanellite.interfaces.DaggerInjector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Base class of a dagger enabled application
 */
public class DaggerApplication extends Application implements DaggerInjector
{
    private ObjectGraph mObjectGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        AndroidAppModule sharedAppModule = new AndroidAppModule();

        // bootstrap. So that it allows no-arg constructor in AndroidAppModule
        sharedAppModule.sApplicationContext = this.getApplicationContext();

        List<Object> modules = new ArrayList<Object>();
        modules.add(sharedAppModule);
        modules.addAll(getAppModules());

        mObjectGraph = ObjectGraph.create(modules.toArray());

        mObjectGraph.inject(this);
    }

    protected List<Object> getAppModules() {
        return Collections.<Object>singletonList(new ApplicationScopeModule());
    }

    @Override
    public void inject(Object object) {
        mObjectGraph.inject(object);
    }

    @Override
    public ObjectGraph getObjectGraph() {
        return mObjectGraph;
    }
}