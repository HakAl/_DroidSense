package com.jacmobile.droidsense.fragments;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jacmobile.droidsense.R;
import com.jacmobile.droidsense.config.ImageUrls;
import com.jacmobile.droidsense.interfaces.Navigatable;
import com.jacmobile.droidsense.util.DeviceInfo;
import com.jacmobile.droidsense.util.SensorListAdapter;
import com.jacmobile.droidsense.util.SensorListItem;
import com.jacmobile.droidsense.util.SystemInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import android.hardware.Sensor;

import javax.inject.Inject;

/**
 * Created by alex on 10/12/14.
 */
public class SensorListFragment extends SensorFragment
{
    @Inject Picasso picasso;
    @Inject LayoutInflater layoutInflater;

    private ListView listView;

    public static SensorListFragment newInstance()
    {
        return new SensorListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_sensor_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        this.listView = getView(R.id.list_sensors);
        this.listView.addHeaderView(this.getHeaderView());
        this.listView.setAdapter(new SensorListAdapter(getActivity(), picasso, layoutInflater, getData()));
    }

    private ArrayList<Navigatable> getData()
    {
        ArrayList<String> sensorNames = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.sensors_array)));
        ArrayList<Sensor> sensors = this.getSensors();


        String[] imageUrls = ImageUrls.getImageUrls();
        ArrayList<Navigatable> result = new ArrayList<Navigatable>();
        for (int i = 1; i < 14; i++) {
            if (sensorManager.getDefaultSensor(i) != null) {
                SensorListItem temp = new SensorListItem();
                temp.setSensor(sensorManager.getDefaultSensor(i));
                temp.setName(sensorNames.get(i-1));
                temp.setIconUrl(imageUrls[i-1]);
                result.add(temp);
            }
        }
        SensorListItem touch = new SensorListItem();
        touch.setIconUrl(ImageUrls.TOUCH);
        touch.setName(sensorNames.get(13));
        result.add(touch);
        return result;
    }

    private View getHeaderView()
    {
        View result = this.layoutInflater.inflate(R.layout.card_device, null);
        ((TextView) result.findViewById(R.id.tv_device_name)).setText(SystemInfo.getVendor());
        ((TextView) result.findViewById(R.id.tv_os_version)).setText(SystemInfo.getVMName());
        this.picasso.load(R.drawable.ic_android).into((ImageView) result.findViewById(R.id.iv_device_icon));
        return result;
    }
}
