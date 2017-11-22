package com.nenton.schools.mvp.views;

import android.support.annotation.IdRes;
import android.view.View;

/**
 * Created by serge on 22.11.2017.
 */

public interface IRootActivityView extends IRootView {

    void changeOnBottom(@IdRes int id);

    void checkBottomNavView(int customIdScreen);
}
