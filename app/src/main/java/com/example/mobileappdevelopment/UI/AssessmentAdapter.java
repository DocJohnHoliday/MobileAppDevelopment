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
import com.example.mobileappdevelopment.entities.Assessments;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    private List<Assessments> mAssessments;
    private final Context context;

    private final LayoutInflater mInflater;

    class AssessmentViewHolder extends RecyclerView.ViewHolder {

        private final TextView courseItemView;

        private final TextView courseItemView2;

        private AssessmentViewHolder(@NonNull View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.textView3);
            courseItemView2 = itemView.findViewById(R.id.textView4);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessments current = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetail.class);
                    intent.putExtra("assessmentID", current.getAssessmentId());
                    intent.putExtra("assessment_title", current.getAssessmentTitle());
                    intent.putExtra("assessment_type", current.getAssessmentType());
                    intent.putExtra("assessment_start_date", current.getAssessmentStartDate());
                    intent.putExtra("assessment_end-date", current.getAssessmentEndDate());
                    intent.putExtra("courseID", current.getCourseId());;
                    context.startActivity(intent);
                }
            });
        }
    }

    public AssessmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if (mAssessments != null) {
            Assessments current = mAssessments.get(position);
            String name = current.getAssessmentTitle();
            int courseId = current.getCourseId();
            holder.courseItemView.setText(name);
            holder.courseItemView2.setText(Integer.toString(courseId));
        } else {
            holder.courseItemView.setText("No Assessment Name");
            holder.courseItemView2.setText("No Course ID");
        }
    }

    @Override
    public int getItemCount() {
        if (mAssessments != null) {
            return mAssessments.size();
        } else {
            return 0;
        }
    }


    public void setmAssessments(List<Assessments> assessments) {
        mAssessments = assessments;
        notifyDataSetChanged();
    }
}
