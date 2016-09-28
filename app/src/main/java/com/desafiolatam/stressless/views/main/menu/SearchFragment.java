package com.desafiolatam.stressless.views.main.menu;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.desafiolatam.stressless.R;
import com.desafiolatam.stressless.data.Pendings;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private static final int EXPANDED = 1;
    private static final int COLLAPSED = 0;
    private AutoCompleteTextView autocompleteTv;
    private ImageView control;
    private List<String> names;
    private ArrayAdapter<String> adapter;

    private SearchListener listener;


    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (SearchListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        autocompleteTv = (AutoCompleteTextView) view.findViewById(R.id.searchBar);

        autocompleteTv.animate().translationX(1500).setDuration(10).start();

        control = (ImageView) view.findViewById(R.id.searchIv);
        control.setTag(COLLAPSED);

        control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int value = (int) control.getTag();
                if (value == COLLAPSED) {
                    control.setImageResource(R.mipmap.ic_close_white_24dp);
                    control.setTag(EXPANDED);
                    autocompleteTv.requestFocus();
                    autocompleteTv.animate().translationX(0).setDuration(400).start();
                    try {
                        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.showSoftInput(autocompleteTv, InputMethodManager.SHOW_FORCED);
                    } catch (NullPointerException e) {

                    }
                } else {
                    control.setImageResource(R.mipmap.ic_search_white_24dp);
                    control.setTag(COLLAPSED);
                    autocompleteTv.animate().translationX(1500).setDuration(800).start();
                    hidekeyboard();
                }

            }
        });

        setCompletion();
    }

    private void setCompletion() {
        names = new Pendings().names();
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, names);
        autocompleteTv.setAdapter(adapter);

        autocompleteTv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = autocompleteTv.getText().toString();
                hidekeyboard();
                listener.search(name);
            }
        });
        autocompleteTv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_SEARCH) {
                    String name = autocompleteTv.getText().toString();
                    hidekeyboard();
                    listener.search(name);
                    autocompleteTv.dismissDropDown();

                    return true;
                }
                return false;
            }
        });

    }

    private void hidekeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);

        } catch (NullPointerException e) {

        }
    }

}