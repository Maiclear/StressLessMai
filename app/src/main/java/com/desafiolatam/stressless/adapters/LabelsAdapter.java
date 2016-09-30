package com.desafiolatam.stressless.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.desafiolatam.stressless.R;
import com.desafiolatam.stressless.models.Label;

import java.util.List;

/**
 * Created by Mai_Clear on 9/30/16.
 */

public class LabelsAdapter extends ArrayAdapter<Label> {

    public LabelsAdapter(Context context, int resource, List<Label> objects) {
        super(context, resource, objects);
    }

    private class ViewChildHolder {
        View colorHolder;
        TextView name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getConvertView(position, convertView, parent, R.layout.list_item_label_prompt);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getConvertView(position, convertView, parent, R.layout.list_item_colored_label);
    }

    public View getConvertView(int position, View convertView, ViewGroup parent, int resource){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewChildHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewChildHolder();
            holder.colorHolder = convertView.findViewById(R.id.colorPicker);
            holder.name = (TextView) convertView.findViewById(R.id.labelName);
            convertView.setTag(holder);
        } else {
            holder = (ViewChildHolder) convertView.getTag();
        }

        Label label = getItem(position);
        holder.colorHolder.setBackgroundColor(Color.parseColor(label.getColor()));
        holder.name.setText(label.getName());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        long id = (getItem(position).getId() != null) ? getItem(position).getId() : 0 ;
        return id;
    }
}