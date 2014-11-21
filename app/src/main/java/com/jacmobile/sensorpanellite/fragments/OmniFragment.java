package com.jacmobile.sensorpanellite.fragments;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jacmobile.sensorpanellite.R;
import com.jacmobile.sensorpanellite.interfaces.Navigable;
import com.jacmobile.sensorpanellite.util.OmniAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by alex on 11/16/14.
 */
public class OmniFragment extends ABaseFragment implements SensorEventListener
{
    @Inject ArrayList<Navigable> sensorData;
    private RecyclerView.Adapter mAdapter;

    public static OmniFragment newInstance()
    {
        return new OmniFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_omni, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_omni);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        String[] titles = new String[sensorData.size()];
        for (int i = 0; i < titles.length; i++) {
            titles[i] = sensorData.get(i).getName();
        }
        mAdapter = OmniAdapter.newInstance(titles);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        switch (event.sensor.getType()) {

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }
}
