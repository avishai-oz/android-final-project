package com.example.project;

import java.util.ArrayList;

public class person {
    private String name;
    private  String password;
    private int pull_ups_record;
    private int push_ups_record;
    private int parallels_record;
    private int leg_lifts_record;
    private int crunches_record;
    public person() {
    }

    public person(String name) {
        this.name = name;
        this.password="";
        this.pull_ups_record = 0;
        this.push_ups_record = 0;
        this.parallels_record = 0;
        this.leg_lifts_record = 0;
        this.crunches_record = 0;
    }
                                            //geters
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getPull_ups_record() {
        return pull_ups_record;
    }

    public int getPush_ups_record() {
        return push_ups_record;
    }

    public int getParallels_record() {
        return parallels_record;
    }

    public int getLeg_lifts_record() {
        return leg_lifts_record;
    }

    public int getCrunches_record() {
        return crunches_record;
    }
                                                                //seters
    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPull_ups_record(int pull_ups_record) {
        this.pull_ups_record = pull_ups_record;
    }

    public void setPush_ups_record(int push_ups_record) {
        this.push_ups_record = push_ups_record;
    }

    public void setParallels_record(int parallels_record) {
        this.parallels_record = parallels_record;
    }

    public void setLeg_lifts_record(int leg_lifts_record) {
        this.leg_lifts_record = leg_lifts_record;
    }

    public void setCrunches_record(int crunches_record) {
        this.crunches_record = crunches_record;
    }
}
