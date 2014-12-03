package com.jacmobile.sensorpanellite.fragments;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jacmobile.sensorpanellite.R;
import com.jacmobile.sensorpanellite.interfaces.Navigator;
import com.jacmobile.sensorpanellite.adapters.RecyclerAdapter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by alex on 11/8/14.
 */
public class SensorProfileFragment extends ABaseFragment
{
    @Inject SensorManager sensorManager;

    private RecyclerView.Adapter mAdapter;
    private Navigator navigator;

    public static SensorProfileFragment newInstance()
    {
        return new SensorProfileFragment();
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        navigator = null;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try {
            this.navigator = (Navigator) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement Navigator");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        navigator.setNavigationTitle(getString(R.string.sensor_profile));
        View list = inflater.inflate(R.layout.fragment_recycler, container, false);
        RecyclerView recyclerView = (RecyclerView) list.findViewById(R.id.recycler);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        List<Sensor> allSensors = this.sensorManager.getSensorList(Sensor.TYPE_ALL);
        String[] sensorTitles = new String[allSensors.size()];
        String[] sensorDescriptions = new String[allSensors.size()];
        for (int i = 0; i < sensorTitles.length; i++) {
            sensorTitles[i] = allSensors.get(i).getName();
            sensorDescriptions[i] = allSensors.get(i).toString();
        }
        for (int i = 0; i < sensorDescriptions.length; i++) {
            sensorDescriptions[i] = sensorDescriptions[i].replace(",", "\n");
            sensorDescriptions[i] = sensorDescriptions[i].replace("{", "");
            sensorDescriptions[i] = sensorDescriptions[i].replace("}", "");
            sensorDescriptions[i] = sensorDescriptions[i].replace("=", ":   ");
            sensorDescriptions[i] = sensorDescriptions[i].replace("\"", "");
        }

        // specify an adapter (see also next example)
        mAdapter = RecyclerAdapter.newInstance(sensorTitles, sensorDescriptions, RecyclerAdapter.SENSOR_INSTANCE);
        recyclerView.setAdapter(mAdapter);
        return list;
    }
}