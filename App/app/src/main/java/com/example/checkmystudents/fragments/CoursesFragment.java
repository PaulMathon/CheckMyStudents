package com.example.checkmystudents.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.checkmystudents.R;
import com.example.checkmystudents.activities.ListOfCourses.ClassClickableAdapter;
import com.example.checkmystudents.activities.ListOfCourses.OnClassItemClickListener;
import com.example.checkmystudents.entities.Course;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class CoursesFragment extends Fragment implements OnClassItemClickListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String teacherLogin_PARAM = "teacherLogin";

    private String mteacherLogin;

    private OnFragmentInteractionListener mListener;

    public CoursesFragment() {
        // Required empty public constructor
    }
    RecyclerView recyclerView;


    public static CoursesFragment newInstance(String teacherLogin) {
        CoursesFragment fragment = new CoursesFragment();
        Bundle args = new Bundle();
        args.putString(teacherLogin_PARAM, teacherLogin);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mteacherLogin = getArguments().getString(teacherLogin_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sendFragmentStatus(true);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_courses, container, false);
        recyclerView = rootView.findViewById(R.id.class_RecyclerView);
        new CoursesFragment.fetchURLTask().execute(getString(R.string.classes)+mteacherLogin);

        return rootView;
    }

    public void sendFragmentStatus(boolean fragmentStatus) {
        if (mListener != null) {
            mListener.onFragmentInteraction(fragmentStatus);
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

        void onFragmentInteraction(boolean fragmentStatus);

    }


    private String TAG = "List Of Classes Fragment";
    private ProgressDialog dialog;

    private List<Course> classesList;
    public static List<Course> static_classesList;

    //Construit la liste des cours à partir des données sous forme de sTRING
    private void buildCoursesList(String data) {
        dialog.setMessage("Build Persons list...");
        classesList = new ArrayList<>();
        int idClass;
        String name, pictureUrl;
        try {

            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                idClass = jsonObject.getInt("idClass");
                name = jsonObject.getString("domainName");
                pictureUrl = jsonObject.getString("pictureUrl");
                classesList.add(new Course(idClass,name,pictureUrl));
            }
            //La liste construite est envoyée à la liste statique, pour pouvoir être réexploitée plus tard
            static_classesList = classesList;
            //La liste étant créée, on peut initialiser la RecyclerView à partir de celle-ci
            initView();


        } catch (Throwable t) {
            Log.e(TAG, "Could not parse malformed JSON: \"" + data + "\"");
        }
    }

    //méthonde d'initialisation de la vue
    private void initView() {

        //Création de l'objet recycler view à partir de son layout

        recyclerView.setHasFixedSize(false);
        //Paramétrage de son conteneur (LinearLayout)
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        final List<Course> stringList = new ArrayList<>();

        for (int i = 0; i < static_classesList.size(); i++) {
            Course course = static_classesList.get(i);
            stringList.add(course);
        }

        //Création et attribution de l'objet adapter correspondant
        ClassClickableAdapter adapter = new ClassClickableAdapter(stringList,this);
        recyclerView.setAdapter(adapter);
    }

    //On a implémenté l'interface OnClassItemClickListener, on doit donc surcharger ses méthodes: OnClickView
    //OnClickView sert à determiner ce que l'application doit faire quand l'utilisateur click sur un item
    @Override
    public void onClickView(View view, int position) {

        openStudentList(position);
    }

    public void openStudentList(int position){
        StudentsFragment fragment = StudentsFragment.newInstance(mteacherLogin,
                static_classesList.get(position).getIdClass(),
                static_classesList.get(position).getDomainName());
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.activity_in,R.anim.activity_out,R.anim.activity_in,R.anim.activity_out)
                .replace(R.id.fragment_container,fragment)
                .addToBackStack(null)
                .commit();
    }

    //Téléchargement des données
    private class fetchURLTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Téléchargement des données...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground 1");
            try {
                Log.d(TAG, "doInBackground 2");
                return loadFromNetwork(strings[0]);
            } catch (IOException e) {
                Log.e(TAG, "background exception ");
                return e.getMessage();
            }
        }

        //Une fois les données téléchargées
        @Override
        protected void onPostExecute(String result) {
            Log.d(TAG, "onPostExecute");
            super.onPostExecute(result);
            buildCoursesList(result);
            dialog.dismiss();
        }

    }

    private String loadFromNetwork(String urlString) throws IOException {
        Log.d(TAG, "loadFromNetwork");
        InputStream stream = null;
        String str = "";
        try {
            stream = downloadURL(urlString);
            str = readIt(stream);
        } finally {
            {
                if (stream != null) {
                    stream.close();
                }
            }
        }
        return str;
    }

    //Méthode servant à créer la connection selon l'url donnée
    private InputStream downloadURL(String urlString) throws IOException {
        Log.d(TAG, "downloadURL " + urlString);
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000000);
        conn.setConnectTimeout(15000000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        return conn.getInputStream();
    }

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

}
