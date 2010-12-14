package de.winterberg.android.money;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static de.winterberg.android.money.Constants.*;
import static de.winterberg.android.money.DateUtils.*;

/**
 * Data access to read and store amount data to SQLite.
 *
 * @author Benjamin Winterberg
 */
public class AmountDao extends SQLiteOpenHelper {
    private static final String TAG = "AmountDao";

    private static final String DATABASE_NAME = "amount.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TEST_CATEGORY = "Test";
    private static final boolean TEST_MODE = false;


    public AmountDao(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        if (TEST_MODE)
            setupTestCategory();
    }

    public void setupTestCategory() {
        Log.d(TAG, "setupTestCategory()");
        removeAll(TEST_CATEGORY);
        save(TEST_CATEGORY, "0", "+", new BigDecimal(0.0), newDate(2010, 1, 1).getTime());
        setTestData();
    }

    private void setTestData() {
        int amount = 0;
        testSave(++amount, setDate(today(), 0, 1));
        testSave(++amount, setDay(today(), 1));
        testSave(++amount, setWeekDay(today(), Calendar.MONDAY));
        testSave(++amount, today());

        // SumToday = 1
        // SumWeek = 2
        // SumMonth = 3
        // SumYear = 4
    }

    private void testSave(int amount, Date date) {
        save(TEST_CATEGORY, "1", "+", new BigDecimal(amount), date.getTime());
    }

    public BigDecimal findSumYear(String category) {
        Log.d(TAG, "findSumYear: category=" + category);
        return findSum(category, setDate(today(), 0, 1).getTime(), now().getTime());
    }

    public BigDecimal findSumMonth(String category) {
        Log.d(TAG, "findSumMonth: category=" + category);
        return findSum(category, setDay(today(), 1).getTime(), now().getTime());
    }

    public BigDecimal findSumWeek(String category) {
        Log.d(TAG, "findSumWeek: category=" + category);
        return findSum(category, setWeekDay(today(), Calendar.MONDAY).getTime(), now().getTime());
    }

    public BigDecimal findSumToday(String category) {
        Log.d(TAG, "findSumToday: category=" + category);
        return findSum(category, today().getTime(), now().getTime());
    }

    public BigDecimal findSumYesterday(String category) {
        Log.d(TAG, "findSumToday: category=" + category);
        return findSum(category, yesterday().getTime(), yesterday(23, 59, 59).getTime());
    }

    private BigDecimal findSum(String category, long fromTime, long toTime) {
        SQLiteDatabase db = getReadableDatabase();

        String sql = "select sum(" + VALUE + ") " +
                "from " + TABLE_NAME + " " +
                "where " + CATEGORY + " = ? and " + TIME + " between ? and ?";

        Cursor cursor = db.rawQuery(sql, new String[]{category, String.valueOf(fromTime), String.valueOf(toTime)});

        try {
            cursor.moveToFirst();
            String sum = cursor.getString(0);
            if (sum == null)
                return new BigDecimal("0");
            return new BigDecimal(sum);
        } finally {
            cursor.close();
        }
    }

    public BigDecimal findAverage(String category) {
        Log.d(TAG, "findAverage: category=" + category);
        return findAvg(category);
    }

    private BigDecimal findAvg(String category) {
        SQLiteDatabase db = getReadableDatabase();

        String sql = "select avg(" + VALUE + ") " +
                "from " + TABLE_NAME + " " +
                "where " + CATEGORY + " = ? and " + VALUE + " != 0";

        Cursor cursor = db.rawQuery(sql, new String[]{category});

        try {
            cursor.moveToFirst();
            String sum = cursor.getString(0);
            if (sum == null)
                return new BigDecimal("0");
            return new BigDecimal(sum);
        } finally {
            cursor.close();
        }
    }

    public Date findFirstDate(String category) {
        Log.d(TAG, "findFirstDate()");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select min(" + TIME + ") from " + TABLE_NAME + " where " + CATEGORY + "=?", new String[]{category});
        try {
            cursor.moveToFirst();
            long timestamp = cursor.getLong(0);
            return new Date(timestamp);
        } finally {
            cursor.close();
        }
    }

    public Date findLastDate(String category) {
        Log.d(TAG, "findLastDate()");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select max(" + TIME + ") from " + TABLE_NAME + " where " + CATEGORY + "=?", new String[]{category});
        try {
            cursor.moveToFirst();
            long timestamp = cursor.getLong(0);
            return new Date(timestamp);
        } finally {
            cursor.close();
        }
    }

    public Cursor findAll(String category) {
        Log.d(TAG, "findAll()");
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_NAME, new String[]{_ID, TIME, ACTION, VALUE, AMOUNT}, CATEGORY + "=?", new String[]{category},
                null, null, TIME + " desc");
    }

    public void removeAll(String category) {
        Log.d(TAG, "remove all entries for category: " + category);
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, CATEGORY + "=?", new String[]{category});
    }

    public List<String> findDistinctCategories() {
        Log.d(TAG, "findDistinctCategories()");
        List<String> categories = new ArrayList<String>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(true, TABLE_NAME, new String[]{CATEGORY}, null, null, null, null, CATEGORY + " asc", null);
        try {
            while (cursor.moveToNext()) {
                String category = cursor.getString(0);
                categories.add(category);
            }
            return categories;
        } finally {
            cursor.close();
        }
    }

    public BigDecimal loadAmount(String category) {
        Log.d(TAG, "loadAmount()");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{AMOUNT, "max(" + TIME + ")"},
                CATEGORY + "=?",
                new String[]{category},
                CATEGORY,
                null,
                TIME + " desc");
        try {
            if (cursor.moveToNext()) {
                String amount = cursor.getString(0);
                Log.d(TAG, "loadAmount: " + amount);
                return new BigDecimal(amount);
            }
            Log.d(TAG, "loadAmount: no data found");
            return new BigDecimal(0.0);
        } finally {
            cursor.close();
        }
    }

    public void delete(long rowId) {
        Log.d(TAG, "delete()");
        getWritableDatabase().delete(TABLE_NAME, _ID + "=?", new String[]{String.valueOf(rowId)});
    }

    public void save(String category, String value, String action, BigDecimal amount) {
        Log.d(TAG, "save()");
        long timeInMillis = System.currentTimeMillis();
        save(category, value, action, amount, timeInMillis);
    }

    private void save(String category, String value, String action, BigDecimal amount, long timeInMillis) {
        ContentValues values = createValues(category, value, action, amount, timeInMillis);
        SQLiteDatabase db = getWritableDatabase();
        db.insertOrThrow(TABLE_NAME, null, values);
    }

    private ContentValues createValues(String category, String value, String action, BigDecimal amount, long timeInMillis) {
        ContentValues values = new ContentValues();
        values.put(TIME, timeInMillis);
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
                TIME + " long not null, " +
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
