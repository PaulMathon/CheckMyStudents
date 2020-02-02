package com.example.checkmystudents.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.checkmystudents.MainActivity;
import com.example.checkmystudents.R;
import com.example.checkmystudents.activities.LoginActivity;
import com.example.checkmystudents.entities.Teacher;

import org.json.JSONObject;

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
import java.util.HashMap;
import java.util.Map;

public class SignUpFragment extends Fragment {

    private String TAG = "SignUpFragment";
    private ProgressDialog dialog;

    private UserSignUpTask mSignUpTask = null;

    //View components
    private EditText loginEdit;
    private EditText emailEdit;
    private EditText password1Edit;
    private EditText password2Edit;
    private Button signUpButton;

    private OnFragmentInteractionListener mListener;

    public SignUpFragment() {
        // Required empty public constructor
    }


    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        loginEdit = rootView.findViewById(R.id.signUp_login);
        emailEdit = rootView.findViewById(R.id.signUp_email);
        password1Edit = rootView.findViewById(R.id.signUp_password_1);
        password2Edit = rootView.findViewById(R.id.signUp_password_2);
        signUpButton = rootView.findViewById(R.id.signUp_button);
        signUpButton.setOnClickListener(btnSignUpClickListener);

        return rootView;
    }

    private View.OnClickListener btnSignUpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String login = loginEdit.getText().toString();
            String email = emailEdit.getText().toString();
            String password1 = password1Edit.getText().toString();
            String password2 = password2Edit.getText().toString();
            Log.d(TAG,"T'as cliqué");

            if (noSignUpProblem(login,email,password1,password2)){
                Log.d(TAG,"Les critères renseignés sont corrects");
                mSignUpTask = new UserSignUpTask(email, login, password1);
                mSignUpTask.execute(getString(R.string.register));
            } else {
                if (!email.contains("@")){
                    emailEdit.setError("L'email renseigné ne répond pas aux critères");
                    emailEdit.setText("");
                } else if (!password1.equals(password2)){
                    password1Edit.setError("Les mots de passe ne sont pas identiques");
                    password1Edit.setText("");
                    password2Edit.setText("");
                }
            }
        }

    };

    public boolean noSignUpProblem(String login,String email,String password1,String password2){
        boolean noProblem=true;
        if (!email.contains("@")){
            noProblem = false;
        }
        if (!password1.equals(password2)){
            noProblem = false;
        }
        return noProblem;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }


    public class UserSignUpTask extends AsyncTask<String, Void, String> {

        private final String mEmail;
        private final String mLogin;
        private final String mPassword;


        UserSignUpTask(String email, String login, String password) {
            mEmail = email;
            mLogin = login;
            mPassword = password;
        }

        // Création de la map contenant les clés-valeurs
        HashMap accessToken = new HashMap();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Démarrage du processus d'authentification");
            dialog.show();
            accessToken.put("email",mEmail);
            accessToken.put("login",mLogin);
            accessToken.put("password",mPassword);
        }

        @Override
        protected String doInBackground(String... strings) {

            try {

                URL url = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000000);
                conn.setConnectTimeout(15000000);
                //Autorisation d'envoyer et de recevoir des données
                conn.setDoOutput(true);
                conn.setDoInput(true);
                //Type de requête
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
                    isSignUp(teacher);

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
            mSignUpTask = null;
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

    public void isSignUp(Teacher teacher) {
        if (teacher != null) {
            navigateToMainActivity(MainActivity.class,teacher);
        }
    }

    //appelée si un teacher est retourné
    public void navigateToMainActivity(Class activityClass, Teacher teacher){
        //Création d'un nouvel intent <-> changement d'activité
        Intent intent = new Intent(getActivity(), activityClass);
        //On y place les données nécessaire : ici l'id de la classe correspondante à la postion dans la liste
        intent.putExtra("login", teacher.getLogin());
        //On démarre l'activité liée à l'intent : StudentListActivity
        startActivity(intent);
    }

    //appelée si aucun teacher n'est retourné
    public void stayInLoginActivity(){
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

}
