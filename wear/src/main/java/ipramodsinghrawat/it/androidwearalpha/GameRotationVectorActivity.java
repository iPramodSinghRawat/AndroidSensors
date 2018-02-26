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

public class GameRotationVectorActivity extends WearableActivity implements SensorEventListener {

    DecimalFormat decimalFormat;
    float[] vector_reading;
    private SensorManager mSensorManager;
    private Sensor senGameRotationVector;
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
        senGameRotationVector = mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);

    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        if(mySensor.getType() == Sensor.TYPE_GAME_ROTATION_VECTOR){
            gameRotationVectorReading(sensorEvent);
            //mGravity = sensorEvent.values;
            //gravityReading(sensorEvent);
        }
    }

    public void gameRotationVectorReading(SensorEvent sensorEvent){
        Sensor mySensor = sensorEvent.sensor;
        // alpha is calculated as t / (t + dT)
        // with t, the low-pass filter's time-constant
        // and dT, the event delivery rate

  //      gravity=new float[3];
        vector_reading=new float[3];

        vector_reading[0] = sensorEvent.values[0];
        vector_reading[1] = sensorEvent.values[1];
        vector_reading[2] = sensorEvent.values[2];

        sensorReadingTV.setText(
                        "\n X= " + decimalFormat.format(vector_reading[0]) +" " +
                        "\n Y= " + decimalFormat.format(vector_reading[1]) +" " +
                        "\n Z= " +  decimalFormat.format(vector_reading[2]) +" ");

        sensorDetailTV.setText(
                        " MaximumRange: " +mySensor.getMaximumRange()+" "+
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
        mSensorManager.registerListener(this, senGameRotationVector, SensorManager.SENSOR_DELAY_NORMAL);
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
