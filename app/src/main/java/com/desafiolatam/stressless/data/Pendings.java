package com.desafiolatam.stressless.data;

import android.util.Log;

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

    public  List<String> names() {
        List<String> names = new ArrayList<>();
        List<Pending> pendings = all();
        if (pendings != null && pendings.size() > 0) {
            for (Pending pending : pendings) {
                names.add(pending.getName());
            }
        }
        return names;
    }

    public List<Pending> byName(String name) {
        List<Pending> pendings = new ArrayList<>();
        String query = "name LIKE" + "'%" + name +"%'";
        List<Pending> pendingList = Pending.find(Pending.class, query);
        if (pendingList != null && pendingList.size() > 0 ) {
            pendings.addAll(pendingList);
        }
        return pendings;
    }
}
