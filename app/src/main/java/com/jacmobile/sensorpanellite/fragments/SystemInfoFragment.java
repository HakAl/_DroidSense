package com.jacmobile.sensorpanellite.fragments;

import android.hardware.Sensor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jacmobile.sensorpanellite.R;
import com.jacmobile.sensorpanellite.util.RecyclerAdapter;
import com.jacmobile.sensorpanellite.util.SystemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 11/9/14.
 */
public class SystemInfoFragment extends ABaseFragment
{
    private RecyclerView.Adapter mAdapter;

    public static SystemInfoFragment newInstance()
    {
        return new SystemInfoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View list = inflater.inflate(R.layout.fragment_recycler, container, false);
        RecyclerView recyclerView = (RecyclerView) list.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        StaggeredGridLayoutManager staggeredLM = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredLM);

        String[] buildArray = getActivity().getResources().getStringArray(R.array.build_array);

        String[] sensorDescriptions = new String[buildArray.length];
        sensorDescriptions[0] = Build.BOARD;
        sensorDescriptions[1] = Build.BOOTLOADER;
        sensorDescriptions[2] = Build.BRAND;
        sensorDescriptions[3] = Build.DEVICE;
        sensorDescriptions[4] = Build.DISPLAY;
        sensorDescriptions[5] = Build.FINGERPRINT;
        sensorDescriptions[6] = Build.HARDWARE;
        sensorDescriptions[7] = Build.HOST;
        sensorDescriptions[8] = Build.ID;
        sensorDescriptions[9] = Build.MODEL;
        sensorDescriptions[10] = Build.PRODUCT;
        sensorDescriptions[11] = Build.RADIO;
        sensorDescriptions[12] = Build.SERIAL;
        sensorDescriptions[13] = Build.TAGS;
        sensorDescriptions[14] = ""+Build.TIME;
        sensorDescriptions[15] = Build.TYPE;
        sensorDescriptions[16] = Build.USER;

        mAdapter = RecyclerAdapter.newInstance(buildArray, sensorDescriptions, RecyclerAdapter.SYSTEM_INSTANCE);
        recyclerView.setAdapter(mAdapter);
        return list;
    }
}
