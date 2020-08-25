package com.lixiaoxue.demolxx.md;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import com.lixiaoxue.demolxx.R;
import com.nucarf.base.utils.LogUtils;

public class ToolbarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
       // toolbar.setLogo(getResources().getDrawable(R.mipmap.display));
        //问题： 设置menu不在条目中显示
        toolbar.inflateMenu(R.menu.toolbar_menu);
        Menu menu = toolbar.getMenu();

        MenuItem menuItem = menu.findItem(R.id.searchview);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.findViewById(R.id.search_plate).setBackground(null);
        //searchView.setBackground(getResources().getDrawable(R.mipmap.ic_ad));

        //默认就是搜索框展开
      //  searchView.setIconified(false);
        //一直都是搜索框，搜索图标在输入框左侧（默认是内嵌的）
        //searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //文字输入完成，提交的回调
            @Override
            public boolean onQueryTextSubmit(String s) {
                LogUtils.e("onQueryTextSubmit");

                return false;
            }

            //输入文字发生改变
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        //点击搜索图标，搜索框展开时的回调
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.e("搜索框展开了");

            }
        });


    }


}