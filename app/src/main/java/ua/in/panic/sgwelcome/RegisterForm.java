package ua.in.panic.sgwelcome;

import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.Loader;
import android.database.Cursor;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by PaNiC on 21.05.2017.
 */

public class RegisterForm extends AppCompatActivity implements LoaderCallbacks<Cursor> {


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mRePasswordView;
    private Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tools = Tools.getInstance();
        setContentView(R.layout.activity_register_form);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.register_email);

        mPasswordView = (EditText) findViewById(R.id.register_password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });

        mRePasswordView = (EditText) findViewById(R.id.register_repassword);
        mRePasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });

        Button mRegButton = (Button) findViewById(R.id.action_register_button);
        mRegButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });
    }


    private void attemptRegister(){
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the register attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String repassword = mRePasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid RePassword, if the user entered one.
        if (TextUtils.isEmpty(repassword)) {
            mRePasswordView.setError(getString(R.string.error_field_required));
            focusView = mRePasswordView;
            cancel = true;
        } else if (!isRePasswordValid(password, repassword)) {
            mRePasswordView.setError(getString(R.string.error_pass_not_match));
            focusView = mRePasswordView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else
            {
            if (tools.getData(email) !=null)
            {
                Toast error = Toast.makeText(getApplicationContext(),
                        "This email is already registered! Sign in.", Toast.LENGTH_LONG);
                error.setGravity(Gravity.CENTER, 0, 0);
                error.show();
                mEmailView.setError("Email is already registered!");
                focusView = mEmailView;
                focusView.requestFocus();

            } else {
                tools.addData(email, password);
                Toast RMess = Toast.makeText(getApplicationContext(),
                        "Registration completed!", Toast.LENGTH_SHORT);
                RMess.setGravity(Gravity.CENTER, 0, 0);
                RMess.show();
                finish();
            }
        }
    }

    private boolean isEmailValid(String email) {
        //Адрес почты должен содержать "эт" (собачку) и быть не менее 6 символов
        return (email.contains("@") && email.length() > 5);
    }

    private boolean isPasswordValid(String password) {
        //Установим минимальную длину пароля не менее 4 символов
        return password.length() > 3;
    }

    private boolean isRePasswordValid(String password, String repassword) {
        //Проверим пароли на идентичность
        return password.equals(repassword);
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

