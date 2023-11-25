package com.example.mobileappdevelopment.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses")
public class Courses {
    @PrimaryKey(autoGenerate = true)
    private int courseId;
    private String courseTitle;

    private String status;
    private String instructorName;
    private String phoneNumber;
    private String email;
    private String courseStartDate;
    private String courseEndDate;

    public String getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(String courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public String getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(String courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    private int termId;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public Courses(int courseId, String courseTitle, String status, String instructorName,
                   String phoneNumber, String email, String courseStartDate, String courseEndDate, int termId) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.status = status;
        this.instructorName = instructorName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.termId = termId;
    }
}
