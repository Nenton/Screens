package com.nenton.screens;

import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.aigestudio.wheelpicker.WheelPicker;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eith_screen);

        //Library used
        //https://github.com/AigeStudio/WheelPicker
        //https://github.com/kyleduo/SwitchButton
        //https://github.com/amilcar-sr/JustifiedTextView

        BottomNavigationView navigationView = ((BottomNavigationView) findViewById(R.id.bottom_navigation));
        disableShiftMode(navigationView);

        //region ========================= FOR 6 SCREEN =========================

//        WheelPicker wheelPicker = (WheelPicker) findViewById(R.id.data_picker);
//        List<String> strings = new ArrayList<>();
//        strings.add("Mountain View");
//        strings.add("Sunnyvale");
//        strings.add("Cupertino");
//        strings.add("Santa Clara");
//        strings.add("San Jose");
//        wheelPicker.setData(strings);

        //endregion
    }

    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }
}
