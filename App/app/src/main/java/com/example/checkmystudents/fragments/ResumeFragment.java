package com.example.checkmystudents.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.example.checkmystudents.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResumeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResumeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResumeFragment extends Fragment {

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String teacherLogin_PARAM = "teacherLogin";
    private static final String domainName_PARAM = "domainName";
    private static final String presenceRate_PARAM = "presenceRate";
    private static final String absenceList_PARAM = "absenceList";


    private String mTeacherLogin;
    private String mDomainName;
    private int mPresenceRate;
    private ArrayList mAbsenceList;

    private OnFragmentInteractionListener mListener;

    private TextView presenceRateView;
    private Button button;
    private TextView absenceView;

    public ResumeFragment() {
        // Required empty public constructor
    }

    public static ResumeFragment newInstance(String teacherLogin, String domainName,
                                             int presenceRate, ArrayList absenceList) {
        ResumeFragment fragment = new ResumeFragment();
        Bundle args = new Bundle();
        args.putString(teacherLogin_PARAM, teacherLogin);
        args.putString(domainName_PARAM, domainName);
        args.putInt(presenceRate_PARAM, presenceRate);
        args.putStringArrayList(absenceList_PARAM, absenceList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTeacherLogin = getArguments().getString(teacherLogin_PARAM);
            mDomainName = getArguments().getString(domainName_PARAM);
            mPresenceRate = getArguments().getInt(presenceRate_PARAM);
            mAbsenceList = getArguments().getStringArrayList(absenceList_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_resume, container, false);

        presenceRateView = rootView.findViewById(R.id.presence_rate);
        presenceRateView.setText(Integer.toString(mPresenceRate));

        absenceView = rootView.findViewById(R.id.absence_list_textView);
        for (int i=0; i<mAbsenceList.size();i++) {
            absenceView.setText(absenceView.getText().toString() + mAbsenceList.get(i)+" \n ");
        }

        button = rootView.findViewById(R.id.signature_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                button.setVisibility(View.GONE);
                SignatureFragment fragment = SignatureFragment.newInstance(mTeacherLogin,mDomainName,mPresenceRate,mAbsenceList);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_down,R.anim.exit_to_down,R.anim.enter_from_down,R.anim.exit_to_down);
                transaction.addToBackStack(null);
                transaction.add(R.id.signature_fragment_container,fragment,"signature_fragment").commit();            }
        });
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }




}
