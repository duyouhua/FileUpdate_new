package com.leng.fileupdate_new.moudle;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.leng.fileupdate_new.Adapter.Page_Adapter;
import com.leng.fileupdate_new.R;

import java.util.ArrayList;
import java.util.List;

public class MoveFileActivity extends AppCompatActivity  implements View.OnClickListener {

    private TabLayout mTablayoutTest;
    private ViewPager mViewpagerTest;
    private FragmentPagerAdapter fAdapter;                               //定义adapter

    private List<Fragment> list_fragment;                                //定义要装fragment的列表
    private List<String> list_title;                                     //tab名称列表
    private ViewPager mViewpager;
    private TABBlankFragment tabBlankFragment;
    private TAB2BlankFragment tab2BlankFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_move_file);
//        getSupportActionBar().hide();
        initView();
    }


        private void initView () {
            mTablayoutTest = (TabLayout) findViewById(R.id.tablayout_test);
            mTablayoutTest.setOnClickListener(this);
            mViewpagerTest = (ViewPager) findViewById(R.id.viewpagerTest);
            mViewpagerTest.setOnClickListener(this);
            //初始化各fragment
            tabBlankFragment = new TABBlankFragment();
            tab2BlankFragment = new TAB2BlankFragment();
            list_fragment = new ArrayList<>();
            list_fragment.add(tabBlankFragment);
            list_fragment.add(tab2BlankFragment);
            list_title = new ArrayList<>();
            list_title.add("默认列表");
            list_title.add("本地列表");
            mTablayoutTest.setTabMode(TabLayout.MODE_FIXED);
            //为TabLayout添加tab名称
            mTablayoutTest.addTab(mTablayoutTest.newTab().setText(list_title.get(0)));
            mTablayoutTest.addTab(mTablayoutTest.newTab().setText(list_title.get(1)));
            fAdapter = new Page_Adapter(MoveFileActivity.this.getSupportFragmentManager(), list_fragment, list_title);

            //viewpager加载adapter
            mViewpagerTest.setAdapter(fAdapter);
            mTablayoutTest.setupWithViewPager(mViewpagerTest);

            //设置返回按钮
        }

    @Override
    public void onClick(View view) {

    }
}
