package com.metacons.customlocation;

import android.location.Location;
import android.os.Message;

public interface LocationFoundListener {

	public void LocationFound(Location loc, Message msg);
}
