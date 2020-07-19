package com.crowd.air.tower_info.home_ui;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.crowd.air.R;
import com.crowd.air.tower_info.home_ui.fragments.DeviceInfoFragment;
import com.crowd.air.tower_info.home_ui.slot_one.SlotOneFragment;
import com.crowd.air.tower_info.home_ui.slot_two.SlotTwoFragment;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class HomeSectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.slot_one_text, R.string.slot_two_text, R.string.device_text};
    private final Context mContext;

    public HomeSectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return SlotOneFragment.newInstance(0);
            case 1:
                return SlotOneFragment.newInstance(1);
            default:
                return DeviceInfoFragment.newInstance();
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }
}