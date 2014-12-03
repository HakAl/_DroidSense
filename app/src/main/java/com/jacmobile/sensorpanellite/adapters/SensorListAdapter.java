package com.jacmobile.sensorpanellite.adapters;

import android.content.Context;
import android.hardware.Sensor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jacmobile.sensorpanellite.R;
import com.jacmobile.sensorpanellite.app.DaggerApplication;
import com.jacmobile.sensorpanellite.interfaces.Navigable;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by alex on 10/12/14.
 */
public class SensorListAdapter extends ArrayAdapter<Navigable>
{
    @Inject Picasso picasso;

    private LayoutInflater inflater;
    private ArrayList<Navigable> data = null;
    private float[][] update;

    public SensorListAdapter(Context context, int resource, ArrayList<Navigable> data)
    {
        super(context, resource, data);
        ((DaggerApplication) context).inject(this);
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = this.inflater.inflate(R.layout.card_sensor, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.tv_sensor_title);
            holder.subTitle = (TextView) convertView.findViewById(R.id.tv_sensor_sub_title);
            holder.unit = (TextView) convertView.findViewById(R.id.tv_sensor_unit);
            holder.icon = (ImageView) convertView.findViewById(R.id.iv_sensor_icon);
            holder.x = (TextView) convertView.findViewById(R.id.tv_x);
            holder.y = (TextView) convertView.findViewById(R.id.tv_y);
            holder.z = (TextView) convertView.findViewById(R.id.tv_z);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        int offset = data.get(position).getSensor().getType();
        if (update[offset] != null) {
            if (isSingleSeries(data.get(position).getSensor())) {
                holder.z.setText(String.format("%.1f",update[offset][0]));
                holder.x.setText("");
                holder.y.setText("");
            } else {
                holder.x.setText(String.format("%.1f", update[offset][0]));
                holder.y.setText(String.format("%.1f",update[offset][1]));
                holder.z.setText(String.format("%.1f",update[offset][2]));
            }
        }
        holder.subTitle.setText(this.data.get(position).getSensor().getVendor());
        holder.title.setText(this.data.get(position).getName());
        holder.unit.setText(this.data.get(position).getUnitLabel());
        this.picasso.load(this.data.get(position).getIconUrl())
                .placeholder(R.drawable.ic_launcher)
                .into(holder.icon);
        return convertView;
    }

    @Override
    public int getCount()
    {
        return this.data.size();
    }

    @Override
    public Navigable getItem(int position)
    {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public void updateData(float[][] update)
    {
        this.update = update;
        this.notifyDataSetChanged();
    }

    public ArrayList<Navigable> getData()
    {
        return data;
    }

    public boolean isSingleSeries(Sensor sensor)
    {
        int sensorType = sensor.getType();
        return (sensorType == Sensor.TYPE_LIGHT ||
                sensorType == Sensor.TYPE_PROXIMITY ||
                sensorType == Sensor.TYPE_GRAVITY||
                sensorType == Sensor.TYPE_AMBIENT_TEMPERATURE ||
                sensorType == Sensor.TYPE_TEMPERATURE||
                sensorType == Sensor.TYPE_RELATIVE_HUMIDITY||
                sensorType == Sensor.TYPE_PRESSURE);
    }

    static class ViewHolder
    {
        ImageView icon;
        TextView title;
        TextView subTitle;
        TextView unit;
        TextView x;
        TextView y;
        TextView z;
    }
}