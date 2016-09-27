package com.desafiolatam.stressless.data;

import com.desafiolatam.stressless.models.Pending;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mai_Clear on 9/27/16.
 */

public class Pendings {

    public List<Pending> all() {
        List<Pending> pendings = new ArrayList<>();
        List<Pending> pendingList = Pending.listAll(Pending.class);
        if (pendingList != null && pendingList.size() > 0) {
            pendings.addAll(pendingList);
        }
        return pendings;
    }

}
