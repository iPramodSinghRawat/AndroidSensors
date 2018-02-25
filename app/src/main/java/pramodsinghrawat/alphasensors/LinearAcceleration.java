package pramodsinghrawat.alphasensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class LinearAcceleration extends SuperDrawerActivity implements SensorEventListener{

    DecimalFormat decimalFormat;
    float[] linear_acceleration;
    float[] gravity;
    private SensorManager mSensorManager;
    private Sensor senLinearAcceleration;
    private TextView sensorReadingTV, sensorDetailTV, sensorDescriptionTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.setDrawerListener(toggle);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        sensorReadingTV = findViewById(R.id.sensorReadingTV);
        sensorDetailTV = findViewById(R.id.sensorDetailTV);
        sensorDescriptionTV = findViewById(R.id.sensorDescriptionTV);

        decimalFormat = new DecimalFormat("##.####");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        decimalFormat.setMaximumFractionDigits(4);
        decimalFormat.setMinimumFractionDigits(4);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        processHideDrawerItems(mSensorManager,this);
        senLinearAcceleration = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorDescriptionTV.setText("Measures the acceleration force in m/s^2 that is applied to a device on all three physical axes (x, y, and z), excluding the force of gravity.");
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
