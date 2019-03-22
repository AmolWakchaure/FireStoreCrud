package com.example.firestorecrud.homepage

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.firestorecrud.R
import com.example.firestorecrud.authentication.activity.LoginActivity
import com.example.firestorecrud.other.Constants
import com.example.firestorecrud.other.F
import com.example.firestorecrud.other.MyApplication
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_home.*
import java.text.DateFormat
import java.util.*

class HomeActivity : AppCompatActivity() {


    // location last updated time
    private var mLastUpdateTime: String? = null

    // location updates interval - 10sec
    private val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000

    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS: Long = 5000

    private val REQUEST_CHECK_SETTINGS = 100


    // bunch of location related apis
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mSettingsClient: SettingsClient? = null
    private var mLocationRequest: LocationRequest? = null
    private var mLocationSettingsRequest: LocationSettingsRequest? = null
    private var mLocationCallback: LocationCallback? = null
    private var mCurrentLocation: Location? = null

    // boolean flag to toggle the ui
    private var mRequestingLocationUpdates: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar!!.hide()
       /* user_name_tv.setText(MyApplication.prefs.getString(Constants.U_USER_NAME,"0"))
        password_tv.setText(MyApplication.prefs.getString(Constants.U_PASWORD,"0"))
        user_id_tv.setText(MyApplication.prefs.getString(Constants.U_USER_ID,"0"))*/


        if (!permissionsGranted())
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 123)
        }
        //set click
        setClick()

        // initialize the necessary libraries
        init()

        // restore the values from saved instance state
        restoreValuesFromBundle(savedInstanceState)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                //doLocationAccessRelatedJob();
            } else {
                // User refused to grant permission. You can add AlertDialog here
                Toast.makeText(this, "You didn't give permission to access device location", Toast.LENGTH_LONG).show()
                //startInstalledAppDetailsActivity();
            }
        }
    }

    private fun permissionsGranted(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun restoreValuesFromBundle(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("is_requesting_updates")) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean("is_requesting_updates")
            }

            if (savedInstanceState.containsKey("last_known_location")) {
                mCurrentLocation = savedInstanceState.getParcelable("last_known_location")
            }

            if (savedInstanceState.containsKey("last_updated_on")) {
                mLastUpdateTime = savedInstanceState.getString("last_updated_on")
            }
        }

        updateLocationUI()
    }

    private fun init() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mSettingsClient = LocationServices.getSettingsClient(this)

        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                // location is received
                mCurrentLocation = locationResult!!.lastLocation
                mLastUpdateTime = DateFormat.getTimeInstance().format(Date())

                updateLocationUI()
            }
        }

        mRequestingLocationUpdates = false

        mLocationRequest = LocationRequest()
        mLocationRequest!!.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS)
        mLocationRequest!!.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS)
        mLocationRequest!!.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest!!)
        mLocationSettingsRequest = builder.build()
    }

    private fun updateLocationUI() {
        if (mCurrentLocation != null) {
            location_result.setText(
                    "Lat: " + mCurrentLocation!!.getLatitude() + ", " + "Lng: " + mCurrentLocation!!.getLongitude()
            )

            // giving a blink animation on TextView
            location_result.setAlpha(0f)
            location_result.animate().alpha(1f).setDuration(300)

            // location last updated time
            updated_on.setText("Last updated on: $mLastUpdateTime")
        }

        toggleButtons()
    }

    private fun toggleButtons() {
        if (mRequestingLocationUpdates!!) {
            btn_start_location_updates.setEnabled(false)
            btn_start_location_updates.setEnabled(true)
        } else {
            btn_start_location_updates.setEnabled(true)
            btn_start_location_updates.setEnabled(false)
        }
    }
    private fun setClick() {


        btn_start_location_updates.setOnClickListener {

            mRequestingLocationUpdates = true
            startLocationUpdates()

        }
        btn_stop_location_updates.setOnClickListener {

            mRequestingLocationUpdates = false
            stopLocationUpdates()
        }
        btn_get_last_location.setOnClickListener {

            if (mCurrentLocation != null) {
                Toast.makeText(applicationContext, "Lat: " + mCurrentLocation!!.getLatitude()
                        + ", Lng: " + mCurrentLocation!!.getLongitude(), Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, "Last known location is not available!", Toast.LENGTH_SHORT).show()
            }
        }

        logoutApp.setOnClickListener {


            var snackbar = Snackbar.make(main_lay,"Do you want to logout?",Snackbar.LENGTH_LONG)
                .setAction("Logout"){

                    MyApplication.editor.clear().commit()
                    var i = Intent(this,LoginActivity::class.java)
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(i)
                    finish()
                }

            snackbar.show()
        }
    }

    fun stopLocationUpdates() {
        // Removing location updates
        mFusedLocationClient!!.removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this) {
                    Toast.makeText(applicationContext, "Location updates stopped!", Toast.LENGTH_SHORT).show()
                    toggleButtons()
                }
    }
    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        mSettingsClient!!.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this) {
                    Log.i("", "All location settings are satisfied.")

                    Toast.makeText(applicationContext, "Started location updates!", Toast.LENGTH_SHORT).show()


                    mFusedLocationClient!!.requestLocationUpdates(mLocationRequest,
                            mLocationCallback, Looper.myLooper())

                    updateLocationUI()
                }
                .addOnFailureListener(this) { e ->
                    val statusCode = (e as ApiException).statusCode
                    when (statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                            Log.i("", "Location settings are not satisfied. Attempting to upgrade " + "location settings ")
                            try {
                                // Show the dialog by calling startResolutionForResult(), and check the
                                // result in onActivityResult().
                                val rae = e as ResolvableApiException
                                rae.startResolutionForResult(this, REQUEST_CHECK_SETTINGS)
                            } catch (sie: IntentSender.SendIntentException) {
                                Log.i("", "PendingIntent unable to execute request.")
                            }

                        }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                            val errorMessage = "Location settings are inadequate, and cannot be " + "fixed here. Fix in Settings."
                            Log.e("", errorMessage)

                            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                        }
                    }

                    updateLocationUI()
                }
    }
}
