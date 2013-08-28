package com.metacons.customlocation;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class CustomLocation implements LocationListener {

	LocationFoundListener locFoundLis = null;
	private int timeout = 7;
	Criteria criteria = null;
	boolean useOnlyGPS = false;
	boolean useOnlyNetwork = false;
	private final String LOG_TAG = "Custom Location";
	int selectedConstructor = 0;
	private int counter = 0;

	LocationManager locman = null;
	Context context = null;
	private Location loc = null;

	// 0
	public CustomLocation(Context cnx, LocationFoundListener locFound) {
		this.context = cnx;
		this.locFoundLis = locFound;
		selectedConstructor = 0;
	}

	// 1
	public CustomLocation(Context cnx, Criteria cri) {
		this.context = cnx;
		this.criteria = cri;
		selectedConstructor = 1;
	}

	// 2
	public CustomLocation(Context cnx, boolean useOnlyGPS,
			boolean useOnlyNetwork) {
		this.context = cnx;
		this.useOnlyGPS = useOnlyGPS;
		this.useOnlyNetwork = useOnlyNetwork;
		selectedConstructor = 2;
	}

	public void startAPI() {
		switch (selectedConstructor) {
		case 0:
			startRegular();
			break;
		case 1:
			startWithCriteria();
			break;
		case 2:
			startWithBooleanValues();
			break;
		default:
			break;
		}

	}

	private void startRegular() {
		locman = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		locman.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		handler.postDelayed(runnable, 100);
	}

	private void startWithCriteria() {
		locman = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		locman.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		handler.postDelayed(runnable, 100);

	}

	private void startWithBooleanValues() {

	}

	private void stopListener() {
		locman.removeUpdates(this);
		handler.removeCallbacks(runnable);
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	@Override
	public void onLocationChanged(Location location) {
		this.loc = location;
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

	/**************************************************/
	/***** CASE 1- HANDLER 1 *****/
	/**************************************************/
	private Handler handler = new Handler() {

	};

	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			try {

				if (loc != null) {
					// location was found
					Message msg = new Message();
					msg.what = MessageCodes.STAT_OK;
					msg.obj = "Location was found";
					stopListener();
					locFoundLis.LocationFound(loc, msg);
				} else {
					counter++;
					if (counter == timeout) {
						locman = (LocationManager) context
								.getSystemService(Context.LOCATION_SERVICE);
						locman.requestLocationUpdates(
								LocationManager.NETWORK_PROVIDER, 0, 0,
								CustomLocation.this);
						Log.d(LOG_TAG, "provider was changed");
						Log.d(LOG_TAG, "provider was changed");
						handler.postDelayed(this, 2000);
					} else if (counter == timeout * 2) {
						locman.removeUpdates(CustomLocation.this);
						
						stopListener();
						Location locationLastKnown = locman
								.getLastKnownLocation(LocationManager.GPS_PROVIDER);
						Message msg = new Message();
						if(locationLastKnown != null) {
							msg.what = MessageCodes.STAT_LASKNOWLOCATION;
							msg.obj = "Last Know Location was sent";
						}else {
							msg.what = MessageCodes.STAT_NOTFOUND;
							msg.obj = "Location not found";
							Log.d(LOG_TAG, "Location not found");
						}
						locFoundLis.LocationFound(loc, msg);
						
						
						
						
					} else {
						handler.postDelayed(this, 2000);
					}

				}
			} catch (Exception ex) {
				Message msg = new Message();
				msg.what = MessageCodes.STAT_UNKNOWNERROR;
				msg.obj = ex.getMessage();
				stopListener();
				locFoundLis.LocationFound(loc, msg);
			}

		}

	};
	/**************************************************/
}
