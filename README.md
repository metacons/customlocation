customlocation
==============

Android Custom Location Finder API

API was developed to find easily location in Android development.

To use API:

Create constructor (choose any of them regarding to your project)

1-) Let the API finds location in 15seconds using both GPS and WiFi

     CustomLocation(Context cnx, LocationFoundListener locFound)
     
2-) Send a criteria then API will try to find using criteria

    CustomLocation(Context cnx, Criteria cri, LocationFoundListener locFound)
    
3-) Send enum variable (use only Wifi or GPS) and API will try to find location using only required type.

    CustomLocation(Context cnx, LOC_TYPE useOnly,LocationFoundListener locFound)
    
Then call method:
    
    startAPI();
    
API try to find location in 15seconds if you would like to set timeout you may use method:

  obj.setTimeout(int timeout) 
