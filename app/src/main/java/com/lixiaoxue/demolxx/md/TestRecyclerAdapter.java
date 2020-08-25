package com.lixiaoxue.demolxx.md;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lixiaoxue.demolxx.R;

import java.util.Random;

import androidx.recyclerview.widget.RecyclerView;

public class TestRecyclerAdapter extends RecyclerView.Adapter<TestRecyclerAdapter.ViewHolder>{

    private LayoutInflater mInflater;
    private String[] mTitles=null;

    public TestRecyclerAdapter(Context context){
        this.mInflater=LayoutInflater.from(context);
        this.mTitles=new String[20];
        for (int i=0;i<20;i++){
            int index=i+1;
            mTitles[i]="item"+index;
        }
    }
    /**
     * item显示类型
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.item_test,parent,false);
        Random myRandom = new Random();
        int ranColor = 0xff000000 | myRandom.nextInt(0x00ffffff);
        view.setBackgroundColor(ranColor);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }
    /**
     * 数据的绑定显示
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.item_tv.setText(mTitles[position]);
    }

    @Override
    public int getItemCount() {
        return mTitles.length;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public  class ViewHolder extends RecyclerView.ViewHolder {
        public TextView item_tv;
        public ViewHolder(View view){
            super(view);
            item_tv = (TextView)view.findViewById(R.id.item_tv);
        }
    }
}

