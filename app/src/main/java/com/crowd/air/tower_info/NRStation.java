package com.crowd.air.tower_info;

import com.crowd.air.tower_info.model.stations.BaseStation;

public class NRStation extends BaseStation {
    private int arfcn;          // Absolute RF Channel Number (or UMTS Absolute RF Channel Number for WCDMA)

    private int bsic_psc_pci;   /* bsic for GSM, psc for WCDMA, pci for LTE,
                                   GSM has #getPsc() but always get Integer.MAX_VALUE,
                                   psc is undefined for GSM */
}
