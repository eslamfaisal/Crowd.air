package com.crowd.air.tower_info.home_ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoNr;
import android.telephony.CellInfoWcdma;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.crowd.air.tower_info.StationType;
import com.crowd.air.tower_info.model.stations.BaseStation;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private static final String TAG = "HomeViewModel";
    private int slotIndex;
    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private MutableLiveData<BaseStation> _baseStation = new MutableLiveData<>();
    private MutableLiveData<List<CellInfo>> _callList = new MutableLiveData<>();
    private MutableLiveData<List<SubscriptionInfo>> _subscriptionInfo = new MutableLiveData<>();

    public LiveData<List<CellInfo>> getCallList() {
        return _callList;
    }

    public LiveData<BaseStation> getBaseStation() {
        return _baseStation;
    }

    public LiveData<List<SubscriptionInfo>> getSubscriptionInfo() {
        return _subscriptionInfo;
    }

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public void getSlotData(Context context, int slotIndex) {

        this.slotIndex = slotIndex;
        SubscriptionManager subscriptionManager = SubscriptionManager.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        List<SubscriptionInfo> activeSubscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();

        _subscriptionInfo.postValue(activeSubscriptionInfoList);

    }

    public void showCellinfo(Context context) {

        List<CellInfo> cellInfoList = null;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
//            tv.setText("لا يوجد إذن");
            return;
        }
        cellInfoList = telephonyManager.getAllCellInfo();
        if (cellInfoList == null) {
            Log.d(TAG, "cellNumber: =" + null);
//            tv.setText("getAllCellInfo() null");
        } else if (cellInfoList.size() == 0) {
            Log.d(TAG, "cellNumber: =" + 000);
//            tv.setText("Base station list is empty\n");
        } else {
            _callList.setValue(cellInfoList);
            int cellNumber = cellInfoList.size();
            Log.d(TAG, "cellNumber: =" + cellNumber);
//            BaseStation main_BS = bindData(cellInfoList.get(0));

//            _baseStationSIMOne.setValue(main_BS);

            List<CellInfo> registerCellInfo = new ArrayList<>();

            for (CellInfo cellInfo : cellInfoList) {
                if (cellInfo.isRegistered()) {
                    registerCellInfo.add(cellInfo);
                } else {
                }
            }


            try {
                Log.d(TAG, "slotIndexslotIndex: " + slotIndex);
                BaseStation bs = bindData(registerCellInfo.get(slotIndex));
            } catch (Exception e) {

            }


        }

    }


    private BaseStation bindData(CellInfo cellInfo) {

//        cellInfo.getTimestampMillis()
        Log.d(TAG, "bindDatabindData: ");
        BaseStation station = new BaseStation();

        station.setTime(cellInfo.getTimeStamp());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            station.setTime(cellInfo.getTimestampMillis());
        }

        if (cellInfo instanceof CellInfoGsm) {
            //2G
            CellInfoGsm cellInfoGsm = (CellInfoGsm) cellInfo;
            CellIdentityGsm cellIdentityGsm = cellInfoGsm.getCellIdentity();

            station = new BaseStation();

            station.setType(StationType.GSM);
            station.setCid(cellIdentityGsm.getCid());
            station.setLac(cellIdentityGsm.getLac());
            station.setMcc(cellIdentityGsm.getMcc());
            station.setMnc(cellIdentityGsm.getMnc());
            station.setBsic_psc_pci(cellIdentityGsm.getPsc());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (cellIdentityGsm.getBsic() != 0) {
                    station.setBsic_psc_pci(cellIdentityGsm.getBsic());
                }
            }

            station.getGsmStation().setPsc(cellIdentityGsm.getPsc());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                station.getGsmStation().setMccString(cellIdentityGsm.getMccString());
                station.getGsmStation().setMncString(cellIdentityGsm.getMncString());
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                station.getGsmStation().setArfcn(cellIdentityGsm.getArfcn());
                station.getGsmStation().setBsic(cellIdentityGsm.getBsic());
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                station.getGsmStation().setMobileNetworkOperator(cellIdentityGsm.getMobileNetworkOperator());
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                station.getGsmStation().getGsmSignalStrength().setRssi(cellInfoGsm.getCellSignalStrength().getRssi());
            }

            station.getGsmStation().getGsmSignalStrength().setAsuLevel(cellInfoGsm.getCellSignalStrength().getAsuLevel());
            station.getGsmStation().getGsmSignalStrength().setSignalLevel(cellInfoGsm.getCellSignalStrength().getLevel());
            station.getGsmStation().getGsmSignalStrength().setDbm(cellInfoGsm.getCellSignalStrength().getDbm());


