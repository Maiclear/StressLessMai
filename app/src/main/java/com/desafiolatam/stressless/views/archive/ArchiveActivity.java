package com.desafiolatam.stressless.views.archive;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.desafiolatam.stressless.R;
import com.desafiolatam.stressless.adapters.DoneAdapter;

public class ArchiveActivity extends AppCompatActivity implements DoneListener{

    private ActionBar actionBar;
    private int restaured = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.doneRv);
        DoneAdapter adapter = new DoneAdapter(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void click() {
        restaured ++;
        actionBar.setTitle(getString(R.string.archive_restore, restaured));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
           onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (restaured > 0) {
            setResult(RESULT_OK);
        }else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }
}
