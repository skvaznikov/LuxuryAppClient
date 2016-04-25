package de.akvilonsoft.luxuryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Manager on 18.04.2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CouponsDB";
    private static final String TABLE_COUPONS = "coupons";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GAMERS_TABLE = "CREATE TABLE "
                + TABLE_COUPONS + " ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "beschreibung TEXT )";
        db.execSQL(CREATE_GAMERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUPONS);
        this.onCreate(db);
    }

    public void addCoupon(Coupon coupon){
       SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", coupon.getName());
        values.put("beschreibung", coupon.getBeschreibung());

        db.insert(TABLE_COUPONS, null, values);
        db.close();
   }

    public Coupon getCoupon(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_COUPONS,
                new String[]{"id", "name", "beschreibung"},
                " id = ?", new String[]{ String.valueOf(id) },
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;

        Coupon coupon = new Coupon();
        coupon.setId(Integer.parseInt(cursor.getString(0)));
        coupon.setName(cursor.getString(1));
        coupon.setBeschreibung(cursor.getString(2));


        return coupon;
    }

    public List<Coupon> getAllCoupons(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Coupon> cursors = new ArrayList<Coupon>();
        Cursor cursor = db.query(TABLE_COUPONS,
                new String[]{"id", "name", "beschreibung"},
                null, null,
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        while (!cursor.isLast()) {
            Coupon coupon = new Coupon();
            coupon.setId(Integer.parseInt(cursor.getString(0)));
            coupon.setName(cursor.getString(1));
            coupon.setBeschreibung(cursor.getString(2));
            cursors.add(coupon);
            cursor.moveToNext();
        }
        }
        else
            return null;



        return cursors;
    }
}