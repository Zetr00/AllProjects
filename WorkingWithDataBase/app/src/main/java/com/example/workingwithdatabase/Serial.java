package com.example.workingwithdatabase;

import java.io.Serializable;

public class Serial implements Serializable {
    private String serial_Name;
    private String serial_Detail;
    private String student_FIO;
    private int student_Score;

    public Serial(String serial_Name, String serial_Detail, String student_FIO, int student_Score){
        this.serial_Name=serial_Name;
        this.serial_Detail=serial_Detail;
        this.student_FIO=student_FIO;
        this.student_Score=student_Score;
    }

    public String getSerial_Name() {
        return serial_Name;
    }

    public void setSerial_Name(String serial_Name) {
        this.serial_Name = serial_Name;
    }

    public String getSerial_Detail() {
        return serial_Detail;
    }

    public void setSerial_Detail(String serial_Detail) {
        this.serial_Detail = serial_Detail;
    }

    public String getStudent_FIO() {
        return student_FIO;
    }

    public void setStudent_FIO(String student_FIO) {
        this.student_FIO = student_FIO;
    }

    public int getStudent_Score() {
        return student_Score;
    }

    public void setStudent_Score(int student_Score) {
        this.student_Score = student_Score;
    }
}
