package com.jacmobile.sensorpanellite.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.jacmobile.sensorpanellite.app.DaggerApplication;
import com.jacmobile.sensorpanellite.injection.ActivityScopeModule;
import com.jacmobile.sensorpanellite.interfaces.DaggerInjector;

import dagger.ObjectGraph;


public abstract class ABaseActivity extends ActionBarActivity implements DaggerInjector {
    private ObjectGraph mActivityGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create the activity graph by .plus-ing our modules onto the application graph.
        mActivityGraph = ((DaggerApplication) getApplication()).getObjectGraph().plus(geActivitytModules());
        // Inject ourselves so subclasses will have dependencies fulfilled when this method returns.
        mActivityGraph.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        // Eagerly clear the reference to the activity graph to allow it to be garbage collected ASAP
        mActivityGraph = null;
        super.onDestroy();
    }

    /**
     * Inject the supplied {@code object} using the activity-specific graph.
     */
    @Override
    public void inject(Object object) {
        mActivityGraph.inject(object);
    }

    public ObjectGraph getObjectGraph() {
        return mActivityGraph;
    }

    protected Object[] geActivitytModules() {
        return new Object[]{
                new ActivityScopeModule(this)
        };
    }
}