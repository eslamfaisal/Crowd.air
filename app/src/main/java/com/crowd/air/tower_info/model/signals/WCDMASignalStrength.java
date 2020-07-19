package com.crowd.air.tower_info.model.signals;

public class WCDMASignalStrength {

    private int signalLevel;
    private int dbm;
    private int asuLevel;
    private int ecNo;

    public WCDMASignalStrength() {
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

    public int getEcNo() {
        return ecNo;
    }

    public void setEcNo(int ecNo) {
        this.ecNo = ecNo;
    }

    @Override
    public String toString() {
        return "WCDMASignalStrength{" +
                "signalLevel=" + signalLevel +
                ", dbm=" + dbm +
                ", asuLevel=" + asuLevel +
                ", ecNo=" + ecNo +
                '}';
    }
}