//            if (slotMnc == indexRegistered) {
//                _baseStation.setValue(station);
//                Log.d(TAG, "bindDatabindData: yes");
//            } else {
//                Log.d(TAG, "bindDatabindData: not");
//            }

        } else if (cellInfo instanceof CellInfoWcdma) {

            // 3g
            CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) cellInfo;

            CellIdentityWcdma cellIdentityWcdma = cellInfoWcdma.getCellIdentity();


            station.setType(StationType.WCDMA);

            // region Common data
            station.setCid(cellIdentityWcdma.getCid());
            station.setLac(cellIdentityWcdma.getLac());
            station.setMcc(cellIdentityWcdma.getMcc());
            station.setMnc(cellIdentityWcdma.getMnc());
            station.setBsic_psc_pci(cellIdentityWcdma.getPsc());

            station.getWcdmaStation().setPsc(cellIdentityWcdma.getPsc());
            // endregion

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                station.getWcdmaStation().setMobileNetworkOperator(cellIdentityWcdma.getMobileNetworkOperator());
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                station.getWcdmaStation().setMccString(cellIdentityWcdma.getMccString());
                station.getWcdmaStation().setMncString(cellIdentityWcdma.getMncString());
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                station.getWcdmaStation().setUarfcn(cellIdentityWcdma.getUarfcn());
            }

            //Get the signal level as an asu value between 0..31, 99 is unknown Asu is calculated based on 3GPP RSRP.
            station.getWcdmaStation().getWcdmaSignalStrength().setAsuLevel(cellInfoWcdma.getCellSignalStrength().getAsuLevel());

            //Get signal level as an int from 0..4
            station.getWcdmaStation().getWcdmaSignalStrength().setSignalLevel(cellInfoWcdma.getCellSignalStrength().getLevel());

            //Get the signal strength as dBm
            station.getWcdmaStation().getWcdmaSignalStrength().setDbm(cellInfoWcdma.getCellSignalStrength().getDbm());


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                station.getWcdmaStation().getWcdmaSignalStrength().setEcNo(cellInfoWcdma.getCellSignalStrength().getEcNo());
            }
//            if (slotMnc == indexRegistered) {
//                _baseStation.setValue(station);
//            }

        } else if (cellInfo instanceof CellInfoLte) {
            //4G
            CellInfoLte cellInfoLte = (CellInfoLte) cellInfo;
            CellIdentityLte cellIdentityLte = cellInfoLte.getCellIdentity();


            station.setType(StationType.LTE);

            station.setMcc(cellIdentityLte.getMcc());
            station.setMnc(cellIdentityLte.getMnc());
            station.setCid(cellIdentityLte.getCi());
            station.setBsic_psc_pci(cellIdentityLte.getPci());

            station.getLteStation().setPci(cellIdentityLte.getPci());
            station.setTac(cellIdentityLte.getTac());


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                station.getLteStation().setEarfcn(cellIdentityLte.getEarfcn());
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                station.getLteStation().setBandwidth(cellIdentityLte.getBandwidth());
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                station.getLteStation().setMccString(cellIdentityLte.getMccString());
                station.getLteStation().setMncString(cellIdentityLte.getMncString());
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                station.getLteStation().setMobileNetworkOperator(cellIdentityLte.getMobileNetworkOperator());
            }

            //signal strength
            station.getLteStation().getLteSignalStrength().setSignalLevel(cellInfoLte.getCellSignalStrength().getLevel());


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                station.getLteStation().getLteSignalStrength().setRsrq(cellInfoLte.getCellSignalStrength().getRsrq());
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                station.getLteStation().getLteSignalStrength().setRssi(cellInfoLte.getCellSignalStrength().getRssi());
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                station.getLteStation().getLteSignalStrength().setRssnr(cellInfoLte.getCellSignalStrength().getRssnr());
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                station.getLteStation().getLteSignalStrength().setRsrp(cellInfoLte.getCellSignalStrength().getRsrp());
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                station.getLteStation().getLteSignalStrength().setCqi(cellInfoLte.getCellSignalStrength().getCqi());
            }

            station.getLteStation().getLteSignalStrength().setAsuLevel(cellInfoLte.getCellSignalStrength().getAsuLevel());
            station.getLteStation().getLteSignalStrength().setSignalLevel(cellInfoLte.getCellSignalStrength().getLevel());
            station.getLteStation().getLteSignalStrength().setDbm(cellInfoLte.getCellSignalStrength().getDbm());

//            if (slotMnc == indexRegistered) {
//                _baseStation.setValue(station);
//            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && cellInfo instanceof CellInfoNr) {
            CellInfoNr cellInfoNr = (CellInfoNr) cellInfo;


            station.setType(StationType.NR);

            station.getNrStation().getNrSignalStrength().setAsuLevel(cellInfoNr.getCellSignalStrength().getAsuLevel());
            station.getNrStation().getNrSignalStrength().setSignalLevel(cellInfoNr.getCellSignalStrength().getLevel());
            station.getNrStation().getNrSignalStrength().setDbm(cellInfoNr.getCellSignalStrength().getDbm());
//            if (slotMnc == indexRegistered) {
//                _baseStation.setValue(station);
//            }
        }

        _baseStation.setValue(station);
        return station;
    }


    private void getTelephonyInfo(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

    }
}