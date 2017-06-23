package com.example.pawel.championsscore.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.pawel.championsscore.MainActivity;
import com.example.pawel.championsscore.R;
import com.example.pawel.championsscore.adapter.StageAdapter;
import com.example.pawel.championsscore.dao.CompetitionDAO;
import com.example.pawel.championsscore.dao.RoundDAO;
import com.example.pawel.championsscore.model.webservice.Round;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class StageFragment extends Fragment {
    private static final int COMPETITION_ID = 36;
    private static final String URL = MainActivity.URL + "rounds?competition_ids=" + COMPETITION_ID;
    private OnStageSelectedListener mCallback;
    private RoundDAO roundDAO;
    private CompetitionDAO competitionDAO;
    private ListView ls;
    private ProgressDialog progress;

    public StageFragment(RoundDAO roundDAO, CompetitionDAO competitionDAO) {
        this.roundDAO = roundDAO;
        this.competitionDAO = competitionDAO;
    }

    public StageFragment() {
    }

    public interface OnStageSelectedListener {
        void onStageSelected(int position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ActionBar toolbar = ((MainActivity) getActivity()).getSupportActionBar();
        if (toolbar != null)
            toolbar.setTitle(getString(R.string.app_name) + " - " + getString(R.string.rounds));
        progress = ProgressDialog.show(getContext(), getString(R.string.loading),
                getString(R.string.loading_text), true);
        View view = inflater.inflate(R.layout.fragment_stages, container, false);
        ls = (ListView) view.findViewById(android.R.id.list);
        if (isConnected()) {
            HttpRequestTask httpRequestTask = new HttpRequestTask();
            httpRequestTask.execute(URL);
        } else {
            List<Round> rounds = roundDAO.findByCompetitionId(COMPETITION_ID);
            updateView(rounds);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnStageSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStageSelectedListener");
        }
    }

    private class HttpRequestTask extends AsyncTask<String, Void, List<Round>> {
        @Override
        protected List<Round> doInBackground(String... params) {
            if (isConnected()) {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpHeaders headers = new HttpHeaders();
                headers.set("x-crowdscores-api-key", MainActivity.API_KEY);
                HttpEntity<?> entity = new HttpEntity<>(headers);
                ResponseEntity<Round[]> respEntity = restTemplate.exchange(URL, HttpMethod.GET, entity, Round[].class);
                return Arrays.asList(respEntity.getBody());
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Round> rounds) {
            updateView(rounds);
        }
    }

    private void updateView(List<Round> rounds) {

        if (rounds != null && !rounds.isEmpty()) {
            sortRounds(rounds);
            ls.setAdapter(new StageAdapter(getActivity(), R.layout.view_stage, rounds));
            ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Round round = ((Round) parent.getAdapter().getItem(position));
                    long itemId = ((Round) parent.getAdapter().getItem(position)).getId();
                    mCallback.onStageSelected((int) itemId);
                    ls.setItemChecked(position, true);
                    if (isConnected()) {
                        competitionDAO.save(round.getCompetition());
                        roundDAO.save(round);
                    }
                }
            });
        } else
            showAlertDialog(getActivity());
        progress.dismiss();
    }

    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getActivity().getApplication().getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
    }

    public void showAlertDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton(getString(R.string.try_again), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progress.show();
                HttpRequestTask httpRequestTask = new HttpRequestTask();
                httpRequestTask.execute(URL);
            }
        });
        builder.setMessage(getString(R.string.no_internet_connection));
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void sortRounds(List<Round> rounds) {
        final List<String> definedOrder = Arrays.asList("Group A", "Group B", "Group C", "Group D", "Group E", "Group F", "Group G", "Group H", "Round of 16", "Quarter Finals", "Semi Finals", "Final");
        Collections.sort(rounds, new Comparator<Round>() {

            @Override
            public int compare(final Round o1, final Round o2) {
                return Integer.valueOf(
                        definedOrder.indexOf(o1.getName()))
                        .compareTo(definedOrder.indexOf(o2.getName()));
            }
        });

    }
}