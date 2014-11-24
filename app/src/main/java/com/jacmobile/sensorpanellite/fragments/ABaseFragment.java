package com.jacmobile.sensorpanellite.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.SharedElementCallback;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Transition;
import android.util.AttributeSet;
import android.view.View;

import com.jacmobile.sensorpanellite.activities.ABaseActivity;

/**
 * Base fragment which performs injection using the activity-scoped object graph
 */
public abstract class ABaseFragment extends Fragment
{
    private boolean mFirstAttach = true;

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        // make sure it's the first time through; we don't want to re-inject a retained
        // fragment that is going through a detach/attach sequence.
        if (mFirstAttach) {
            ((ABaseActivity) getActivity()).inject(this);
            mFirstAttach = false;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View getView()
    {
        return super.getView();
    }

    @Override
    public void setTargetFragment(Fragment fragment, int requestCode)
    {
        super.setTargetFragment(fragment, requestCode);
    }

    @Override
    public void setArguments(Bundle args)
    {
        super.setArguments(args);
    }

    @Override
    public void onStop()
    {
        super.onStop();
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState)
    {
        super.onInflate(activity, attrs, savedInstanceState);
    }
}