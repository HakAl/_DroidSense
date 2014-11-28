package com.jacmobile.sensorpanellite.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jacmobile.sensorpanellite.R;

/**
 * Created by alex on 11/8/14.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
{
    public static final int SENSOR_INSTANCE = 11;
    public static final int SYSTEM_INSTANCE = 111;

    private int instance;
    private String[] sensorTitles;
    private String[] sensorDescriptions;

    public static RecyclerAdapter newInstance(String[] sensorTitles, String[] sensorDescriptions, int instance)
    {
        return new RecyclerAdapter(sensorTitles, sensorDescriptions, instance);
    }

    private RecyclerAdapter(String[] sensorTitles, String[] sensorDescriptions, int instance)
    {
        this.sensorTitles = sensorTitles;
        this.sensorDescriptions = sensorDescriptions;
        this.instance = instance;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = null;
        switch (instance) {
            case SENSOR_INSTANCE:
                v = inflater.inflate(R.layout.recycler_item, parent, false);
                break;
            case SYSTEM_INSTANCE:
                v = inflater.inflate(R.layout.recycler_item, parent, false);
                break;
            default:
                break;
        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        switch (instance) {
            case SENSOR_INSTANCE:
                if (position == 0) {
                    holder.title.setText("Total device sensors: " + (sensorTitles.length - 1));
                    holder.description.setText("");
                } else {
                    holder.title.setText(sensorTitles[position - 1]);
                    holder.description.setText(sensorDescriptions[position - 1]);
                }
                break;
            case SYSTEM_INSTANCE:
                if (position == 0) {
                    holder.title.setText(sensorTitles[position]);
                    holder.description.setText(sensorDescriptions[position]);
                } else {
                    holder.title.setText(sensorTitles[position]);
                    holder.description.setText(sensorDescriptions[position]);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount()
    {
        return (SYSTEM_INSTANCE == instance) ? sensorTitles.length : sensorTitles.length + 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public final TextView title, description;

        public ViewHolder(View v)
        {
            super(v);
            title = (TextView) v.findViewById(R.id.card_title);
            description = (TextView) v.findViewById(R.id.card_description);
        }
    }
}