package com.example.checkmystudents.fragments;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.checkmystudents.R;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SignatureFragment extends Fragment {

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

    private Button mClear, mCancel,mSendEmail;
    private File file;
    private LinearLayout mContent;
    private View view;
    private Signature mSignature;
    private Bitmap bitmap;

    private boolean send = false;

    // Creating Separate Directory for saving Generated Images
    String DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/UserSignature/";
    String pic_name = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    String StoredPath = DIRECTORY + pic_name + ".png";

    public SignatureFragment() {
        // Required empty public constructor
    }

    public static SignatureFragment newInstance(String teacherLogin, String domainName, int presenceRate, ArrayList absenceList) {
        SignatureFragment fragment = new SignatureFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_signature, container, false);
        mContent = rootView.findViewById(R.id.canvasLayout);
        mSignature = new Signature(getActivity(), null);
        mSignature.setBackgroundColor(Color.LTGRAY);
        // Dynamically generating Layout through java code
        mContent.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mClear = rootView.findViewById(R.id.clear);
        mCancel = rootView.findViewById(R.id.cancel);
        view = mContent;

        mClear.setOnClickListener(onButtonClick);
        mCancel.setOnClickListener(onButtonClick);

        // Method to create Directory, if the Directory doesn't exists
        file = new File(DIRECTORY);
        if (!file.exists()) {
            file.mkdir();
        }

        mSendEmail = rootView.findViewById(R.id.send_email_button);
        mSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("path",DIRECTORY);
                if (Build.VERSION.SDK_INT >= 23) {
                    isStoragePermissionGranted();
                } else {
                    view.setDrawingCacheEnabled(true);
                    mSignature.save(view, StoredPath);
                }
                sendEmail();
                send=true;
            }
        });
        return rootView;
    }
    Button.OnClickListener onButtonClick = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (v == mClear) {
                Log.v("log_tag", "Panel Cleared");
                mSignature.clear();
                //mGetSign.setEnabled(false);
            } else if(v == mCancel){
                Log.v("log_tag", "Panel Canceled");
                CoursesFragment fragment = CoursesFragment.newInstance(mTeacherLogin);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.activity_in,R.anim.activity_out,R.anim.activity_in,R.anim.activity_out);
                transaction.addToBackStack(null);
                transaction.add(R.id.fragment_container,fragment,"Courses_fragment").commit();
            }
        }
    };

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



    public void sendEmail() {
        send=true;
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setType("text/html");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "paul.mathon@hei.yncrea.fr" });
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Fiche de présence du cours de " + mDomainName);

        String message = "Nombre d'absents : " + mPresenceRate + "\n";
        for (int i=0;i<mAbsenceList.size();i++) {
            message = message + mAbsenceList.get(i) + "\n";
        }
        message = message + "\n" +mTeacherLogin;

        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        //Uri uri = Uri.parse(StoredPath);
        Resources res = this.getResources();
        /**
         * Creates a Uri which parses the given encoded URI string.
         * @param uriString an RFC 2396-compliant, encoded URI
         * @throws NullPointerException if uriString is null
         * @return Uri for this given uri string
         */
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + this.getResources().getResourcePackageName(R.drawable.baa)
                + '/' + this.getResources().getResourceTypeName(R.drawable.baa)
                + '/' + this.getResources().getResourceEntryName(R.drawable.baa) );

        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);


        startActivity(Intent.createChooser(emailIntent,"Send Email"));

    }


    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                mSignature.save(view, StoredPath);
                return true;
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("POINT", "You are in onRequestPermissionsResult");
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            view.setDrawingCacheEnabled(true);
            mSignature.save(view, StoredPath);
            Toast.makeText(getActivity(), "Successfully Saved", Toast.LENGTH_SHORT).show();
            // Calling the same class
            getActivity().recreate();
        }
        else
        {
            Toast.makeText(getActivity(), "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
        }
    }

    public class Signature extends View {

        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public Signature(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        public void save(View v, String StoredPath) {
            Log.v("Signature Image", "Width: " + v.getWidth());
            Log.v("Signature Image", "Height: " + v.getHeight());
            Log.d("Stored path", StoredPath);
            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);
            }
            Canvas canvas = new Canvas(bitmap);
            try {
                // Output the file
                FileOutputStream mFileOutStream = new FileOutputStream(StoredPath);
                v.draw(canvas);

                // Convert the output file to Image such as .png
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                mFileOutStream.flush();
                mFileOutStream.close();
                Log.d("Signature save","SUCCESS");

            } catch (Exception e) {
                Log.v("Signature Save ERROR", e.toString());
            }

        }

        public void clear() {
            path.reset();
            invalidate();
            //mGetSign.setEnabled(false);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();
            //mGetSign.setEnabled(true);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string) {

            Log.v("log_tag", string);

        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }
}
