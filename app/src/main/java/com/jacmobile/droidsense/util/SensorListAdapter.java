package com.jacmobile.droidsense.util;

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

import java.util.ArrayList;

/**
 * Created by alex on 10/12/14.
 */
public class SensorListAdapter extends BaseAdapter
{
    private Context context;
    private Picasso picasso;
    private LayoutInflater inflater;
    private ArrayList<Navigatable> data = null;

    public SensorListAdapter(Context context, Picasso picasso, LayoutInflater inflater, ArrayList<Navigatable> data)
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
            holder.title = (TextView) convertView.findViewById(R.id.tv_sensor_title);
            holder.subTitle = (TextView) convertView.findViewById(R.id.tv_sensor_sub_title);
            holder.descript = (TextView) convertView.findViewById(R.id.tv_sensor_descript);
            holder.icon = (ImageView) convertView.findViewById(R.id.iv_sensor_icon);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (this.data.get(position).getName().equals("Touch")) {
            holder.title.setText(this.data.get(position).getName());
            this.picasso.load(this.data.get(position).getIconUrl()).placeholder(R.drawable.gear).into(holder.icon);
            holder.subTitle.setText("");
            holder.descript.setText("");
        } else {
            holder.subTitle.setText(this.data.get(position).getSensor().getVendor());
            holder.title.setText(this.data.get(position).getName());
            holder.descript.setText(this.data.get(position).getSensor().getName());
            this.picasso.load(this.data.get(position).getIconUrl()).placeholder(R.drawable.gear).into(holder.icon);
        }
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
        return this.data.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.data.get(position);
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
        TextView subTitle;
        TextView descript;
    }
}