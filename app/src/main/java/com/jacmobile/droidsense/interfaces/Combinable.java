package com.jacmobile.droidsense.interfaces;

import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;

import com.jacmobile.droidsense.util.AnimationListener;

/**
 * This interface is implemented only by animation classes that can be combined
 * to animate together.
 *
 */
public interface Combinable {

    public void animate();
    public AnimatorSet getAnimatorSet();
    public com.jacmobile.droidsense.util.FlipVerticalAnimation setInterpolator(TimeInterpolator interpolator);
    public long getDuration();
    public com.jacmobile.droidsense.util.FlipVerticalAnimation setDuration(long duration);
    public com.jacmobile.droidsense.util.FlipVerticalAnimation setListener(AnimationListener listener);

}