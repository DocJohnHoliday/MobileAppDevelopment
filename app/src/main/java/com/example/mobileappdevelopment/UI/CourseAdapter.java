package com.example.mobileappdevelopment.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileappdevelopment.R;
import com.example.mobileappdevelopment.entities.Courses;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private List<Courses> mCourse;
    private final Context context;

    private final LayoutInflater mInflater;

    class CourseViewHolder extends RecyclerView.ViewHolder {

        private final TextView courseItemView;

        private final TextView courseItemView2;

        private CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.textView3);
            courseItemView2 = itemView.findViewById(R.id.textView4);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Courses current = mCourse.get(position);
                    Intent intent = new Intent(context, CourseDetail.class);
                    intent.putExtra("courseID", current.getCourseId());
                    intent.putExtra("title_course", current.getCourseTitle());
                    intent.putExtra("status", current.getStatus());
                    intent.putExtra("name", current.getInstructorName());
                    intent.putExtra("phone", current.getPhoneNumber());
                    intent.putExtra("email", current.getEmail());
                    intent.putExtra("course_start_date", current.getCourseStartDate());
                    intent.putExtra("course_end_date", current.getCourseEndDate());
                    intent.putExtra("termID", current.getTermId());
                    context.startActivity(intent);
                }
            });
        }
    }

    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if (mCourse != null) {
            Courses current = mCourse.get(position);
            String name = current.getCourseTitle();
            int termId = current.getTermId();
            holder.courseItemView.setText(name);
            holder.courseItemView2.setText(Integer.toString(termId));
        } else {
            holder.courseItemView.setText("No course Name");
            holder.courseItemView2.setText("No Term ID");
        }
    }

    @Override
    public int getItemCount() {
        if (mCourse != null) {
            return mCourse.size();
        } else {
            return 0;
        }
    }


    public void setmCourse(List<Courses> courses) {
        mCourse = courses;
        notifyDataSetChanged();
    }
}
