package ipramodsinghrawat.it.androidwearalpha;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class Main2Activity extends WearableActivity implements SensorEventListener{

    private TextView mTextView;
    private ImageButton btnStart;
    private ImageButton btnPause;
    private Drawable imgStart;
    private SensorManager mSensorManager;
    private Sensor mHeartRateSensor;
    Sensor accelerometerSensor;
    Sensor stepCounterSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mTextView = (TextView) findViewById(R.id.heartRateText);
        btnStart = (ImageButton) findViewById(R.id.btnStart);
        btnPause = (ImageButton) findViewById(R.id.btnPause);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStart.setVisibility(ImageButton.GONE);
                btnPause.setVisibility(ImageButton.VISIBLE);
                mTextView.setText("Please wait...");
                startMeasure();
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPause.setVisibility(ImageButton.GONE);
                btnStart.setVisibility(ImageButton.VISIBLE);
                mTextView.setText("--");
                stopMeasure();
            }
        });


        // Enables Always-on
        setAmbientEnabled();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        final List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for(Sensor type : deviceSensors){
            Log.e("sensors",type.getStringType());
        }

        //mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE); //NOTE: is null
        mHeartRateSensor = mSensorManager.getDefaultSensor(65538); //wellness sensor
        if(mHeartRateSensor == null){
            Log.e("mHeartRateSensorD","no mHeartRateSensor");
        }else{
            Log.e("mHeartRateSensorD","Ok mHeartRateSensor");
        }

        /*
        accelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(accelerometerSensor == null){
            Log.e("accelerometerSensor","no accelerometerSensor");
        }else{
            Log.e("accelerometerSensor","Ok accelerometerSensor");
            boolean sensorRegistered = mSensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
            Log.d("Sensor Status:", " Sensor registered: " + (sensorRegistered ? "yes" : "no"));

        }
        */
        stepCounterSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(stepCounterSensor == null){
            Log.e("stepCounterSensor","no stepCounterSensor");
        }else{
            Log.e("stepCounterSensor","Ok stepCounterSensor");
            boolean sensorRegistered = mSensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
            Log.d("Sensor Status:", " Sensor registered: " + (sensorRegistered ? "yes" : "no"));

        }

    }

    private void startMeasure() {
        boolean sensorRegistered = mSensorManager.registerListener(this, mHeartRateSensor, SensorManager.SENSOR_DELAY_FASTEST);
        Log.d("Sensor Status:", " Sensor registered: " + (sensorRegistered ? "yes" : "no"));
    }

    private void stopMeasure() {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
/*
        float mHeartRateFloat = sensorEvent.values[0];

        int mHeartRate = Math.round(mHeartRateFloat);

        Log.d( "mHeartRate:", " mHeartRate: " + mHeartRate);

        mTextView.setText(Integer.toString(mHeartRate));
*/
/*
        if (sensorEvent.sensor.getType() == 65538) {
            String msg = "" + (int) sensorEvent.values[0];
            Log.d("Main Activity", msg);
        }*/

        //client.sendSensorData(sensorEvent.sensor.getType(), sensorEvent.accuracy, sensorEvent.timestamp, sensorEvent.values);
        Log.d("onSensorChanged", sensorEvent.sensor.getType()+", "+sensorEvent.accuracy+", "+sensorEvent.timestamp+", "+sensorEvent.values);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        Log.d("Main Activity", "accuracy : " + i + " sensor : " + sensor.getName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

}
