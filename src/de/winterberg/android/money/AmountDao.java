package de.winterberg.android.money;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static de.winterberg.android.money.Constants.*;

/**
 * Data access to read and store amount data to SQLite.
 *
 * @author Benjamin Winterberg
 */
public class AmountDao extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "amount.db";
    private static final int DATABASE_VERSION = 1;


    public AmountDao(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(" +
                _ID + " integer primary key autoincrement, " +
                TIME + " integer not null, " +
                CATEGORY + " string not null, " +
                VALUE + " double, " +
                ACTION + " string not null, " +
                AMOUNT + " double not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: migrate old data
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }
}
