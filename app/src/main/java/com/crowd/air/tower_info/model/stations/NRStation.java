package com.crowd.air.tower_info.model.stations;

import com.crowd.air.tower_info.model.signals.NRSignalStrength;

public class NRStation {


    private NRSignalStrength nrSignalStrength = new NRSignalStrength();


    public NRStation() {
    }

    public NRSignalStrength getNrSignalStrength() {
        return nrSignalStrength;
    }

    public void setNrSignalStrength(NRSignalStrength nrSignalStrength) {
        this.nrSignalStrength = nrSignalStrength;
    }

    @Override
    public String toString() {
        return "NRStation{" +
                "nrSignalStrength=" + nrSignalStrength +
                '}';
    }
}