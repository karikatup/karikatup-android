package com.labkod.karikatup;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

/**
 * A login screen that offers login via username/password.
 */
public class RegisterActivity extends AppCompatActivity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserRegisterTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mNameView;
    private AutoCompleteTextView mUsernameView;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mPasswordConfirmationView;
    private View mProgressView;
    private View mRegisterFormView;

    private static String APIToken;

    public String errMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Set up the login form.
        mUsernameView = (AutoCompleteTextView) findViewById(R.id.username);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mNameView = (AutoCompleteTextView) findViewById(R.id.name);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordConfirmationView = (EditText) findViewById(R.id.password_confirmation);

        Button mRegisterButton = (Button) findViewById(R.id.do_register_button);
        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

        Button mLoginButton = (Button) findViewById(R.id.do_login_button);
        mLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        mRegisterFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegister() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mNameView.setError(null);
        mUsernameView.setError(null);
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mPasswordConfirmationView.setError(null);

        // Store values at the time of the login attempt.
        String name = mNameView.getText().toString();
        String email = mEmailView.getText().toString();
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();
        String password_confirmation = mPasswordConfirmationView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError("Lütfen bir şifre giriniz");
            focusView = mPasswordView;
            cancel = true;
        }
        if (!isPasswordValid(password)) {
            mPasswordView.setError("Şifre 6 karakterden uzun olmalıdır");
            focusView = mPasswordView;
            cancel = true;
        }
        if(!TextUtils.isEmpty(password) && !TextUtils.equals(password, password_confirmation)){
            mPasswordView.setError("Şifre ve şifre tekrarı uyuşmuyor");
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError("Bu alan gereklidir");
            focusView = mEmailView;
            cancel = true;
        }

        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError("Bu alan gereklidir");
            focusView = mUsernameView;
            cancel = true;
        }

        if (TextUtils.isEmpty(name)) {
            mNameView.setError("Bu alan gereklidir");
            focusView = mNameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserRegisterTask(name, email, username, password, password_confirmation);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private final String mPassword;
        private final String mEmail;
        private final String mName;
        private final String mPasswordConfirmation;

        UserRegisterTask(String name, String email, String username, String password, String passwordConfirmation) {
            mUsername = username;
            mName = name;
            mEmail = email;
            mPassword = password;
            mPasswordConfirmation = passwordConfirmation;

            Log.d("mUsername", username);
            Log.d("mName", name);
            Log.d("mEmail", email);
            Log.d("mPassword", password);
            Log.d("mPasCon", passwordConfirmation);
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            HttpURLConnection conn;
            URL url = null;

            try {
                url = new URL("http://192.168.1.45/api/auth/register");
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("POST");

                conn.setDoInput(true);
                conn.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("username", mUsername)
                        .appendQueryParameter("password", mPassword)
                        .appendQueryParameter("email", mEmail)
                        .appendQueryParameter("name", mName)
                        .appendQueryParameter("password_confirmation", mPasswordConfirmation);
                String query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

                int responseCode = conn.getResponseCode();

                Log.d("NET APIIII", String.valueOf(responseCode));

                if(responseCode == 422) {

                    InputStream input = conn.getErrorStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    String jsonData = result.toString();
                    Log.d("JSON RESULT", jsonData);

                    JSONObject jsonObj = new JSONObject(jsonData);
                    JSONObject error = jsonObj.getJSONObject("error");
                    JSONObject errors = error.getJSONObject("errors");
                    Log.d("JSON ERR", errors.toString());

                    Iterator<?> keys = errors.keys();
                    while( keys.hasNext() ) {
                        String key = (String)keys.next();

                        JSONArray errArr = errors.getJSONArray(key);

                        for (int i = 0; i < errArr.length(); i++) {
                            String errText = errArr.getString(i);
                            Log.d("HATAMESAJ", errText);
                            errMsg = errText;
                        }

                        Log.d("Eray", key);
                    }

                    return false;
                } else if(responseCode == 201) {

                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    String jsonData = result.toString();

                    try {
                        JSONObject jsonObj = new JSONObject(jsonData);
                        APIToken = jsonObj.getString("token");
                    } catch (final JSONException e) {
                        Log.e("JSON", "Json parsing error: " + e.getMessage());
                    }
                    Log.d("RESULT", result.toString());
                    return true;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e("NET", "Malformed URL Exception");
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("NET", "IO Exception");
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("NET", "JSON Exception");
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                // Go to next activity
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                if(!TextUtils.isEmpty(errMsg)){
                    Toast.makeText(RegisterActivity.this, errMsg, Toast.LENGTH_LONG).show();
                    errMsg = null;
                }
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}