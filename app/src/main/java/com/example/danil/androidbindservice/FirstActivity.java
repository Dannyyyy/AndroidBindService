package com.example.danil.androidbindservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class FirstActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    boolean bound = false;
    ServiceConnection serviceConnection;
    Intent intent;
    FirstBindService firstBindService;
    TextView textViewResult;
    TextView textViewValue;
    Button btnStart;
    Button btnPlus;
    Button btnMinus;
    SeekBar seekBar;
    int result;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        textViewResult = (TextView) findViewById(R.id.textViewResult);
        textViewValue = (TextView) findViewById(R.id.textViewValue);

        btnStart = (Button) findViewById(R.id.buttonStart);
        btnStart.setOnClickListener(onClickStartListener);
        btnPlus = (Button) findViewById(R.id.buttonPlus);
        btnPlus.setOnClickListener(onClickPlusListener);
        btnMinus = (Button) findViewById(R.id.buttonMinus);
        btnMinus.setOnClickListener(onClickMinusListener);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(this);

        textViewValue.setText(String.valueOf(seekBar.getProgress()));

        intent = new Intent(this, FirstBindService.class);
        serviceConnection = new ServiceConnection() {

            public void onServiceConnected(ComponentName name, IBinder binder) {
                firstBindService = ((FirstBindService.FirstBinder) binder).getService();
                bound = true;
            }

            public void onServiceDisconnected(ComponentName name) {
                bound = false;
            }
        };
    }

    private final View.OnClickListener onClickStartListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onClickStart(v);
        }
    };

    private final View.OnClickListener onClickPlusListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onClickPlus(v);
        }
    };

    private final View.OnClickListener onClickMinusListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onClickMinus(v);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        bindService(intent, serviceConnection, 0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!bound) return;
        unbindService(serviceConnection);
        bound = false;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        textViewValue.setText(String.valueOf(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) { }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) { }

    public void onClickStart(View v) {
        startService(intent);
    }

    public void onClickPlus(View v) {
        if (!bound) return;
        int value = Integer.parseInt(textViewValue.getText().toString());
        result = firstBindService.addition(value);
        textViewResult.setText(String.valueOf(result));
    }

    public void onClickMinus(View v) {
        if (!bound) return;
        int value = Integer.parseInt(textViewValue.getText().toString());
        result = firstBindService.subtraction(value);
        textViewResult.setText(String.valueOf(result));
    }
}
