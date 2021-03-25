package com.lixiaoxue.demolxx.android;


import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

/**
 *  An implementation of a {@link ItemDetailsLookup} to be used to get details when a user make a selection of an item.
 */

public class StringItemDetailsLookup extends ItemDetailsLookup {

    private final RecyclerView mRecyclerView;

    StringItemDetailsLookup(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    @Nullable
    @Override
    public ItemDetails getItemDetails(@NonNull MotionEvent e) {
        View view = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
        if (view != null) {
            RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(view);
            if (holder instanceof StringItemRecyclerViewAdapter.ItemViewHolder) {
                return ((StringItemRecyclerViewAdapter.ItemViewHolder) holder).getItemDetails();
            }
        }
        return null;
    }
}
