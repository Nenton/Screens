package com.nenton.schools.ui.screens.live;

import android.content.Context;
import android.util.AttributeSet;

import com.nenton.schools.di.DaggerService;
import com.nenton.schools.mvp.views.AbstractView;

/**
 * Created by serge on 22.11.2017.
 */

class LiveView extends AbstractView<LiveScreen.LivePresenter>{

    public LiveView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<LiveScreen.Component>getDaggerComponent(context).inject(this);
    }
}
