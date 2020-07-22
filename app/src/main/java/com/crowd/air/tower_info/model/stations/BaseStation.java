package com.crowd.air.tower_info.model.stations;

import com.crowd.air.tower_info.StationType;

public class BaseStation {

    // region Common fields

    private StationType type;               // Signal type, GSM or WCDMA or LTE or CDMA

    private int mcc;                        // Mobile Country Code

    private int mnc;                        // Mobile Network Code

    private int lac;                        // Location Area Code or TAC(Tracking Area Code) for LTE

    private int tac;                        // Track Area Code or TAC(Tracking Area Code) for LTE

    private int cid;                        // Cell Identity

    private double lon;                     // Base station longitude

    private double lat;                     // Base station latitude

    private int bsic_psc_pci;                        /* bsic for GSM, psc for WCDMA, pci for LTE,
                                               GSM has #getPsc() but always get Integer.MAX_VALUE,
                                               psc is undefined for GSM */


    private GSMStation gsmStation = new GSMStation();

    private WCDMAStation wcdmaStation = new WCDMAStation();

    private LTEStation lteStation = new LTEStation();

    private NRStation nrStation = new NRStation();


    public BaseStation() {
    }

    public int getTac() {
        return tac;
    }


    public int getBsic_psc_pci() {
        return bsic_psc_pci;
    }

    public void setBsic_psc_pci(int bsic_psc_pci) {
        this.bsic_psc_pci = bsic_psc_pci;
    }

    public void setTac(int tac) {
        this.tac = tac;
    }


    public StationType getType() {
        return type;
    }

    public void setType(StationType type) {
        this.type = type;
    }

    public int getMcc() {
        return mcc;
    }

    public void setMcc(int mcc) {
        this.mcc = mcc;
    }

    public int getMnc() {
        return mnc;
    }

    public void setMnc(int mnc) {
        this.mnc = mnc;
    }

    public int getLac() {
        return lac;
    }

    public void setLac(int lac) {
        this.lac = lac;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public GSMStation getGsmStation() {
        return gsmStation;
    }

    public void setGsmStation(GSMStation gsmStation) {
        this.gsmStation = gsmStation;
    }

    public WCDMAStation getWcdmaStation() {
        return wcdmaStation;
    }

    public void setWcdmaStation(WCDMAStation wcdmaStation) {
        this.wcdmaStation = wcdmaStation;
    }

    public LTEStation getLteStation() {
        return lteStation;
    }

    public void setLteStation(LTEStation lteStation) {
        this.lteStation = lteStation;
    }

    public NRStation getNrStation() {
        return nrStation;
    }

    public void setNrStation(NRStation nrStation) {
        this.nrStation = nrStation;
    }

    @Override
    public String toString() {
        return "BaseStation{" +
                "type=" + type +
                ", mcc=" + mcc +
                ", mnc=" + mnc +
                ", lac=" + lac +
                ", tac=" + tac +
                ", cid=" + cid +
                ", lon=" + lon +
                ", lat=" + lat +
                ", gsmStation=" + gsmStation +
                ", wcdmaStation=" + wcdmaStation +
                ", lteStation=" + lteStation +
                ", nrStation=" + nrStation +
                '}';
    }
}
