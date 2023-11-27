package com.example.mobileappdevelopment.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.mobileappdevelopment.R;
import com.example.mobileappdevelopment.database.Repository;
import com.example.mobileappdevelopment.entities.Courses;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CourseList extends AppCompatActivity {

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_courses);
        FloatingActionButton fab = findViewById(R.id.floatingActionButtonCourseList);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseList.this, CourseDetail.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerviewCourseList);
        repository = new Repository(getApplication());
            List<Courses> allCourses = repository.getAllCourses();
            final CourseAdapter courseAdapter = new CourseAdapter(this);
            recyclerView.setAdapter(courseAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            courseAdapter.setmCourse(allCourses);
        //System.out.println(getIntent().getStringExtra("test"));
    }

//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_term_list, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        return true;
//    }

//    @Override
//    protected void onResume() {
//
////        super.onResume();
////        List<Terms> allTerms;
////        try {
////            allTerms = repository.getmAllTerms();
////        } catch (InterruptedException e) {
////            throw new RuntimeException(e);
////        }
////        RecyclerView recyclerView = findViewById(R.id.recyclerview);
////        final TermAdapter termAdapter = new TermAdapter(this);
////        recyclerView.setAdapter(termAdapter);
////        recyclerView.setLayoutManager(new LinearLayoutManager(this));
////        termAdapter.setTerms(allTerms);
//    }
}