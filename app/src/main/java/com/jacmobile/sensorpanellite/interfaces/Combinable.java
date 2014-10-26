package com.jacmobile.sensorpanellite.interfaces;

import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;

import com.jacmobile.sensorpanellite.util.FlipVerticalAnimation;

/**
 * This interface is implemented only by animation classes that can be combined
 * to animate together.
 */
public interface Combinable
{

    public void animate();

    public long getDuration();

    public AnimatorSet getAnimatorSet();

    public FlipVerticalAnimation setInterpolator(TimeInterpolator interpolator);

    public FlipVerticalAnimation setDuration(long duration);

    public FlipVerticalAnimation setListener(AnimationListener listener);

}