package de.winterberg.android.money;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Date;

/**
 * Activity for creating new history entries.
 *
 * @author Benjamin Winterberg
 */
public class AddHistoryActivity extends Activity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    public static final String RESULT_KEY_AMOUNT = "RESULT_KEY_AMOUNT";
    public static final String RESULT_KEY_TIMESTAMP = "RESULT_KEY_TIMESTAMP";

    public static final int REQUEST_CODE = 1;
    public static final int RESULT_CODE = 2;

    private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance(DateFormat.MEDIUM);
    private static final DateFormat TIME_FORMAT = DateFormat.getTimeInstance(DateFormat.SHORT);

    private int year;
    private int month;
    private int day;

    private int hour;
    private int minute;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_history_entry);

        Date now = DateUtils.now();

        year = DateUtils.getYear(now);
        month = DateUtils.getMonth(now);
        day = DateUtils.getDay(now);

        hour = DateUtils.getHour(now);
        minute = DateUtils.getMinute(now);

        refreshView();
    }

    private void refreshView() {
        Date date = createDate();

        Button dateButton = (Button) findViewById(R.id.add_history_date);
        Button timeButton = (Button) findViewById(R.id.add_history_time);

        dateButton.setText(DATE_FORMAT.format(date));
        timeButton.setText(TIME_FORMAT.format(date));
    }

    private Date createDate() {
        return DateUtils.newDate(year, month, day, hour, minute, 0);
    }

    public void onSaveButtonClick(View view) {
        EditText amountInput = (EditText) findViewById(R.id.add_history_amount);
        String amount = amountInput.getText().toString();

        Intent data = new Intent();
        data.putExtra(RESULT_KEY_AMOUNT, amount);
        data.putExtra(RESULT_KEY_TIMESTAMP, createDate().getTime());

        setResult(RESULT_CODE, data);
        finish();
    }

    public void onCancelButtonClick(View view) {
        finish();
    }

    public void onDateButtonClick(View view) {
        new DatePickerDialog(this, this, year, month, day).show();
    }

    public void onTimeButtonClick(View view) {
        new TimePickerDialog(this, this, hour, minute, true).show();
    }

    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        refreshView();
    }

    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
        refreshView();
    }
}