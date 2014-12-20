package com.gigaspaces.model;

import com.gigaspaces.annotation.pojo.SpaceId;

/**
 * Created by kobi on 12/10/14.
 */
public class Student {
    private StudentKey key;
    private String name;
    private String title;

    @SpaceId
    public StudentKey getKey() {
        return key;
    }

    public void setKey(StudentKey key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
