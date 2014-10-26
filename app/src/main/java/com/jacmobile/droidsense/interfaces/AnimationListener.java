package com.jacmobile.droidsense.interfaces;

import com.jacmobile.droidsense.util.Animation;

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