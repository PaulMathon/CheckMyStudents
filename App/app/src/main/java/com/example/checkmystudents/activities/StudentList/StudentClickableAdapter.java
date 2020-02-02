package com.example.checkmystudents.activities.StudentList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.checkmystudents.R;
import com.example.checkmystudents.entities.Student;

import java.util.List;

import static com.example.checkmystudents.fragments.StudentsFragment.absenceList;
import static com.example.checkmystudents.fragments.StudentsFragment.presenceRate;


public class StudentClickableAdapter extends RecyclerView.Adapter<StudentClickableAdapter.MyViewHolder> {

    private List<Student> studentList;
    private static OnStudentItemClickListener onStudentClickListener;

    public StudentClickableAdapter(List<Student> _studentList, OnStudentItemClickListener _onStudentItemClickListener) {
        studentList = _studentList;
        onStudentClickListener =_onStudentItemClickListener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView studentName;
        public CheckBox studentCheckBox;

        public MyViewHolder(View view) {
            super(view);
            studentName = view.findViewById(R.id.textView_student_name);
            studentCheckBox = view.findViewById(R.id.checkBox_student);

            studentCheckBox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (((CheckBox) v).isChecked()) {
                        presenceRate++;
                        absenceList.add(studentName.getText().toString());
                    } else {
                        presenceRate--;
                        absenceList.remove(studentName.getText().toString());
                    }
                }
            });

            studentName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (studentCheckBox.isChecked()) {
                        studentCheckBox.setChecked(false);
                        presenceRate--;
                        absenceList.remove(studentName.getText().toString());
                    } else {
                        studentCheckBox.setChecked(true);
                        presenceRate++;
                        absenceList.add(studentName.getText().toString());
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            onStudentClickListener.onClickView(v,getAdapterPosition(),false);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String studentName = studentList.get(position).getName();
        String studentFirstName = studentList.get(position).getFirstName();
        holder.studentName.setText(studentFirstName + " " + studentName);
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }
}
