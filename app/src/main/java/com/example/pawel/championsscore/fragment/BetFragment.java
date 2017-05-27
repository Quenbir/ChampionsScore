package com.example.pawel.championsscore.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pawel.championsscore.R;

public class BetFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.view_bet, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
