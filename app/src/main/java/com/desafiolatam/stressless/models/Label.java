package com.desafiolatam.stressless.models;

import com.orm.SugarRecord;

/**
 * Created by Mai_Clear on 9/30/16.
 */

public class Label extends SugarRecord {

    private String name, color;


    public Label() {
    }

    public Label(String name, String color) {
        this.name = name;
        this.color ="#" + color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
