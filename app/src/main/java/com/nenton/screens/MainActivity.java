package com.nenton.screens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aigestudio.wheelpicker.WheelPicker;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sixth_screen);
        WheelPicker wheelPicker = (WheelPicker) findViewById(R.id.data_picker);
        List<String> strings = new ArrayList<>();
        strings.add("Mountain View");
        strings.add("Sunnyvale");
        strings.add("Cupertino");
        strings.add("Santa Clara");
        strings.add("San Jose");
        wheelPicker.setData(strings);
    }
}
