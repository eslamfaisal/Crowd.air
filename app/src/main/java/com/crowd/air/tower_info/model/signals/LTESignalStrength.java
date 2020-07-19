package com.crowd.air.tower_info.model.signals;

public class LTESignalStrength {


    private int rsrq;
    private int rssi;
    private int rssnr;
    private int rsrp;
    private int cqi;
    private int dbm;
    private int asuLevel;
    private int signalLevel;
    private int timingAdvance;

    public LTESignalStrength() {
    }

    public int getAsuLevel() {
        return asuLevel;
    }

    public void setAsuLevel(int asuLevel) {
        this.asuLevel = asuLevel;
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

    public int getRsrq() {
        return rsrq;
    }

    public void setRsrq(int rsrq) {
        this.rsrq = rsrq;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public int getRssnr() {
        return rssnr;
    }

    public void setRssnr(int rssnr) {
        this.rssnr = rssnr;
    }

    public int getRsrp() {
        return rsrp;
    }

    public void setRsrp(int rsrp) {
        this.rsrp = rsrp;
    }

    public int getCqi() {
        return cqi;
    }

    public void setCqi(int cqi) {
        this.cqi = cqi;
    }

    public int getTimingAdvance() {
        return timingAdvance;
    }

    public void setTimingAdvance(int timingAdvance) {
        this.timingAdvance = timingAdvance;
    }

    @Override
    public String toString() {
        return "LTESignalStrength{" +
                "signalLevel=" + signalLevel +
                ", rsrq=" + rsrq +
                ", rssi=" + rssi +
                ", rssnr=" + rssnr +
                ", rsrp=" + rsrp +
                ", cqi=" + cqi +
                ", dbm=" + dbm +
                ", asuLevel=" + asuLevel +
                ", timingAdvance=" + timingAdvance +
                '}';
    }
}
