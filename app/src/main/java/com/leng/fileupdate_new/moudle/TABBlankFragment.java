package com.leng.fileupdate_new.moudle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leng.fileupdate_new.Adapter.TABadapter;
import com.leng.fileupdate_new.R;
import com.leng.fileupdate_new.utils.SharedPreferencesUtils;

import java.util.ArrayList;


public class TABBlankFragment extends Fragment implements View.OnClickListener {
    private View view;
    private ArrayList<String> mListPath = new ArrayList<>();
    private Context mContext;
    private ListView mTabListview;
    private RelativeLayout mTabRelativeYes;
    private RelativeLayout mTabRelativeNo;

    private String TAG = "TABBlankFragment";
    /**
     * 当前文件夹没有文件
     */
    private TextView mEmptyView;
    /**
     * 取消
     */
    private TextView mTabMooveQuxaio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        view = inflater.inflate(R.layout.fragment_tabblank, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mListPath.clear();
        for (int i = 1; i < 6; i++) {
            String re = (String) SharedPreferencesUtils.getParam(mContext, "mSettingPath" + i, "null");
            if (re.equals("null")) {

            } else {

                mListPath.add(re);
            }
        }
        mTabListview = (ListView) view.findViewById(R.id.tab_listview);
        mTabListview.setOnItemClickListener(clickListener);
        mTabRelativeYes = (RelativeLayout) view.findViewById(R.id.tab_relative_yes);
        mTabRelativeNo = (RelativeLayout) view.findViewById(R.id.tab_relative_no);
        if (mListPath.size() > 0) {
            mTabRelativeYes.setVisibility(View.VISIBLE);
            mTabRelativeNo.setVisibility(View.GONE);
//            ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_expandable_list_item_1, mListPath);

            mTabListview.setAdapter(new TABadapter(mListPath, mContext));
        } else {
            mEmptyView.setText(getResources().getText(R.string.no_deauflt_text));
            mTabRelativeYes.setVisibility(View.GONE);
            mTabRelativeNo.setVisibility(View.VISIBLE);
        }

        mEmptyView = (TextView) view.findViewById(R.id.emptyView);
        mTabMooveQuxaio = (TextView) view.findViewById(R.id.tab_moove_quxaio);
        mTabMooveQuxaio.setOnClickListener(this);
    }

    AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            SharedPreferencesUtils.setParam(mContext, "tabkeypath", mListPath.get(i));

            //判断当前的目录是不是与选中的文件是否一致
           String as= (String) SharedPreferencesUtils.getParam(mContext,"jiluFilePath","null");
            if (!as.equals("null")&&as.equals(mListPath.get(i))){
                Toast.makeText(mContext, "当前目录与选择的目录一致，请重新选择", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent();
            getActivity().setResult(99, intent);
            getActivity().finish();
            Log.i(TAG, mListPath.get(i) + "   adasasaasa");
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_moove_quxaio:
                getActivity().finish();
                break;
        }
    }
}
