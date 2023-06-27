package utils;

import com.github.javafaker.Faker;

public class RandomPopulator {

    Faker faker = new Faker();

    public String setName() {
        return faker.name().firstName();
    }

    public int setAge() {
        return faker.number().numberBetween(18, 90);
    }

    public String setSex() {
        return faker.demographic().sex().toUpperCase();
    }

    public String setZipCode() {
        return String.valueOf(faker.number().numberBetween(10001, 99999));
    }

    public String setIncorrectZipCode() {
        return String.valueOf(faker.number().numberBetween(999991, 999999));
    }

}
