package com.gigaspaces.model;

import java.io.Serializable;

/**
 * Created by kobi on 12/10/14.
 */
public class StudentKey implements Comparable, Serializable{
    private String classs;
    private String section;
    private Integer rollid;

    public String getClasss() {
        return classs;
    }

    public void setClasss(String classs) {
        this.classs = classs;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Integer getRollid() {
        return rollid;
    }

    public void setRollid(Integer rollid) {
        this.rollid = rollid;
    }

    @Override
    public int compareTo(Object o) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;

        int retval =  classs.compareTo(((StudentKey) o).getClasss());
        if(retval != EQUAL){
            return retval;
        }

        retval =  rollid.compareTo(((StudentKey) o).getRollid());
        if(retval != EQUAL){
            return retval;
        }

        return section.compareTo(((StudentKey) o).getSection());

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentKey that = (StudentKey) o;

        if (classs != null ? !classs.equals(that.classs) : that.classs != null) return false;
        if (rollid != null ? !rollid.equals(that.rollid) : that.rollid != null) return false;
        if (section != null ? !section.equals(that.section) : that.section != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = classs != null ? classs.hashCode() : 0;
        result = 31 * result + (section != null ? section.hashCode() : 0);
        result = 31 * result + (rollid != null ? rollid.hashCode() : 0);
        return result;
    }
}
