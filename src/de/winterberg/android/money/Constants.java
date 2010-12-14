package de.winterberg.android.money;

import android.provider.BaseColumns;

/**
 * SQLite constants like table and column names.
 *
 * @author Benjamin Winterberg
 */
public interface Constants extends BaseColumns {
    public static final String TABLE_NAME = "amounts";

    public static final String TIME = "time";
    public static final String CATEGORY = "category";
    public static final String VALUE = "value";
}