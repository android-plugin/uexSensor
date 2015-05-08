package org.zywx.wbpalmstar.plugin.uexsensor;

import org.zywx.wbpalmstar.engine.EBrowserView;
import org.zywx.wbpalmstar.engine.universalex.EUExBase;
import org.zywx.wbpalmstar.engine.universalex.EUExCallback;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


public class EUExSensor extends EUExBase {
	
	public static final String onFunction_AccelerometerChange = "uexSensor.onAccelerometerChange";
	public static final String onFunction_OrientationChange = "uexSensor.onOrientationChange";
	public static final String onFunction_MagneticChange = "uexSensor.onMagneticChange";
	public static final String onFunction_TemperatureChange = "uexSensor.onTemperatureChange";
	public static final String onFunction_PressureChange = "uexSensor.onPressureChange";
	public static final String onFunction_LightChange = "uexSensor.onLightChange";
	
	
	public static final int F_SENSOR_TYPE_ACCELEROMETER				= 1;
	public static final int F_SENSOR_TYPE_ORIENTATION				= 2;
	public static final int F_SENSOR_TYPE_MAGNETIC_FIELD 			= 3;
	public static final int F_SENSOR_TYPE_TEMPERATURE  				= 4;
	public static final int F_SENSOR_TYPE_PRESSURE   				= 5;
	public static final int F_SENSOR_TYPE_LIGHT 	   				= 6;
	
	public static final int F_SENSOR_RATE_FASTEST					= 0;
	public static final int F_SENSOR_RATE_GAME						= 1;
	public static final int F_SENSOR_RATE_UI						= 2;
	public static final int F_SENSOR_RATE_NORMAL					= 3;	
	
	private SensorManager mSensorMgr;
	private SensorEventListener mAccelerometerListener;
	private SensorEventListener mOrientationListener;
	private SensorEventListener mMagneticListener;
	private SensorEventListener mTemperatureListener;
	private SensorEventListener mPressureListener;
	private SensorEventListener mLightListener;
	
	public EUExSensor(Context context, EBrowserView inParent) {
		super(context, inParent);
	}
	
	public void open(String[] parm){
		if(parm.length < 0){
			return;
		}
		String inType = parm[0];
		String inRate = parm[1];
		mSensorMgr = (SensorManager)mContext.getSystemService(Context.SENSOR_SERVICE);
		if(null == mSensorMgr){
			return;
		}
		int type = 0;
		int rate = 0;
		try{
			type = Integer.valueOf(inType);
			rate = Integer.valueOf(inRate);
		}catch (Exception e) {
			errorCallback(0, EUExCallback.F_E_UEXSENSOR_OPEN, "Illegal parameter");
			return;
		}
		switch(type) {
		case F_SENSOR_TYPE_ACCELEROMETER:
			Sensor acc = mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			if(null == acc){
				return;
			}
			if(null == mAccelerometerListener){
				mAccelerometerListener = new AccelerometerListener();
			}
			mSensorMgr.registerListener(mAccelerometerListener, acc, rate);
			break;
		case F_SENSOR_TYPE_ORIENTATION:
			Sensor ori = mSensorMgr.getDefaultSensor(Sensor.TYPE_ORIENTATION);
			if(null == ori){
				return;
			}
			if(null == mOrientationListener){
				mOrientationListener = new OrientationListener();
			}
			mSensorMgr.registerListener(mOrientationListener, ori, rate);
			break;
		case F_SENSOR_TYPE_MAGNETIC_FIELD:
			Sensor mag = mSensorMgr.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
			if(null == mag){
				return;
			}
			if(null == mMagneticListener){
				mMagneticListener = new MagneticListener();
			}
			mSensorMgr.registerListener(mMagneticListener, mag, rate);
			break;
		case F_SENSOR_TYPE_TEMPERATURE:
			Sensor tem = mSensorMgr.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
			if(null == tem){
				return;
			}
			if(null == mTemperatureListener){
				mTemperatureListener = new TemperatureListener();
			}
			mSensorMgr.registerListener(mTemperatureListener, tem, rate);
			break;
		case F_SENSOR_TYPE_PRESSURE:
			Sensor pre = mSensorMgr.getDefaultSensor(Sensor.TYPE_PRESSURE);
			if(null == pre){
				return;
			}
			if(null == mPressureListener){
				mPressureListener = new PressureListener();
			}
			mSensorMgr.registerListener(mPressureListener, pre, rate);
			break;
		case F_SENSOR_TYPE_LIGHT:
			Sensor lig = mSensorMgr.getDefaultSensor(Sensor.TYPE_LIGHT);
			if(null == lig){
				return;
			}
			if(null == mLightListener){
				mLightListener = new LightListener();
			}
			mSensorMgr.registerListener(mLightListener, lig, rate);
			break;
		}
		
	}
	
