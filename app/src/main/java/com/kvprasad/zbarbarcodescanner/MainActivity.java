package com.kvprasad.zbarbarcodescanner;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<List<Model>> mLstModel = new ArrayList<>();
    private Button scannerButton;
    private TabViewAdapter mTabViewAdapter;
    private ViewPager mViewPager;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
//        fakeData();
        initUI();
        initScannerBtn();
    }

    private void initUI() {
        mViewPager = (ViewPager) findViewById(R.id.pager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDataBg load = new loadDataBg();
        load.execute();
    }

    private void initPager() {
        if(mTabViewAdapter != null){
            mTabViewAdapter.setListModel(mLstModel);
            mTabViewAdapter.notifyDataSetChanged();
        }else {
            mTabViewAdapter = new TabViewAdapter(getSupportFragmentManager(), this, mLstModel);
            mViewPager.setAdapter(mTabViewAdapter);
        }

        mViewPager.setCurrentItem(0);
        mViewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewPager.setCurrentItem(mViewPager.getAdapter().getCount());
            }
        }, 100);
    }

    private void initScannerBtn() {
        scannerButton = (Button) findViewById(R.id.scannerButton);
        scannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), BarcodeScanner.class);
                startActivity(intent);
            }
        });

    }

    private void fakeData(){
        for (int i = 0; i < 5; i++) {
            Model mModel = new Model();
            mModel.setBarCode("78923"+i);
            mModel.setStrPrice("541,000");
            mModel.setTimeInMillis(Calendar.getInstance().getTimeInMillis());
            SQLiteHelper.getInstance(this).addData(this,mModel);
        }

        for (int i = 0; i < 5; i++) {
            Model mModel = new Model();
            mModel.setBarCode("23465"+i);
            mModel.setStrPrice("541,000");
            mModel.setTimeInMillis(1457941205000L);
            SQLiteHelper.getInstance(this).addData(this,mModel);
        }

        for (int i = 0; i < 5; i++) {
            Model mModel = new Model();
            mModel.setBarCode("45667"+i);
            mModel.setStrPrice("541,000");
            mModel.setTimeInMillis(1457768405000L);
            SQLiteHelper.getInstance(this).addData(this,mModel);
        }

        for (int i = 0; i < 5; i++) {
            Model mModel = new Model();
            mModel.setBarCode("76978234"+i);
            mModel.setStrPrice("541,000");
            mModel.setTimeInMillis(1457682005000L);
            SQLiteHelper.getInstance(this).addData(this,mModel);
        }
    }

    private void loadData() {
        mLstModel = new ArrayList<>();
        List<Model> mLstModelAll = SQLiteHelper.getInstance(mContext).getAllModels();
        List<Model> mLstModeByDate = new ArrayList<>();
        if(mLstModelAll.size() <= 0) {
            fakeData();
            loadData();
            return;
        }
        int crtDate = mLstModelAll.get(0).getDate();
        int crtMonth = mLstModelAll.get(0).getMonth();
        int crtYear = mLstModelAll.get(0).getYear();
        for (int i = 0; i < mLstModelAll.size(); i++) {
            if(crtDate == mLstModelAll.get(i).getDate() && crtMonth == mLstModelAll.get(i).getMonth() &&
                    crtYear == mLstModelAll.get(i).getYear()){
                mLstModeByDate.add(mLstModelAll.get(i));
            }else{
                mLstModel.add(mLstModeByDate);
                mLstModeByDate = new ArrayList<>();
                mLstModeByDate.add(mLstModelAll.get(i));
                crtDate = mLstModelAll.get(i).getDate();
                crtMonth = mLstModelAll.get(i).getMonth();
                crtYear = mLstModelAll.get(i).getYear();
            }
        }
        mLstModel.add(mLstModeByDate);
    }

    private class loadDataBg extends AsyncTask<Void, Void, Void>{

        private ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(mContext);
            progress.setTitle("Đang tải dữ liệu");
            progress.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            loadData();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            initPager();
            if(progress.isShowing()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progress.dismiss();
                    }
                }, 1000);
            }
        }
    }

}
