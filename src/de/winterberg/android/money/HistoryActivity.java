package de.winterberg.android.money;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

import static de.winterberg.android.money.Constants.*;
import static de.winterberg.android.money.Constants.ACTION;

/**
 * Activity for showing all history entries of a category.
 *
 * @author Benjamin Winterberg
 */
public class HistoryActivity extends ListActivity implements AmountDaoAware {
    private String category;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        category = getIntent().getStringExtra(MoneyActivity.KEY_CATEGORY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    private void refreshData() {
        Cursor cursor = getAmountDao().findAll(category);
        startManagingCursor(cursor);
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.history_item, cursor,
                new String[]{
                        TIME,
                        ACTION,
                        VALUE,
                        AMOUNT
                },
                new int[]{
                        R.id.history_time,
                        R.id.history_action,
                        R.id.history_diff,
                        R.id.history_amount
                });

        

        setListAdapter(simpleCursorAdapter);
    }

    public AmountDao getAmountDao() {
        MoneyApplication application = (MoneyApplication) getApplication();
        return application.getAmountDao();
    }
}