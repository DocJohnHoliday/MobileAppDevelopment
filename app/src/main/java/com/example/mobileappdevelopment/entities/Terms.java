package com.example.mobileappdevelopment.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "terms")
public class Terms {
    @PrimaryKey(autoGenerate = true)
    private int termId;
    private String termTitle;
    private String startDate;
    private String endDate;

    public String getStartDate() {
        return startDate;
    }

    public String toString() {
        return termTitle;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getTermTitle() {
        return termTitle;
    }

    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
    }

    public Terms(int termId, String termTitle, String startDate, String endDate) {
        this.termId = termId;
        this.termTitle = termTitle;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
