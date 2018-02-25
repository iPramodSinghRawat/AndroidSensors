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

public class GyroscopeUncalibrated extends SuperDrawerActivity implements SensorEventListener {

    DecimalFormat decimalFormat;
    private SensorManager mSensorManager;
    private Sensor senGyroscopeUncalibrated;
    private TextView sensorReadingTV,sensorDetailTV,sensorDescriptionTV;

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

        decimalFormat = new DecimalFormat("##.######");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        decimalFormat.setMaximumFractionDigits(4);
        decimalFormat.setMinimumFractionDigits(4);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        processHideDrawerItems(mSensorManager,this);
        senGyroscopeUncalibrated = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);

        sensorDescriptionTV.setText("Similar to TYPE_GYROSCOPE but no gyro-drift compensation has " +
                "been performed to adjust the given sensor values. However, such gyro-drift bias " +
                "values are returned to you separately in the result values so you may use them " +
                "for custom calibrations. ");
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
