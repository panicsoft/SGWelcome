package ua.in.panic.sgwelcome;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailForm extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public TextView emailText;
    public TextView WelcomeText;
    private Tools tools;

    // это файл настроек
    public static final String APP_PREFERENCES = "sgdata";
    public static final String APP_PREFERENCES_EMAIL = "email";
    public static final String APP_PREFERENCES_LOGGED_IN = "logged_in";

    SharedPreferences mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tools = Tools.getInstance();
        mData = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        setContentView(R.layout.activity_detail_form);

        WelcomeText = (TextView) findViewById(R.id.WelcomeText);
        WelcomeText.setText("Welcome!");

        emailText = (TextView) findViewById(R.id.EmailText);
        emailText.setText(getIntent().getStringExtra("email"));

        Button mExitButton = (Button) findViewById(R.id.exit_button);
        mExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor e = mData.edit();
                e.putString(APP_PREFERENCES_EMAIL, "");
                e.putBoolean(APP_PREFERENCES_LOGGED_IN, false);
                e.apply();

                finish();
            }
        });

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
