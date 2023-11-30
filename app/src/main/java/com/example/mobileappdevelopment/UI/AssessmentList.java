package com.example.mobileappdevelopment.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.example.mobileappdevelopment.R;
import com.example.mobileappdevelopment.database.Repository;
import com.example.mobileappdevelopment.entities.Assessments;
import com.example.mobileappdevelopment.entities.Terms;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AssessmentList extends AppCompatActivity {

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_assessments);
//        FloatingActionButton fab = findViewById(R.id.floatingActionButtonAssessmentList);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(AssessmentList.this, AssessmentDetail.class);
//                startActivity(intent);
//            }
//        });
        RecyclerView recyclerView = findViewById(R.id.recyclerviewAssessmentList);
        repository = new Repository(getApplication());
            List<Assessments> allAssessments = repository.getAllAssessments();
            final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
            recyclerView.setAdapter(assessmentAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            assessmentAdapter.setmAssessments(allAssessments);
        //System.out.println(getIntent().getStringExtra("test"));
    }
}