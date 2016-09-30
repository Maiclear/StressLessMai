package com.desafiolatam.stressless.data;

import com.desafiolatam.stressless.models.Label;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mai_Clear on 9/30/16.
 */

public class Labels {

    public List<Label> all() {

        List<Label> labels = new ArrayList<>();
        List<Label> labelList = Label.listAll(Label.class);
        if (labelList != null && labelList.size() >0 ) {
            labels.addAll(labelList);
        }
        return labels;
    }
}
