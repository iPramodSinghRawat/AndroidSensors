package ipramodsinghrawat.it.androidwearalpha;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wear.widget.WearableLinearLayoutManager;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import android.support.wear.widget.WearableRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends WearableActivity implements MenuAdapter.ItemSelectedListener {

    WearableRecyclerView mWearableRecyclerView;// = (WearableRecyclerView) findViewById(R.id.recycler_launcher_view);

    private ArrayList<MenuItemModel> menuItemModels;

    private SensorManager mSensorManager;

    TextView sensorCounterTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Enables Always-on
        setAmbientEnabled();

        sensorCounterTV = (TextView) findViewById(R.id.sensorCounterTV);
        mWearableRecyclerView = (WearableRecyclerView) findViewById(R.id.recycler_launcher_view);

        menuItemModels = new ArrayList<>();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        final List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        //sensorCounterTV.setText(deviceSensors.size() + " Sensors");
        for(Sensor type : deviceSensors){

            //Log.e("senType", String.valueOf(type));

            //Log.e("sensors",type.getStringType());
            //Log.e("sensorsNAME",type.getName());
            //Log.e("sensorsType", String.valueOf(type.getType()));
            Log.e("sensorToString",type.toString());
        }

        /*
            DN:android.sensor.accelerometer: {Sensor name="Accelerometer Sensor", vendor="Motorola", version=1, type=1, maxRange=78.48, resolution=0.009810001, power=0.45, minDelay=20000}
            DN:android.sensor.step_counter: {Sensor name="Step Counter Sensor", vendor="Motorola", version=1, type=19, maxRange=65535.0, resolution=1.0, power=0.45, minDelay=0}
            DN:android.sensor.gyroscope: {Sensor name="Gyro Sensor", vendor="Motorola", version=1, type=4, maxRange=34.9, resolution=0.01, power=0.45, minDelay=20000}
            DN:android.sensor.light: {Sensor name="Light Sensor", vendor="Motorola", version=1, type=5, maxRange=65535.0, resolution=1.0, power=0.45, minDelay=0}
            DN:android.sensor.gravity: {Sensor name="Gravity Sensor", vendor="Motorola", version=1, type=9, maxRange=78.48, resolution=0.009810001, power=0.45, minDelay=40000}
            DN:android.sensor.linear_acceleration: {Sensor name="Linear Acceleration Sensor", vendor="Motorola", version=1, type=10, maxRange=78.48, resolution=0.009810001, power=0.45, minDelay=40000}

            DN:android.sensor.game_rotation_vector: {Sensor name="Game Rotation Vector Sensor", vendor="Motorola", version=1, type=15, maxRange=78.48, resolution=0.009810001, power=0.45, minDelay=40000}
            DN:android.sensor.significant_motion: {Sensor name="Significant Motion Sensor", vendor="Motorola", version=1, type=17, maxRange=1.0, resolution=1.0, power=0.45, minDelay=-1}

            android.sensor.wrist_tilt_gesture: {Sensor name="Wrist Tilt Sensor", vendor="Motorola", version=1, type=26, maxRange=1.0, resolution=1.0, power=0.45, minDelay=0}

            remain not listed:
            {Sensor name="Detailed Step Counter Sensor", vendor="Motorola", version=1, type=65537, maxRange=65535.0, resolution=1.0, power=0.45, minDelay=1000000}
            {Sensor name="Wellness Passive Sensor", vendor="Motorola", version=1, type=65538, maxRange=65535.0, resolution=1.0, power=0.45, minDelay=0}
            {Sensor name="User Profile Sensor", vendor="Motorola", version=1, type=65539, maxRange=0.0, resolution=0.0, power=0.45, minDelay=0}

        */

        // To align the edge children (first and last) with the center of the screen
        mWearableRecyclerView.setEdgeItemsCenteringEnabled(true);

        // Improves performance because we know changes in content do not change the layout size of
        // the RecyclerView.
        mWearableRecyclerView.setHasFixedSize(true);

        List<Sensor> sensorList;// = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensorList.size() > 0) {
            menuItemModels.add(new MenuItemModel(R.string.activity_sensor_accelerometer,
                    R.drawable.icon_accelerometer, new Intent(this, AccelerometerActivity.class)));
        }

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if (sensorList.size() > 0) {
            menuItemModels.add(new MenuItemModel(R.string.activity_sensor_ambient_temperature,
                    R.drawable.icon_ambient_temperature, new Intent(this, AmbientTemperatureActivity.class)));
        }

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_GRAVITY);
        Log.e("mSensCount", String.valueOf(sensorList.size()));
        if (sensorList.size() > 0) {
            menuItemModels.add(new MenuItemModel(R.string.activity_sensor_gravity,
                    R.drawable.icon_gravity, new Intent(this, GravityActivity.class)));

            /*Note: to be use other sensor type not working in android wear */
 /*           menuItemModels.add(new MenuItemModel(R.string.activity_sensor_orientation,
                    R.drawable.icon_orientation, new Intent(MainActivity.this, OrientationActivity.class)));
*/
            sensorList = mSensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
            Log.e("mSensCount", String.valueOf(sensorList.size()));

            if (sensorList.size() > 0) {
                menuItemModels.add(new MenuItemModel(R.string.activity_sensor_orientation,
                        R.drawable.icon_orientation, new Intent(this, MagneticFieldActivity.class)));
            }
        }

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_GYROSCOPE);
        Log.e("gyro", String.valueOf(sensorList.size()));
        if (sensorList.size() > 0) {
            menuItemModels.add(new MenuItemModel(R.string.activity_sensor_gyroscope,
                    R.drawable.icon_gyroscope, new Intent(this, GyroscopeActivity.class)));
        }

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_LIGHT);
        Log.e("light sens", String.valueOf(sensorList.size()));
        if (sensorList.size() > 0) {
            //nav_Menu.findItem(R.id.nav_light).setVisible(false);
            menuItemModels.add(new MenuItemModel(R.string.activity_sensor_light,
                    R.drawable.icon_light, new Intent(this, LightActivity.class)));
        }

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_LINEAR_ACCELERATION);
        Log.e("lAcc", String.valueOf(sensorList.size()));
        if (sensorList.size() > 0) {
            menuItemModels.add(new MenuItemModel(R.string.activity_linear_accelerometer,
                    R.drawable.icon_linear_accelerometer, new Intent(this, LinearAccelerationActivity.class)));
        }

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
        Log.e("mg fld", String.valueOf(sensorList.size()));
        if (sensorList.size() > 0) {
            menuItemModels.add(new MenuItemModel(R.string.activity_sensor_magnetic_field,
                    R.drawable.icon_magnetic_field, new Intent(this, MagneticFieldActivity.class)));

            sensorList = mSensorManager.getSensorList(Sensor.TYPE_GRAVITY);
            if (sensorList.size() > 0) {
                menuItemModels.add(new MenuItemModel(R.string.activity_sensor_orientation,
                        R.drawable.icon_orientation, new Intent(this, GravityActivity.class)));
            }
        }

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_PRESSURE);
        Log.e("presr", String.valueOf(sensorList.size()));
        if (sensorList.size() > 0) {
            menuItemModels.add(new MenuItemModel(R.string.activity_sensor_pressure,
                    R.drawable.icon_pressure, new Intent(this, PressureActivity.class)));
        }

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_PROXIMITY);
        Log.e("prxmty sens", String.valueOf(sensorList.size()));
        if (sensorList.size() > 0) {
            menuItemModels.add(new MenuItemModel(R.string.activity_sensor_proximity,
                    R.drawable.icon_proximity, new Intent(this, ProximityActivity.class)));
        }

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_RELATIVE_HUMIDITY);
        Log.e("light hmdt", String.valueOf(sensorList.size()));
        if (sensorList.size() > 0) {
            menuItemModels.add(new MenuItemModel(R.string.activity_sensor_relative_humidity,
                    R.drawable.icon_relative_humidity, new Intent(this, RelativeHumidityActivity.class)));
        }

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_ROTATION_VECTOR);
        Log.e("rtvctr", String.valueOf(sensorList.size()));
        if (sensorList.size() > 0) {
            menuItemModels.add(new MenuItemModel(R.string.activity_sensor_rotation_vector,
                    R.drawable.icon_rotation_vector, new Intent(this, RotationVectorActivity.class)));
        }

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED);
        Log.e("m fld u", String.valueOf(sensorList.size()));
        if (sensorList.size() > 0) {
            menuItemModels.add(new MenuItemModel(R.string.activity_sensor_magnetic_field_uncalibrated,
                    R.drawable.icon_magnetic_field_u, new Intent(this, MagneticFieldActivity.class)));
        }

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
        Log.e("gyro u", String.valueOf(sensorList.size()));
        if (sensorList.size() > 0) {
            menuItemModels.add(new MenuItemModel(R.string.activity_sensor_gyroscope_uncalibrated,
                    R.drawable.icon_gyroscope_u, new Intent(this, GyroscopeUncalibratedActivity.class)));
        }

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_STEP_COUNTER);
        Log.e("step c sens", String.valueOf(sensorList.size()));
        if (sensorList.size() > 0) {
            menuItemModels.add(new MenuItemModel(R.string.activity_sensor_step_counter,
                    R.drawable.icon_step_counter, new Intent(this, StepCounterActivity.class)));
        }

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_GAME_ROTATION_VECTOR);
        Log.e("rgrscc", String.valueOf(sensorList.size()));
        if (sensorList.size() > 0) {
            menuItemModels.add(new MenuItemModel(R.string.activity_game_rotation_vector,
                    R.drawable.icon_linear_accelerometer, new Intent(this, GameRotationVectorActivity.class)));
        }

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_SIGNIFICANT_MOTION);
        Log.e("significant_motion", String.valueOf(sensorList.size()));
        if (sensorList.size() > 0) {
            menuItemModels.add(new MenuItemModel(R.string.activity_significant_motion,
                    R.drawable.icon_gyroscope, new Intent(this, SignificantMotionActivity.class)));
        }

        /* Not Needed Right Now
        sensorList = mSensorManager.getSensorList(26);//TYPE_WRIST_TILT_GESTURE
        Log.e("significant_motion", String.valueOf(sensorList.size()));
        if (sensorList.size() > 0) {
            menuItemModels.add(new MenuItemModel(R.string.activity_significant_motion,
                    R.drawable.icon_gyroscope, new Intent(this, SignificantMotionActivity.class)));
        }
        */

        //*/
        CustomScrollingLayoutCallback customScrollingLayoutCallback =
                new CustomScrollingLayoutCallback();
        mWearableRecyclerView.setLayoutManager(
                new WearableLinearLayoutManager(this, customScrollingLayoutCallback));

        MenuAdapter menuAdapter = new MenuAdapter(MainActivity.this, menuItemModels);
        mWearableRecyclerView.setAdapter(menuAdapter);

        menuAdapter.setListener(this);
        //checkForFirstTimeOpen();

    }

    @Override
    //@AddTrace(name = "onItemSelected")
    public void onItemSelected(int position) {
        startActivity(menuItemModels.get(position).getActivity());
    }

    private void checkForFirstTimeOpen() {
        /*FirstTimeRunChecker firstTimeRunChecker = new FirstTimeRunChecker(this);
        if (firstTimeRunChecker.firstEverOpen()) {
            analytics.openedAppForFirstTime(false, false);
        }
        firstTimeRunChecker.updateSharedPrefWithCurrentVersion();
        */
    }

    public static String sensorTypeToString(int sensorType) {
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                return "Accelerometer";
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                return "Ambient Temperature";
            case Sensor.TYPE_GAME_ROTATION_VECTOR:
                return "Game Rotation Vector";
            case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
                return "Geomagnetic Rotation Vector";
            case Sensor.TYPE_GRAVITY:
                return "Gravity";
            case Sensor.TYPE_GYROSCOPE:
                return "Gyroscope";
            case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                return "Gyroscope Uncalibrated";
            case Sensor.TYPE_HEART_RATE:
                return "Heart Rate Monitor";
            case Sensor.TYPE_LIGHT:
                return "Light";
            case Sensor.TYPE_LINEAR_ACCELERATION:
                return "Linear Acceleration";
            case Sensor.TYPE_MAGNETIC_FIELD:
                return "Magnetic Field";
            case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                return "Magnetic Field Uncalibrated";
            case Sensor.TYPE_PRESSURE:
                return "Pressure";
            case Sensor.TYPE_PROXIMITY:
                return "Proximity";
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                return "Relative Humidity";
            case Sensor.TYPE_ROTATION_VECTOR:
                return "Rotation Vector";
            case Sensor.TYPE_SIGNIFICANT_MOTION:
                return "Significant Motion";
            case Sensor.TYPE_STEP_COUNTER:
                return "Step Counter";
            case Sensor.TYPE_STEP_DETECTOR:
                return "Step Detector";
            default:
                return "Unknown";
        }
    }
}
