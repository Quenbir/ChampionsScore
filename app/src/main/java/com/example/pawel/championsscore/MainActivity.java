package com.example.pawel.championsscore;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.pawel.championsscore.adapter.StageAdapter;
import com.example.pawel.championsscore.model.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Stage> stages = new ArrayList<>(Arrays.asList(new Stage("1/8"),new Stage("1/4"),new Stage("1/2"),new Stage("final")));

        StageAdapter adapter = new StageAdapter(this, R.layout.activity_stage, stages);
        setListAdapter(adapter);

    }
}