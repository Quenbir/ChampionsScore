package com.example.pawel.championsscore.service;


import com.example.pawel.championsscore.model.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StageService {

    public List<Stage> getAllStages() {
        return new ArrayList<Stage>(Arrays.asList(new Stage(1, "1/8"), new Stage(2, "1/4"), new Stage(3, "1/2"), new Stage(4, "final")));

    }

    public Stage getStageById(int stageId) {
        for (Stage stage : getAllStages()) {
            if (stage.getId() == stageId)
                return stage;
        }
        return null;
    }
}
