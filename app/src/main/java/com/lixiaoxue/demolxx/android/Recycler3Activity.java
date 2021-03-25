package com.lixiaoxue.demolxx.android;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.lixiaoxue.demolxx.R;
import java.util.Arrays;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Recycler3Activity extends AppCompatActivity {

    public static final List<String> ITEMS = Arrays.asList(
            "A",
            "B",
            "C",
            "D",
            "E",
            "F",
            "G",
            "H",
            "I",
            "J",
            "K",
            "L"
    );

    private RecyclerView mRecyclerView;
    private StringItemRecyclerViewAdapter mAdapter;
    private SelectionTracker mSelectionTracker;

    private Button btnClearSelection;
    private TextView tvSelectionCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle3);

        btnClearSelection = findViewById(R.id.btnClearSelection);
        btnClearSelection.post(new Runnable() {
            @Override
            public void run() {

            }
        });
        btnClearSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSelectionTracker.hasSelection())
                    mSelectionTracker.clearSelection();
            }
        });

        tvSelectionCount = findViewById(R.id.tvSelectionCount);

        mRecyclerView = findViewById(R.id.itemsRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new StringItemRecyclerViewAdapter(ITEMS);
        mRecyclerView.setAdapter(mAdapter);

        mSelectionTracker = new SelectionTracker.Builder<>(
                "string-items-selection",
                mRecyclerView,
                new StringItemKeyProvider(1, ITEMS),
                new StringItemDetailsLookup(mRecyclerView),
                StorageStrategy.createStringStorage()
        ).withSelectionPredicate(SelectionPredicates.<String>createSelectAnything())
                .build();

        mAdapter.setSelectionTracker(mSelectionTracker);

        mSelectionTracker.addObserver(new SelectionTracker.SelectionObserver() {
            @Override
            public void onItemStateChanged(@NonNull Object key, boolean selected) {
                super.onItemStateChanged(key, selected);
                Log.e("ItemStateChanged",key+"--"+selected);
            }

            @Override
            public void onSelectionRefresh() {
                super.onSelectionRefresh();
              //  Timber.i("onSelectionRefresh()");
                tvSelectionCount.setText("Selection Count: 0");
            }

            @Override
            public void onSelectionChanged() {
                super.onSelectionChanged();
                Log.i("----","onSelectionChanged()");

                if (mSelectionTracker.hasSelection()) {
                    tvSelectionCount.setText(String.format("Selection Count: %d", mSelectionTracker.getSelection().size()));
                } else {
                    tvSelectionCount.setText("Selection Count: 0");
                }
            }

            @Override
            public void onSelectionRestored() {
                super.onSelectionRestored();
                Log.i("----","onSelectionRestored()");
                tvSelectionCount.setText("Selection Count: 0");
            }
        });

        if (savedInstanceState != null)
            mSelectionTracker.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mSelectionTracker.onSaveInstanceState(outState);
    }

}
