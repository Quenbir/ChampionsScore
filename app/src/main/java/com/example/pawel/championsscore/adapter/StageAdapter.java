package com.example.pawel.championsscore.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.pawel.championsscore.R;
import com.example.pawel.championsscore.model.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz on 23.03.2017.
 */

public class StageAdapter extends ArrayAdapter<Stage> {
    private Context context;
    @SuppressWarnings("unused")
    private List<Stage> stages;

    private List<Button> buttons = new ArrayList<>();

    public StageAdapter(Context context, int resource, List <Stage> objects) {
        super(context, resource, objects);
        this.context = context;
        this.stages = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_stage, parent, false);

        final Stage stage = stages.get(position);

        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(stage.getName());
        textView.setTag(stage.getId());
        return view;
    }
}
