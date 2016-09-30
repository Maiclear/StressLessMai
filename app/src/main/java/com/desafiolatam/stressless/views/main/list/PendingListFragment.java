package com.desafiolatam.stressless.views.main.list;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.desafiolatam.stressless.R;
import com.desafiolatam.stressless.adapters.PendingsAdapter;
import com.desafiolatam.stressless.models.Pending;
import com.desafiolatam.stressless.views.details.DetailsActivity;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A placeholder fragment containing a simple view.
 */
public class PendingListFragment extends Fragment implements PendingListener {


    private PendingsAdapter adapter;
    private RecyclerView recyclerView;
    public static final String PENDING_ID = "PENDING_ID";
    public static final String PENDING_POSITION = "Pending_POSITION";
    public static final int DETAILS_INTENT = 100;

    public PendingListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_pending, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         recyclerView = (RecyclerView) view.findViewById(R.id.pendingRecycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setLayoutManager(layoutManager);

        adapter = new PendingsAdapter(this);

        recyclerView.setAdapter(adapter);

        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.pendingSRL);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.reset();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (refreshLayout.isRefreshing()) {
                            refreshLayout.setRefreshing(false);
                        }
                    }
                }, 800);
            }
        });

    }

    public void addPending(Pending pending) {
        adapter.addPending(pending);
        recyclerView.scrollToPosition(0);
    }

    public void search(String name) {
        adapter.search(name);
    }

    public void reset(){
        adapter.reset();
    }

    @Override
    public void click(long id , int position) {
        Intent intent = new Intent(getContext(),DetailsActivity.class);
        intent.putExtra(PENDING_ID, id);
        intent.putExtra(PENDING_POSITION, position);
        startActivityForResult(intent, DETAILS_INTENT);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (DETAILS_INTENT == requestCode) {
            if (RESULT_OK == resultCode) {

            }else {
                int position = data.getIntExtra(PENDING_POSITION, 0);
                adapter.delete(position);
            }
        }
    }
}
