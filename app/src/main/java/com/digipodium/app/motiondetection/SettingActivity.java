package com.digipodium.app.motiondetection;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Switch themeswitch = findViewById(R.id.themeswitch);
        final Button button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        final Button btn=findViewById(R.id.button2);
        final Button bttn=findViewById(R.id.button3);
        final SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        boolean theme = pref.getBoolean("theme", false);

        themeswitch.setChecked(theme);
        if (theme) {
            //change theme to light
        } else {
            // change to dark
        }
        themeswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                pref.edit().putBoolean("theme", b).apply();
            }
        });
    }
}
