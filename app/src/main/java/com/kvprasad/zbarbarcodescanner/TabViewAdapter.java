package com.kvprasad.zbarbarcodescanner;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by khiem.tran on 16/03/2016.
 */
public class TabViewAdapter extends FragmentStatePagerAdapter {

    private final Context mContext;

    private List<List<Model>> mLstModel = new ArrayList<>();

    public TabViewAdapter(FragmentManager fm, Context mContext, List<List<Model>> mLstModel) {
        super(fm);
        this.mContext = mContext;
        this.mLstModel = mLstModel;
    }

    public void setListModel(List<List<Model>> mLstModel){
        this.mLstModel = mLstModel;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new FragmentProduct(mLstModel.get(i), mContext);
        Bundle args = new Bundle();
        // Our object is just an integer :-P
        args.putInt(FragmentProduct.ARG_OBJECT, i + 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return mLstModel.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(mLstModel.get(position).get(0).getTimeInMillis());

        return cal.get(Calendar.DAY_OF_MONTH)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR);
    }

}
