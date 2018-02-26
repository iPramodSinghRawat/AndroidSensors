package ipramodsinghrawat.it.androidwearalpha;

import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;

/**
 * Created by Alpinesoft on 25/02/18.
 */

class TriggerListener extends TriggerEventListener {
    public void onTrigger(TriggerEvent event) {
        // Do Work.

        // As it is a one shot sensor, it will be canceled automatically.
        // SensorManager.requestTriggerSensor(this, mSigMotion); needs to
        // be called again, if needed.
    }
}
