package com.crowd.air.tower_info.model.stations;

import com.crowd.air.tower_info.model.signals.LTESignalStrength;

import java.util.Set;

public class LTEStation {

    private int pci;
    private int earfcn;
    private int bandwidth;
    private String mccString;
    private String mncString;
    private String mobileNetworkOperator;
    private LTESignalStrength lteSignalStrength = new LTESignalStrength();

    public void setPci(int pci) {

        this.pci = pci;
    }

    public LTESignalStrength getLteSignalStrength() {
        return lteSignalStrength;
    }

    public void setLteSignalStrength(LTESignalStrength lteSignalStrength) {
        this.lteSignalStrength = lteSignalStrength;
    }

    public int getPci() {
        return pci;
    }


    public void setEarfcn(int earfcn) {
        this.earfcn = earfcn;
    }

    public int getEarfcn() {
        return earfcn;
    }

    public void setBandwidth(int bandwidth) {

        this.bandwidth = bandwidth;
    }

    public int getBandwidth() {
        return bandwidth;
    }

    public void setMccString(String mccString) {
        this.mccString = mccString;
    }

    public String getMccString() {
        return mccString;
    }

    public void setMncString(String mncString) {
        this.mncString = mncString;
    }

    public String getMncString() {
        return mncString;
    }

    public void setMobileNetworkOperator(String mobileNetworkOperator) {
        this.mobileNetworkOperator = mobileNetworkOperator;
    }

    public String getMobileNetworkOperator() {
        return mobileNetworkOperator;
    }


    @Override
    public String toString() {
        return "LTEStation{" +
                "pci=" + pci +
                ", earfcn=" + earfcn +
                ", bandwidth=" + bandwidth +
                ", mccString='" + mccString + '\'' +
                ", mncString='" + mncString + '\'' +
                ", mobileNetworkOperator='" + mobileNetworkOperator + '\'' +
                ", lteSignalStrength=" + lteSignalStrength +
                '}';
    }
}
