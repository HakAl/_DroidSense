package com.jacmobile.sensorpanellite.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidplot.xy.XYPlot;
import com.jacmobile.sensorpanellite.R;

/**
 * Created by alex on 11/8/14.
 */
public class OmniAdapter extends RecyclerView.Adapter<OmniAdapter.ViewHolder>
{
    private String[] sensorTitles;

    public static OmniAdapter newInstance(String[] sensorTitles)
    {
        return new OmniAdapter(sensorTitles);
    }

    private OmniAdapter(String[] sensorTitles)
    {
        this.sensorTitles = sensorTitles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        OmniAdapter.ViewHolder holder = new ViewHolder(inflater.inflate(R.layout.omni_card, parent, false));
        return new ViewHolder(inflater.inflate(R.layout.omni_card, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.title.setText(sensorTitles[position]);
    }

    @Override
    public int getItemCount()
    {
        return sensorTitles.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public final TextView title;

        public ViewHolder(View v)
        {
            super(v);
            title = (TextView) v.findViewById(R.id.txt_omni_title);
        }
    }
}