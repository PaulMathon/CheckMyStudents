package com.example.checkmystudents.activities.ListOfCourses;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.checkmystudents.R;
import com.example.checkmystudents.entities.Course;

import java.util.List;
//Classe qui permet de paramettrer ce qu'il y aura dans la liste
public class ClassClickableAdapter extends  RecyclerView.Adapter<ClassClickableAdapter.MyViewHolder> {

    private List<Course> classList;
    private static OnClassItemClickListener onClassClickListener;

    //Constructeur de l'adapteur
    public ClassClickableAdapter(List<Course> _classList, OnClassItemClickListener _onClassItemClickListener) {
        classList = _classList;
        onClassClickListener =_onClassItemClickListener;
    }

    //Classe qui permet de paramettrer un item de la liste
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //La view qui contient le nom de la classe
        public TextView className;
        public ImageView classPicture;
        public LinearLayout classContainer;

        //Constructeur
        public MyViewHolder(View view) {
            super(view);
            classContainer = view.findViewById(R.id.course_container);
            className = view.findViewById(R.id.textView_class_name);
            classPicture = view.findViewById(R.id.class_imageView);
            view.setOnClickListener(this);
        }

        //Classe surchargée par l'implémentation de OnClickListener
        @Override
        public void onClick(View v) {
            onClassClickListener.onClickView(v,getAdapterPosition());
        }
    }


    //Méthode qui crée la liste
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item, parent, false);

        return new MyViewHolder(itemView);
    }

    //Méthode qui relie la liste avec les items
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String className = classList.get(position).getDomainName();
        String pictureUrl = classList.get(position).getPictureUrl();
        holder.className.setText(className);
        if (position % 2 == 0) {
            holder.classContainer.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            holder.classContainer.setBackgroundColor(Color.parseColor("#EDEDED"));
        }
        Log.d("Picture url", pictureUrl);

        if (pictureUrl.equals("iti.png")) {
            Log.d("info", "t'es dans iti");
            holder.classPicture.setImageResource(R.drawable.iti);
        } else if (pictureUrl.equals("smartcities.png")) {
            Log.d("info", "t'es dans smart");
            holder.classPicture.setImageResource(R.drawable.smartcities);
        } else if (pictureUrl.equals("baa.png")) {
            Log.d("info", "t'es dans baa");
            holder.classPicture.setImageResource(R.drawable.baa);
        } else if (pictureUrl.equals("btp.png")) {
            Log.d("info", "t'es dans btp");
        holder.classPicture.setImageResource(R.drawable.btp);
        } else if (pictureUrl.equals("esea.png")) {
            Log.d("info", "t'es dans esea");
            holder.classPicture.setImageResource(R.drawable.esea);
        } else if (pictureUrl.equals("ims.png")) {
            Log.d("info", "t'es dans ims");
            holder.classPicture.setImageResource(R.drawable.ims);
        } else {
            Log.e("erreur", "Drawable non trouvé");
        }

    }

    //Méthode qui dit combien il faut d'item dans la liste (là on dit qu'on veut tous les items dans la liste)
    @Override
    public int getItemCount() {

        return classList.size();
    }
}
