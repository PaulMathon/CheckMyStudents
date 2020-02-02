package com.example.checkmystudents.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.checkmystudents.MainActivity;
import com.example.checkmystudents.R;
import com.example.checkmystudents.entities.Teacher;
import com.example.checkmystudents.fragments.SignUpFragment;

import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity implements SignUpFragment.OnFragmentInteractionListener {

    private String TAG = "LoginActivity";
    private ProgressDialog dialog;

    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Button mEmailSignInButton;
    private Button signUpButton;
    private FrameLayout signUpFragmentContainer;
    private ImageView loginImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView =  findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);

        loginImage = findViewById(R.id.login_image);

        mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailView.getText().toString();
                String password = mPasswordView.getText().toString();
                mAuthTask = new UserLoginTask(email, password);
                mAuthTask.execute(getString(R.string.authentification));
            }
        });

        signUpButton = findViewById(R.id.signup_button);
        signUpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment();
            }
        });

        signUpFragmentContainer = findViewById(R.id.signUp_fragment_container);

    }

    public void openFragment() {
        final Animation an2 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_fade_out);
        loginImage.startAnimation(an2);
        loginImage.setVisibility(View.GONE);
        mEmailView.setVisibility(View.GONE);
        mPasswordView.setVisibility(View.GONE);
        mEmailSignInButton.setVisibility(View.GONE);
        signUpButton.setVisibility(View.GONE);

        SignUpFragment fragment = SignUpFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_down,R.anim.exit_to_down,R.anim.enter_from_down,R.anim.exit_to_down);
        //transaction.replace(R.id.signUp_fragment_container,fragment);
        transaction.addToBackStack(null);
        transaction.add(R.id.signUp_fragment_container,fragment,"sign_up_fragment").commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        loginImage.setVisibility(View.VISIBLE);
        mEmailView.setVisibility(View.VISIBLE);
        mPasswordView.setVisibility(View.VISIBLE);
        mEmailSignInButton.setVisibility(View.VISIBLE);
        signUpButton.setVisibility(View.VISIBLE);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */

    //Téléchargement des données
    public class UserLoginTask extends AsyncTask<String, Void, String> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        // Création de la map contenant les clés-valeurs
        HashMap accessToken = new HashMap();
        

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(LoginActivity.this);
            dialog.setMessage("Démarrage du processus d'authentification");
            dialog.show();
            accessToken.put("login",mEmail);
            accessToken.put("password",mPassword);
        }

        @Override
        protected String doInBackground(String... strings) {

            try {

                URL url = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000000);
                conn.setConnectTimeout(15000000);
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("charset", "utf-8");

                conn.setDoOutput(true);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

                writer.write(getDataString(accessToken));
                writer.flush();
                writer.close();

                conn.connect();

                int statusCode = conn.getResponseCode();

                if (statusCode ==  200) {

                    InputStream inputStream = new BufferedInputStream(conn.getInputStream());

                    String response = readIt(inputStream);

                    // From here you can convert the string to JSON with whatever JSON parser you like to use
                    Teacher teacher = getResponse(response);

                    Log.d("Teacher Object", teacher.getLogin());
                    isAuth(teacher);

                } else {
                    // Status code is not 200
                    Log.d("Http error",Integer.toString(statusCode));
                    // Do something to handle the error
                    stayInLoginActivity();
                }

            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }

    }


    //Méthode
    private String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
        Log.d(TAG, "readIt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    private String getDataString(HashMap<String, String> params) throws UnsupportedEncodingException{
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

    //Construit la liste des cours à partir des données sous forme de sTRING
    private Teacher getResponse(String data) {
        dialog.setMessage("Response for authentification received...");
        Teacher teacher = null;
        long teacherId;
        String login, password;
        try {
            Log.d("data", data);
            JSONObject jsonObject = new JSONObject(data);
            teacherId = jsonObject.getInt("idTeacher");
            Log.d("teacherId",Long.toString(teacherId));
            login = jsonObject.getString("login");
            Log.d("login",login);
            password = jsonObject.getString("password");
            Log.d("password",password);

            teacher = new Teacher(teacherId, login, password);

        } catch (Throwable t) {
            Log.e(TAG, "Could not parse malformed JSON: \"" + data + "\"");
        }

        return teacher;
    }

    public void isAuth(Teacher teacher) {
        if (teacher != null) {
            //navigateToCoursesList(ListOfCoursesActivity.class,teacher);
            navigateToCoursesList(MainActivity.class,teacher);
        }
    }

    //appelée si un teacher est retourné
    public void navigateToCoursesList(Class activityClass, Teacher teacher){
        //Création d'un nouvel intent <-> changement d'activité
        Intent intent = new Intent(this, activityClass);
        //On y place les données nécessaire : ici l'id de la classe correspondante à la postion dans la liste
        intent.putExtra("login", teacher.getLogin());
        //On démarre l'activité liée à l'intent : StudentListActivity
        startActivity(intent);
    }

    //appelée si un teacher est retourné
    public void stayInLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

}