	public void close(String[] parm){
		if(null == mSensorMgr){
			return;
		}
		String inType = parm[0];
		int type = 0;
		try{
			type = Integer.valueOf(inType);
		}catch (Exception e) {
			errorCallback(0, EUExCallback.F_E_UEXSENSOR_CLOSE, "Illegal parameter");
			return;
		}
		switch(type) {
		case F_SENSOR_TYPE_ACCELEROMETER:
			mSensorMgr.unregisterListener(mAccelerometerListener);
			break;
		case F_SENSOR_TYPE_ORIENTATION:
			mSensorMgr.unregisterListener(mOrientationListener);
			break;
		case F_SENSOR_TYPE_MAGNETIC_FIELD:
			mSensorMgr.unregisterListener(mMagneticListener);
			break;
		case F_SENSOR_TYPE_TEMPERATURE:
			mSensorMgr.unregisterListener(mTemperatureListener);
			break;
		case F_SENSOR_TYPE_PRESSURE:
			mSensorMgr.unregisterListener(mPressureListener);
			break;
		case F_SENSOR_TYPE_LIGHT:
			mSensorMgr.unregisterListener(mLightListener);
			break;
		}
	}

	@Override
	protected boolean clean() {
		if(null != mSensorMgr){
			mSensorMgr.unregisterListener(mAccelerometerListener);
			mSensorMgr.unregisterListener(mOrientationListener);
			mSensorMgr.unregisterListener(mMagneticListener);
			mSensorMgr.unregisterListener(mTemperatureListener);
			mSensorMgr.unregisterListener(mPressureListener);
			mSensorMgr.unregisterListener(mLightListener);
			mSensorMgr = null;
		}
		return true;
	}
	
	class AccelerometerListener implements SensorEventListener{
		public void onSensorChanged(SensorEvent event) {
			String js = SCRIPT_HEADER + "if(" + onFunction_AccelerometerChange + "){" + onFunction_AccelerometerChange + "(" + (event.values[0] * -1) + "," + (event.values[1] * -1) + "," + (event.values[2] * -1) + ");}";
			onCallback(js);
		}
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			;
		}
	}
	
	class OrientationListener implements SensorEventListener{
		public void onSensorChanged(SensorEvent event) {
			String js = SCRIPT_HEADER + "if(" + onFunction_OrientationChange + "){" + onFunction_OrientationChange + "(" + event.values[0] + "," + event.values[1] + "," + event.values[2] + ");}";
			onCallback(js);
		}
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			;
		}
	}
	
	class MagneticListener implements SensorEventListener{
		public void onSensorChanged(SensorEvent event) {
			String js = SCRIPT_HEADER + "if(" + onFunction_MagneticChange + "){" + onFunction_MagneticChange + "(" + event.values[0] + "," + event.values[1] + "," + event.values[2] + ");}";
			onCallback(js);
		}
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			;
		}
	}
	
	class TemperatureListener implements SensorEventListener{
		public void onSensorChanged(SensorEvent event) {
			String js = SCRIPT_HEADER + "if(" + onFunction_TemperatureChange + "){" + onFunction_TemperatureChange + "(" + event.values[0] + ");}";
			onCallback(js);
		}
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			;
		}
	}
	
	class PressureListener implements SensorEventListener{
		public void onSensorChanged(SensorEvent event) {
			String js = SCRIPT_HEADER + "if(" + onFunction_PressureChange + "){" + onFunction_PressureChange + "(" + event.values[0] + ");}";
			onCallback(js);
		}
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			;
		}
	}
	
	class LightListener implements SensorEventListener{
		public void onSensorChanged(SensorEvent event) {
			String js = SCRIPT_HEADER + "if(" + onFunction_LightChange + "){" + onFunction_LightChange + "(" + event.values[0] + ");}";
			onCallback(js);
		}
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			;
		}
	}
	
}
