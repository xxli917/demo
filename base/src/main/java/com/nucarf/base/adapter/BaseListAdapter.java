package com.nucarf.base.adapter;



import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixx on 19-11-12.
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {
    private static final String TAG = "BaseListAdapter";
    protected Context context;

    public BaseListAdapter(List<T> list) {
        if (list != null) {
            mList = list;
        }
    }

    public BaseListAdapter(Context context, List<T> list) {
        if (list != null) {
            mList = list;

        }
        this.context = context;
    }

    protected BaseListAdapter() {
    }

    public List<T> getList() {
        return mList;
    }

    public void setList(List<T> list) {
        mList = list;
    }

    public void addList(List<T> list) {
        mList.addAll(list);
    }

    public void clear() {
        setList(new ArrayList<T>());
    }

    @Override
    public int getCount() {
        // Log.d("SearchListAdapter","执行了getCount"+((mList == null) ? 0 : mList.size()));
        return (mList == null) ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return (mList == null) ? true : mList.isEmpty();
    }

    public void remove(T item) {
        if (mList.contains(item)) {
            mList.remove(item);
        }
    }

    public List<T> mList = new ArrayList<T>();

}

