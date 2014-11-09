package com.jacmobile.sensorpanellite.activities;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jacmobile.sensorpanellite.R;
import com.jacmobile.sensorpanellite.util.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by alex on 11/8/14.
 */
public class RecyclerActivity extends ABaseActivity
{
    @Inject SensorManager sensorManager;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
//        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(mLayoutManager);


        List<Sensor> allSensors = this.sensorManager.getSensorList(Sensor.TYPE_ALL);
        String[] sensorTitles = new String[allSensors.size()];
        String[] sensorDescriptions = new String[allSensors.size()];
        for (int i = 0; i < sensorTitles.length; i++) {
            sensorTitles[i] = allSensors.get(i).getName();
            sensorDescriptions[i] = allSensors.get(i).toString();
        }
        for (int i = 0; i < sensorDescriptions.length; i++){
            sensorDescriptions[i] = sensorDescriptions[i].replace(",", "\n");
            sensorDescriptions[i] = sensorDescriptions[i].replace("{","");
            sensorDescriptions[i] = sensorDescriptions[i].replace("}","");
            sensorDescriptions[i] = sensorDescriptions[i].replace("=", ":   ");
        }

        // specify an adapter (see also next example)
        mAdapter = new RecyclerAdapter(sensorTitles, sensorDescriptions);
        mRecyclerView.setAdapter(mAdapter);
    }
}
