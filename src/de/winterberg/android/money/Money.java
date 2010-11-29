package de.winterberg.android.money;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Main activity shows each monetary category as a list.
 *
 * @author Benjamin Winterberg
 */
public class Money extends ListActivity {
    protected static final String TAG = "Money";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        List<String> categories = new ArrayList<String>();
        categories.add("Category 1");
        categories.add("Category 2");
        categories.add("Category 3");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.item, categories);
        setListAdapter(arrayAdapter);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemClick");
                Toast.makeText(getApplicationContext(), "Item clicked: " + ((TextView)view).getText(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}