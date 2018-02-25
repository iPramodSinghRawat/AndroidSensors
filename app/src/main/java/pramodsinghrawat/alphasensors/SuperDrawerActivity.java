package pramodsinghrawat.alphasensors;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

/**
 * Created by Pramod Singh Rawat on 12-07-2016.
 */
public class SuperDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    /*
    * TYPE_ACCELEROMETER: Accelerometer
    * TYPE_AMBIENT_TEMPERATURE: AmbientTemperature
    * TYPE_GRAVITY: Gravity
    * TYPE_GYROSCOPE: Gyroscope
    * TYPE_LIGHT: Light
    * TYPE_LINEAR_ACCELERATION: LinearAcceleration
    * TYPE_MAGNETIC_FIELD: MagneticField
    * TYPE_ORIENTATION: Orientation
    * TYPE_PRESSURE: Pressure
    * TYPE_PROXIMITY: Proximity
    * TYPE_RELATIVE_HUMIDITY: RelativeHumidity
    * TYPE_ROTATION_VECTOR: RotationVector
    * TYPE_TEMPERATURE: Temperature(replaced with the TYPE_AMBIENT_TEMPERATURE sensor in API Level 14)
    ********
    * TYPE_MAGNETIC_FIELD_UNCALIBRATED: MagneticFieldUncalibrated
    * TYPE_GYROSCOPE_UNCALIBRATED: GyroscopeUncalibrated
    * TYPE_STEP_COUNTER: StepCounter
    */

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            String actvityName=this.getClass().getSimpleName();
            if(actvityName.equals("MainActivity")){
                finish();
                exitApp();
                finish();
            }else{
                finish();
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }
    }
    public void exitApp(){
        finish();
        Intent intent=new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            go2Setting();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_accelerometer) {
            go2Accelerometer();
            // Handle the camera action
        } else if (id == R.id.nav_ambient_temperature) {//AmbientTemperature
            go2AmbientTemperature();
        } else if (id == R.id.nav_gravity) {
            go2Gravity();
        } else if (id == R.id.nav_gyroscope) {
            go2Gyroscope();
        } else if (id == R.id.nav_light) {
            go2Light();
        } else if (id == R.id.nav_linear_accelerometer) {
            go2LinearAccelerometer();
        } else if (id == R.id.nav_magnetic_field) {
            go2MagneticField();
        } else if (id == R.id.nav_orientation) {
            go2Orientation();
        } else if (id == R.id.nav_pressure) {
            go2Pressure();
        } else if (id == R.id.nav_proximity) {
            go2Proximity();
        } else if (id == R.id.nav_relative_humidity) {
            go2RelativeHumidity();
        } else if (id == R.id.nav_rotation_vector) {
            go2RotationVector();
        } else if (id == R.id.nav_magnetic_field_uncalibrated) {
            go2MagneticFieldUncalibrated();
        } else if (id == R.id.nav_gyroscope_uncalibrated) {
            go2GyroscopeUncalibrated();
        } else if (id == R.id.nav_step_counter) {
            go2StepCounter();
        } else if (id == R.id.nav_exit) {
            exitApp();
        }/* else if (id == R.id.nav_setting) {
            go2Setting();
        }*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void go2Accelerometer(){
        finish();
        startActivity(new Intent(getApplicationContext(), Accelerometer.class));
        finish();
    }

    public void go2AmbientTemperature(){
        finish();
        startActivity(new Intent(getApplicationContext(), AmbientTemperature.class));
        finish();
    }

    public void go2Gravity(){
        finish();
        startActivity(new Intent(getApplicationContext(), Gravity.class));
        finish();
    }

    public void go2Gyroscope(){
        finish();
        startActivity(new Intent(getApplicationContext(), Gyroscope.class));
        finish();
    }

    public void go2Light(){
        finish();
        startActivity(new Intent(getApplicationContext(), Light.class));
        finish();
    }

    public void go2LinearAccelerometer(){
        finish();
        startActivity(new Intent(getApplicationContext(), LinearAcceleration.class));
        finish();
    }

    public void go2MagneticField(){
        finish();
        startActivity(new Intent(getApplicationContext(), MagneticField.class));
        finish();
    }

    public void go2Orientation(){
        finish();
        startActivity(new Intent(getApplicationContext(), Orientation.class));
        finish();
    }

    public void go2Proximity(){
        finish();
        startActivity(new Intent(getApplicationContext(), Proximity.class));
        finish();
    }

    public void go2Pressure(){
        finish();
        startActivity(new Intent(getApplicationContext(), Pressure.class));
        finish();
    }

    public void go2RotationVector(){
        finish();
        startActivity(new Intent(getApplicationContext(), RotationVector.class));
        finish();
    }

    public void go2RelativeHumidity(){
        finish();
        startActivity(new Intent(getApplicationContext(), RelativeHumidity.class));
        finish();
    }

    public void go2GyroscopeUncalibrated(){
        finish();
        startActivity(new Intent(getApplicationContext(), GyroscopeUncalibrated.class));
        finish();
    }

    public void go2MagneticFieldUncalibrated(){
        finish();
        startActivity(new Intent(getApplicationContext(), MagneticFieldUncalibrated.class));
        finish();
    }

    public void go2StepCounter(){
        finish();
        startActivity(new Intent(getApplicationContext(), StepCounter.class));
        finish();
    }

    public void go2Setting(){
       // finish();
       // startActivity(new Intent(getApplicationContext(), StepCounter.class));
       // finish();
    }

    public void processHideDrawerItems(SensorManager mSensorManager,Context context){
/*
       View namebar = (View)findViewById(R.id.nav_accelerometer);
        namebar.setVisibility(View.GONE);
*/
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        //nav_Menu.findItem(R.id.nav_accelerometer).setVisible(false);

        List<Sensor> sensorList;// = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        Log.e("sensor p: ", String.valueOf(sensorList));
        Log.e("sensor p: ", String.valueOf(sensorList.size()));

        /* if sensorList == null
            hide view
         */
        if (sensorList == null) {
            nav_Menu.findItem(R.id.nav_accelerometer).setVisible(false);
        }

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if (sensorList.size() <= 0) {
            nav_Menu.findItem(R.id.nav_ambient_temperature).setVisible(false);
        }

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_GRAVITY);
        if (sensorList.size() <= 0) {
            nav_Menu.findItem(R.id.nav_gravity).setVisible(false);
            sensorList = mSensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
            if (sensorList.size() <= 0) {
                nav_Menu.findItem(R.id.nav_orientation).setVisible(false);
            }
        }

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_GYROSCOPE);
        if (sensorList.size() <= 0) {
            nav_Menu.findItem(R.id.nav_gyroscope).setVisible(false);
        }

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_LIGHT);
        if (sensorList.size() <= 0) {
            nav_Menu.findItem(R.id.nav_light).setVisible(false);
        }

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_LINEAR_ACCELERATION);
        if (sensorList.size() <= 0) {
            nav_Menu.findItem(R.id.nav_linear_accelerometer).setVisible(false);
        }

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
        if (sensorList.size() <= 0) {
            nav_Menu.findItem(R.id.nav_magnetic_field).setVisible(false);

            sensorList = mSensorManager.getSensorList(Sensor.TYPE_GRAVITY);
            if (sensorList.size() <= 0) {
                nav_Menu.findItem(R.id.nav_orientation).setVisible(false);
            }
        }

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_PRESSURE);
        if (sensorList.size() <= 0) {
            nav_Menu.findItem(R.id.nav_pressure).setVisible(false);
        }

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_PROXIMITY);
        if (sensorList.size() <= 0) {
            nav_Menu.findItem(R.id.nav_proximity).setVisible(false);
        }

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_RELATIVE_HUMIDITY);
        if (sensorList.size() <= 0) {
            nav_Menu.findItem(R.id.nav_relative_humidity).setVisible(false);
        }

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_ROTATION_VECTOR);
        if (sensorList.size() <= 0) {
            nav_Menu.findItem(R.id.nav_rotation_vector).setVisible(false);
        }

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED);
        if (sensorList.size() <= 0) {
            nav_Menu.findItem(R.id.nav_magnetic_field_uncalibrated).setVisible(false);
        }

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
        if (sensorList.size() <= 0) {
            nav_Menu.findItem(R.id.nav_gyroscope_uncalibrated).setVisible(false);
        }

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_STEP_COUNTER);
        if (sensorList.size() <= 0) {
            nav_Menu.findItem(R.id.nav_step_counter).setVisible(false);
        }
    }
}
