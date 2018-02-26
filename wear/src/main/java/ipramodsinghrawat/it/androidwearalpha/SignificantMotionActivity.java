package ipramodsinghrawat.it.androidwearalpha;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;
import android.widget.Toast;

public class SignificantMotionActivity extends WearableActivity{ // implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSigMotion;
    private TextView sensorReadingTV,sensorDetailTV;
    private TriggerEventListener moListener = new TriggerEvntListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        // Enables Always-on
        setAmbientEnabled();

        sensorReadingTV = findViewById(R.id.sensorReadingTV);
        sensorDetailTV = findViewById(R.id.sensorDetailTV);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mSigMotion = mSensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION);
    }

    protected void onResume() {
        super.onResume();
        if (mSensorManager != null) {
            mSensorManager.requestTriggerSensor(moListener, mSigMotion);
        }
    }

    protected void onPause() {
        super.onPause();
        // Call disable to ensure that the trigger request has been canceled.
        mSensorManager.cancelTriggerSensor(moListener, mSigMotion);
    }

    class TriggerEvntListener extends TriggerEventListener {
        public void onTrigger(TriggerEvent event) {
            // Do Work.

            // As it is a one shot sensor, it will be canceled automatically.
            // SensorManager.requestTriggerSensor(this, mSigMotion); needs to
            // be called again, if needed.

            Toast.makeText(getApplicationContext(), "Significant Motion Detected",Toast.LENGTH_SHORT).show();
            // As it is a one shot sensor, it will be canceled automatically.
            // SensorManager.requestTriggerSensor(this, mSigMotion); needs to
            // be called again, if needed.
            //long currentTimeStamp = System.currentTimeMillis();
            //tv.setText("Last movement triggered at "+String.valueOf(currentTimeStamp));

            //Log.e("sigmotiion","Last movement triggered at "+String.valueOf(currentTimeStamp));
            //sensorReadingTV.setText("Last movement triggered at "+String.valueOf(currentTimeStamp));
            //event.sensor.getVendor()

            sensorReadingTV.setText(" "+ event.values[0]);
            sensorDetailTV.setText(
                    "MaximumRange: " + event.sensor.getMaximumRange() +" "+
                            "\n Vendor: " + event.sensor.getVendor() +
                            "\n Version: " + event.sensor.getVersion()+
                            "\n Resolution: " + event.sensor.getResolution()+
                            "\n Power: " + event.sensor.getPower());
        }
    }
}

