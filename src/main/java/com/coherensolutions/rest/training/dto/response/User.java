package com.coherensolutions.rest.training.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private String name;
    private int age;
    private String sex;
    private String zipCode;

    @Override
    public boolean equals(Object obj) {
        User other = (User) obj;
        if (!this.name.equals(other.name)) {
            return false;
        }
        if (this.age != other.age) {
            return false;
        }
        if (!this.sex.equals(other.sex)) {
            return false;
        }
        if (this.zipCode != null) {
            return this.zipCode.equals(other.zipCode);
        }
        return true;
    }

}