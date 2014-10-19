package com.jacmobile.droidsense.fragments;

import android.os.Bundle;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

/**
 * Created by alex on 10/12/14.
 */
public class SensorListFragment extends ABaseFragment
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

    private Navigatable[] getData()
    {
        ArrayList<String> sensorNames = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.sensors_array)));
        String[] imageUrls = ImageUrls.getImageUrls();
        SensorListItem[] result = new SensorListItem[sensorNames.size()];
        for (int i = 0; i < result.length; i++) {
            SensorListItem temp = new SensorListItem();
            temp.setDescription("Sensor " + i);
            temp.setName(sensorNames.get(i));
            temp.setIconUrl(imageUrls[i]);
            result[i] = temp;
        }
        return result;
    }

    private View getHeaderView()
    {
        View result = this.layoutInflater.inflate(R.layout.card_device, null);
        this.picasso.load(R.drawable.ic_launcher).into((ImageView)result.findViewById(R.id.iv_device_icon));
        ((TextView) result.findViewById(R.id.tv_device_name)).setText(DeviceInfo.getModel());
        ((TextView) result.findViewById(R.id.tv_os_version)).setText(DeviceInfo.getOSVersion());
        return result;
    }
}
