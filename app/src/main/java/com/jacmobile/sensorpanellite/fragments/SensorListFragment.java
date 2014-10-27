package com.jacmobile.sensorpanellite.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jacmobile.sensorpanellite.R;
import com.jacmobile.sensorpanellite.interfaces.Navigable;
import com.jacmobile.sensorpanellite.interfaces.Navigator;
import com.jacmobile.sensorpanellite.util.Animation;
import com.jacmobile.sensorpanellite.interfaces.AnimationListener;
import com.jacmobile.sensorpanellite.util.FlipVerticalAnimation;
import com.jacmobile.sensorpanellite.util.SensorListAdapter;
import com.jacmobile.sensorpanellite.util.SystemInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by alex on 10/12/14.
 */
public class SensorListFragment extends ABaseFragment
{
    private int length;
    private ListView listView;
    private Navigator navigatorListener;
    private FlipVerticalAnimation animation;

    @Inject Picasso picasso;
    @Inject LayoutInflater layoutInflater;
    @Inject ArrayList<Navigable> sensorData;

    public static SensorListFragment newInstance()
    {
        return new SensorListFragment();
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try {
            this.navigatorListener = (Navigator) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement Navigator");
        }
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
        this.listView.addFooterView(this.getFooterView());
        this.length=this.sensorData.size();
        this.listView.setAdapter(new SensorListAdapter(this.picasso, this.layoutInflater, this.sensorData));
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
            {
                if (position != length) {
                    animation = new FlipVerticalAnimation(view);
                    animation.setListener(new AnimationListener()
                    {
                        @Override
                        public void onAnimationEnd(Animation animation)
                        {
                            navigatorListener.onTransition(position);
                        }
                    });
                } else {
                    //todo navigate to vendor url
                    animation = new FlipVerticalAnimation(view, 180);
                }
                animation.animate();
            }
        });
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