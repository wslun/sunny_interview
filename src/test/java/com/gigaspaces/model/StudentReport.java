package com.gigaspaces.model;

import com.gigaspaces.annotation.pojo.SpaceId;

import java.io.Serializable;

/**
 * Created by kobi on 12/10/14.
 */
public class StudentReport implements Serializable{

    private StudentKey key;
    private String grade;
    private String semester;
    private Integer classAttended;

    @SpaceId
    public StudentKey getKey() {
        return key;
    }

    public void setKey(StudentKey key) {
        this.key = key;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Integer getClassAttended() {
        return classAttended;
    }

    public void setClassAttended(Integer classAttended) {
        this.classAttended = classAttended;
    }
}
