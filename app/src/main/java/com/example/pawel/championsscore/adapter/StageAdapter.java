package com.example.pawel.championsscore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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
    private int resource;

    public StageAdapter(Context context, int resource, List<Round> objects) {
        super(context, resource, objects);
        this.context = context;
        this.stages = objects;
        this.resource = resource;
    }

    @Override
    public
    @NonNull
    View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        final Round round = stages.get(position);
        TextView textView = (TextView) convertView.findViewById(R.id.textView);
        textView.setText(round.getName());
        return convertView;
    }
}
