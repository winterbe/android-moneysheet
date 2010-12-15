package de.winterberg.android.money;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Activity for displaying an about dialog.
 * 
 * @author Benjamin Winterberg
 */
public class AboutActivity extends Activity {
    private static final String TAG = "AboutActiviy";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        TextView view = (TextView) findViewById(R.id.about_version);
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo("de.winterberg.android.money", 0);
            view.setText("Version " + packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "error while retrieving app version", e);
        }
    }

    public void onWebsiteButtonClick(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/winterbe/money"));
        startActivity(intent);
    }

}