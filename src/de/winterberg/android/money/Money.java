package de.winterberg.android.money;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    static final String TAG = "Money";
    static final String KEY_CATEGORY = "CATEGORY_NAME";

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
                String category = ((TextView) view).getText().toString();
                Log.d(TAG, "onItemClick: " + category);

                Intent intent = new Intent(getApplicationContext(), Category.class);
                intent.putExtra(KEY_CATEGORY, category);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add_category:
                Toast.makeText(getApplicationContext(), "Add clicked", Toast.LENGTH_SHORT).show();
                return true;
//            case R.id.edit_category:
//                Toast.makeText(getApplicationContext(), "Edit clicked", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.remove_category:
//                Toast.makeText(getApplicationContext(), "Remove clicked", Toast.LENGTH_SHORT).show();
//                return true;
            case R.id.settings:
                startActivity(new Intent(this, Prefs.class));
                return true;
            case R.id.about:
                startActivity(new Intent(this, About.class));
                return true;
            case R.id.exit:
                Toast.makeText(getApplicationContext(), "Exit clicked", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }
}