package com.nenton.schools.mvp.views;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by serge on 03.06.2017.
 */

public interface IRootView extends IView{
    void showMessage(String message);
    void showError(Throwable e);

    void showLoad();
    void hideLoad();

    @Nullable
    IView getCurrentScreen();
}
