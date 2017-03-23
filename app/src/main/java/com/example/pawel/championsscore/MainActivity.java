package com.example.pawel.championsscore;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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

        final ListView ls = (ListView) findViewById(android.R.id.list);

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MatchesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }
}