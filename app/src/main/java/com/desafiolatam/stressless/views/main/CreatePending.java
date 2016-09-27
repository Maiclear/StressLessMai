package com.desafiolatam.stressless.views.main;

import com.desafiolatam.stressless.models.Pending;

/**
 * Created by Mai_Clear on 9/27/16.
 */

public class CreatePending {
    private CreationCallback callback;

    public CreatePending(CreationCallback callback) {
        this.callback = callback;
    }

    public void validation(String name) {

        if (name.length() >0 ){
            Pending pending = new Pending();
            pending.setName(name);
            pending.save();
            callback.created(pending);

        }else {
            callback.noName();

        }


    }
}
