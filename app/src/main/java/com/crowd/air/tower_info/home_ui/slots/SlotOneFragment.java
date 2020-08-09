package com.crowd.air.tower_info.home_ui.slots;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.crowd.air.R;
import com.crowd.air.tower_info.home_ui.HomeViewModel;
import com.crowd.air.tower_info.model.apis.CellLocationRequest;
import com.crowd.air.tower_info.model.apis.CellLocationResponse;
import com.crowd.air.tower_info.model.apis.CellRequest;
import com.crowd.air.tower_info.model.stations.BaseStation;
import com.crowd.air.tower_info.server.BaseClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A placeholder fragment containing a simple view.
 */
public class SlotOneFragment extends Fragment implements OnMapReadyCallback {


    private static final String TAG = "SlotOneFragment";
    private CellLocationResponse cellLocationResponse;
    // COMMON TEXTS
    private TextView time, iccid_tv, sim_operator_tv, operator_tv,
            mcc_tv, mnc_tv, tac_tv,
            cell_type_tv, dbm_signal_tv,
            signalLevel_signal_tv, asuLevel_signal_tv;

    // GSM text view
    private TextView psc_gsm_tv, arfcn_gsm_tv, mcc_string_gsm_tv,
            mobilen_etwork_operator_gsm_tv, mnc_string_gsm_tv,
            bsic_gsm_tv, rssi_gsm_signal_tv;


    // WCDMA text views
    private TextView psc_wcdma_tv, mobilen_etwork_operator_wcdma_tv,
            mcc_string_wcdma_tv, mnc_string_wcdma_tv, uarfcn_tv, ecNo_gsm_signal_tv;

    // LTE text views
    private TextView pci_lte_tv, erfcn_gsm_tv, bandwidth_gsm_tv,
            mobilen_network_operator_lte_tv, mcc_string_lte_tv,
            mnc_string_lte_tv, rsrq_lte_signal_tv, rssi_lte_signal_tv,
            rssnr_lte_signal_tv, rsrp_lte_signal_tv, cqi_lte_signal_tv,
            timingAdvance_lte_signal_tv;


    private ImageView operator_image;
    private HomeViewModel homeViewModel;

    private View gsm_cel_info_view, wcdma_view, lte_cell_info_view,
            wcdma_signal_strength_view, gsm_signal_strength_view,
            lte_signal_strength_view;


    private TextView cell_lon_tv, cell_lat_tv, cell_accuracy_tv, cell_address_tv;
    private BaseStation baseStation;

    Callback<CellLocationResponse> callback = new Callback<CellLocationResponse>() {
        @Override
        public void onResponse(Call<CellLocationResponse> call, Response<CellLocationResponse> response) {
            Log.d(TAG, "onResponse: " + response.body());
            cellLocationResponse = response.body();
            baseStation.setCellLocation(cellLocationResponse);
            uploadStation();
            if (response.isSuccessful()) {
                if (response.body() != null) {
                    try {
                        cell_lon_tv.setText(response.body().getLon().toString());
                        cell_lat_tv.setText(response.body().getLat().toString());
                        cell_accuracy_tv.setText(response.body().getAccuracy().toString());
                        cell_address_tv.setText(response.body().getAddress());
                        LatLng currentLatLng = new LatLng(response.body().getLat(),
                                response.body().getLon());
                        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(currentLatLng,
                                16);
                        googleMap.moveCamera(update);
                        // Creating a marker
                        MarkerOptions markerOptions = new MarkerOptions();

                        // Setting the position for the marker
                        markerOptions.position(currentLatLng);

                        // Setting the title for the marker.
                        // This will be displayed on taping the marker
                        markerOptions.title(response.body().getAddress());

                        // Clears the previously touched position
                        googleMap.clear();

                        // Animating to the touched position
                        googleMap.animateCamera(CameraUpdateFactory.newLatLng(currentLatLng));
                        List<LatLng> latLngs = new  ArrayList<>();
                        latLngs.add(currentLatLng);
                        latLngs.add(new LatLng(googleMap.getMyLocation().getLatitude(),googleMap.getMyLocation().getLongitude()));
                        // Placing a marker on the touched position
                        googleMap.addMarker(markerOptions);
                        googleMap.addPolyline(new PolylineOptions()
                                .addAll(latLngs)
                                .width(5)
                                .color(Color.RED));
                    } catch (Exception e) {
                        cell_lon_tv.setText("Zero balance");
                        cell_lat_tv.setText("Invalid token");
                    }

                }

            }
        }

        @Override
        public void onFailure(Call<CellLocationResponse> call, Throwable t) {

        }
    };
    private int slotIndex = 0;
    private Timer timer;
    private String networkInfoStr;


