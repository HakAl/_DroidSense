package com.jacmobile.droidsense.activities;

import android.app.Activity;
import android.os.Bundle;

import com.jacmobile.droidsense.injection.ActivityScopeModule;
import com.jacmobile.droidsense.injection.DaggerApplication;
import com.jacmobile.droidsense.interfaces.DaggerInjector;

import dagger.ObjectGraph;


public abstract class ABaseActivity extends Activity implements DaggerInjector
{
    private ObjectGraph mActivityGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Create the activity graph by .plus-ing our modules onto the application graph.
        DaggerApplication application = (DaggerApplication) getApplication();
        mActivityGraph = application.getObjectGraph().plus(geActivitytModules());

        // Inject ourselves so subclasses will have dependencies fulfilled when this method returns.
        mActivityGraph.inject(this);
    }

    @Override
    protected void onDestroy()
    {
        // Eagerly clear the reference to the activity graph to allow it to be garbage collected ASAP
        mActivityGraph = null;
        super.onDestroy();
    }

    protected <T> T getView(int id)
    {
        return (T) findViewById(id);
    }

    /**
     * Inject the supplied {@code object} using the activity-specific graph.
     */
    @Override
    public void inject(Object object)
    {
        mActivityGraph.inject(object);
    }

    public ObjectGraph getObjectGraph()
    {
        return mActivityGraph;
    }

    protected Object[] geActivitytModules()
    {
        return new Object[]{
                new ActivityScopeModule(this)
                // new AnotherActivityScopedModule(),
        };
    }
}
