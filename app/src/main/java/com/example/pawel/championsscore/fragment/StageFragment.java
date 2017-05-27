package com.example.pawel.championsscore.fragment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.pawel.championsscore.MainActivity;
import com.example.pawel.championsscore.R;
import com.example.pawel.championsscore.adapter.StageAdapter;
import com.example.pawel.championsscore.model.Stage;
import com.example.pawel.championsscore.model.db.Competition;
import com.example.pawel.championsscore.model.db.CompetitionDao;
import com.example.pawel.championsscore.model.db.DaoSession;
import com.example.pawel.championsscore.model.db.RoundDao;
import com.example.pawel.championsscore.model.webservice.Round;
import com.example.pawel.championsscore.service.StageService;
import com.example.pawel.championsscore.utils.WSBeanConverter;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class StageFragment extends Fragment {
    private static final String URL = "https://api.crowdscores.com/v1/rounds?competition_ids=36";
    private OnStageSelectedListener mCallback;
    private StageService stageService = new StageService();
    private ListView ls = null;
    private RoundDao roundDao;
    private CompetitionDao competitionDao;
    private Query<com.example.pawel.championsscore.model.db.Round> roundQuery;

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


        DaoSession daoSession = ((MainActivity) getActivity()).getDaoSession();
        roundDao = daoSession.getRoundDao();
        competitionDao = daoSession.getCompetitionDao();

        View view = inflater.inflate(R.layout.activity_stages, container, false);
        ls = (ListView) view.findViewById(android.R.id.list);

        if (MainActivity.isConnected) {
            HttpRequestTask httpRequestTask = new HttpRequestTask();
            httpRequestTask.execute(URL);
        } else {
            List<Competition> list = competitionDao.loadAll();
            List<com.example.pawel.championsscore.model.db.Round> rounds = roundDao.loadAll();
            updateView(WSBeanConverter.convertRoundsToWS(rounds));
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
            headers.set("x-crowdscores-api-key", "0b5895d6750d45c6ba0871b8019e8b48");
            HttpEntity<?> entity = new HttpEntity<Object>(headers);
            ResponseEntity<Round[]> respEntity = restTemplate.exchange(URL, HttpMethod.GET, entity, Round[].class);
            List<Round> rounds = Arrays.asList(respEntity.getBody());
            return rounds;
        }

        @Override
        protected void onPostExecute(List<Round> rounds) {
            updateView(rounds);
            for (Round round : rounds) {
                com.example.pawel.championsscore.model.db.Round r = new com.example.pawel.championsscore.model.db.Round();
                r.setId(round.getId());
                r.setName(round.getName());
                Competition c = new Competition();
                c.setName(round.getCompetition().getName());
                c.setFlagUrl(round.getCompetition().getFlagUrl());
                c.setId(round.getCompetition().getId());
                competitionDao.insertOrReplaceInTx(c);
                r.setCompetition(c);
                roundDao.insertOrReplaceInTx(r);
            }

            List<com.example.pawel.championsscore.model.db.Round> ro = roundDao.loadAll();
            System.out.print(ro.toString());
        }
    }

    private void updateView(List<Round> rounds) {
        ls.setAdapter(new StageAdapter(getActivity(), R.layout.activity_stage, rounds));
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemId = ((Stage) parent.getAdapter().getItem(position)).getId();
                mCallback.onStageSelected(itemId);
                ls.setItemChecked(position, true);
            }
        });
    }
}