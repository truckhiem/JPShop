package com.kvprasad.zbarbarcodescanner;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by khiem.tran on 14/03/2016.
 */
public class InputText implements TextWatcher {
    private String current;
    private final WeakReference<EditText> editTextWeakReference;

    public InputText(EditText editText) {
        editTextWeakReference = new WeakReference<EditText>(editText);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(!s.toString().equals(current)){
            try {
                EditText editText = editTextWeakReference.get();
                String cleanString = s.toString().replaceAll("[$,.]", "");
                if(cleanString.length() < 10) {
                    long parse = Long.parseLong(cleanString);
                    NumberFormat format = new DecimalFormat("#,##0");
                    current = format.format(parse);
                }
                editText.setText(current);
                editText.setSelection(current.length());
            }catch(NumberFormatException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
