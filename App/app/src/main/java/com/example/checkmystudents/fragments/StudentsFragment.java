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
import android.widget.Button;

import com.example.checkmystudents.R;
import com.example.checkmystudents.activities.StudentList.OnStudentItemClickListener;
import com.example.checkmystudents.activities.StudentList.StudentClickableAdapter;
import com.example.checkmystudents.entities.Student;

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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StudentsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StudentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentsFragment extends Fragment implements OnStudentItemClickListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String teacherLogin_PARAM = "teacherLogin";
    private static final String idClass_PARAM = "idClass";
    private static final String domainName_PARAM = "domainName";

    private String TAG = "StudentListActivity";
    private ProgressDialog dialog;

    private List<Student> studentList;
    public static List<Student> static_studentList;

    public static int presenceRate;
    public static ArrayList<String> absenceList;

    private String mTeacherLogin;
    private int mIdClass;
    private String mDomainName;

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private Button button;

    private boolean isOriginFragment;

    public StudentsFragment() {
        // Required empty public constructor
    }


    public static StudentsFragment newInstance(String tmpTeacherLogin, int tmpIdClass, String tmpDomainName) {
        StudentsFragment fragment = new StudentsFragment();
        Bundle args = new Bundle();
        args.putString(teacherLogin_PARAM, tmpTeacherLogin);
        args.putInt(idClass_PARAM, tmpIdClass);
        args.putString(domainName_PARAM, tmpDomainName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        absenceList = new ArrayList<>();

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTeacherLogin = getArguments().getString(teacherLogin_PARAM);
            mIdClass = getArguments().getInt(idClass_PARAM);
            mDomainName = getArguments().getString(domainName_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_students, container, false);

        new StudentsFragment.fetchURLTask().execute(getString(R.string.students) + mIdClass);

        button = rootView.findViewById(R.id.send_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openResume();
            }
        });

        recyclerView = rootView.findViewById(R.id.student_RecyclerView);

        sendFragmentStatus(false);

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

    public void openResume(){
        ResumeFragment fragment = ResumeFragment.newInstance(mTeacherLogin, mDomainName, presenceRate, absenceList);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_down,R.anim.exit_to_down,R.anim.enter_from_down,R.anim.exit_to_down)
                .replace(R.id.fragment_container,fragment)
                .addToBackStack(null)
                .commit();
    }

    private void buildStudentList(String data) {
        dialog.setMessage("Build Students list...");
        studentList = new ArrayList<>();
        String name, firstName;
        int id, idClass;
        try {

            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id = jsonObject.getInt("id");
                name = jsonObject.getString("name");
                firstName = jsonObject.getString("firstName");
                idClass = jsonObject.getInt("id_class");

                studentList.add(new Student(id,name,firstName,idClass));
            }
            static_studentList = studentList;
            initView();

        } catch (Throwable t) {
            Log.e(TAG, "Could not parse malformed JSON: \"" + data + "\"");
        }
    }

    private void initView() {

        recyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        final List<Student> studentList = new ArrayList<>();

        for (int i = 0; i < static_studentList.size(); i++) {
            Student student = static_studentList.get(i);
            studentList.add(student);
        }

        StudentClickableAdapter adapter = new StudentClickableAdapter(studentList,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickView(View view, int position, boolean isLongClick) {
    }


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
                Log.d(TAG, "doInBackground 3");
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d(TAG, "onPostExecute");
            super.onPostExecute(result);
            buildStudentList(result);
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
