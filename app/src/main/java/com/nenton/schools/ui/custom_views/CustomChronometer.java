package com.nenton.schools.ui.custom_views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Chronometer;

/**
 * Created by serge on 17.11.2017.
 */

public class CustomChronometer extends Chronometer {

    public CustomChronometer(Context context) {
        super(context);
    }

    public CustomChronometer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private boolean isRunning = false;

    @Override
    public void start() {
        super.start();
        isRunning = true;
    }

    @Override
    public void stop() {
        isRunning = false;
        super.stop();
    }

    public boolean isRunning() {
        return isRunning;
    }
}
