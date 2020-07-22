package com.crowd.air.tower_info.home_ui.fragments;

import android.content.Context;
import android.graphics.Point;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.crowd.air.R;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeviceInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeviceInfoFragment extends Fragment {


    int mWidthPixels;
    int mHeightPixels;
    private TextView mac_address_tv,screenInchesTv, UID_value_tv, Manufacturer_value_tv, Hardware_value_tv, Model_value_tv,
            Brand_value_tv, android_id_tv, Serial_nO_tv, ScreenResolution_tv, screen_density_tv,
            BootLoader_tv, User_tv, Host, Version_tv, Fingerprint_tv,system_tv;

    public DeviceInfoFragment() {
        // Required empty public constructor
    }

    public static DeviceInfoFragment newInstance() {
        DeviceInfoFragment fragment = new DeviceInfoFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_device_info, container, false);
    }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    String hex = Integer.toHexString(b & 0xFF);
                    if (hex.length() == 1)
                        hex = "0".concat(hex);
                    res1.append(hex.concat(":"));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "";
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        WifiManager wifiManager = (WifiManager) requireActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        String macAddress = wInfo.getMacAddress();
        mac_address_tv.setText(getMacAddr());
        TelephonyManager telephonyManager = (TelephonyManager) requireActivity().getSystemService(Context.TELEPHONY_SERVICE);
        setRealDeviceSizeInPixels();

        DisplayMetrics dm = new DisplayMetrics();

        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(mWidthPixels / dm.xdpi, 2);
        double y = Math.pow(mHeightPixels / dm.ydpi, 2);

        double screenInches = Math.sqrt(x + y);
        screenInchesTv.setText(String.valueOf(screenInches));

//        String UID_value = telephonyManager.getDeviceId();
//        UID_value_tv.setText(String.valueOf(UID_value));

        String Manufacturer_value = Build.MANUFACTURER;
        Manufacturer_value_tv.setText(String.valueOf(Manufacturer_value));

        String Brand_value = Build.BRAND;
        Brand_value_tv.setText(String.valueOf(Brand_value));

        String Model_value = Build.MODEL;
        Model_value_tv.setText(String.valueOf(Model_value));

        String Board_value = Build.BOARD;
        Model_value_tv.setText(String.valueOf(Board_value));

        String Hardware_value = Build.HARDWARE;
        Hardware_value_tv.setText(String.valueOf(Hardware_value));

        String Serial_nO_value = Build.SERIAL;
        Serial_nO_tv.setText(String.valueOf(Serial_nO_value));

        String android_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        android_id_tv.setText(String.valueOf(android_id));

        String ScreenResolution = mHeightPixels + " * " + mWidthPixels + " Pixels";
        ScreenResolution_tv.setText(ScreenResolution);

//        String screen_size = rounded + " Inches";
        String screen_density = dm.densityDpi + " dpi";
        screen_density_tv.setText(screen_density);

        String BootLoader = Build.BOOTLOADER;
        BootLoader_tv.setText(BootLoader);

        String User = Build.USER;
        User_tv.setText(User);

        String Host_value = Build.HOST;
        Host.setText(Host_value);

        String Version = Build.VERSION.RELEASE;
        Version_tv.setText(Version);

        String API_level = Build.VERSION.SDK_INT + "";
        String Build_ID = Build.ID;
        String Build_Time = Build.TIME + "";
        String Fingerprint = Build.FINGERPRINT;
        Fingerprint_tv.setText(Fingerprint);

        String s="";
        s += "\n OS Version: " + System.getProperty("os.version") + "(" + android.os.Build.VERSION.INCREMENTAL + ")";
        s += "\n OS API Level: " + android.os.Build.VERSION.SDK_INT;
        s += "\n Device: " + android.os.Build.DEVICE;
        s += "\n Model (and Product): " + android.os.Build.MODEL + " ("+ android.os.Build.PRODUCT + ")";
        system_tv.setText(s);
    }

    private void setRealDeviceSizeInPixels() {
        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);


        // since SDK_INT = 1;
        mWidthPixels = displayMetrics.widthPixels;
        mHeightPixels = displayMetrics.heightPixels;

        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17) {
            try {
                mWidthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
                mHeightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
            } catch (Exception ignored) {
            }
        }

        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 17) {
            try {
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
                mWidthPixels = realSize.x;
                mHeightPixels = realSize.y;
            } catch (Exception ignored) {
            }
        }
    }

    private void initViews(View view) {
        mac_address_tv = view.findViewById(R.id.mac_address_tv);
        screenInchesTv = view.findViewById(R.id.screenInches_tv);
        UID_value_tv = view.findViewById(R.id.UID_value_tv);
        Brand_value_tv = view.findViewById(R.id.Brand_value_tv);
        Model_value_tv = view.findViewById(R.id.Model_value_tv);
        Manufacturer_value_tv = view.findViewById(R.id.Manufacturer_value_tv);
        Hardware_value_tv = view.findViewById(R.id.Hardware_value_tv);
        android_id_tv = view.findViewById(R.id.android_id_tv);
        Serial_nO_tv = view.findViewById(R.id.Serial_nO_value_tv);
        ScreenResolution_tv = view.findViewById(R.id.ScreenResolution_tv);
        screen_density_tv = view.findViewById(R.id.screen_density_tv);
        BootLoader_tv = view.findViewById(R.id.BootLoader_tv);
        User_tv = view.findViewById(R.id.User_tv);
        Host = view.findViewById(R.id.Host_tv);
        Version_tv = view.findViewById(R.id.Version_tv);
        Fingerprint_tv = view.findViewById(R.id.Fingerprint_tv);
        system_tv = view.findViewById(R.id.system_tv);
    }

}