package com.jacmobile.droidsense.fragments;

import android.app.Activity;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jacmobile.droidsense.R;
import com.jacmobile.droidsense.activities.ABaseActivity;
import com.jacmobile.droidsense.interfaces.Navigatable;
import com.jacmobile.droidsense.interfaces.Navigator;
import com.jacmobile.droidsense.util.Animation;
import com.jacmobile.droidsense.util.AnimationListener;
import com.jacmobile.droidsense.util.FlipVerticalAnimation;
import com.jacmobile.droidsense.util.SensorListAdapter;
import com.jacmobile.droidsense.util.SystemInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

/**
 * Created by alex on 10/12/14.
 */
public class SensorListFragment extends ABaseFragment
{
    private int length;
    private ListView listView;
    private Navigator navigatorListener;

    @Inject Picasso picasso;
    @Inject LayoutInflater layoutInflater;
    @Inject ArrayList<Navigatable> sensorData;

    public static SensorListFragment newInstance()
    {
        return new SensorListFragment();
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        this.navigatorListener = (Navigator) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_sensor_list, container, false);
    }

//    TODO
    //        this.listView.addFooterView(getFooterView);
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        this.listView = getView(R.id.list_sensors);
        this.listView.addFooterView(this.getFooterView());
        this.length=this.sensorData.size();
        this.listView.setAdapter(new SensorListAdapter(this.picasso, this.layoutInflater, this.sensorData));
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
            {
                if (position != length) {
                    FlipVerticalAnimation animation = new FlipVerticalAnimation(view);
                    animation.setListener(new AnimationListener()
                    {
                        @Override
                        public void onAnimationEnd(Animation animation)
                        {
                            navigatorListener.onTransition(position);
                        }
                    });
                    animation.animate();
                }
            }
        });
    }


    private int getSensor(String name)
    {
        ArrayList<String> sensorNames = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.sensors_array)));
        for (int i = 0; i < sensorNames.size(); i++) {
            if (sensorNames.get(i).equals(name)) {
                return i;
            }
        }
        return -1;
    }


    private View getHeaderView()
    {
//        SensorListItem touch = new SensorListItem();
//        touch.setIconUrl(ImageUrls.TOUCH);
//        touch.setName(sensorNames.get(13));
//        result.add(touch);
        return new View(getActivity());
    }

    private View getFooterView()
    {
        View result = this.layoutInflater.inflate(R.layout.card_device, null);
        ((TextView) result.findViewById(R.id.tv_device_name)).setText(SystemInfo.getVendor());
        ((TextView) result.findViewById(R.id.tv_os_version)).setText(SystemInfo.getVMName());
        this.picasso.load(R.drawable.gear).into((ImageView) result.findViewById(R.id.iv_device_icon));
        return result;
    }
}