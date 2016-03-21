package com.kvprasad.zbarbarcodescanner;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by mJroundi on 16/02/2015.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "JP.db";

    public static final String TABLE = "tableProduct";
    public static final String COL_BARCODE = "Col_BarCode";
    public static final String COL_PRICE = "Col_Price";
    public static final String COL_IMAGE = "Col_Image";
    public static final String COL_DATE = "Col_Date";
    private Context mContext;

    private String[] allColumns = {
            COL_BARCODE,
            COL_PRICE,
            COL_IMAGE,
            COL_DATE,
    };

    public static final String TAG = SQLiteHelper.class
            .getSimpleName();

    private static SQLiteHelper sInstance;
    static SQLiteDatabase database;

    private SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    public static SQLiteHelper getInstance(Context mContext) {

        if (sInstance == null) {
            sInstance = new SQLiteHelper(mContext);
        }
        return sInstance;
    }

    public static SQLiteDatabase getDatabase(Context mContext) {
        if (database == null) {
                database = SQLiteHelper.getInstance(mContext).getWritableDatabase();
        }
        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.v(TAG, "onCreate SQLiteDatabase called");
        database.execSQL("create table "
                + TABLE
                + "("
                + COL_BARCODE + " String primary key , "
                + COL_PRICE + " long , "
                + COL_DATE + " long , "
                + COL_IMAGE + " text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addData(Context mContext, Model mModel) {
        getDatabase(mContext).beginTransaction();
        
        ContentValues values = new ContentValues();
        values.put(COL_BARCODE, mModel.getBarCode());
        values.put(COL_PRICE, mModel.getPrice());
        values.put(COL_IMAGE, mModel.getImage());
        values.put(COL_DATE, mModel.getTimeInMillis());

        getDatabase(mContext).insertWithOnConflict(TABLE,
                null, values, SQLiteDatabase.CONFLICT_REPLACE);
        getDatabase(mContext).setTransactionSuccessful();
        getDatabase(mContext).endTransaction();
    }

    public List<Model> getAllModels() {
        List<Model> Models = new ArrayList<Model>();

        Cursor cursor =  getDatabase(mContext).query(TABLE,
                allColumns, null, null, null, null, COL_DATE + " ASC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Model mModel = new Model();
            mModel.setBarCode(cursor.getString(cursor.getColumnIndex(COL_BARCODE)));
            mModel.setPrice(cursor.getLong(cursor.getColumnIndex(COL_PRICE)));
//            mModel.setImage(cursor.getBlob(cursor.getColumnIndex(COL_BARCODE)));
            long timeInMillis = cursor.getLong(cursor.getColumnIndex(COL_DATE));
            mModel.setDate(getItemTime(Calendar.DAY_OF_MONTH, timeInMillis));
            mModel.setMonth(getItemTime(Calendar.MONTH, timeInMillis));
            mModel.setYear(getItemTime(Calendar.YEAR, timeInMillis));
            mModel.setTimeInMillis(timeInMillis);
            Models.add(mModel);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        return Models;
    }

    private int getItemTime(int type, long timeInMillis){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeInMillis);
        return cal.get(type);
    }

}