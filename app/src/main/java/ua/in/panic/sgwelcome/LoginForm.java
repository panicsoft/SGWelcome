package ua.in.panic.sgwelcome;

import android.content.Intent;
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

public class LoginForm extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Tools tools;
    private View focusView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tools = Tools.getInstance();
        setContentView(R.layout.activity_login_form);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent RegForm = new Intent(LoginForm.this, RegisterForm.class);
                startActivity(RegForm);
                emptyInputFields();
                focusView = mEmailView;
                focusView.requestFocus();

            }
        });

    }

    private void attemptLogin() {

        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;

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
            //Если ошибка - остаёмся на стартовом экране в первом поле с ошибкой
            focusView.requestFocus();
            //Проверяем пароль и выводим сообщения об ощибках или переходим на экран №3
        } else if (tools.getData(email) != null)
            {
                if (tools.getData(email).equals(tools.md5(password))) {
                    Intent DetailForm = new Intent(LoginForm.this, DetailForm.class);
                    DetailForm.putExtra("email", email);
                    startActivity(DetailForm);
                    emptyInputFields();
                    focusView = mEmailView;
                    focusView.requestFocus();
                } else {
                    Toast error = Toast.makeText(getApplicationContext(),
                            "Wrong password", Toast.LENGTH_LONG);
                    error.setGravity(Gravity.CENTER, 0, 0);
                    error.show();
                    mPasswordView.setError("Wrong password");
                    focusView = mPasswordView;
                    focusView.requestFocus();
                }
            }
            else {
                Toast error = Toast.makeText(getApplicationContext(),
                        "Email not registered!!!", Toast.LENGTH_LONG);
                error.setGravity(Gravity.CENTER, 0, 0);
                error.show();
                mEmailView.setError("Email not registered!!!");
                focusView = mEmailView;
                focusView.requestFocus();
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

    private void emptyInputFields() {
        mEmailView.setText(null);
        mEmailView.setError(null);
        mPasswordView.setText(null);
        mPasswordView.setError(null);
    }

}