    public static SlotOneFragment newInstance(int slotIndex) {
        SlotOneFragment fragment = new SlotOneFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("slotIndex", slotIndex);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        slotIndex = getArguments().getInt("slotIndex");
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initMap();

        initView(view);



    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    private void initView(View view) {
        time = view.findViewById(R.id.time);
        iccid_tv = view.findViewById(R.id.iccid_tv);
        sim_operator_tv = view.findViewById(R.id.sim_operator_tv);
        operator_tv = view.findViewById(R.id.operator_tv);
        mcc_tv = view.findViewById(R.id.mcc_tv);
        mnc_tv = view.findViewById(R.id.mnc_tv);
        operator_image = view.findViewById(R.id.operator_image);
        tac_tv = view.findViewById(R.id.tac_tv);
        cell_type_tv = view.findViewById(R.id.cell_type_tv);
        cell_type_tv = view.findViewById(R.id.cell_type_tv);
        dbm_signal_tv = view.findViewById(R.id.dbm_signal_tv);
        signalLevel_signal_tv = view.findViewById(R.id.signalLevel_signal_tv);
        asuLevel_signal_tv = view.findViewById(R.id.asuLevel_signal_tv);


        // GSM views
        gsm_cel_info_view = view.findViewById(R.id.gsm_cel_info_view);
        psc_gsm_tv = view.findViewById(R.id.psc_gsm_tv);
        arfcn_gsm_tv = view.findViewById(R.id.arfcn_gsm_tv);
        mcc_string_gsm_tv = view.findViewById(R.id.mcc_string_gsm_tv);
        mnc_string_gsm_tv = view.findViewById(R.id.mnc_string_gsm_tv);
        mobilen_etwork_operator_gsm_tv = view.findViewById(R.id.mobilen_etwork_operator_gsm_tv);
        bsic_gsm_tv = view.findViewById(R.id.bsic_gsm_tv);

        // GSM signal strength
        gsm_signal_strength_view = view.findViewById(R.id.gsm_signal_strength_view);
        rssi_gsm_signal_tv = view.findViewById(R.id.rssi_gsm_signal_tv);


        // WCDMA text views
        wcdma_view = view.findViewById(R.id.wcdma_view);
        psc_wcdma_tv = view.findViewById(R.id.psc_wcdma_tv);
        mobilen_etwork_operator_wcdma_tv = view.findViewById(R.id.mobilen_etwork_operator_wcdma_tv);
        mcc_string_wcdma_tv = view.findViewById(R.id.mcc_string_wcdma_tv);
        mnc_string_wcdma_tv = view.findViewById(R.id.mnc_string_wcdma_tv);
        uarfcn_tv = view.findViewById(R.id.uarfcn_tv);

        //WCDMA signal strength
        wcdma_signal_strength_view = view.findViewById(R.id.wcdma_signal_strength_view);
        ecNo_gsm_signal_tv = view.findViewById(R.id.ecNo_gsm_signal_tv);


        // LTE text views
        lte_cell_info_view = view.findViewById(R.id.lte_cell_info_view);
        pci_lte_tv = view.findViewById(R.id.pci_lte_tv);
        erfcn_gsm_tv = view.findViewById(R.id.erfcn_gsm_tv);
        bandwidth_gsm_tv = view.findViewById(R.id.bandwidth_gsm_tv);
        mobilen_network_operator_lte_tv = view.findViewById(R.id.mobilen_network_operator_lte_tv);
        mcc_string_lte_tv = view.findViewById(R.id.mcc_string_lte_tv);
        mnc_string_lte_tv = view.findViewById(R.id.mnc_string_lte_tv);

        //LTE signal strength
        lte_signal_strength_view = view.findViewById(R.id.lte_signal_strength_view);
        rsrq_lte_signal_tv = view.findViewById(R.id.rsrq_lte_signal_tv);
        rssi_lte_signal_tv = view.findViewById(R.id.rssi_lte_signal_tv);
        rssnr_lte_signal_tv = view.findViewById(R.id.rssnr_lte_signal_tv);
        rsrp_lte_signal_tv = view.findViewById(R.id.rsrp_lte_signal_tv);
        cqi_lte_signal_tv = view.findViewById(R.id.cqi_lte_signal_tv);
        timingAdvance_lte_signal_tv = view.findViewById(R.id.timingAdvance_lte_signal_tv);


        // Cell location

        cell_lon_tv = view.findViewById(R.id.cell_lon_tv);
        cell_lat_tv = view.findViewById(R.id.cell_lat_tv);
        cell_accuracy_tv = view.findViewById(R.id.cell_accuracy_tv);
        cell_address_tv = view.findViewById(R.id.cell_address_tv);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        homeViewModel.getSubscriptionInfo().observe(getViewLifecycleOwner(), new Observer<List<SubscriptionInfo>>() {
            @Override
            public void onChanged(List<SubscriptionInfo> subscriptionInfos) {

                try {
                    iccid_tv.setText(subscriptionInfos.get(slotIndex).getIccId());
                    sim_operator_tv.setText(subscriptionInfos.get(slotIndex).getDisplayName());
                    operator_image.setImageBitmap(subscriptionInfos.get(slotIndex).createIconBitmap(requireActivity()));
                    operator_tv.setText(subscriptionInfos.get(slotIndex).getCarrierName());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        mcc_tv.setText(String.valueOf(subscriptionInfos.get(slotIndex).getMccString()));
                    } else {
                        mcc_tv.setText(String.valueOf(subscriptionInfos.get(slotIndex).getMcc()));
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        mnc_tv.setText(String.valueOf(subscriptionInfos.get(slotIndex).getMncString()));
                    } else {
                        mnc_tv.setText(String.valueOf(subscriptionInfos.get(slotIndex).getMnc()));
                    }
                } catch (Exception e) {

                }


            }
        });

        homeViewModel.getBaseStation().observe(getViewLifecycleOwner(), baseStation -> {

            this.baseStation = baseStation;
            BaseClient.getApi().getCellLocation(buildRequestObject(baseStation)).enqueue(callback);

            Log.d(TAG, "onChangedbaseStation: " + baseStation.toString());

            time.setText(new Date(baseStation.getTime()).toString() + "\n" + new Date().toString());
            // common fields
            cell_type_tv.setText(String.valueOf(baseStation.getType()));

            if (baseStation.getTac() != 0)
                tac_tv.setText(String.valueOf(baseStation.getTac()));
            else
                tac_tv.getRootView().findViewById(R.id.tac_view).setVisibility(View.GONE);

            switch (baseStation.getType()) {

                case GSM: {


                    // region GSM views
                    gsm_cel_info_view.setVisibility(View.VISIBLE);
                    gsm_signal_strength_view.setVisibility(View.VISIBLE);
                    wcdma_view.setVisibility(View.GONE);
                    lte_cell_info_view.setVisibility(View.GONE);
                    wcdma_signal_strength_view.setVisibility(View.GONE);
                    lte_signal_strength_view.setVisibility(View.GONE);

                    // region GSM Station info

                    if (baseStation.getGsmStation().getPsc() != 0)
                        psc_gsm_tv.setText(String.valueOf(baseStation.getGsmStation().getPsc()));
                    else
                        psc_gsm_tv.getRootView().findViewById(R.id.psc_gsm_view).setVisibility(View.GONE);

                    if (baseStation.getGsmStation().getArfcn() != 0)
                        arfcn_gsm_tv.setText(String.valueOf(baseStation.getGsmStation().getArfcn()));
                    else
                        arfcn_gsm_tv.getRootView().findViewById(R.id.arfcn_gsm_view).setVisibility(View.GONE);

                    if (baseStation.getGsmStation().getMccString() != null)
                        mcc_string_gsm_tv.setText(baseStation.getGsmStation().getMccString());
                    else
                        mcc_string_gsm_tv.getRootView().findViewById(R.id.mcc_string_gsm_view).setVisibility(View.GONE);

                    if (baseStation.getGsmStation().getMncString() != null)
                        mnc_string_gsm_tv.setText(baseStation.getGsmStation().getMncString());
                    else
                        mnc_string_gsm_tv.getRootView().findViewById(R.id.mnc_string_gsm_view).setVisibility(View.GONE);

                    if (baseStation.getGsmStation().getMobileNetworkOperator() != null)
                        mobilen_etwork_operator_gsm_tv.setText(baseStation.getGsmStation().getMobileNetworkOperator());
                    else
                        mobilen_etwork_operator_gsm_tv.getRootView().findViewById(R.id.mobilen_etwork_operator_gsm_view).setVisibility(View.GONE);

                    if (baseStation.getGsmStation().getBsic() != 0)
                        bsic_gsm_tv.setText(String.valueOf(baseStation.getGsmStation().getBsic()));
                    else
                        bsic_gsm_tv.getRootView().findViewById(R.id.bsic_gsm_view).setVisibility(View.GONE);

                    // endregion

                    // region GSM Signal Strength

                    dbm_signal_tv.setText(String.valueOf(baseStation.getGsmStation().getGsmSignalStrength().getDbm()));

                    signalLevel_signal_tv.setText(String.valueOf(baseStation.getGsmStation().getGsmSignalStrength().getSignalLevel()));

                    asuLevel_signal_tv.setText(String.valueOf(baseStation.getGsmStation().getGsmSignalStrength().getAsuLevel()));

                    if (baseStation.getGsmStation().getGsmSignalStrength().getRssi() != 0)
                        rssi_gsm_signal_tv.setText(String.valueOf(baseStation.getGsmStation().getGsmSignalStrength().getRssi()));
                    else
                        rssi_gsm_signal_tv.getRootView().findViewById(R.id.rssi_gsm_signal_view).setVisibility(View.GONE);


                    //endregion

                    // endregion

                    break;
                }
                case WCDMA: {

                    // region WCDMA views
                    wcdma_view.setVisibility(View.VISIBLE);
                    wcdma_signal_strength_view.setVisibility(View.VISIBLE);
                    gsm_cel_info_view.setVisibility(View.GONE);
                    gsm_signal_strength_view.setVisibility(View.GONE);
                    lte_cell_info_view.setVisibility(View.GONE);
                    lte_signal_strength_view.setVisibility(View.GONE);

                    if (baseStation.getWcdmaStation().getMobileNetworkOperator() != null)
                        mobilen_etwork_operator_wcdma_tv.setText(baseStation.getWcdmaStation().getMobileNetworkOperator());
                    else
                        mobilen_etwork_operator_wcdma_tv.getRootView().findViewById(R.id.mobilen_etwork_operator_wcdma_view).setVisibility(View.GONE);

                    if (baseStation.getWcdmaStation().getPsc() != 0)
                        psc_wcdma_tv.setText(String.valueOf(baseStation.getWcdmaStation().getPsc()));
                    else
                        psc_wcdma_tv.getRootView().findViewById(R.id.psc_wcdma_view).setVisibility(View.GONE);

                    if (baseStation.getWcdmaStation().getMccString() != null)
                        mcc_string_wcdma_tv.setText(baseStation.getWcdmaStation().getMccString());
                    else
                        mcc_string_wcdma_tv.getRootView().findViewById(R.id.mcc_string_wcdma_view).setVisibility(View.GONE);

                    if (baseStation.getWcdmaStation().getMncString() != null)
                        mnc_string_wcdma_tv.setText(baseStation.getWcdmaStation().getMncString());
                    else
                        mnc_string_wcdma_tv.getRootView().findViewById(R.id.mnc_string_wcdma_view).setVisibility(View.GONE);

                    if (baseStation.getWcdmaStation().getUarfcn() != 0)
                        uarfcn_tv.setText(String.valueOf(baseStation.getWcdmaStation().getUarfcn()));
                    else
                        uarfcn_tv.getRootView().findViewById(R.id.uarfcn_view).setVisibility(View.GONE);

                    // endregion


                    // region WCDMA Signal Strength

                    dbm_signal_tv.setText(String.valueOf(baseStation.getWcdmaStation().getWcdmaSignalStrength().getDbm()));

                    signalLevel_signal_tv.setText(String.valueOf(baseStation.getWcdmaStation().getWcdmaSignalStrength().getSignalLevel()));

                    asuLevel_signal_tv.setText(String.valueOf(baseStation.getWcdmaStation().getWcdmaSignalStrength().getAsuLevel()));


                    if (baseStation.getWcdmaStation().getWcdmaSignalStrength().getEcNo() != 0)
                        ecNo_gsm_signal_tv.setText(String.valueOf(baseStation.getWcdmaStation().getWcdmaSignalStrength().getEcNo()));
                    else
                        ecNo_gsm_signal_tv.getRootView().findViewById(R.id.ecNo_gsm_signal_view).setVisibility(View.GONE);


                    //endregion

                    break;
                }
                case LTE: {

                    // region LTE views
                    lte_cell_info_view.setVisibility(View.VISIBLE);
                    wcdma_signal_strength_view.setVisibility(View.GONE);
                    gsm_cel_info_view.setVisibility(View.GONE);
                    wcdma_view.setVisibility(View.GONE);
                    lte_signal_strength_view.setVisibility(View.VISIBLE);


                    if (baseStation.getLteStation().getPci() != 0)
                        pci_lte_tv.setText(String.valueOf(baseStation.getLteStation().getPci()));
                    else
                        pci_lte_tv.getRootView().findViewById(R.id.pci_lte_view).setVisibility(View.GONE);

                    if (baseStation.getLteStation().getEarfcn() != 0)
                        erfcn_gsm_tv.setText(String.valueOf(baseStation.getLteStation().getEarfcn()));
                    else
                        erfcn_gsm_tv.getRootView().findViewById(R.id.erfcn_gsm_view).setVisibility(View.GONE);

                    if (baseStation.getLteStation().getBandwidth() != 0)
                        bandwidth_gsm_tv.setText(String.valueOf(baseStation.getLteStation().getBandwidth()));
                    else
                        bandwidth_gsm_tv.getRootView().findViewById(R.id.bandwidth_gsm_view).setVisibility(View.GONE);

                    if (baseStation.getLteStation().getMobileNetworkOperator() != null)
                        mobilen_network_operator_lte_tv.setText(baseStation.getLteStation().getMobileNetworkOperator());
                    else
                        mobilen_network_operator_lte_tv.getRootView().findViewById(R.id.mobilen_network_operator_lte_view).setVisibility(View.GONE);


                    if (baseStation.getLteStation().getMccString() != null)
                        mcc_string_lte_tv.setText(baseStation.getLteStation().getMccString());
                    else
                        mcc_string_lte_tv.getRootView().findViewById(R.id.mcc_string_lte_view).setVisibility(View.GONE);

                    if (baseStation.getLteStation().getMncString() != null)
                        mnc_string_lte_tv.setText(baseStation.getLteStation().getMncString());
                    else
                        mnc_string_lte_tv.getRootView().findViewById(R.id.mnc_string_lte_view).setVisibility(View.GONE);


                    // endregion

                    // region LTE Signal Strength

                    dbm_signal_tv.setText(String.valueOf(baseStation.getLteStation().getLteSignalStrength().getDbm()));

                    signalLevel_signal_tv.setText(String.valueOf(baseStation.getLteStation().getLteSignalStrength().getSignalLevel()));

                    asuLevel_signal_tv.setText(String.valueOf(baseStation.getLteStation().getLteSignalStrength().getAsuLevel()));


                    if (baseStation.getLteStation().getLteSignalStrength().getRsrq() != 0)
                        rsrq_lte_signal_tv.setText(String.valueOf(baseStation.getLteStation().getLteSignalStrength().getRsrq()));
                    else
                        rsrq_lte_signal_tv.getRootView().findViewById(R.id.rsrq_lte_signal_view).setVisibility(View.GONE);

                    if (baseStation.getLteStation().getLteSignalStrength().getRssi() != 0)
                        rssi_lte_signal_tv.setText(String.valueOf(baseStation.getLteStation().getLteSignalStrength().getRssi()));
                    else
                        rssi_lte_signal_tv.getRootView().findViewById(R.id.rssi_lte_signal_view).setVisibility(View.GONE);

                    if (baseStation.getLteStation().getLteSignalStrength().getRssnr() != 0)
                        rssnr_lte_signal_tv.setText(String.valueOf(baseStation.getLteStation().getLteSignalStrength().getRssnr()));
                    else
                        rssnr_lte_signal_tv.getRootView().findViewById(R.id.rssnr_lte_signal_view).setVisibility(View.GONE);

                    if (baseStation.getLteStation().getLteSignalStrength().getRsrp() != 0)
                        rsrp_lte_signal_tv.setText(String.valueOf(baseStation.getLteStation().getLteSignalStrength().getRsrp()));
                    else
                        rsrp_lte_signal_tv.getRootView().findViewById(R.id.rsrp_lte_signal_tv).setVisibility(View.GONE);


                    //endregion


                    break;
                }
                case NR: {
                    gsm_cel_info_view.setVisibility(View.GONE);
                    gsm_signal_strength_view.setVisibility(View.GONE);
                    wcdma_signal_strength_view.setVisibility(View.GONE);
                    wcdma_view.setVisibility(View.GONE);
                    lte_signal_strength_view.setVisibility(View.GONE);

                    // region WCDMA Signal Strength

                    dbm_signal_tv.setText(String.valueOf(baseStation.getNrStation().getNrSignalStrength().getDbm()));

                    signalLevel_signal_tv.setText(String.valueOf(baseStation.getNrStation().getNrSignalStrength().getSignalLevel()));

                    asuLevel_signal_tv.setText(String.valueOf(baseStation.getNrStation().getNrSignalStrength().getAsuLevel()));


                    //endregion

                    break;
                }

            }


        });

        homeViewModel.getCallList().observe(getViewLifecycleOwner(), cellInfos -> {
            Log.d(TAG, "onChanged: " + cellInfos.size());
            Log.d(TAG, "allshowCellinfo: " + cellInfos.toString());
        });

        startGatherMetrics();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void uploadStation() {
        FirebaseFirestore.getInstance().collection("stations")
                .document().set(baseStation)
                .addOnCompleteListener(task -> {

                });
    }

    private void validateFieldVisibility(TextView textView, String value) {
        try {
            if (value.equals("")) {
                textView.setVisibility(View.GONE);
            } else if (value.equals("0")) {
                textView.setVisibility(View.GONE);
            } else {
                textView.setVisibility(View.VISIBLE);
                textView.setText(value);
            }
        } catch (Exception e) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(e.getMessage());
        }


    }

