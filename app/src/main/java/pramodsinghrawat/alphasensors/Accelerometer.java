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

public class Accelerometer extends SuperDrawerActivity implements SensorEventListener {

    private static final int SHAKE_THRESHOLD = 600;
    DecimalFormat decimalFormat;
    private SensorManager mSensorManager;
    private Sensor senAccelerometer;
    private TextView sensorReadingTV,sensorDetailTV,sensorDescriptionTV;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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
        senAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorDescriptionTV.setText("Measures the acceleration force in m/s^2 that is applied to a device on all three physical axes (x, y, and z), including the force of gravity.");
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
