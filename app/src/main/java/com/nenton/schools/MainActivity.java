package com.nenton.schools;

import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eith_screen);

        //Library used
        //https://github.com/AigeStudio/WheelPicker
        //https://github.com/kyleduo/SwitchButton
        //https://github.com/amilcar-sr/JustifiedTextView

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

}
