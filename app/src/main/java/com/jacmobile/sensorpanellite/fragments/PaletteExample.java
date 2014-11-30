package com.jacmobile.sensorpanellite.fragments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jacmobile.sensorpanellite.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import javax.inject.Inject;

/**
 * Created by alex on 11/27/14.
 */
public class PaletteExample extends ABaseFragment
{
    private static final String url = "http://im.ziffdavisinternational.com/t/ign_pt/screenshot/default/captain-marvel-vol-7-2-textless2_nqvr.1920.jpg";
    private static final String url_1 = "http://schmoesknow.com/wp-content/uploads/2014/09/file_184599_0_msmarvel658.jpg";
    private static final String url_2 = "http://material-design.storage.googleapis.com/publish/v_2/material_ext_publish/0Bx4BSt6jniD7RmdHSUxhbEFTR2s/style_imagery_introduction.png";
    private static final String superUrl = "http://fc07.deviantart.net/fs26/i/2008/090/e/4/Superman_in_Color_Widescreen_by_the_big_al.jpg";
    @Inject Picasso picasso;

    View one, two, three, four, five, six;
    ImageView ivPalette;
    Bitmap mBitmap;

    public static PaletteExample newInstance()
    {
        return new PaletteExample();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_palette, container, false);
        ivPalette = (ImageView) view.findViewById(R.id.iv_palette);
        one = view.findViewById(R.id.palette_vibrant);
        two = view.findViewById(R.id.palette_muted);
        three = view.findViewById(R.id.palette_vibrant_dark);
        four = view.findViewById(R.id.palette_muted_dark);
        five = view.findViewById(R.id.palette_vibrant_light);
        six = view.findViewById(R.id.palette_muted_light);
        picasso.load("http://static.wixstatic.com/media/4b7811_74be4ca37352d776874410839132ea67.jpg_srz_p_886_573_85_22_0.50_1.20_0.00_jpg_srz").into(target);
        return view;
    }

    private void getPallette()
    {
        Palette.generateAsync(mBitmap, new Palette.PaletteAsyncListener()
        {
            public void onGenerated(Palette palette)
            {
                setColor(palette);
            }
        });
    }

    private void setColor(Palette palette)
    {
        one.setBackgroundColor(palette.getVibrantColor(Color.CYAN));
        two.setBackgroundColor(palette.getMutedColor(Color.RED));
        three.setBackgroundColor(palette.getDarkVibrantColor(Color.YELLOW));
        four.setBackgroundColor(palette.getDarkMutedColor(Color.YELLOW));
        five.setBackgroundColor(palette.getLightVibrantColor(Color.YELLOW));
        six.setBackgroundColor(palette.getLightMutedColor(Color.YELLOW));
    }

    private Target target = new Target()
    {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
        {
            mBitmap = bitmap;
            ivPalette.setImageBitmap(bitmap);
            ivPalette.post(new Runnable()
            {
                @Override
                public void run()
                {
                    getPallette();
                }
            });
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable)
        {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable)
        {

        }
    };
}