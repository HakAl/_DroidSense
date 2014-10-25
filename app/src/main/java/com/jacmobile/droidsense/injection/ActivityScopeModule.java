package com.jacmobile.droidsense.injection;

import android.app.Activity;
import android.content.Context;

import com.jacmobile.droidsense.activities.ABaseActivity;
import com.jacmobile.droidsense.activities.DSActivity;
import com.jacmobile.droidsense.fragments.MainFragment;
import com.jacmobile.droidsense.fragments.SensorFragment;
import com.jacmobile.droidsense.fragments.SensorListFragment;
import com.jacmobile.droidsense.injection.annotations.ForActivity;
import com.jacmobile.droidsense.interfaces.ContentView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        complete = true,    // Here we enable object graph validation
        library = true,
        addsTo = ApplicationScopeModule.class, // Important for object graph validation at compile time
        injects = {
                DSActivity.class,
                MainFragment.class,
                SensorFragment.class,
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


    @Provides @Singleton
    ContentView provideContentView() {
        return ContentView.DEFAULT;
    }
//
//    @Provides
//    @Singleton
//    XYPlot provideXYPlot(ContentView contentView, LayoutInflater layoutInflater)
//    {
//        // Init Plot and x,y,z series
//        XYPlot xyPlot = (XYPlot) layoutInflater.inflate(R.layout.sensor_plot, new LinearLayout(mActivity), false);
//
//        SimpleXYSeries xSeries = new SimpleXYSeries("X");
//        xSeries.useImplicitXVals();
//        SimpleXYSeries ySeries = new SimpleXYSeries("Y");
//        ySeries.useImplicitXVals();
//        SimpleXYSeries zSeries = new SimpleXYSeries("Z");
//        zSeries.useImplicitXVals();
//        // Set domain & range
//        xyPlot.setDomainBoundaries(0, 100, BoundaryMode.FIXED);
//        xyPlot.setRangeBoundaries(-20, 20, BoundaryMode.FIXED);
//        // Incorporate x,y,z series
//        xyPlot.addSeries(xSeries, new LineAndPointFormatter(Color.RED, null, null, null));
//        xyPlot.addSeries(ySeries, new LineAndPointFormatter(Color.CYAN, null, null, null));
//        xyPlot.addSeries(zSeries, new LineAndPointFormatter(Color.YELLOW, null, null, null));
//        // Set drawing speed
//        xyPlot.setDomainStepMode(XYStepMode.INCREMENT_BY_VAL);
//        xyPlot.setDomainStepValue(10 / 1);
//        xyPlot.setTicksPerRangeLabel(3);
//        // Number format
//        xyPlot.setDomainValueFormat(new DecimalFormat("#"));
//        xyPlot.setRangeValueFormat(new DecimalFormat("#"));
//        return xyPlot;
//    }
}