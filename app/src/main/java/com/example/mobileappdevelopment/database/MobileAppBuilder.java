package com.example.mobileappdevelopment.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mobileappdevelopment.dao.AssessmentDAO;
import com.example.mobileappdevelopment.dao.CourseDAO;
import com.example.mobileappdevelopment.dao.TermDAO;
import com.example.mobileappdevelopment.entities.Assessments;
import com.example.mobileappdevelopment.entities.Courses;
import com.example.mobileappdevelopment.entities.Terms;

@Database(entities = {Assessments.class, Courses.class, Terms.class}, version = 4, exportSchema = false)
public abstract class MobileAppBuilder extends RoomDatabase {
    public abstract TermDAO termDao();

    public abstract CourseDAO courseDAO();

    public abstract AssessmentDAO assessmentDAO();

    private static volatile MobileAppBuilder INSTANCE;

    static MobileAppBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MobileAppBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MobileAppBuilder.class, "MyAppDatabase.db")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
