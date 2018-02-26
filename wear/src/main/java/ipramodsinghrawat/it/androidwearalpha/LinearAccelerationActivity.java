package ipramodsinghrawat.it.androidwearalpha;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class LinearAccelerationActivity extends WearableActivity implements SensorEventListener {

    DecimalFormat decimalFormat;
    float[] linear_acceleration;
    float[] gravity;
    private SensorManager mSensorManager;
    private Sensor senLinearAcceleration;
    private TextView sensorReadingTV, sensorDetailTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        // Enables Always-on
        setAmbientEnabled();

        sensorReadingTV = findViewById(R.id.sensorReadingTV);
        sensorDetailTV = findViewById(R.id.sensorDetailTV);

        decimalFormat = new DecimalFormat("##.####");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        decimalFormat.setMaximumFractionDigits(4);
        decimalFormat.setMinimumFractionDigits(4);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        senLinearAcceleration = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        if(mySensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
            linearAccelerometerReading(sensorEvent);
            //mGravity = sensorEvent.values;
            //gravityReading(sensorEvent);
        }
    }

    public void linearAccelerometerReading(SensorEvent sensorEvent){
        Sensor mySensor = sensorEvent.sensor;
        // alpha is calculated as t / (t + dT)
        // with t, the low-pass filter's time-constant
        // and dT, the event delivery rate

        gravity=new float[3];
        linear_acceleration=new float[3];

        final double alpha = 0.8;

        gravity[0] = (float) (alpha * gravity[0] + (1 - alpha) * sensorEvent.values[0]);
        gravity[1] = (float) (alpha * gravity[1] + (1 - alpha) * sensorEvent.values[1]);
        gravity[2] = (float) (alpha * gravity[2] + (1 - alpha) * sensorEvent.values[2]);

        linear_acceleration[0] = sensorEvent.values[0] - gravity[0];
        linear_acceleration[1] = sensorEvent.values[1] - gravity[1];
        linear_acceleration[2] = sensorEvent.values[2] - gravity[2];

        sensorReadingTV.setText(
                "\n X= " + decimalFormat.format(linear_acceleration[0]) +" m/s^2" +
                        "\n Y= " + decimalFormat.format(linear_acceleration[1]) +" m/s^2" +
                        "\n Z= " +  decimalFormat.format(linear_acceleration[2]) +" m/s^2");

        sensorDetailTV.setText(
                " MaximumRange: " +mySensor.getMaximumRange()+" m/s^2"+
                        "\n Vendor: " + mySensor.getVendor() +
                        "\n Version: " + mySensor.getVersion()+
                        "\n Resolution: " + mySensor.getResolution()+
                        "\n Power: " + mySensor.getPower());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, senLinearAcceleration, SensorManager.SENSOR_DELAY_NORMAL);
        //mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // unregister sensor listeners to prevent the activity from draining the device's battery.
        mSensorManager.unregisterListener(this);
    }

    //When this Activity isn't visible anymore
    @Override
    protected void onStop(){
        super.onStop();
        //unregister the sensor listener
        mSensorManager.unregisterListener(this);
    }
}
