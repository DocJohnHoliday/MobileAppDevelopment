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
import com.example.mobileappdevelopment.entities.Terms;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TermList extends AppCompatActivity {

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_terms);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermList.this, TermDetail.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        repository = new Repository(getApplication());
        try {
            List<Terms> allTerms = repository.getmAllTerms();
            final TermAdapter termAdapter = new TermAdapter(this);
            recyclerView.setAdapter(termAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            termAdapter.setTerms(allTerms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(getIntent().getStringExtra("test"));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sample) {
            repository = new Repository(getApplication());
            //Toast.makeText(ListOfTerms.this, "Put in sample data", Toast.LENGTH_LONG).show();
            Terms terms = new Terms(0, "First Term", "2023-05-01", "2023-05-01");
            repository.insert(terms);
            terms = new Terms(0, "Second Term", "2023-05-01", "2023-05-01");
            repository.insert(terms);
            Courses courses = new Courses(0, "C196", "Incomplete",
                    "Carolyn", "555-555-5555", "wgu.email", 1);
            repository.insert(courses);
            courses = new Courses(0, "C856", "Incomplete",
                    "Taylor", "555-444-5555", "wgu.email", 1);
            repository.insert(courses);

            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return true;
    }

    @Override
    protected void onResume() {

        super.onResume();
        List<Terms> allTerms;
        try {
            allTerms = repository.getmAllTerms();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);
    }
}