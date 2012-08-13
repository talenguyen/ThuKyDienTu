package myapp.thukydientu.service;

import java.util.Timer;
import java.util.TimerTask;

import myapp.thukydientu.util.TimeUtils;
import myapp.thukydientu.util.WebservicesUtils;
import myapp.thukydientu.view.MainActivity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

public class ShareLocationService extends Service implements LocationListener {

	private LocationManager mLocationManager;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public ShareLocationService(Context context, int minute) {
		mLocationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
		Timer timer = new Timer();
		if (minute > 0) {
			timer.schedule(shareLocationLoop, 0, minute * 60 * 1000);
		} else {
			updateLocation();
			timer.cancel();
		}
	}

	final Handler handler = new Handler();
	
	TimerTask shareLocationLoop = new TimerTask() {
		@Override
		public void run() {
			handler.post(new Runnable() {
				@Override
				public void run() {
					updateLocation();
				}
			});
		}
	};
	
	private void updateLocation() {
		mLocationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 0, 0, ShareLocationService.this);
		Location location = mLocationManager
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		final double latitude = location.getLatitude();
		final double longitude = location.getLongitude();
		WebservicesUtils.addLocation(MainActivity.sUserId, latitude, longitude,
				false, TimeUtils.convert2String14(System.currentTimeMillis()));
	}
	@Override
	public void onLocationChanged(Location location) {
		mLocationManager.removeUpdates(this);
	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

}
