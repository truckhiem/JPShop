package com.kvprasad.zbarbarcodescanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by khiem.tran on 16/03/2016.
 */
public class ListViewAdapter extends BaseAdapter {

    private List<Model> mLstModel;
    private Context mContext;

    public ListViewAdapter(Context mContext, List<Model> mLstModel){
        this.mContext = mContext;
        this.mLstModel = mLstModel;
    }

    @Override
    public int getCount() {
        return mLstModel.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_product_data, null);

        LinearLayout mRootLayout = (LinearLayout) convertView.findViewById(R.id.root_layout);
        if(position % 2 == 1){
            mRootLayout.setBackgroundColor(mContext.getResources().getColor(R.color.white_gray));
        }else{
            mRootLayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }

        TextView mTxtBarcode = (TextView) convertView.findViewById(R.id.barcode);
        TextView mTxtPrice = (TextView) convertView.findViewById(R.id.price);

        mTxtBarcode.setText(mLstModel.get(position).getBarCode());
        mTxtPrice.setText(mLstModel.get(position).getStrPrice());
        return convertView;
    }
}
