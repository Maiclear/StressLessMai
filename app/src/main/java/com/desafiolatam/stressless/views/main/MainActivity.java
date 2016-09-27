package com.desafiolatam.stressless.views.main;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.desafiolatam.stressless.R;
import com.desafiolatam.stressless.models.Pending;
import com.desafiolatam.stressless.views.MainActivityFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CreationCallback {

    private EditText pendingInput;
    private Dialog dialog;
    private MainActivityFragment mainActivityFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        List<Pending> pendings = Pending.listAll(Pending.class);
        if (pendings != null && pendings.size() > 0) {
            for (int i = 0; i < pendings.size(); i++) {
                Log.d("Pendings",pendings.get(i).getName());
            }
        }else {
            Log.d("Pending", "no hay");
        }

        mainActivityFragment = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment);

        setFab();

    }

    private void setFab() {

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_create_pending);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);


        pendingInput = (EditText) dialog.findViewById(R.id.pendingInput);
        ImageButton saveBtn = (ImageButton) dialog.findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               getPendingName();

            }
        });

//ahpra seteamos que funcione el btn von el enter (desde el key board (soft keyboard) para escuchar al teclado
        pendingInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
                    getPendingName();
                    return true;
                }
                return false;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialog.show();
            }
        });





    }

    private void getPendingName() {
        String pendingName = pendingInput.getText().toString();
        new CreatePending(this).validation(pendingName);

    }



    @Override
    public void created(Pending pending) {
        mainActivityFragment.addPending(pending);
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        }catch (NullPointerException e) {

        }

        dialog.dismiss();

    }

    @Override
    public void noName() {
        pendingInput.setError("!");
    }
}
