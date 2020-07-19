package com.crowd.air.tower_info;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.crowd.air.R;


public class TowerActivity extends Activity {
    private static final String TAG = "MainActivity";

    private LocationManager locationManager;

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //若要实时获取GPS更新，可在此处理
            Log.i(TAG, "Location changed...");
            Log.i(TAG, "Latitude :        " + location.getLatitude());
            Log.i(TAG, "Longitude :       " + location.getLongitude());
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


    private TelephonyManager telephonyManager;
    private int MY_PERMISSIONS_REQUEST_LOCATION = 222;
    //    private List<NeighboringCellInfo> neighbours;
//    private NeighboringCellInfo neighborCellInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tower);
//        checkLocationPermission();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
//
//
//    public void showGps(View view) {
//        TextView tv = findViewById(R.id.gps_value);
//        if (locationManager != null) {
//            String provider = LocationManager.GPS_PROVIDER;
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                tv.setText("No permission");
//                return;
//            }
//            Location lastKnownLocation = locationManager.getLastKnownLocation(provider);
//            if (lastKnownLocation != null) {
//                double lat = lastKnownLocation.getLatitude();
//                double lon = lastKnownLocation.getLongitude();
//                double alt = lastKnownLocation.getAltitude();
//                float acc = lastKnownLocation.getAccuracy();
//                float spd = lastKnownLocation.getSpeed();
//                tv.setText("Latitude: " + lat + ", Longitude: " + lon + ",\nAltitude: " + alt + ", Accuracy: " + acc + ", Speed: " + spd);
//            } else {
//                tv.setText("GPS not obtained\n");
//            }
//        }
//    }
//
////    private CellInfo getSlotCellInfo(int slotIndex) {
////        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
////            // TODO: Consider calling
////            //    ActivityCompat#requestPermissions
////            // here to request the missing permissions, and then overriding
////            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
////            //                                          int[] grantResults)
////            // to handle the case where the user grants the permission. See the documentation
////            // for ActivityCompat#requestPermissions for more details.
////
////        }
////        ArrayList<CellInfo> allCellInfo = new ArrayList<>(telephonyManager.getAllCellInfo());
////        SubscriptionManager subscriptionManager = null;
////        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
////            subscriptionManager = SubscriptionManager.from(getActivity());
////        }
////        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
////            // TODO: Consider calling
////            //    ActivityCompat#requestPermissions
////            // here to request the missing permissions, and then overriding
////            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
////            //                                          int[] grantResults)
////            // to handle the case where the user grants the permission. See the documentation
////            // for ActivityCompat#requestPermissions for more details.
////        }
////        List<SubscriptionInfo> activeSubscriptionInfoList = null;
////        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
////            activeSubscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();
////        }
////        SubscriptionInfo subscriptionInfo = null;
////
////        for (int i = 0; i < activeSubscriptionInfoList.size(); i++) {
////            SubscriptionInfo temp = activeSubscriptionInfoList.get(i);
////            if (temp.getSimSlotIndex() == slotIndex) {
////                subscriptionInfo = temp;
////                break;
////            }
////        }
////
////        for (int index = 0; index < allCellInfo.size(); index++) {
////            int mnc = 0;
////            CellInfo temp = allCellInfo.get(index);
////            String cellType = "checkCellType(temp)";
////            if (cellType == "GSM") {
////                CellIdentityGsm identity = (((CellInfoGsm) temp).getCellIdentity());
////                mnc = identity.getMnc();
////            } else if (cellType == "WCDMA") {
////                CellIdentityWcdma identity = (((CellInfoWcdma) temp).getCellIdentity());
////                mnc = identity.getMnc();
////            } else if (cellType == "LTE") {
////                CellIdentityLte identity = (((CellInfoLte) temp).getCellIdentity());
////                mnc = identity.getMnc();
////            }
////            if (mnc == subscriptionInfo.getMnc()) {
////                return temp;
////            }
////        }
////    }
//
//    public void showCellinfo(View view) {
//        TextView tv = findViewById(R.id.cell_value);
//        List<CellInfo> cellInfoList = null;
//        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            tv.setText("لا يوجد إذن");
//            return;
//        }
//        cellInfoList = telephonyManager.getAllCellInfo();
//        if (cellInfoList == null) {
//            tv.setText("getAllCellInfo() null");
//        } else if (cellInfoList.size() == 0) {
//            tv.setText("Base station list is empty\n");
//        } else {
//            int cellNumber = cellInfoList.size();
//            BaseStation main_BS = bindData(cellInfoList.get(0));
//            tv.setText("Obtained" + cellNumber + "Base stations, \nMain base station information：\n" + main_BS.toString());
//
//            FirebaseFirestore.getInstance().collection("test")
//                    .document().set(main_BS).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//
//                }
//            });
//            for (CellInfo cellInfo : cellInfoList) {
//                BaseStation bs = bindData(cellInfo);
//                Log.i(TAG, bs.toString());
//            }
//
//        }
//
//
//    }
//
//    private BaseStation bindData(CellInfo cellInfo) {
//        BaseStation GSMStation = null;
//
//        if (cellInfo instanceof CellInfoGsm) {
//            //2G
//            CellInfoGsm cellInfoGsm = (CellInfoGsm) cellInfo;
//            CellIdentityGsm cellIdentityGsm = cellInfoGsm.getCellIdentity();
//
//            GSMStation = new BaseStation();
//
//            GSMStation.setType(StationType.GSM);
//            GSMStation.setCid(cellIdentityGsm.getCid());
//            GSMStation.setLac(cellIdentityGsm.getLac());
//            GSMStation.setMcc(cellIdentityGsm.getMcc());
//            GSMStation.setMnc(cellIdentityGsm.getMnc());
//            GSMStation.setPsc(cellIdentityGsm.getPsc());
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                GSMStation.setMccString(cellIdentityGsm.getMccString());
//                GSMStation.setMncString(cellIdentityGsm.getMncString());
//            }
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                GSMStation.setArfcn(cellIdentityGsm.getArfcn());
//
//            }
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                GSMStation.setMobileNetworkOperator(cellIdentityGsm.getMobileNetworkOperator());
//            }
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                GSMStation.setRssi(cellInfoGsm.getCellSignalStrength().getRssi());
//            }
//
//            GSMStation.setAsuLevel(cellInfoGsm.getCellSignalStrength().getAsuLevel());
//            GSMStation.setSignalLevel(cellInfoGsm.getCellSignalStrength().getLevel());
//            GSMStation.setDbm(cellInfoGsm.getCellSignalStrength().getDbm());
//
//        } else if (cellInfo instanceof CellInfoWcdma) {
//
//            // 3g
//            CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) cellInfo;
//
//            CellIdentityWcdma cellIdentityWcdma = cellInfoWcdma.getCellIdentity();
//
//            GSMStation = new GSMStation();
//
//            GSMStation.setType(StationType.WCDMA);
//
//            // region Common data
//            GSMStation.setCid(cellIdentityWcdma.getCid());
//            GSMStation.setLac(cellIdentityWcdma.getLac());
//            GSMStation.setMcc(cellIdentityWcdma.getMcc());
//            GSMStation.setMnc(cellIdentityWcdma.getMnc());
//            GSMStation.setPsc(cellIdentityWcdma.getPsc());
//            // endregion
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                GSMStation.setMobileNetworkOperator(cellIdentityWcdma.getMobileNetworkOperator());
//            }
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                GSMStation.setMccString(cellIdentityWcdma.getMccString());
//                GSMStation.setMncString(cellIdentityWcdma.getMncString());
//            }
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                GSMStation.setAdditionalPlmns(cellIdentityWcdma.getAdditionalPlmns());
//            }
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                GSMStation.setUarfcn(cellIdentityWcdma.getUarfcn());
//            }
//
//            cellInfoWcdma.getCellSignalStrength();
//
//            //Get the signal level as an asu value between 0..31, 99 is unknown Asu is calculated based on 3GPP RSRP.
//            GSMStation.setAsuLevel(cellInfoWcdma.getCellSignalStrength().getAsuLevel());
//
//            //Get signal level as an int from 0..4
//            GSMStation.setSignalLevel(cellInfoWcdma.getCellSignalStrength().getLevel());
//
//            //Get the signal strength as dBm
//            GSMStation.setDbm(cellInfoWcdma.getCellSignalStrength().getDbm());
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                GSMStation.setEcNo(cellInfoWcdma.getCellSignalStrength().getEcNo());
//            }
//
//        } else if (cellInfo instanceof CellInfoLte) {
//            //4G
//            CellInfoLte cellInfoLte = (CellInfoLte) cellInfo;
//            CellIdentityLte cellIdentityLte = cellInfoLte.getCellIdentity();
//            GSMStation = new GSMStation();
//            GSMStation.setType(StationType.LTE);
//
//            GSMStation.setMcc(cellIdentityLte.getMcc());
//            GSMStation.setMnc(cellIdentityLte.getMnc());
//            GSMStation.setCid(cellIdentityLte.getCi());
//            GSMStation.setPsc(cellIdentityLte.getPci());
//            GSMStation.setTac(cellIdentityLte.getTac());
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                GSMStation.setArfcn(cellIdentityLte.getEarfcn());
//            }
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                GSMStation.setEarfcn(cellIdentityLte.getEarfcn());
//            }
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                GSMStation.setBandwidth(cellIdentityLte.getBandwidth());
//            }
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                GSMStation.setMccString(cellIdentityLte.getMccString());
//                GSMStation.setMncString(cellIdentityLte.getMncString());
//            }
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                GSMStation.setMobileNetworkOperator(cellIdentityLte.getMobileNetworkOperator());
//            }
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                GSMStation.setAdditionalPlmns(cellIdentityLte.getAdditionalPlmns());
//            }
//
//
//            //signal strength
//
//            GSMStation.setSignalLevel(cellInfoLte.getCellSignalStrength().getLevel());
//
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                GSMStation.setRsrq(cellInfoLte.getCellSignalStrength().getRsrq());
//            }
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                GSMStation.setRssi(cellInfoLte.getCellSignalStrength().getRssi());
//            }
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                GSMStation.setRssnr(cellInfoLte.getCellSignalStrength().getRssnr());
//            }
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                GSMStation.setRsrp(cellInfoLte.getCellSignalStrength().getRsrp());
//            }
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                GSMStation.setCqi(cellInfoLte.getCellSignalStrength().getCqi());
//            }
//
//            GSMStation.setDbm(cellInfoLte.getCellSignalStrength().getDbm());
//
//            GSMStation.setAsuLevel(cellInfoLte.getCellSignalStrength().getAsuLevel());
//
//            GSMStation.setTimingAdvance(cellInfoLte.getCellSignalStrength().getTimingAdvance());
//
//
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && cellInfo instanceof CellInfoNr) {
//            CellInfoNr cellInfoNr = (CellInfoNr) cellInfo;
//
//            GSMStation = new GSMStation();
//            GSMStation.setType(StationType.NR);
//
//            GSMStation.setAsuLevel(cellInfoNr.getCellSignalStrength().getAsuLevel());
//            GSMStation.setSignalLevel(cellInfoNr.getCellSignalStrength().getLevel());
//            GSMStation.setDbm(cellInfoNr.getCellSignalStrength().getDbm());
//            GSMStation.setDbm(cellInfoNr.getCellSignalStrength().getDbm());
//
//        }
//
////        requestCellInfoUpdate
//        return GSMStation;
//    }
//
//    public boolean checkLocationPermission() {
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.ACCESS_FINE_LOCATION)) {
//
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE},
//                        MY_PERMISSIONS_REQUEST_LOCATION);
//
//
//            } else {
//                // No explanation needed, we can request the permission.
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE},
//                        MY_PERMISSIONS_REQUEST_LOCATION);
//            }
//            return false;
//        } else {
//            return true;
//        }
//
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//
//    }
}