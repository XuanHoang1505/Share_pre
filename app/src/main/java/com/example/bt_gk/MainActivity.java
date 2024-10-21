package com.example.bt_gk;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String SHARED_PREFERENCE_NAME = "SettingDisplay";
    private CheckBox cbBrightness;
    private CheckBox cbTextSize;
    private SeekBar sbBrightness;
    private SeekBar sbTextSize;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cbBrightness = (CheckBox) findViewById(R.id.cb_brightness);
        cbTextSize = (CheckBox) findViewById(R.id.cb_text_size);
        sbBrightness = (SeekBar) findViewById(R.id.sb_brightness);
        sbTextSize = (SeekBar) findViewById(R.id.sb_text_size);
        btnSave = (Button) findViewById(R.id.btn_save);

        cbBrightness.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    sbBrightness.setVisibility(View.VISIBLE);
                else
                    sbBrightness.setVisibility(View.INVISIBLE);
            }
        });

        cbTextSize.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    sbTextSize.setVisibility(View.VISIBLE);
                else
                    sbTextSize.setVisibility(View.INVISIBLE);
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        boolean isBrightness = sharedPreferences.getBoolean("brightness", false);
        boolean isTextSize = sharedPreferences.getBoolean("text_size", false);
        int brightnessValue = sharedPreferences.getInt("brightness_value", 50);
        int textSizeValue = sharedPreferences.getInt("text_size_value", 16);

        cbBrightness.setChecked(isBrightness);
        cbTextSize.setChecked(isTextSize);

        if (isBrightness)
            sbBrightness.setProgress(brightnessValue);
        else
            sbBrightness.setVisibility(View.INVISIBLE);

        if (isTextSize)
            sbTextSize.setProgress(textSizeValue);
        else
            sbTextSize.setVisibility(View.INVISIBLE);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("brightness", cbBrightness.isChecked());
                editor.putBoolean("text_size", cbTextSize.isChecked());

                if (cbBrightness.isChecked())
                    editor.putInt("brightness_value", sbBrightness.getProgress());
                else
                    editor.putInt("brightness_value", 50);

                if (cbTextSize.isChecked())
                    editor.putInt("text_size_value", sbTextSize.getProgress());
                else
                    editor.putInt("text_size_value", 16);
                editor.commit();
                Toast.makeText(MainActivity.this, "Settings saved successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}