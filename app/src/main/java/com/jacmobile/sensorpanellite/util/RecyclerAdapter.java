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
//    private OnItemClickListener mListener;

    /**
     * Interface for receiving click events from cells.
     */
    public interface OnItemClickListener
    {
        public void onClick(View view, int position);
    }

    /**
     * Custom viewholder for our planet views.
     */
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

    public RecyclerAdapter(String[] sensorTitles, String[] sensorDescriptions)
    {
        this.sensorTitles = sensorTitles;
        this.sensorDescriptions = sensorDescriptions;
//        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.recycler_item, parent, false);
//        TextView tv = (TextView) v.findViewById(R.id.card_title);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        holder.title.setText(sensorTitles[position]);
        holder.description.setText(sensorDescriptions[position]);
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
        return sensorTitles.length;
    }
}