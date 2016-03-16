package com.kvprasad.zbarbarcodescanner;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by khiem.tran on 16/03/2016.
 */
public class FragmentProduct extends Fragment {
    private List<Model> mLstModel;
    private Context mContext;

    public FragmentProduct(List<Model> mLstModel, Context mContext){
        this.mLstModel = mLstModel;
        this.mContext = mContext;
    }

    public static final String ARG_OBJECT = "object";

    private ListView mLstView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_product, container, false);
        Bundle args = getArguments();
        args.getInt(ARG_OBJECT);
        TextView txtTotal = (TextView) rootView.findViewById(R.id.total);
        txtTotal.setText(String.format(getString(R.string.txt_total),sumTotalPrice()));
        mLstView = (ListView) rootView.findViewById(R.id.list_product);
        ListViewAdapter adapter = new ListViewAdapter(mContext, mLstModel);
        mLstView.setAdapter(adapter);
        return rootView;
    }

    private String sumTotalPrice(){
        long sum = 0;
        for (Model mModel : mLstModel) {
            sum += mModel.getPrice();
        }
        NumberFormat format = new DecimalFormat("#,##0");
        return format.format(sum);
    }
}
