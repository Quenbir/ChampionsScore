package com.example.pawel.championsscore.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pawel.championsscore.R;
import com.example.pawel.championsscore.model.webservice.Round;

import java.util.List;


public class StageAdapter extends ArrayAdapter<Round> {

    private Context context;
    private List<Round> stages;

    public StageAdapter(Context context, int resource, List<Round> objects) {
        super(context, resource, objects);
        this.context = context;
        this.stages = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_stage, parent, false);

        final Round round = stages.get(position);

        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(round.getName());
        return view;
    }
}
