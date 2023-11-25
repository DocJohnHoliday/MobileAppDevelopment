package com.example.mobileappdevelopment.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileappdevelopment.R;
import com.example.mobileappdevelopment.database.Repository;
import com.example.mobileappdevelopment.entities.Courses;
import com.example.mobileappdevelopment.entities.Terms;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TermDetail extends AppCompatActivity {
    String title;
    String start;
    String end;
    int termId;
    EditText editTitle;
    TextView editStartTerm;
    TextView editEndTerm;
    Repository repository;
    int numCourses;
    Terms currentTerm;

    final Calendar myCalendarStartTerm = Calendar.getInstance();
    final Calendar myCalendarEndTerm = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener startDateTerm;
    DatePickerDialog.OnDateSetListener endDateTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail_view);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);

        editTitle = findViewById(R.id.titleText);
        editStartTerm = findViewById(R.id.termStartDate);
        editEndTerm = findViewById(R.id.termEndDate);

        termId = getIntent().getIntExtra("termID", -1);
        title = getIntent().getStringExtra("title_term");
        start = getIntent().getStringExtra("start_term");
        end = getIntent().getStringExtra("end_term");

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editTitle.setText(title);
        editStartTerm.setText(start);
        editEndTerm.setText(end);

        editStartTerm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info = editStartTerm.getText().toString();
                if (info.equals("")) info = LocalDate.now().toString();
                try {
                    myCalendarStartTerm.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetail.this, startDateTerm, myCalendarStartTerm
                        .get(Calendar.YEAR), myCalendarStartTerm.get(Calendar.MONTH),
                        myCalendarStartTerm.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editEndTerm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info2 = editEndTerm.getText().toString();
                if (info2.equals("")) info2 = LocalDate.now().plusDays(1).toString();
                try {
                    myCalendarEndTerm.setTime(sdf.parse(info2));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetail.this, endDateTerm, myCalendarEndTerm
                        .get(Calendar.YEAR), myCalendarEndTerm.get(Calendar.MONTH),
                        myCalendarEndTerm.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDateTerm = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarEndTerm.set(Calendar.YEAR, year);
                myCalendarEndTerm.set(Calendar.MONTH, month);
                myCalendarEndTerm.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEndTerm();

            }
        };

        startDateTerm = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarStartTerm.set(Calendar.YEAR, year);
                myCalendarStartTerm.set(Calendar.MONTH, month);
                myCalendarStartTerm.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStartTerm();

            }
        };
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

    private void updateLabelStartTerm() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editStartTerm.setText(sdf.format(myCalendarStartTerm.getTime()));
    }
    private void updateLabelEndTerm() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editEndTerm.setText(sdf.format(myCalendarEndTerm.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.termsave) {
            Terms terms;
            if (myCalendarStartTerm.after(myCalendarEndTerm)) {
                Toast.makeText(TermDetail.this, "Start Date cannot be after End Date", Toast.LENGTH_LONG).show();
            } else {
                if (termId == -1) {
                    try {
                        if (repository.getmAllTerms().size() == 0) termId = 1;
                        else
                            termId = repository.getmAllTerms().get(repository.getmAllTerms().size() - 1).getTermId() + 1;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    terms = new Terms(termId, editTitle.getText().toString(), editStartTerm.getText().toString(), editEndTerm.getText().toString());
                    repository.insert(terms);
                    Toast.makeText(TermDetail.this, "Term Created", Toast.LENGTH_LONG).show();
                } else {
                    terms = new Terms(termId, editTitle.getText().toString(), editStartTerm.getText().toString(), editEndTerm.getText().toString());
                    repository.update(terms);
                    Toast.makeText(TermDetail.this, "Term Updated", Toast.LENGTH_LONG).show();
                }
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