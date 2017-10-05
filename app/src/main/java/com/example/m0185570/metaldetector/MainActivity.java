package com.example.m0185570.metaldetector;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import static android.R.attr.button;
import static com.example.m0185570.metaldetector.R.id.erfassen;
import static com.example.m0185570.metaldetector.R.id.text;

public class MainActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener {
    private ProgressBar bar;
    private SensorManager sensorManager;
    private Sensor magneticFieldSensor;
    private TextView textView1;
    private TextView textView2;
    private Button erfassen;
    double betrag;
    int betragInt;
    int counter;
    String betragString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bar = (ProgressBar) findViewById(R.id.magFieldProgressBar);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        erfassen = (Button) findViewById(R.id.erfassen);

        erfassen.setOnClickListener(this);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        magneticFieldSensor = sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD).get(0);
        //bar.setMax((int)magneticFieldSensor.getMaximumRange() / 3);
        bar.setMax(1400);

    }

    @Override
    public void onClick(View v){
        if (counter <= 12){
            textView2.append("\n" + betragString);
            counter++;
        }
        else {
            textView2.setText("");
            counter = 1;

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (magneticFieldSensor != null) {
            sensorManager.registerListener(this, magneticFieldSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //here get value from sensor and give it to progress bar
        float[] mag = sensorEvent.values;
        betrag = Math.sqrt(mag[0] * mag[0] + mag[1] * mag[1] + mag[2] * mag[2]);
        betragInt = (int) betrag;


        bar.setProgress(betragInt);

        betragString = String.valueOf(betragInt);
        textView1.setText(betragString);


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //EMPTY
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);

    }


}
