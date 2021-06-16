package com.example.sharedpreference;

import java.io.Serializable;

public class Classes implements Serializable {
    private String classCode;
    private String classTitle;
    private String classTime;

    public Classes(String classCode, String classTitle, String classTime) {
        this.classCode = classCode;
        this.classTitle = classTitle;
        this.classTime = classTime;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getClassTitle() {
        return classTitle;
    }

    public void setClassTitle(String classTitle) {
        this.classTitle = classTitle;
    }

    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }
}
