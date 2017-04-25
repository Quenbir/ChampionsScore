package com.example.pawel.championsscore.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.pawel.championsscore.R;
import com.example.pawel.championsscore.adapter.MatchAdapter;
import com.example.pawel.championsscore.service.MatchService;


/**
 * Created by Mateusz on 25.04.2017.
 */

public class MatchesFragment extends Fragment {
    public final static String ARG_POSITION = "position";
    private int mCurrentPosition = -1;
    private OnMatchSelectedListener mCallback;
    private ListView ls = null;
    private MatchService matchService = new MatchService();


    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnMatchSelectedListener {
        /**
         * Called by StageFragment when a list item is selected
         */
        public void onMatchSelected(int position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }

        View view = inflater.inflate(R.layout.activity_matches, container, false);
        ls = (ListView) view.findViewById(android.R.id.list);

        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateMachtesView(args.getInt(ARG_POSITION));
        } else if (mCurrentPosition != -1) {
            // Set article based on saved instance state defined during onCreateView
            updateMachtesView(mCurrentPosition);
        }


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnMatchSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnMatchSelectedListener");
        }
    }

    public void updateMachtesView(int position) {

        ls.setAdapter(new MatchAdapter(getActivity(), R.layout.activity_match, matchService.getMatches(position)));

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Notify the parent activity of selected item
                mCallback.onMatchSelected(position);

                // Set the item as checked to be highlighted when in two-pane layout
                ls.setItemChecked(position, true);
            }
        });

        mCurrentPosition = position;
    }

}