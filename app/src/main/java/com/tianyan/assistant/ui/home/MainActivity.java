package com.tianyan.assistant.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianyan.assistant.R;
import com.tianyan.assistant.home.Tab1Fragment;
import com.tianyan.assistant.home.Tab2Fragment;
import com.tianyan.assistant.home.Tab3Fragment;
import com.tianyan.assistant.home.Tab4Fragment;
import com.tianyan.assistant.utils.SnackbarUtil;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private LinearLayout tab1, tab2, tab3, tab4;
    private Tab1Fragment tab1Fragment;
    private Tab2Fragment tab2Fragment;
    private Tab3Fragment tab3Fragment;
    private Tab4Fragment tab4Fragment;
    private TextView txt1, txt2, txt3, txt4;
    private int currenttab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SnackbarUtil.ShortSnackbar(view, "妹子向你发来一条消息", SnackbarUtil.Info).setActionTextColor(Color.WHITE).show();
            }
        });
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tab1 = (LinearLayout) findViewById(R.id.tab1);
        tab2 = (LinearLayout) findViewById(R.id.tab2);
        tab3 = (LinearLayout) findViewById(R.id.tab3);
        tab4 = (LinearLayout) findViewById(R.id.tab4);
        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);
        txt3 = (TextView) findViewById(R.id.txt3);
        txt4 = (TextView) findViewById(R.id.txt4);
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        tab4.setOnClickListener(this);

        fragments = new ArrayList<>();
        tab1Fragment = new Tab1Fragment();
        tab2Fragment = new Tab2Fragment();
        tab3Fragment = new Tab3Fragment();
        tab4Fragment = new Tab4Fragment();
        fragments.add(tab1Fragment);
        fragments.add(tab2Fragment);
        fragments.add(tab3Fragment);
        fragments.add(tab4Fragment);

        viewPager.setAdapter(new MyFrageStatePagerAdapter(getSupportFragmentManager()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab1:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tab2:
                viewPager.setCurrentItem(1);
                break;
            case R.id.tab3:
                viewPager.setCurrentItem(2);
                break;
            case R.id.tab4:
                viewPager.setCurrentItem(3);
                break;
        }
    }

    /**
     * 定义自己的ViewPager适配器。
     * 也可以使用FragmentPagerAdapter。关于这两者之间的区别，可以自己去搜一下。
     */
    class MyFrageStatePagerAdapter extends FragmentStatePagerAdapter {

        public MyFrageStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        /**
         * 每次更新完成ViewPager的内容后，调用该接口，此处复写主要是为了让导航按钮上层的覆盖层能够动态的移动
         */
        @Override
        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);//这句话要放在最前面，否则会报错
            //获取当前的视图是位于ViewGroup的第几个位置，用来更新对应的覆盖层所在的位置
            int currentItem = viewPager.getCurrentItem();
            if (currentItem == currenttab) {
                return;
            }
            changeView(viewPager.getCurrentItem());
            currenttab = viewPager.getCurrentItem();
        }

    }

    private void changeView(int position) {
        switch (position) {
            case 0:
                txt1.setTextColor(getResources().getColor(R.color.main_theme));
                txt2.setTextColor(getResources().getColor(R.color.text_color));
                txt3.setTextColor(getResources().getColor(R.color.text_color));
                txt4.setTextColor(getResources().getColor(R.color.text_color));
                break;
            case 1:
                txt1.setTextColor(getResources().getColor(R.color.text_color));
                txt2.setTextColor(getResources().getColor(R.color.main_theme));
                txt3.setTextColor(getResources().getColor(R.color.text_color));
                txt4.setTextColor(getResources().getColor(R.color.text_color));
                break;
            case 2:
                txt1.setTextColor(getResources().getColor(R.color.text_color));
                txt2.setTextColor(getResources().getColor(R.color.text_color));
                txt3.setTextColor(getResources().getColor(R.color.main_theme));
                txt4.setTextColor(getResources().getColor(R.color.text_color));

                break;
            case 3:
                txt1.setTextColor(getResources().getColor(R.color.text_color));
                txt2.setTextColor(getResources().getColor(R.color.text_color));
                txt3.setTextColor(getResources().getColor(R.color.text_color));
                txt4.setTextColor(getResources().getColor(R.color.main_theme));
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
