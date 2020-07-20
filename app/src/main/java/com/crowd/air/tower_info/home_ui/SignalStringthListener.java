package com.crowd.air.tower_info.home_ui;

import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;

public class SignalStringthListener extends PhoneStateListener {

    @Override
    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
        super.onSignalStrengthsChanged(signalStrength);

    }
}
