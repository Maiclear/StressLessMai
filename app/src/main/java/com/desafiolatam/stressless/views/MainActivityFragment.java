package com.desafiolatam.stressless.views;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.desafiolatam.stressless.R;
import com.desafiolatam.stressless.adapters.PendingsAdapter;
import com.desafiolatam.stressless.models.Pending;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {


    private PendingsAdapter adapter;
    private RecyclerView recyclerView;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         recyclerView = (RecyclerView) view.findViewById(R.id.pendingRecycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setLayoutManager(layoutManager);

        adapter = new PendingsAdapter();

        recyclerView.setAdapter(adapter);

    }

    public void addPending(Pending pending) {
        adapter.addPending(pending);
        recyclerView.scrollToPosition(0);
    }
}
