package com.coherensolutions.rest.training.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class User {

    private String name;
    private int age;
    private String sex;
    private String zipCode;

    public User() {
    }

    public User(String name, int age, String sex, String zipCode) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.zipCode = zipCode;
    }

    public User(int age, String zipCode) {
        this.age = age;
        this.zipCode = zipCode;
    }

    public User(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    public User(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    @Override
    public boolean equals(Object obj) {
        User other = (User) obj;
        List<Boolean> booleans = new ArrayList<>();
        if (this.name != null) {
            booleans.add(this.name.equals(other.name));
        }
        if (this.age != other.age) {
            booleans.add(false);
        }
        if (this.sex != null) {
            booleans.add(this.sex.equals(other.sex));
        }
        if (this.zipCode != null) {
            booleans.add(this.zipCode.equals(other.zipCode));
        }
        return !booleans.contains(false);
    }

}