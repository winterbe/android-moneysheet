package de.winterberg.android.money;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

/**
 * Activity for creating new history entries.
 *
 * @author Benjamin Winterberg
 */
public class AddHistoryActivity extends Activity {
    private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance();
    private static final DateFormat TIME_FORMAT = DateFormat.getTimeInstance();


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_history_entry);
        initView();
    }

    private void initView() {
        Button dateButton = (Button) findViewById(R.id.add_history_date);
        Button timeButton = (Button) findViewById(R.id.add_history_time);
        Date now = DateUtils.now();
        dateButton.setText(DATE_FORMAT.format(now));
        timeButton.setText(TIME_FORMAT.format(now));
    }

    public void onDateButtonClick(View view) {
        Toast.makeText(this, "date button clicked", Toast.LENGTH_SHORT).show();
    }

    public void onTimeButtonClick(View view) {
        Toast.makeText(this, "time button clicked", Toast.LENGTH_SHORT).show();
    }

}