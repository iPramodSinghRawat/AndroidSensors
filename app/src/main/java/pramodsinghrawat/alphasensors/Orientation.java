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

public class Orientation extends SuperDrawerActivity implements SensorEventListener {

    DecimalFormat decimalFormat;
    float[] mGravity;
    float[] mGeomagnetic;
    private SensorManager mSensorManager;
    private Sensor senMagneticField, senGravity, senAccelerometer;
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

        decimalFormat = new DecimalFormat("###.###");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        decimalFormat.setMaximumFractionDigits(3);
        decimalFormat.setMinimumFractionDigits(3);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        processHideDrawerItems(mSensorManager,this);
        senMagneticField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        senGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        //senAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorDescriptionTV.setText("Measures degrees of rotation that a device makes around all three physical axes (x, y, z).");

    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
/*
        //if sensor is unreliable, return void
        if (sensorEvent.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
        {
            return;
        }
*/

        if(mySensor.getType() == Sensor.TYPE_GRAVITY){
            mGravity = sensorEvent.values;
        }

        if (mySensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            mGeomagnetic = sensorEvent.values;
        }

        if (mGravity != null && mGeomagnetic != null) {
            orientationDat();
        }
    }

    public void orientationDat(){

        float azimuthInDegress,azimuth,pitch,roll;  // View to draw a compass

        float R[] = new float[9];
        float I[] = new float[9];
        boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
        if (success) {
            float orientation[] = new float[3];
            SensorManager.getOrientation(R, orientation);
            azimuth = orientation[0]; // orientation contains: azimut, pitch and roll
            pitch = orientation[1];
            roll = orientation[2];

            azimuthInDegress = (float)(Math.toDegrees(azimuth)+360)%360;

            sensorReadingTV.setText(
                            "Direction:" + getDirection(azimuthInDegress)+
                            "\n azimuth(Degree)= " + decimalFormat.format(azimuthInDegress) + " °" +
                            "\n azimuth(Radians)= " + decimalFormat.format(azimuth) + " rad" +
                            "\n pitch= " + decimalFormat.format(pitch) + " °" +
                            "\n roll= " + decimalFormat.format(roll) +" °");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, senMagneticField, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, senGravity, SensorManager.SENSOR_DELAY_NORMAL);
        //mSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
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

    public String getDirection(double x){
        /*
        String directions[] = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
        return directions[ (int)Math.round((  ((double)x % 360) / 45)) % 8 ];
        */
        String directions[] = {"N","NNE","NE","ENE","E","ESE","SE","SSE","S","SSW","SW","WSW","W","WNW","NW","NNW"};
        return directions[(int) Math.round(((x % 360) / 22.5)) % 16];
    }
}