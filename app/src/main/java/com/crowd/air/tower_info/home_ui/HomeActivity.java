package com.crowd.air.tower_info.home_ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.crowd.air.R;
import com.crowd.air.tower_info.model.apis.CellLocationRequest;
import com.crowd.air.tower_info.model.apis.CellLocationResponse;
import com.crowd.air.tower_info.model.apis.CellRequest;
import com.crowd.air.tower_info.model.device.DeviceInfo;
import com.crowd.air.tower_info.server.BaseClient;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class    HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    public HomeViewModel homeViewModel;
    private int slotIndex;
    private int MY_PERMISSIONS_REQUEST_LOCATION;
    private Timer timer;

    private DeviceInfo deviceInfo;

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            Log.i(TAG, "Location changed...");
            Log.i(TAG, "Latitude :        " + location.getLatitude());
            Log.i(TAG, "Longitude :       " + location.getLongitude());

            deviceInfo.setLat(location.getLatitude());
            deviceInfo.setLng(location.getLongitude());


        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        deviceInfo = new DeviceInfo();


        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE},
                MY_PERMISSIONS_REQUEST_LOCATION);
        homeViewModel = new ViewModelProvider(HomeActivity.this).get(HomeViewModel.class);


        HomeSectionsPagerAdapter homeSectionsPagerAdapter = new HomeSectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(homeSectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


        getLocation();


    }


    private void getLocation() {

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String provider = LocationManager.GPS_PROVIDER;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(provider, 1000, 1, locationListener);//参数依次为provider（GPS，或者NETWORK_PROVIDER或PASSIVE_PROVIDER），执行更新的最小时间，执行更新的最小距离，更新后的listener
//        locationManager.requestSingleUpdate(provider, locationListener, null);//或者仅仅进行单词更新


    }


    @Override
    protected void onResume() {
        super.onResume();


//        Log.d(TAG, "checkLocationPermission0: " + getSlotCellInfo(0));
//        Log.d(TAG, "checkLocationPermission1: " + getSlotCellInfo(1));


//        homeViewModel.showCellinfo(HomeActivity.this,1);
//        homeViewModel.getSlotData(HomeActivity.this);

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }


    }


    private CellInfo getSlotCellInfo(int slotIndex) {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        ArrayList<CellInfo> allCellInfo = new ArrayList<>(telephonyManager.getAllCellInfo());
        SubscriptionManager subscriptionManager = SubscriptionManager.from(this);
        List<SubscriptionInfo> activeSubscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();
        SubscriptionInfo subscriptionInfo = null;

        for (int i = 0; i < activeSubscriptionInfoList.size(); i++) {
            SubscriptionInfo temp = activeSubscriptionInfoList.get(i);
            if (temp.getSimSlotIndex() == slotIndex) {
                subscriptionInfo = temp;
                break;
            }
        }
        CellInfo temp = null;
        for (int index = 0; index < allCellInfo.size(); index++) {
            int mnc = 0;
            temp = allCellInfo.get(index);
            String cellType = checkCellType(temp);
            if (cellType == "GSM") {
                CellIdentityGsm identity = (((CellInfoGsm) temp).getCellIdentity());
                mnc = identity.getMnc();
            } else if (cellType == "WCDMA") {
                CellIdentityWcdma identity = (((CellInfoWcdma) temp).getCellIdentity());
                mnc = identity.getMnc();
            } else if (cellType == "LTE") {
                CellIdentityLte identity = (((CellInfoLte) temp).getCellIdentity());
                mnc = identity.getMnc();
            }
            if (mnc == subscriptionInfo.getMnc()) {
                return temp;
            }
        }
        return temp;
    }

    private String checkCellType(CellInfo cellInfo) {
        if (cellInfo instanceof CellInfoGsm) {
            return "GSM";
        } else if (cellInfo instanceof CellInfoWcdma) {
            return "WCDMA";
        } else {
            return "LTE";
        }
    }

    private ArrayList<CellInfo> updateCellInfo(ArrayList<CellInfo> cellInfo) {
        //Create new ArrayList
        ArrayList<CellInfo> cellInfos = new ArrayList<>();
        //cellInfo is obtained from telephonyManager.getAllCellInfo()
        if (cellInfo.size() != 0) {
            for (int i = 0; i < cellInfo.size(); i++) {
                //Return registered cells only
                int index = 0;
                CellInfo temp = cellInfo.get(i);
                if (temp.isRegistered()) {
                    cellInfos.add(index, temp);
                    index++;
                }
            }
        }
        return cellInfos;
    }


}