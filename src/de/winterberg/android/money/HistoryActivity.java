package de.winterberg.android.money;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.Date;

import static de.winterberg.android.money.Constants.*;

/**
 * Activity for showing all history entries of a category.
 *
 * @author Benjamin Winterberg
 */
public class HistoryActivity extends ListActivity implements AmountDaoAware {
    private MoneyApplication application;

    private String category;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        category = getIntent().getStringExtra(MoneyActivity.KEY_CATEGORY);
        application = (MoneyApplication) getApplication();
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
                        VALUE,
                        AMOUNT
                },
                new int[]{
                        R.id.history_time,
                        R.id.history_diff,
                        R.id.history_amount
                });

        simpleCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                TextView textView = (TextView) view;
                switch (columnIndex) {
                    case 1:
                        long timeInMillis = cursor.getLong(columnIndex);
                        String formattedString = application.getDateFormat().format(new Date(timeInMillis));
                        textView.setText(formattedString);
                        return true;
                    case 3:
                        String diff = cursor.getString(columnIndex);
                        textView.setText("(" + diff + ")");
                        return true;
                    case 4:
                        String amount = cursor.getString(columnIndex);
                        textView.setText(amount + " €");
                        return true;
                    default:
                        return false;
                }
            }
        });

        setListAdapter(simpleCursorAdapter);
    }

    public AmountDao getAmountDao() {
        MoneyApplication application = (MoneyApplication) getApplication();
        return application.getAmountDao();
    }
}