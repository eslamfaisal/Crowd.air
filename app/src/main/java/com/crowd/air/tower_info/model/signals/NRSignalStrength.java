package com.crowd.air.tower_info.model.signals;

public class NRSignalStrength {

    private int signalLevel;
    private int dbm;
    private int asuLevel;



    public NRSignalStrength() {
    }

    public int getSignalLevel() {
        return signalLevel;
    }

    public void setSignalLevel(int signalLevel) {
        this.signalLevel = signalLevel;
    }

    public int getDbm() {
        return dbm;
    }

    public void setDbm(int dbm) {
        this.dbm = dbm;
    }

    public int getAsuLevel() {
        return asuLevel;
    }

    public void setAsuLevel(int asuLevel) {
        this.asuLevel = asuLevel;
    }

    @Override
    public String toString() {
        return "NRSignalStrength{" +
                "signalLevel=" + signalLevel +
                ", dbm=" + dbm +
                ", asuLevel=" + asuLevel +
                '}';
    }
}
