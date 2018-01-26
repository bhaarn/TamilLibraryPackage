package com.padhuga.tamillibrary.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.padhuga.tamillibrary.R;
import com.padhuga.tamillibrary.models.ParentModel;
import com.padhuga.tamillibrary.utils.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class MenuLogic  {

    static ParentModel parentModel;
    private SharedPreferences sharedPref;
    private int progRess;

    public ParentModel readJSONFromAssetsAndConvertTogson(Activity activity, String jsonFileName) {
        try {
            InputStream is = activity.getAssets().open(jsonFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            int bytesRead = is.read(buffer);
            is.close();
            String json = new String(buffer, activity.getResources().getString(R.string.json_open_file_format));
            Gson gson = new Gson();
            parentModel = gson.fromJson(json, ParentModel.class);
            Log.d("Json Bytes Read", Integer.toString(bytesRead));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return parentModel;
    }

    public void shareApp(Activity activity, String appName, String subject, String desc) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, desc + appName);
        activity.startActivity(Intent.createChooser(sharingIntent, "Share the application"));
    }

    public void moreApps(Activity activity) {
        activity.startActivity(new Intent(
                Intent.ACTION_VIEW,
                Uri.parse(activity.getResources().getString(R.string.play_more_apps))));
    }

    public void aboutUs(Activity activity) {
        Fragment fragment = new AboutFragment();
        ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
    }

    public void rateApp(Activity activity, String appName) {
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(activity.getResources().getString(R.string.old_play_store)
                            + appName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            activity.startActivity(new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(activity.getResources().getString(R.string.new_play_store)
                            + appName)));
        }
    }

    public void showHelp(Activity activity) {
        Fragment fragment = new HelpFragment();
        ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
    }

    public void handleIntent(Activity activity, Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Fragment searchFragment = new SearchFragment();
            Bundle args = new Bundle();
            args.putString(Constants.ARG_QUERY_TEXT, query);
            searchFragment.setArguments(args);
            ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, searchFragment, searchFragment.getClass().getSimpleName()).addToBackStack(null).commit();
        }
    }

    public void performSearch(Activity activity, MenuItem item) {
        openKeyboardPreference(activity);
        SearchManager searchManager =
                (SearchManager) activity.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) item.getActionView();
        searchView.setSearchableInfo(
                searchManager != null ? searchManager.getSearchableInfo(activity.getComponentName()) : null);
    }

    private void openKeyboardPreference(Activity activity) {
        Toast.makeText(activity.getApplicationContext(), R.string.tamil_keyboard_check, Toast.LENGTH_LONG).show();
        InputMethodManager imeManager = (InputMethodManager) activity.getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
        if(imeManager != null) {
            imeManager.showInputMethodPicker();
        }
        Resources res = activity.getBaseContext().getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale("ta_IN".toLowerCase());
        res.updateConfiguration(conf, dm);
        Log.i("inside onStart", "after ever");
    }

    public void changeFont(final Activity activity) {
        final AlertDialog.Builder fontSizeSelectorDialog = new AlertDialog.Builder(activity);
        final SeekBar fontSizeSetter = new SeekBar(activity);
        fontSizeSetter.setMax(Integer.parseInt(activity.getResources().getString(R.string.font_size_max)));  // 14 18 22
        sharedPref = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        fontSizeSetter.setProgress(sharedPref.getInt(Constants.PREF_COMMON_TEXT_SIZE, 16));
        fontSizeSelectorDialog.setIcon(android.R.drawable.ic_menu_sort_alphabetically);
        fontSizeSelectorDialog.setTitle(R.string.font_size_setter);
        fontSizeSelectorDialog.setView(fontSizeSetter);

        fontSizeSetter.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progRess = progress;
            }

            public void onStartTrackingTouch(SeekBar arg0) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        fontSizeSelectorDialog.setPositiveButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface fontSizeSelectorDialog, int which) {
                        if (progRess != 0) {
                            storeFontSize(progRess);
                            activity.recreate();
                        }
                        fontSizeSelectorDialog.dismiss();
                    }
                });

        fontSizeSelectorDialog.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface fontSizeSelectorDialog, int i) {
                        fontSizeSelectorDialog.dismiss();
                    }
                });


        fontSizeSelectorDialog.create();
        fontSizeSelectorDialog.show();
    }

    public void ChangeTheme(Activity activity) {
        Intent intent = new Intent(activity, PrefsActivity.class);
        activity.startActivityForResult(intent, 1);
    }

    private void storeFontSize(int fontSize) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(Constants.PREF_COMMON_TEXT_SIZE, fontSize);
        editor.apply();
    }
}
