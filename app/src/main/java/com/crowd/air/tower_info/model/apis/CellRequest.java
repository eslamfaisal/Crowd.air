package com.crowd.air.tower_info.model.apis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CellRequest implements Serializable {

    @SerializedName("lac")
    @Expose
    private Integer lac;
    @SerializedName("cid")
    @Expose
    private Integer cid;
    @SerializedName("psc")
    @Expose
    private Integer psc;

    public CellRequest() {
    }

    /**
     * @param psc
     * @param lac
     * @param cid
     */
    public CellRequest(Integer lac, Integer cid, Integer psc) {
        super();
        this.lac = lac;
        this.cid = cid;
        this.psc = psc;
    }

    public Integer getLac() {
        return lac;
    }

    public void setLac(Integer lac) {
        this.lac = lac;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getPsc() {
        return psc;
    }

    public void setPsc(Integer psc) {
        this.psc = psc;
    }

}
