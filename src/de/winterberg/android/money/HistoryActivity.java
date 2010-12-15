package de.winterberg.android.money;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import static de.winterberg.android.money.Constants.*;

/**
 * Activity for showing all history entries of a category.
 *
 * @author Benjamin Winterberg
 */
public class HistoryActivity extends ListActivity implements AmountDaoAware {
    private static final String TAG = "History";

    private MoneyApplication application;

    private String category;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        initClickListeners();
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
                        VALUE
                },
                new int[]{
                        R.id.history_time,
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
                    case 2:
                        String amount = cursor.getString(columnIndex);
                        textView.setText(getDecimalFormat().format(new BigDecimal(amount)));
                        return true;
                    default:
                        return false;
                }
            }
        });

        setListAdapter(simpleCursorAdapter);
    }

    private void initClickListeners() {
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long rowId) {
                Log.d(TAG, "history entry long-clicked: position=" + position + ", rowId=" + rowId);
                if (getListView().getCount() > 1) {
                    openRemoveHistoryEntryDialog(rowId);
                }
                return true;
            }
        });
    }

    private void openRemoveHistoryEntryDialog(final long rowId) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        doRemove(rowId);
                        break;
                }
            }
        };

        new AlertDialog.Builder(HistoryActivity.this)
                .setTitle(R.string.remove_history_entry_label)
                .setMessage(R.string.remove_history_entry_confirm)
                .setPositiveButton(R.string.ok, dialogClickListener)
                .setNegativeButton(R.string.cancel, dialogClickListener)
                .show();
    }

    private void doRemove(long rowId) {
        Log.d(TAG, "removing history entry with rowId=" + rowId);
        getAmountDao().delete(rowId);
        refreshData();
    }

    public AmountDao getAmountDao() {
        MoneyApplication application = (MoneyApplication) getApplication();
        return application.getAmountDao();
    }

    private DecimalFormat getDecimalFormat() {
        return ((MoneyApplication) getApplication()).getDecimalFormat();
    }
}