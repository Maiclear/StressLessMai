package com.desafiolatam.stressless.adapters;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.desafiolatam.stressless.R;
import com.desafiolatam.stressless.data.Pendings;
import com.desafiolatam.stressless.models.Pending;
import com.desafiolatam.stressless.views.archive.DoneListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mai_Clear on 9/27/16.
 */

public class DoneAdapter extends RecyclerView.Adapter<DoneAdapter.ViewHolder> {

    private List<Pending> pendingList = new Pendings().dones();
    private DoneListener listener;

    public DoneAdapter(DoneListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pending, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.setIsRecyclable(false);

        final Pending pending = pendingList.get(position);

        holder.name.setText(pending.getName());

        final int auxPosition = position;
        holder.status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    pending.setDone(false);
                    pending.save();
                    listener.click();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            try{
                                pendingList.remove(auxPosition);
                                notifyItemRemoved(auxPosition);
                            }catch (IndexOutOfBoundsException e) {

                            }

                        }
                    }, 100);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return pendingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox status;
        TextView name;
        //Same that above, create a subclass which you will use to access your views
        //But this time, the inflating is above and the finding is here

        ViewHolder(View view) {
            super(view);
            status = (CheckBox) view.findViewById(R.id.pendingCb);
            name = (TextView) view.findViewById(R.id.pendingName);

        }

    }



}
