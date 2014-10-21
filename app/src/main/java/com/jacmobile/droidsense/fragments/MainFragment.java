package com.jacmobile.droidsense.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jacmobile.droidsense.R;
import com.jacmobile.droidsense.config.ImageUrls;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;


/**
 * Created by alex on 10/12/14.
 */
public class MainFragment extends ABaseFragment
{
    @Inject
    Picasso picasso;

    public static MainFragment newInstance()
    {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        ((TextView) this.getView(R.id.txt_poc)).setText("");

        ImageView imageView = this.getView(R.id.img_poc);
        picasso.load(ImageUrls.GYRO).into(imageView);
    }
}
