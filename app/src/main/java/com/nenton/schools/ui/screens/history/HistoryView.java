package com.nenton.schools.ui.screens.history;

import android.content.Context;
import android.util.AttributeSet;

import com.nenton.schools.di.DaggerService;
import com.nenton.schools.mvp.views.AbstractView;

/**
 * Created by serge on 22.11.2017.
 */

public class HistoryView extends AbstractView<HistoryScreen.HistoryPresenter>{

    public HistoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<HistoryScreen.Component>getDaggerComponent(context).inject(this);
    }
}
