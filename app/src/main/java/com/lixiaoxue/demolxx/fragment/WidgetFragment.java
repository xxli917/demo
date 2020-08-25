package com.lixiaoxue.demolxx.fragment;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lixiaoxue.demolxx.R;
import com.lixiaoxue.demolxx.widget.AnimateActivity;
import com.lixiaoxue.demolxx.widget.BeerView;
import com.lixiaoxue.demolxx.widget.MeituanActivity;
import com.lixiaoxue.demolxx.widget.StatusBar;
import com.lixiaoxue.demolxx.widget.SwipLoadActivity;
import com.nucarf.base.utils.LogUtils;
import com.winterpei.LicensePlateView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.Unbinder;

public class WidgetFragment extends Fragment implements View.OnClickListener {
   // @BindView(R.id.status_bar)
    StatusBar statusBar;
   // @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;
    private Unbinder unbinder;
    private AnimationDrawable animationDrawable;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // ButterKnife.bind(getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_widget, container, false);
        initView(view);

        return view;


    }

    private void initView(View view) {
        //unbinder = ButterKnife.bind(view);
        RelativeLayout rootView = view.findViewById(R.id.relativelayout);


        com.lixiaoxue.demolxx.widget.LicensePlateView plateView = view.findViewById(R.id.plate_view);
        // plateView.setKeyboardContainerLayout(rootView);
        plateView.setInputListener(new com.lixiaoxue.demolxx.widget.LicensePlateView.InputListener() {
            @Override
            public void inputComplete(String content) {

            }

            @Override
            public void deleteContent() {

            }
        });
        View carView = view.findViewById(R.id.car_plate);
        carView.setOnClickListener(this);
        SeekBar seekBar = view.findViewById(R.id.seekBar);
        BeerView beerView = view.findViewById(R.id.beer_view);
        ImageView imageView = view.findViewById(R.id.imageView);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                LogUtils.e("progress ="+progress);
                float pro = (float)progress/100;
                LogUtils.e("pro ="+pro);
                if(progress>50){
                    animationDrawable = (AnimationDrawable) imageView.getDrawable();
                    animationDrawable.start();
                }
                if(progress>80){
                    animationDrawable.stop();
                    imageView.setImageResource(R.drawable.pull_to_refresh_third_anim);

                    //imageView.setImageDrawable(getResources().getDrawable(R.drawable.pull_to_refresh_third_anim));
                    animationDrawable = (AnimationDrawable) imageView.getDrawable();
                    animationDrawable.start();
                }


                beerView.setCurrentProgress(pro);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(this);
        Button animateView = view.findViewById(R.id.animate);
        animateView.setOnClickListener(this);
        Button swipeButton = view.findViewById(R.id.swipe_load);
        swipeButton.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        statusBar = view.findViewById(R.id.status_bar);

        statusBar.setImageOnClick(new StatusBar.ImageOnClickListener() {
            @Override
            public void leftOnClick(View v) {
                Toast.makeText(getContext(), "点击left按钮", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void rightOnClick(View v) {
                Toast.makeText(getContext(), "点击right按钮", Toast.LENGTH_SHORT).show();


            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.car_plate:

                break;
            case R.id.button:
                Intent intent = new Intent(getActivity(), MeituanActivity.class);
                startActivity(intent);
                break;
            case R.id.animate:
                Intent animateIntent = new Intent(getActivity(), AnimateActivity.class);
                startActivity(animateIntent);
                break;
            case R.id.swipe_load:
                Intent swipeIntent = new Intent(getActivity(), SwipLoadActivity.class);
                startActivity(swipeIntent);
                break;
        }

    }
}
