package com.example.mobileappdevelopment.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileappdevelopment.R;
import com.example.mobileappdevelopment.database.Repository;
import com.example.mobileappdevelopment.entities.Courses;
import com.example.mobileappdevelopment.entities.Terms;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TermDetail extends AppCompatActivity {
    String title;
    String start;
    String end;
    int termId;
    EditText editTitle;
    EditText editStart;
    EditText editEnd;
    Repository repository;
    int numCourses;
    Terms currentTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail_view);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);

        editTitle = findViewById(R.id.titletext);
        editStart = findViewById(R.id.starttext);
        editEnd = findViewById(R.id.endtext);
        termId = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("name");
        start = getIntent().getStringExtra("price");
        end = getIntent().getStringExtra("end");
        editTitle.setText(title);
        editEnd.setText(start);
        editEnd.setText(end);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermDetail.this, CourseDetail.class);
                intent.putExtra("termID", termId);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.courserecyclerview);
        repository = new Repository(getApplication());
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Courses> filteredCourse = new ArrayList<>();
        for (Courses c : repository.getAllCourses()) {
            if (c.getTermId() == termId) filteredCourse.add(c);
        }
        courseAdapter.setmCourse(filteredCourse);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.termsave) {
            Terms terms;
            if (termId == -1) {
                try {
                    if (repository.getmAllTerms().size() == 0) termId = 1;
                    else
                        termId = repository.getmAllTerms().get(repository.getmAllTerms().size() - 1).getTermId() + 1;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                terms = new Terms(termId, editTitle.getText().toString(), editStart.getText().toString(),editEnd.getText().toString());
                repository.insert(terms);
                this.finish();
            } else {
                terms = new Terms(termId, editTitle.getText().toString(), editStart.getText().toString(),editEnd.getText().toString());
                repository.update(terms);
                this.finish();
            }
        }
        if (item.getItemId() == R.id.termdelete) {
            try {
                for (Terms terms : repository.getmAllTerms()) {
                    if (terms.getTermId() == termId) currentTerm = terms;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            numCourses = 0;
            for (Courses courses : repository.getAllCourses()) {
                if (courses.getTermId() == termId) ++numCourses;
            }
            if (numCourses == 0) {
                repository.delete(currentTerm);
                Toast.makeText(TermDetail.this, currentTerm.getTermTitle() + " was deleted", Toast.LENGTH_LONG).show();
                TermDetail.this.finish();
            } else {
                Toast.makeText(TermDetail.this, "Can't delete a term with courses", Toast.LENGTH_LONG).show();
            }
        }
        return true;
    }

    @Override
    protected void onResume() {

        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.courserecyclerview);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Courses> filteredCourse = new ArrayList<>();
        for (Courses c : repository.getAllCourses()) {
            if (c.getTermId() == termId) filteredCourse.add(c);
        }
        courseAdapter.setmCourse(filteredCourse);

        //Toast.makeText(ProductDetails.this,"refresh list",Toast.LENGTH_LONG).show();
    }
}