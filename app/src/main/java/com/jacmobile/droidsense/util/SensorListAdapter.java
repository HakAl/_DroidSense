package com.jacmobile.droidsense.util;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jacmobile.droidsense.R;
import com.jacmobile.droidsense.interfaces.Navigatable;
import com.squareup.picasso.Picasso;

/**
 * Created by alex on 10/12/14.
 */
public class SensorListAdapter extends BaseAdapter
{
    private Context context;
    private Picasso picasso;
    private LayoutInflater inflater;
    private Navigatable data[] = null;

    public SensorListAdapter(Context context, Picasso picasso, LayoutInflater inflater, Navigatable[] data)
    {
        super();
        this.context = context;
        this.data = data;
        this.inflater = inflater;
        this.picasso = picasso;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = this.inflater.inflate(R.layout.card_sensor, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.tv_sensor_name);
            holder.descript = (TextView) convertView.findViewById(R.id.tv_sensor_descript);
            holder.icon = (ImageView) convertView.findViewById(R.id.iv_sensor_icon);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(this.data[position].getName());
        holder.descript.setText(this.data[position].getDescription());
        this.picasso.load(this.data[position].getIconUrl()).placeholder(R.drawable.gear).into(holder.icon);

        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context, "Position: " + position, Toast.LENGTH_LONG).show();
            }
        });


        return convertView;
    }

    @Override
    public int getCount()
    {
        return this.data.length;
    }

    @Override
    public Object getItem(int position)
    {
        return this.data[position];
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    static class ViewHolder
    {
        ImageView icon;
        TextView title;
        TextView descript;
    }
}