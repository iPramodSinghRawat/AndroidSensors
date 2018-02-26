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

public class AccelerometerActivity extends WearableActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor senAccelerometer;
    private TextView sensorReadingTV,sensorDetailTV;

    DecimalFormat decimalFormat;

    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        // Enables Always-on
        setAmbientEnabled();

        sensorReadingTV = (TextView) findViewById(R.id.sensorReadingTV);
        sensorDetailTV = (TextView) findViewById(R.id.sensorDetailTV);
        //sensorDescriptionTV = (TextView) findViewById(R.id.sensorDescriptionTV);

        decimalFormat = new DecimalFormat("##.####");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        decimalFormat.setMaximumFractionDigits(4);
        decimalFormat.setMinimumFractionDigits(4);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //processHideDrawerItems(mSensorManager,this);
        senAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //sensorDescriptionTV.setText("Measures the acceleration force in m/s^2 that is applied to a device on all three physical axes (x, y, and z), including the force of gravity.");

    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        if(mySensor.getType() == Sensor.TYPE_ACCELEROMETER){
            accelerometerReading(sensorEvent);
            //mGravity = sensorEvent.values;
            //gravityReading(sensorEvent);
        }
    }

    public void accelerometerReading(SensorEvent sensorEvent){
        Sensor mySensor = sensorEvent.sensor;
        float speed=0;

        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];

        long curTime = System.currentTimeMillis();

        if ((curTime - lastUpdate) > 100) {
            long diffTime = (curTime - lastUpdate);
            lastUpdate = curTime;

            speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000;

            if (speed > SHAKE_THRESHOLD) {

            }

            last_x = x;
            last_y = y;
            last_z = z;
        }

        sensorReadingTV.setText(
                " X= " + decimalFormat.format(x) +" m/s^2"+
                        "\n Y= " + decimalFormat.format(y) +" m/s^2" +
                        "\n Z= " + decimalFormat.format(z) +" m/s^2" +
                        "\n Speed= " + decimalFormat.format(speed));

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
        mSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
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
