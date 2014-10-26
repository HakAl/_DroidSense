package com.jacmobile.sensorpanellite.interfaces;

import com.jacmobile.sensorpanellite.util.Animation;

/**
 * This interface is a listener to determine the end of an animation.
 *
 */
public interface AnimationListener {

    /**
     * Called when the animation ends.
     *
     * @param animation the Animation object.
     */
    public void onAnimationEnd(Animation animation);
}