package com.jacmobile.sensorpanellite.util;

import android.support.v7.widget.CardView;
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
    private String[] sensorTitles;
    private String[] sensorDescriptions;
    private boolean isSystemInstance;

    public static RecyclerAdapter newInstance(String[] sensorTitles, String[] sensorDescriptions)
    {
        return new RecyclerAdapter(sensorTitles, sensorDescriptions);
    }

    public static RecyclerAdapter newInstance(String[] sensorTitles, String[] sensorDescriptions, boolean isSystemInstance)
    {
        return new RecyclerAdapter(sensorTitles, sensorDescriptions, true);
    }

    private RecyclerAdapter(String[] sensorTitles, String[] sensorDescriptions)
    {
        this.sensorTitles = sensorTitles;
        this.sensorDescriptions = sensorDescriptions;
//        mListener = listener;
    }

    private RecyclerAdapter(String[] sensorTitles, String[] sensorDescriptions, boolean isSystemInstance)
    {
        this.sensorTitles = sensorTitles;
        this.sensorDescriptions = sensorDescriptions;
        this.isSystemInstance = true;
//        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        if (position == 0) {
            if (isSystemInstance) {
                holder.title.setText("System Properties");
            } else {
                holder.title.setText("Total device sensors: " + sensorTitles.length);
            }
            holder.description.setText("");
        } else {
            holder.title.setText(sensorTitles[position - 1]);
            holder.description.setText(sensorDescriptions[position - 1]);
        }

//        holder.mTextView.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                mListener.onClick(view, position);
//            }
//        });
    }

    @Override
    public int getItemCount()
    {
        return sensorTitles.length + 1;
    }
//    private OnItemClickListener mListener;

    /**
     * Interface for receiving click events from cells.
     */
    public interface OnItemClickListener
    {
        public void onClick(View view, int position);
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