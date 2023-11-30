package com.example.mobileappdevelopment.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileappdevelopment.R;
import com.example.mobileappdevelopment.database.Repository;
import com.example.mobileappdevelopment.entities.Assessments;
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

public class CourseDetail extends AppCompatActivity {
    String title;
    String instrName;
    String status;
    String instrPhone;
    String instrEmail;
    String courseStart;
    String courseEnd;
    String note;
    int courseID;
    int termID;
    EditText editTitle;
    Spinner editStatus;
    EditText editName;
    EditText editPhone;
    EditText editEmail;
    EditText editNote;
    TextView editCourseStart;
    TextView editCourseEnd;
    Repository repository;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();

    Courses currentCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_course_view);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton4);
        repository = new Repository(getApplication());

        title = getIntent().getStringExtra("title_course");
        editTitle = findViewById(R.id.courseTitle);
        editTitle.setText(title);

        instrName = getIntent().getStringExtra("name");
        editName = findViewById(R.id.instructorName);
        editName.setText(instrName);

        instrPhone = getIntent().getStringExtra("phone");
        editPhone = findViewById(R.id.instructorPhone);
        editPhone.setText(instrPhone);

        instrEmail = getIntent().getStringExtra("email");
        editEmail = findViewById(R.id.instructorEmail);
        editEmail.setText(instrEmail);

        courseStart = getIntent().getStringExtra("course_start_date");
        editCourseStart = findViewById(R.id.courseStartDate);
        editCourseStart.setText(courseStart);

        courseEnd = getIntent().getStringExtra("course_end_date");
        editCourseEnd = findViewById(R.id.courseEndDate);
        editCourseEnd.setText(courseEnd);

        note = getIntent().getStringExtra("note");
        editNote = findViewById(R.id.note);
        editNote.setText(note);

        courseID = getIntent().getIntExtra("courseID", -1);
        termID = getIntent().getIntExtra("termID", -1);

        status = getIntent().getStringExtra("status");

        editStatus = findViewById(R.id.courseSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        editStatus.setAdapter(adapter);

        if (status != null) {
            int spinnerPosition = adapter.getPosition(status);
            editStatus.setSelection(spinnerPosition);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseDetail.this, AssessmentDetail.class);
                intent.putExtra("courseID", courseID);
                startActivity(intent);
            }
        });


        editNote = findViewById(R.id.note);

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editCourseStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info = editCourseStart.getText().toString();
                if (info.equals("")) info = LocalDate.now().toString();
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetail.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();

            }
        };

        editCourseEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info2 = editCourseEnd.getText().toString();
                if (info2.equals("")) info2 = LocalDate.now().toString();
                try {
                    myCalendarEnd.setTime(sdf.parse(info2));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetail.this, endDate, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, month);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();

            }
        };

        RecyclerView recyclerView = findViewById(R.id.assessmentrecycleview);
        repository = new Repository(getApplication());
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessments> filteredAssessment = new ArrayList<>();
        for (Assessments a : repository.getAllAssessments()) {
            if (a.getCourseId() == courseID) filteredAssessment.add(a);
        }
        assessmentAdapter.setmAssessments(filteredAssessment);
    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editCourseStart.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editCourseEnd.setText(sdf.format(myCalendarEnd.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        // return true;
//                Intent intent=new Intent(PartDetails.this,MainActivity.class);
//                startActivity(intent);
//                return true;

        if (item.getItemId() == R.id.coursesave) {
            Courses courses;
            if (termID == -1) {
                Toast.makeText(CourseDetail.this, "You must create a Term to associate this course with.", Toast.LENGTH_LONG).show();
            } else {
                if (courseID == -1) {
                    if (myCalendarStart.after(myCalendarEnd)) {
                        Toast.makeText(CourseDetail.this, "Start Date cannot be after End Date", Toast.LENGTH_LONG).show();
                    } else {
                        if (repository.getAllCourses().size() == 0)
                            courseID = 1;
                        else

                            courseID = repository.getAllCourses().get(repository.getAllCourses().size() - 1).getCourseId() + 1;
                        courses = new Courses(courseID, editTitle.getText().toString(), editStatus.getSelectedItem().toString(),
                                editName.getText().toString(), editPhone.getText().toString(), editEmail.getText().toString(),
                                editCourseStart.getText().toString(), editCourseEnd.getText().toString(), editNote.getText().toString(), termID);
                        repository.insert(courses);
                        Toast.makeText(CourseDetail.this, "Course Created", Toast.LENGTH_LONG).show();
                    }
                } else {
                    courses = new Courses(courseID, editTitle.getText().toString(), editStatus.getSelectedItem().toString(),
                            editName.getText().toString(), editPhone.getText().toString(), editEmail.getText().toString(),
                            editCourseStart.getText().toString(), editCourseEnd.getText().toString(), editNote.getText().toString(), termID);
                    repository.update(courses);
                    Toast.makeText(CourseDetail.this, "Course Updated.", Toast.LENGTH_LONG).show();
                }
            }
        }
        if (item.getItemId() == R.id.coursedelete) {
            for (Courses courses : repository.getAllCourses()) {
                if (courses.getCourseId() == courseID) currentCourses = courses;
            }
            repository.delete(currentCourses);
            Toast.makeText(CourseDetail.this, currentCourses.getCourseTitle() + " was deleted", Toast.LENGTH_LONG).show();
            CourseDetail.this.finish();
        }
        if (item.getItemId() == R.id.sharenote) {
            Intent sentIntent = new Intent();
            sentIntent.setAction(Intent.ACTION_SEND);
            sentIntent.putExtra(Intent.EXTRA_TEXT, editNote.getText().toString());
            sentIntent.putExtra(Intent.EXTRA_TITLE, editNote.getText().toString());
            sentIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sentIntent, null);
            startActivity(shareIntent);
            return true;
        }
        if (item.getItemId() == R.id.notifyCourseStart) {
            String dateFromScreen = editCourseStart.getText().toString();
            String myFormat = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long trigger = myDate.getTime();
            Intent intent = new Intent(CourseDetail.this, MyReceiver.class);
            intent.putExtra("key", "Course Start Date");
            PendingIntent sender = PendingIntent.getBroadcast(CourseDetail.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            Toast.makeText(CourseDetail.this, "Notification set for Course Start Date", Toast.LENGTH_LONG).show();
            return true;
        }
        if (item.getItemId() == R.id.notifyCourseEnd) {
            String dateFromScreen = editCourseEnd.getText().toString();
            String myFormat = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long trigger = myDate.getTime();
            Intent intent = new Intent(CourseDetail.this, MyReceiver.class);
            intent.putExtra("key", "Course End Date");
            PendingIntent sender = PendingIntent.getBroadcast(CourseDetail.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            Toast.makeText(CourseDetail.this, "Notification set for Course End Date", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}