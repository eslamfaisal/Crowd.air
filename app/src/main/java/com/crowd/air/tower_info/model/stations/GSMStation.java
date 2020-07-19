package com.crowd.air.tower_info.model.stations;

import com.crowd.air.tower_info.model.signals.GSMSignalStrength;

public class GSMStation {


    private int psc;                        /* bsic for GSM, psc for WCDMA, pci for LTE,
                                               GSM has #getPsc() but always get Integer.MAX_VALUE,
                                               psc is undefined for GSM */

    private String mccString;               // Mobile Country Code in string version, null if unavailable.

    private String mncString;               // Mobile Network Code in string version, null if unavailable.

    private int arfcn;                      // Absolute RF Channel Number (or UMTS Absolute RF Channel Number for WCDMA)


    private String mobileNetworkOperator;   // a 5 or 6 character string (MCC+MNC), null if any field is unknown.

    private int bsic;


    private GSMSignalStrength gsmSignalStrength = new GSMSignalStrength();


    public GSMStation() {
    }

    public GSMSignalStrength getGsmSignalStrength() {
        return gsmSignalStrength;
    }

    public void setGsmSignalStrength(GSMSignalStrength gsmSignalStrength) {
        this.gsmSignalStrength = gsmSignalStrength;
    }

    public String getMncString() {
        return mncString;
    }

    public void setMncString(String mncString) {
        this.mncString = mncString;
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

    public int getArfcn() {
        return arfcn;
    }

    public void setArfcn(int arfcn) {
        this.arfcn = arfcn;
    }

    public int getPsc() {
        return psc;
    }

    public void setPsc(int psc) {
        this.psc = psc;
    }

    public int getBsic() {
        return bsic;
    }

    public void setBsic(int bsic) {
        this.bsic = bsic;
    }

    @Override
    public String toString() {
        return "GSMStation{" +
                "psc=" + psc +
                ", mccString='" + mccString + '\'' +
                ", mncString='" + mncString + '\'' +
                ", arfcn=" + arfcn +
                ", mobileNetworkOperator='" + mobileNetworkOperator + '\'' +
                ", bsic=" + bsic +
                ", gsmSignalStrength=" + gsmSignalStrength +
                '}';
    }
}