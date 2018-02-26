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

public class GyroscopeUncalibratedActivity extends WearableActivity implements SensorEventListener {

    DecimalFormat decimalFormat;
    private SensorManager mSensorManager;
    private Sensor senGyroscopeUncalibrated;
    private TextView sensorReadingTV,sensorDetailTV,sensorDescriptionTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        // Enables Always-on
        setAmbientEnabled();

        sensorReadingTV = findViewById(R.id.sensorReadingTV);
        sensorDetailTV = findViewById(R.id.sensorDetailTV);

        decimalFormat = new DecimalFormat("##.######");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        decimalFormat.setMaximumFractionDigits(4);
        decimalFormat.setMinimumFractionDigits(4);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        senGyroscopeUncalibrated = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        if(mySensor.getType() == Sensor.TYPE_GYROSCOPE_UNCALIBRATED){
            gyroscopeUncalibratedReading(sensorEvent);
        }
    }

    public void gyroscopeUncalibratedReading(SensorEvent sensorEvent){
        Sensor mySensor = sensorEvent.sensor;

        sensorReadingTV.setText(
                "Gyroscope(Uncalibrated) Reading:"+
                        "\n X= " + decimalFormat.format(sensorEvent.values[0]) +" rad/sec"+
                        "\n Y= " + decimalFormat.format(sensorEvent.values[1]) +" rad/sec"+
                        "\n Z= " + decimalFormat.format(sensorEvent.values[2]) +" rad/sec"+
                        "\n estimated drift around X= " + decimalFormat.format(sensorEvent.values[3]) +" rad/sec"+
                        "\n estimated drift around Y= " + decimalFormat.format(sensorEvent.values[4]) +" rad/sec"+
                        "\n estimated drift around Z= " + decimalFormat.format(sensorEvent.values[5]) +" rad/sec");

        sensorDetailTV.setText(
                " MaximumRange: " + mySensor.getMaximumRange() +" rad/sec" +
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
        mSensorManager.registerListener(this, senGyroscopeUncalibrated, SensorManager.SENSOR_DELAY_NORMAL);
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
