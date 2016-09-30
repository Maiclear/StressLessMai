package com.desafiolatam.stressless.views.details;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.ColorInt;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.desafiolatam.stressless.R;
import com.desafiolatam.stressless.adapters.LabelsAdapter;
import com.desafiolatam.stressless.data.Labels;
import com.desafiolatam.stressless.models.Label;
import com.desafiolatam.stressless.models.Pending;
import com.desafiolatam.stressless.views.main.list.PendingListFragment;
import com.thebluealliance.spectrum.SpectrumPalette;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private Pending pending;
    private EditText descriptionEt;
    private Spinner labelDd;
    private ActionBar actionBar;
    private List<Label> labels = new ArrayList<>();
    private LabelsAdapter labelsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        long id = getIntent().getLongExtra(PendingListFragment.PENDING_ID, 0);
        pending = Pending.findById(Pending.class,id);

        descriptionEt = (EditText) findViewById(R.id.descriptionEt);
        labelDd = (Spinner) findViewById(R.id.labelDd);


        String description = pending.getDescription();
        if (description != null) {
            descriptionEt.setText(description);


        }

        actionBar = getSupportActionBar();
        actionBar.setTitle(pending.getName());

        labels = new Labels().all();

        Label prompt = new Label("Seleccione una etiqueta o Cree una","FFFFFF");
        labels.add(0, prompt);

        labelsAdapter = new LabelsAdapter(this, 0, labels);

        labelDd.setAdapter(labelsAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.labelMenu:
                final Dialog dialog = new Dialog(this, R.style.FullscreenTheme);
                dialog.setContentView(R.layout.dialog_pending_label);

                final EditText laberName = (EditText) dialog.findViewById(R.id.labelName);
                SpectrumPalette spectrumPalette = (SpectrumPalette) dialog.findViewById(R.id.colorPicker);
                int[] colors = getResources().getIntArray(R.array.demo_colors);
                spectrumPalette.setColors(colors);
                spectrumPalette.setOnColorSelectedListener(new SpectrumPalette.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(@ColorInt int color) {
                        Label label = new Label(laberName.getText().toString(),Integer.toHexString(color).toUpperCase());
                        label.save();
                        pending.setCategory_id(label.getId());
                        pending.save();
                        labels.add(0,label);
                        labelsAdapter.notifyDataSetChanged();
                        labelDd.setSelection(0);
                        dialog.dismiss();
                    }
                });

                dialog.show();
                return true;
            case R.id.deleteMenu:
                Intent intent = new Intent();
                intent.putExtra(PendingListFragment.PENDING_POSITION, getIntent().getIntExtra(PendingListFragment.PENDING_POSITION,0));
                setResult(RESULT_CANCELED, intent);
                finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onPause() {
        String description = descriptionEt.getText().toString();
        pending.setDescription(description);
        pending.save();
        super.onPause();
    }
}
