package com.crowd.air.tower_info.model.signals;

public class GSMSignalStrength {

    private int rssi;
    private int dbm;
    private int signalLevel;
    private int asuLevel;

    public GSMSignalStrength() {
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public int getDbm() {
        return dbm;
    }

    public void setDbm(int dbm) {
        this.dbm = dbm;
    }

    public int getSignalLevel() {
        return signalLevel;
    }

    public void setSignalLevel(int signalLevel) {
        this.signalLevel = signalLevel;
    }

    public int getAsuLevel() {
        return asuLevel;
    }

    public void setAsuLevel(int asuLevel) {
        this.asuLevel = asuLevel;
    }

    @Override
    public String toString() {
        return "GSMSignalStrength{" +
                "rssi=" + rssi +
                ", dbm=" + dbm +
                ", signalLevel=" + signalLevel +
                ", asuLevel=" + asuLevel +
                '}';
    }
}
