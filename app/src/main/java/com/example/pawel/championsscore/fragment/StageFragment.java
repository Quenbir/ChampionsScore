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
import com.example.pawel.championsscore.adapter.StageAdapter;
import com.example.pawel.championsscore.model.Stage;
import com.example.pawel.championsscore.service.StageService;


public class StageFragment extends Fragment {
    private OnStageSelectedListener mCallback;
    private StageService stageService = new StageService();
    private ListView ls = null;

    public interface OnStageSelectedListener {

        void onStageSelected(int position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_stages, container, false);
        ls = (ListView) view.findViewById(android.R.id.list);
        ls.setAdapter(new StageAdapter(getActivity(), R.layout.activity_stage, stageService.getAllStages()));

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemId = ((Stage) parent.getAdapter().getItem(position)).getId();
                mCallback.onStageSelected(itemId);
                ls.setItemChecked(position, true);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnStageSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnStageSelectedListener");
        }
    }
}