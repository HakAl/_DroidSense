package com.jacmobile.sensorpanellite.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;

import com.jacmobile.sensorpanellite.app.DaggerApplication;
import com.jacmobile.sensorpanellite.injection.ActivityScopeModule;
import com.jacmobile.sensorpanellite.interfaces.DaggerInjector;

import dagger.ObjectGraph;

/**
 *
         onCreateAndPrepareToDisplay()   [instead of onCreate() ]
         onPrepareToDisplay()            [instead of onRestart() ]
         onVisible()                     [instead of onStart() ]
         onBeginInteraction()            [instead of onResume() ]
         onPauseInteraction()            [instead of onPause() ]
         onInvisible()                   [instead of onStop]
         onDestroy()                     [no change]
 */
public abstract class ABaseActivity extends ActionBarActivity implements DaggerInjector
{
    private ObjectGraph activityGraph;

    /**
     * Create the activity graph by .plus-ing our modules onto the application graph.
     * Inject ourselves so subclasses will have dependencies fulfilled when this method returns.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        activityGraph = ((DaggerApplication) getApplication())
                .getObjectGraph()
                .plus(getActivityModules());
        activityGraph.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy()
    {
        // Eagerly clear the reference to the activity graph to allow it to be garbage collected ASAP
        activityGraph = null;
        super.onDestroy();
    }

    /**
     * Inject the supplied {@code object} using the activity-specific graph.
     */
    @Override
    public void inject(Object object)
    {
        activityGraph.inject(object);
    }

    public ObjectGraph getObjectGraph()
    {
        return activityGraph;
    }

    protected Object[] getActivityModules()
    {
        return new Object[] {
                new ActivityScopeModule(this)
        };
    }
}