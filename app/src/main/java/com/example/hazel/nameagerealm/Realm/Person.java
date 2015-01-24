package com.example.hazel.nameagerealm.Realm;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by Hazel on 1/11/2015.
 */
public class Person extends RealmObject {
    private String name;
    private int age;

    //gets and sets the name and age of each person. sets it w/in this class
    //so it can be used in other activities
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}


