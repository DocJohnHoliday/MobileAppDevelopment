package com.example.mobileappdevelopment.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.mobileappdevelopment.R;
import com.example.mobileappdevelopment.database.Repository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TermDetail extends AppCompatActivity {
    String title;
    int termId;
    String startDate;
    String endDate;
    EditText editTitle;
    EditText editStart;
    EditText editEnd;

    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail_view);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);

        editTitle = findViewById(R.id.titleText);
        editStart = findViewById(R.id.startText);
        editEnd = findViewById(R.id.endText);

        title = getIntent().getStringExtra("title");
        startDate = getIntent().getStringExtra("Start");
        endDate = getIntent().getStringExtra("End");

        editTitle.setText(title);
        editStart.setText(startDate);
        editEnd.setText(endDate);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermDetail.this, CourseDetail.class);
                intent.putExtra("termId", termId);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.courserecycleview);
        repository = new Repository(getApplication());
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setmCourse(repository.getAllCourses());
    }
}