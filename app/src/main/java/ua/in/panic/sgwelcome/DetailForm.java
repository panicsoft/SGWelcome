package ua.in.panic.sgwelcome;

import android.app.LoaderManager;
import android.content.Loader;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tools = Tools.getInstance();
        setContentView(R.layout.activity_detail_form);

        WelcomeText = (TextView) findViewById(R.id.WelcomeText);
        WelcomeText.setText("Welcome!");

        emailText = (TextView) findViewById(R.id.EmailText);
        emailText.setText(getIntent().getStringExtra("email"));

        Button mExitButton = (Button) findViewById(R.id.exit_button);
        mExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
