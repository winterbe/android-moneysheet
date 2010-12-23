package de.winterberg.android.money;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import java.util.Date;

/**
 * @author Benjamin Winterberg
 */
public class AddHistoryActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_history_entry);

        Button dateButton = (Button) findViewById(R.id.add_history_date);
        dateButton.setText(new Date().toString());
    }

}