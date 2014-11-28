package com.jacmobile.sensorpanellite.util;

import android.content.Context;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
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
            float z = update[offset][2];
            if (z == 0f) {
                holder.z.setText(String.valueOf(((float) Math.round(100 * update[offset][0]) / 100)));
            } else {
                holder.x.setText(String.valueOf((float) Math.round(100 * update[offset][0]) / 100));
                holder.y.setText(String.valueOf((float) Math.round(100 * update[offset][1]) / 100));
                holder.z.setText(String.valueOf((float) Math.round(100 * update[offset][2]) / 100));
            }
        }
        holder.subTitle.setText(this.data.get(position).getSensor().getVendor());
        holder.title.setText(this.data.get(position).getName());
        holder.unit.setText(this.data.get(position).getUnitLabel());
        this.picasso.load(this.data.get(position).getIconUrl()).placeholder(R.drawable.ic_launcher).into(holder.icon);
        return convertView;
    }

    @Override
    public int getCount() {
        return this.data.size();
    }

    @Override
    public Navigable getItem(int position)
    {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateData(float[][] update)
    {
        this.update = update;
        this.notifyDataSetChanged();
    }

    static class ViewHolder {
        ImageView icon;
        TextView title;
        TextView subTitle;
        TextView unit;
        TextView x;
        TextView y;
        TextView z;
    }
}