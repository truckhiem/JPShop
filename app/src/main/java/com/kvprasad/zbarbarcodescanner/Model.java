package com.kvprasad.zbarbarcodescanner;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Created by khiem.tran on 14/03/2016.
 */
public class Model {
    private String barCode;
    private long price;
    private byte[] image;
    private long timeInMillis;
    private int date, month, year;

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setStrPrice(String strPrice){
        try {
            NumberFormat format = new DecimalFormat("#,##0");
            price = (long) format.parse(strPrice);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getStrPrice(){
        NumberFormat format = new DecimalFormat("#,##0");
        return format.format(price);
    }
}
