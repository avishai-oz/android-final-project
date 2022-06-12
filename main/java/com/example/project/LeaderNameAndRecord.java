package com.example.project;

public class LeaderNameAndRecord {
    private String leadingPersonName = "no one";
    private String previousPersonName = "no one";
    private int leadingRecord = 0;

    public LeaderNameAndRecord() {
    }

    public LeaderNameAndRecord(String leadingPersonName, String previousPersonName, int leadingRecord) {
        this.leadingPersonName = leadingPersonName;
        this.previousPersonName = previousPersonName;
        this.leadingRecord = leadingRecord;
    }


    public void setLeadingPersonName(String leadingPersonName) {
        this.leadingPersonName = leadingPersonName;
    }

    public void setLeadingRecord(int leadingRecord) {
        this.leadingRecord = leadingRecord;
    }

    public String getLeadingPersonName() {
        return leadingPersonName;
    }

    public int getLeadingRecord() {
        return leadingRecord;
    }

    public String getPreviousPersonName() {
        return previousPersonName;
    }

    public void setPreviousPersonName(String previousPersonName) {
        this.previousPersonName = previousPersonName;
    }

}
