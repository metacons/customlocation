package com.metacons.customlocation;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements LocationFoundListener {

	TextView txt=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txt = (TextView) findViewById(R.id.txt);
		CustomLocation c = new CustomLocation(this, this);
		c.startAPI();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void LocationFound(Location loc, Message msg) {
		Log.e("Location found", "" + msg.what);
		txt.setText(""+msg.obj);
		Toast.makeText(this, "" + msg.obj, Toast.LENGTH_LONG).show();
		if (msg.what == MessageCodes.STAT_OK) {

		}
	}

}
