package com.desafiolatam.stressless.adapters;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.desafiolatam.stressless.R;
import com.desafiolatam.stressless.data.Pendings;
import com.desafiolatam.stressless.models.Pending;
import com.desafiolatam.stressless.views.main.list.PendingListener;

import java.util.List;

/**
 * Created by Mai_Clear on 9/27/16.
 */

public class PendingsAdapter extends RecyclerView.Adapter<PendingsAdapter.ViewHolder> {

    private List<Pending> pendingList = new Pendings().all();
    private PendingListener listener;

    public PendingsAdapter(PendingListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pending, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Pending pending = pendingList.get(position);

        holder.status.setChecked(pending.isDone());

        holder.name.setText(pending.getName());

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long id = pending.getId();
                listener.click(id, position);
            }
        });

        holder.status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                pending.setDone(true);
                                pending.save();
                                pendingList.remove(position);
                                notifyDataSetChanged();

                            } catch (IndexOutOfBoundsException e) {

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

    public void addPending(Pending pending) {
        pendingList.add(0, pending);
        notifyDataSetChanged();
    }

    public void search(String name) {
        pendingList.clear();
        List<Pending> pendings = new Pendings().byName(name);
        if (pendings.size() > 0) {
            pendingList.addAll(pendings);
        }
        notifyDataSetChanged();
    }

    public void reset() {
        pendingList.clear();
        List<Pending> pendings = new Pendings().all();
        if (pendings.size() > 0) {
            pendingList.addAll(pendings);
        }
        notifyDataSetChanged();
    }

    public void delete(int position) {
        pendingList.get(position).delete();
        pendingList.remove(position);
        notifyDataSetChanged();

    }
}
