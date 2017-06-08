package com.example.pawel.championsscore.fragment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.pawel.championsscore.MainActivity;
import com.example.pawel.championsscore.R;
import com.example.pawel.championsscore.adapter.StageAdapter;
import com.example.pawel.championsscore.dao.CompetitionDAO;
import com.example.pawel.championsscore.dao.RoundDao;
import com.example.pawel.championsscore.model.webservice.Round;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


public class StageFragment extends Fragment {
    private static final int COMPETITION_ID = 36;
    private static final String URL = "https://api.crowdscores.com/v1/rounds?competition_ids=" + COMPETITION_ID;
    private OnStageSelectedListener mCallback;
    private RoundDao roundDAO;
    private CompetitionDAO competitionDAO;
    private ListView ls;

    public StageFragment(RoundDao roundDAO, CompetitionDAO competitionDAO) {
        this.roundDAO = roundDAO;
        this.competitionDAO = competitionDAO;
    }

    public StageFragment (){}

    public interface OnStageSelectedListener {
        void onStageSelected(int position);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_stages, container, false);
        ls = (ListView) view.findViewById(android.R.id.list);
        if (MainActivity.isConnected) {
            HttpRequestTask httpRequestTask = new HttpRequestTask();
            httpRequestTask.execute(URL);
        } else {
            List<Round> rounds = roundDAO.findByCompetitionId(COMPETITION_ID);
            updateView(rounds);
        }
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

    private class HttpRequestTask extends AsyncTask<String, Void, List<Round>> {
        @Override
        protected List<Round> doInBackground(String... params) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-crowdscores-api-key", MainActivity.API_KEY);
            HttpEntity<?> entity = new HttpEntity<Object>(headers);
            ResponseEntity<Round[]> respEntity = restTemplate.exchange(URL, HttpMethod.GET, entity, Round[].class);
            return Arrays.asList(respEntity.getBody());
        }

        @Override
        protected void onPostExecute(List<Round> rounds) {
            updateView(rounds);
            for (Round round : rounds) {
                competitionDAO.save(round.getCompetition());
                roundDAO.save(round);
            }
        }
    }

    private void updateView(List<Round> rounds) {
        ls.setAdapter(new StageAdapter(getActivity(), R.layout.activity_stage, rounds));
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long itemId = ((Round) parent.getAdapter().getItem(position)).getId();
                mCallback.onStageSelected((int)itemId);
                ls.setItemChecked(position, true);
            }
        });
    }
}