    private int calculateECI(BaseStation baseStation) {
//        int ECGI = ;
        return baseStation.getCid() & 0xFFFFFFF;
    }

    private CellLocationRequest buildRequestObject(BaseStation baseStation) {

        CellLocationRequest cellLocationRequest = new CellLocationRequest();

        cellLocationRequest.setToken("8ae81d0cb4ae65");
        cellLocationRequest.setRadio(baseStation.getType().toString());
        cellLocationRequest.setMnc(baseStation.getMnc());
        cellLocationRequest.setMcc(baseStation.getMcc());
        cellLocationRequest.setAddress(1);

        List<CellRequest> cellRequests = new ArrayList<>();

        CellRequest cellRequest = new CellRequest();
        cellRequest.setCid(baseStation.getCid());
        cellRequest.setLac(baseStation.getLac());
        cellRequest.setPsc(baseStation.getBsic_psc_pci());


        cellRequests.add(cellRequest);

        cellLocationRequest.setCellRequests(cellRequests);

        return cellLocationRequest;

    }

    public void startGatherMetrics() {

        ConnectivityManager connectivityManager = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        final TelephonyManager telephonyManager = (TelephonyManager) requireActivity().getSystemService(Context.TELEPHONY_SERVICE);


        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            networkInfoStr = connectivityManager.getActiveNetworkInfo().toString();

            // gather Network Capabilities
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                Network network = connectivityManager.getActiveNetwork();
                networkInfoStr += "; " + connectivityManager.getNetworkCapabilities(network).toString();
            }
        }
        TelephonyManager manager = (TelephonyManager) requireActivity().getSystemService(Context.TELEPHONY_SERVICE);
        String carrierName = manager.getNetworkOperatorName();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                telephonyManager.listen(new PhoneStateListener() {
                    @Override
                    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                        super.onSignalStrengthsChanged(signalStrength);

                        Log.d("A_NETWORK_METRICS",
                                "Signal Strength (0-4 / dBm):" + " / " + signalStrength.toString());
                        //                        + getDbm(signalStrength))
//                        getLevel(signalStrength) +

                        requireActivity().runOnUiThread(() -> {
                            homeViewModel.getSlotData(requireActivity(), slotIndex);
                            homeViewModel.showCellinfo(requireActivity());
                        });


                    }
                }, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);

                Looper.loop();
            }
        }).start();

    }


    @Override
    public void onResume() {
        super.onResume();
//        if (timer == null) {
//            timer = new Timer();
//            timer.scheduleAtFixedRate(new TimerTask() {
//                @Override
//                public void run() {
//                    requireActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//
//
//                        }
//                    });
//                }
//            }, 500, 1500);
//        }
    }

    GoogleMap googleMap;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        googleMap.setMyLocationEnabled(true);

    }
}