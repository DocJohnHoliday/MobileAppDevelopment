package com.example.mobileappdevelopment.UI;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.mobileappdevelopment.R;
import com.example.mobileappdevelopment.database.Repository;
import com.example.mobileappdevelopment.entities.Courses;
import com.example.mobileappdevelopment.entities.Terms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CourseDetail extends AppCompatActivity {
    String title;
    String status;
    String instrName;
    String instrPhone;
    String instrEmail;
    int courseID;
    int termID;
    EditText editTitle;
    EditText editStatus;
    EditText editName;
    EditText editPhone;
    EditText editEmail;
    EditText editNote;
    TextView editDate;
    Repository repository;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_course_view);
        repository = new Repository(getApplication());

        title = getIntent().getStringExtra("title");
        editTitle = findViewById(R.id.courseTitle);
        editTitle.setText(title);

        status = getIntent().getStringExtra("status");
        editStatus = findViewById(R.id.courseStatus);
        editStatus.setText(status);

        instrName = getIntent().getStringExtra("name");
        editName = findViewById(R.id.instructorName);
        editName.setText(instrName);

        instrPhone = getIntent().getStringExtra("phone");
        editPhone = findViewById(R.id.instructorPhone);
        editPhone.setText(instrPhone);

        instrEmail = getIntent().getStringExtra("email");
        editEmail = findViewById(R.id.instructorEmail);
        editEmail.setText(instrEmail);

        courseID = getIntent().getIntExtra("id", -1);
        termID = getIntent().getIntExtra("termID", -1);

        editNote = findViewById(R.id.note);
        editDate = findViewById(R.id.date);

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info = editDate.getText().toString();
                if (info.equals("")) info = "07/01/23";
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
        Spinner spinner = findViewById(R.id.spinner);
        ArrayList<Terms> termArrayList;

        try {
            termArrayList = new ArrayList<>(repository.getmAllTerms());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        ArrayAdapter<Terms> termAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, termArrayList);
        spinner.setAdapter(termAdapter);
        spinner.setSelection(0);
    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editDate.setText(sdf.format(myCalendarStart.getTime()));
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
            if (courseID == -1) {
                if (repository.getAllCourses().size() == 0)
                    courseID = 1;
                else
                    courseID = repository.getAllCourses().get(repository.getAllCourses().size() - 1).getCourseId() + 1;
                courses = new Courses(courseID, editTitle.getText().toString(), editStatus.getText().toString(), editName.getText().toString(),
                        editPhone.getText().toString(), editEmail.getText().toString(), termID);
                repository.insert(courses);
            } else {
                courses = new Courses(courseID, editTitle.getText().toString(), editStatus.getText().toString(), editName.getText().toString(),
                        editPhone.getText().toString(), editEmail.getText().toString(), termID);
                repository.update(courses);
            }
            return true;
        }
        if (item.getItemId() == R.id.sharenote) {
            Intent sentIntent = new Intent();
            sentIntent.setAction(Intent.ACTION_SEND);
            sentIntent.putExtra(Intent.EXTRA_TEXT, editNote.getText().toString() + "EXTRA_TEXT");
            sentIntent.putExtra(Intent.EXTRA_TITLE, editNote.getText().toString() + "EXTRA_TITLE");
            sentIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sentIntent, null);
            startActivity(shareIntent);
            return true;
        }
        if (item.getItemId() == R.id.notify) {
            String dateFromScreen = editDate.getText().toString();
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
            intent.putExtra("key", "message I want to see");
            PendingIntent sender = PendingIntent.getBroadcast(CourseDetail.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}