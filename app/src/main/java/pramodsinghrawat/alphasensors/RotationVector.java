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

public class RotationVector extends SuperDrawerActivity implements SensorEventListener {

    DecimalFormat decimalFormat;
    private SensorManager mSensorManager;
    private Sensor senRotationVector;
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

        decimalFormat = new DecimalFormat("##.##");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setMinimumFractionDigits(2);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        processHideDrawerItems(mSensorManager,this);
        senRotationVector = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        sensorDescriptionTV.setText("Measures the orientation of a device by providing the three elements of the device's rotation vector.");

    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        if(mySensor.getType() == Sensor.TYPE_ROTATION_VECTOR){
            rotationVector(sensorEvent);
        }
    }

    public void rotationVector(SensorEvent sensorEvent){
        Sensor mySensor = sensorEvent.sensor;

        sensorReadingTV.setText(
                        "\n x*sin(θ/2)= " + decimalFormat.format(sensorEvent.values[0]) +
                        "\n y*sin(θ/2)= " + decimalFormat.format(sensorEvent.values[1]) +
                        "\n z*sin(θ/2)= " + decimalFormat.format(sensorEvent.values[2])  +
                        "\n cos(θ/2) = " + decimalFormat.format(sensorEvent.values[3]) +
                        "\n Estimated Heading Accuracy= " + decimalFormat.format(sensorEvent.values[4]) +" rad");


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
        mSensorManager.registerListener(this, senRotationVector, SensorManager.SENSOR_DELAY_NORMAL);
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