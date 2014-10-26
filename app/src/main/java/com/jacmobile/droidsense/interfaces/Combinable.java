package com.jacmobile.droidsense.interfaces;

import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;

/**
 * This interface is implemented only by animation classes that can be combined
 * to animate together.
 */
public interface Combinable
{

    public void animate();

    public long getDuration();

    public AnimatorSet getAnimatorSet();

    public com.jacmobile.droidsense.util.FlipVerticalAnimation setInterpolator(TimeInterpolator interpolator);

    public com.jacmobile.droidsense.util.FlipVerticalAnimation setDuration(long duration);

    public com.jacmobile.droidsense.util.FlipVerticalAnimation setListener(AnimationListener listener);

}