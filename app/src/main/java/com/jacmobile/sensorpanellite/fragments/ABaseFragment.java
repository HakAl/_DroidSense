package com.jacmobile.sensorpanellite.fragments;

import android.app.Fragment;
import android.os.Bundle;

import com.jacmobile.sensorpanellite.activities.ABaseActivity;

/**
 * Base fragment which performs injection using the activity-scoped object graph
 */
public abstract class ABaseFragment extends Fragment
{
    private boolean mFirstAttach = true;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // make sure it's the first time through; we don't want to re-inject a retained
        // fragment that is going through a detach/attach sequence.
        if (mFirstAttach) {
            ((ABaseActivity) getActivity()).inject(this);
            mFirstAttach = false;
        }
    }

    protected <T> T getView(int id)
    {
        return (T) getView().findViewById(id);
    }
}