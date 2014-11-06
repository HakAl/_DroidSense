package com.jacmobile.sensorpanellite.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jacmobile.sensorpanellite.R;
import com.jacmobile.sensorpanellite.interfaces.AnimationListener;
import com.jacmobile.sensorpanellite.interfaces.Navigator;
import com.jacmobile.sensorpanellite.util.Animation;
import com.jacmobile.sensorpanellite.util.FlipVerticalAnimation;
import com.jacmobile.sensorpanellite.util.SensorListAdapter;
import com.jacmobile.sensorpanellite.util.SystemInfo;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

/**
 * Created by alex on 10/12/14.
 */
public class SensorListFragment extends AListFragment
{
    private Navigator navigatorListener;
    private FlipVerticalAnimation animation;

    @Inject Picasso picasso;
    @Inject SensorListAdapter adapter;
    @Inject LayoutInflater layoutInflater;

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
        View view = inflater.inflate(R.layout.fragment_sensor_list, container, false);
        ListView listView = (ListView) view.findViewById(R.id.list_sensors);
        listView.addFooterView(this.getFooterView());
        listView.setAdapter(adapter);
        return view;
    }

    private View getFooterView()
    {
        View result = this.layoutInflater.inflate(R.layout.card_device, null);
        ((TextView) result.findViewById(R.id.tv_device_name)).setText(SystemInfo.getVendor());
        ((TextView) result.findViewById(R.id.tv_os_version)).setText(SystemInfo.getVMName());
        this.picasso.load(R.drawable.ic_launcher).into((ImageView) result.findViewById(R.id.iv_device_icon));
        return result;
    }

    @Override
    public void onListItemClick(ListView l, View view, final int position, long id)
    {
        super.onListItemClick(l, view, position, id);
        animation = new FlipVerticalAnimation(view);
        if (position != adapter.getCount()) {
            animation.setListener(new AnimationListener()
            {
                @Override
                public void onAnimationEnd(Animation animation)
                {
                    navigatorListener.onTransition(position);
                }
            });
        } else {
            animation.setListener(new AnimationListener()
            {
                @Override
                public void onAnimationEnd(Animation animation)
                {
                    int[] temp = {position,0};
                    navigatorListener.onTransition(temp);
                }
            });
        }
        animation.animate();
    }
}