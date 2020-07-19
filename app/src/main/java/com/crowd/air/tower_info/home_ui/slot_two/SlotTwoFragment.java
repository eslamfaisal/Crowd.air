package com.crowd.air.tower_info.home_ui.slot_two;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crowd.air.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SlotTwoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SlotTwoFragment extends Fragment {

    public SlotTwoFragment() {
        // Required empty public constructor
    }


    public static SlotTwoFragment newInstance(int slotIndex) {
        SlotTwoFragment fragment = new SlotTwoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("slotIndex",slotIndex);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_slot_two, container, false);
    }
}