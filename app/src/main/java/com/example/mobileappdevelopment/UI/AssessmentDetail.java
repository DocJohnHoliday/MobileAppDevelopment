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
import android.widget.Toast;

import com.example.mobileappdevelopment.R;
import com.example.mobileappdevelopment.database.Repository;
import com.example.mobileappdevelopment.entities.Assessments;
import com.example.mobileappdevelopment.entities.Courses;
import com.example.mobileappdevelopment.entities.Terms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AssessmentDetail extends AppCompatActivity {
    String title;
    String type;
    String assessmentStart;
    String assessmentEnd;
    int courseID;
    int assessmentID;
    EditText editTitle;
    Spinner editType;
    TextView editAssessmentStart;
    TextView editAssessmentEnd;
    Repository repository;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();

    Assessments currentAssessments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_assessment_view);
        repository = new Repository(getApplication());

        title = getIntent().getStringExtra("assessment_title");
        editTitle = findViewById(R.id.assessmentTitle);
        editTitle.setText(title);

        assessmentStart = getIntent().getStringExtra("assessment_start_date");
        editAssessmentStart = findViewById(R.id.assessmentStartDate);
        editAssessmentStart.setText(assessmentStart);

        assessmentEnd = getIntent().getStringExtra("assessment_end_date");
        editAssessmentEnd = findViewById(R.id.assessmentEndDate);
        editAssessmentEnd.setText(assessmentEnd);

        courseID = getIntent().getIntExtra("courseID", -1);
        assessmentID = getIntent().getIntExtra("assessmentID", -1);

        type = getIntent().getStringExtra("assessment_type");

        editType = findViewById(R.id.assessmentSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.assessment_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        editType.setAdapter(adapter);

        if (type != null) {
            int spinnerPosition = adapter.getPosition(type);
            editType.setSelection(spinnerPosition);
        }

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editAssessmentStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info = editAssessmentStart.getText().toString();
                if (info.equals("")) info = LocalDate.now().toString();
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetail.this, startDate, myCalendarStart
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

        editAssessmentEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info2 = editAssessmentEnd.getText().toString();
                if (info2.equals("")) info2 = LocalDate.now().toString();
                try {
                    myCalendarEnd.setTime(sdf.parse(info2));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetail.this, endDate, myCalendarEnd
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
    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editAssessmentStart.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editAssessmentEnd.setText(sdf.format(myCalendarEnd.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        if (item.getItemId() == R.id.assessmentSave) {
            Assessments assessments;
            if (courseID == -1) {
                Toast.makeText(AssessmentDetail.this, "You must create a Course to associate this course with.", Toast.LENGTH_LONG).show();
            } else {
                if (assessmentID == -1) {
                    if (myCalendarStart.after(myCalendarEnd)) {
                        Toast.makeText(AssessmentDetail.this, "Start Date cannot be after End Date", Toast.LENGTH_LONG).show();
                    } else {
                        if (repository.getAllAssessments().size() == 0)
                            assessmentID = 1;
                        else

                            assessmentID = repository.getAllAssessments().get(repository.getAllAssessments().size() - 1).getAssessmentId() + 1;
                        assessments = new Assessments(assessmentID, editTitle.getText().toString(), editType.getSelectedItem().toString(),
                                editAssessmentStart.getText().toString(), editAssessmentEnd.getText().toString(), courseID);
                        repository.insert(assessments);
                        Toast.makeText(AssessmentDetail.this, "Assessment Created", Toast.LENGTH_LONG).show();
                    }
                } else {
                    assessments = new Assessments(assessmentID, editTitle.getText().toString(), editType.getSelectedItem().toString(),
                            editAssessmentStart.getText().toString(), editAssessmentEnd.getText().toString(), courseID);
                    repository.update(assessments);
                    Toast.makeText(AssessmentDetail.this, "Assessment Updated", Toast.LENGTH_LONG).show();
                }
            }
        }
        if (item.getItemId() == R.id.assessmentDelete) {
            for (Assessments assessments : repository.getAllAssessments()) {
                if (assessments.getAssessmentId() == assessmentID) currentAssessments = assessments;
            }
            repository.delete(currentAssessments);
            Toast.makeText(AssessmentDetail.this, currentAssessments.getAssessmentTitle() + " was deleted", Toast.LENGTH_LONG).show();
            AssessmentDetail.this.finish();
        }

        if (item.getItemId() == R.id.notifyAssessmentStart) {
            String dateFromScreen = editAssessmentStart.getText().toString();
            String myFormat = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long trigger = myDate.getTime();
            Intent intent = new Intent(AssessmentDetail.this, MyReceiver.class);
            intent.putExtra("key", "Assessment Start Date");
            PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetail.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            Toast.makeText(AssessmentDetail.this, "Notification set for Assessment Start Date", Toast.LENGTH_LONG).show();
            return true;
        }

        if (item.getItemId() == R.id.notifyAssessmentEnd) {
            String dateFromScreen = editAssessmentEnd.getText().toString();
            String myFormat = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long trigger = myDate.getTime();
            Intent intent = new Intent(AssessmentDetail.this, MyReceiver.class);
            intent.putExtra("key", "Assessment End Date");
            PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetail.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            Toast.makeText(AssessmentDetail.this, "Notification set for Assessment End Date", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}