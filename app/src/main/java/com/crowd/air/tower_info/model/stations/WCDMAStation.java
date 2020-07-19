package com.crowd.air.tower_info.model.stations;

import com.crowd.air.tower_info.model.signals.WCDMASignalStrength;

import java.util.Set;

public class WCDMAStation {

    private int psc;
    private String mobileNetworkOperator;
    private String mccString;
    private String mncString;
    private int uarfcn;
    private WCDMASignalStrength wcdmaSignalStrength = new WCDMASignalStrength();

    public WCDMAStation() {
    }

    public WCDMASignalStrength getWcdmaSignalStrength() {
        return wcdmaSignalStrength;
    }

    public void setWcdmaSignalStrength(WCDMASignalStrength wcdmaSignalStrength) {
        this.wcdmaSignalStrength = wcdmaSignalStrength;
    }

    public int getPsc() {
        return psc;
    }

    public void setPsc(int psc) {
        this.psc = psc;
    }

    public String getMobileNetworkOperator() {
        return mobileNetworkOperator;
    }

    public void setMobileNetworkOperator(String mobileNetworkOperator) {
        this.mobileNetworkOperator = mobileNetworkOperator;
    }

    public String getMccString() {
        return mccString;
    }

    public void setMccString(String mccString) {
        this.mccString = mccString;
    }

    public String getMncString() {
        return mncString;
    }

    public void setMncString(String mncString) {
        this.mncString = mncString;
    }

    public int getUarfcn() {
        return uarfcn;
    }

    public void setUarfcn(int uarfcn) {
        this.uarfcn = uarfcn;
    }

    @Override
    public String toString() {
        return "WCDMAStation{" +
                "psc=" + psc +
                ", mobileNetworkOperator='" + mobileNetworkOperator + '\'' +
                ", mccString='" + mccString + '\'' +
                ", mncString='" + mncString + '\'' +
                ", uarfcn=" + uarfcn +
                ", wcdmaSignalStrength=" + wcdmaSignalStrength +
                '}';
    }
}