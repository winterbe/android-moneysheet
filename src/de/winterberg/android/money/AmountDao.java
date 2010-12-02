package de.winterberg.android.money;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.math.BigDecimal;

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

    public void save(String category, String value, String action, BigDecimal amount) {
        ContentValues values = createValues(category, value, action, amount);
        SQLiteDatabase db = getWritableDatabase();
        db.insertOrThrow(TABLE_NAME, null, values);
    }

    private ContentValues createValues(String category, String value, String action, BigDecimal amount) {
        ContentValues values = new ContentValues();
        values.put(TIME, System.currentTimeMillis());
        values.put(CATEGORY, category);
        values.put(ACTION, action);
        values.put(VALUE, value);
        values.put(AMOUNT, amount.toString());
        return values;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(" +
                _ID + " integer primary key autoincrement, " +
                TIME + " integer not null, " +
                CATEGORY + " string not null, " +
                VALUE + " string, " +
                ACTION + " string not null, " +
                AMOUNT + " string not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: migrate old data
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }
}
